package net.frozenblock.unforked;

import lombok.experimental.UtilityClass;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.fabricmc.loader.impl.ModContainerImpl;
import net.fabricmc.loader.impl.launch.FabricLauncherBase;
import net.fabricmc.loader.impl.metadata.DependencyOverrides;
import net.fabricmc.loader.impl.metadata.LoaderModMetadata;
import net.fabricmc.loader.impl.metadata.ModMetadataParser;
import net.fabricmc.loader.impl.metadata.ParseMetadataException;
import net.fabricmc.loader.impl.metadata.VersionOverrides;
import net.frozenblock.unforked.util.QuiltMod;
import org.jetbrains.annotations.ApiStatus;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@ApiStatus.Internal
@UtilityClass
public class UnforkedLoader {

	private static final Set<QuiltMod> MODS = new HashSet<>();
	private static final FabricLoaderImpl LOADER = FabricLoaderImpl.INSTANCE;

	void init() {
		System.out.println("This is working");

		File modsDir = new File("mods/quilt"); // TODO: Scan for quilt exclusive mods in mods dir

		try {
			Files.createDirectories(modsDir.toPath());
		} catch (IOException e) {
			System.out.println("bruh it cant create the dir");
		}

		for (File file : modsDir.listFiles()) {
			System.out.println("adding " + file.getPath());
			try {
				MODS.add(new QuiltMod(file.toPath()));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		System.out.println("Adding quilt mods");
		FabricLoaderInterface.sync(LOADER);
		System.out.println("Hopefully added quilt mods");
	}

	public static void loadMods() {
		for (QuiltMod mod : MODS) {
			try {
				LoaderModMetadata info = ModMetadataParser.parseMetadata(Files.newInputStream(mod.qmj),
					mod.url.toString(),
					new ArrayList<>(),
					new VersionOverrides(),
					new DependencyOverrides(FabricLoader.getInstance().getConfigDir()),
					false
				);
				System.out.println(info.getAccessWidener());
				FabricLoaderInterface.addMod(LOADER, FabricLoaderInterface.createPlain(mod.rootPath, info, true, new HashSet<>()));
			} catch (IOException | ParseMetadataException e) {
				throw new IllegalStateException(e);
			}
		}
	}
}
