package tlg.context

import tlg.geometry.TlgPoint
import tlg.geometry.TlgRectangle
import java.io.*

abstract class PlatformContext {
    fun drawRectangle(c: Int, r: TlgRectangle) {
        drawLine(c, r.tL, r.tR)
        drawLine(c, r.tR, r.bR)
        drawLine(c, r.bR, r.bL)
        drawLine(c, r.bL, r.tL)
    }

    abstract fun drawLine(color: Int, p1: TlgPoint, p2: TlgPoint)
    abstract fun drawFilledRectangle(color: Int, rect: TlgRectangle)

    abstract fun colorBackground(): Int
    abstract fun colorDark(): Int
    abstract fun colorHighlight(): Int
    abstract fun colorGrayed(): Int
    abstract fun colorFrame(): Int
    abstract fun colorGrid(): Int
    abstract fun countOfColor(): Int
    abstract fun getColor(i: Int): Int

    //////
    open val configDirectory: File
        get() = File(System.getProperty("user.home"), ".config/tlg")

    @Throws(FileNotFoundException::class)
    fun getInputStream(fileName: String): BufferedInputStream {
        val file = File(configDirectory, fileName)
        return BufferedInputStream(FileInputStream(file))
    }

    @Throws(FileNotFoundException::class)
    fun getOutputStream(fileName: String): BufferedOutputStream {
        val directory = configDirectory
        directory.mkdirs()
        val file = File(directory, fileName)
        return BufferedOutputStream(FileOutputStream(file))
    }

    open fun setDirtyRect(rect: TlgRectangle) {

    }
}
