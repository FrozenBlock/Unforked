package net.frozenblock.unforked;

import lombok.experimental.UtilityClass;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.fabricmc.loader.impl.ModContainerImpl;
import net.fabricmc.loader.impl.discovery.ModCandidateImpl;
import net.fabricmc.loader.impl.launch.FabricLauncherBase;
import net.fabricmc.loader.impl.metadata.LoaderModMetadata;
import net.frozenblock.unforked.util.IteratorCallbackList;
import org.jetbrains.annotations.ApiStatus;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

// Heavily inspired by ModsMod
@ApiStatus.Internal
@UtilityClass
public class FabricLoaderInterface {
	static void init() {}

	private static final Method ADD_MOD;
	private static final Method CREATE_PLAIN;
	private static final Field MODS;

	static {
		try {
			ADD_MOD = FabricLoaderImpl.class.getDeclaredMethod("addMod", ModCandidateImpl.class);
			ADD_MOD.setAccessible(true);

			MODS = FabricLoaderImpl.class.getDeclaredField("mods");
			MODS.setAccessible(true);

			CREATE_PLAIN = ModCandidateImpl.class.getDeclaredMethod("createPlain", List.class, LoaderModMetadata.class, boolean.class, Collection.class);
			CREATE_PLAIN.setAccessible(true);
		} catch (NoSuchMethodException | NoSuchFieldException e) {
			throw new RuntimeException(e);
		}
	}

	public static void sync(FabricLoaderImpl loader) {
		try {
			MODS.set(loader, new IteratorCallbackList<>((List<ModContainerImpl>) MODS.get(loader), modContainers -> {
				try {
					MODS.set(loader, modContainers);

					// add mods to classpath
					// TODO: Maybe find a better solution so that only the quilt mods get added
					for (ModContainerImpl mod : modContainers) {
						if (!mod.getMetadata().getId().equals(FabricLoaderImpl.MOD_ID) && !mod.getMetadata().getType().equals("builtin")) {
							for (Path path : mod.getCodeSourcePaths()) {
								FabricLauncherBase.getLauncher().addToClassPath(path);
							}
						}
					}
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}, UnforkedLoader::loadMods));
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public static void addMod(FabricLoaderImpl loader, ModCandidateImpl candidate) {
		try {
			ADD_MOD.invoke(loader, candidate);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new IllegalStateException("Cant inject the quilt mod into fabric loader bruh", e);
		}
	}

	public static ModCandidateImpl createPlain(Path path, LoaderModMetadata metadata, boolean requiresRemap, Collection<ModCandidateImpl> nestedMods) {
		try {
			return (ModCandidateImpl) CREATE_PLAIN.invoke(null, List.of(path), metadata, requiresRemap, nestedMods);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new IllegalStateException("Cant create the plain mod container bruh", e);
		}
	}
}
