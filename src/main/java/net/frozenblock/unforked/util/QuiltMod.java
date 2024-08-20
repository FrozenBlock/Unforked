package net.frozenblock.unforked.util;

import net.fabricmc.loader.impl.util.FileSystemUtil;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.Path;

// Based on ModMeta from ModsMod
public class QuiltMod {

	public final Path rootPath;
	public final Path qmj;
	public final URL url;

	public QuiltMod(Path file) throws IOException {
		this(FileSystemUtil.getJarFileSystem(file, true).get(), file);
	}

	public QuiltMod(FileSystem fs, Path file) throws MalformedURLException {
		this.rootPath = fs.getPath("");
		this.qmj = getQmj(fs);
		this.url = file.toUri().toURL();
	}

	public static Path getQmj(FileSystem fs) {
		return fs.getPath("quilt.mod.json");
	}
}
