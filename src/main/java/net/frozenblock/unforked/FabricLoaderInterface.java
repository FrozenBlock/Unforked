package net.frozenblock.unforked;

import lombok.experimental.UtilityClass;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.fabricmc.loader.impl.discovery.ModCandidate;
import net.fabricmc.loader.impl.metadata.LoaderModMetadata;
import org.jetbrains.annotations.ApiStatus;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

// Inspired by ModsMod
@ApiStatus.Internal
@UtilityClass
public class FabricLoaderInterface {
	static void init() {}

	private static final Method ADD_MOD;
	private static final Method CREATE_PLAIN;
	private static final Field MODS;

	static {
		try {
			ADD_MOD = FabricLoaderImpl.class.getDeclaredMethod("addMod", ModCandidate.class);
			ADD_MOD.setAccessible(true);

			MODS = FabricLoaderImpl.class.getDeclaredField("mods");
			MODS.setAccessible(true);

			CREATE_PLAIN = ModCandidate.class.getDeclaredMethod("createPlain", List.class, LoaderModMetadata.class, boolean.class, Collection.class);
			CREATE_PLAIN.setAccessible(true);
		} catch (NoSuchMethodException | NoSuchFieldException e) {
			throw new RuntimeException(e);
		}
	}
}
