package net.frozenblock.unforked.util

import net.fabricmc.loader.impl.util.FileSystemUtil
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import java.nio.file.FileSystem
import java.nio.file.Path
import kotlin.jvm.Throws

// based on ModMeta from ModsMod
class QuiltMod {
    @JvmField
    val rootPath: Path
    @JvmField
    val qmj: Path
    @JvmField
    val url: URL

    @Throws(IOException::class)
    constructor(file: Path) : this(FileSystemUtil.getJarFileSystem(file, false).get(), file)

    @Throws(MalformedURLException::class)
    constructor(fs: FileSystem, file: Path) {
        this.rootPath = fs.getPath("")
        this.qmj = getQmj(fs)
        this.url = file.toUri().toURL()
    }

    fun getQmj(fs: FileSystem): Path = fs.getPath("quilt.mod.json")
}
