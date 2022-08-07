package ch.bailu.tlg_android.context

import android.content.Context
import android.graphics.Color
import ch.bailu.tlg.PlatformContext
import java.io.File

abstract class AndroidContext(private val context: Context) : PlatformContext() {
    companion object {
        private val palette = ArrayList<Int>().apply {
            val colorStep = 360 / 8
            var h = 0f
            for (i in indices) {
                add(Color.HSVToColor(floatArrayOf(h, 1f, 1f)))
                h += colorStep
                h %= 360
            }
        }
    }

    fun getAndroidContext(): Context {
        return context
    }

    abstract fun unlockCanvas()

    override fun colorBackground(): Int {
        return Color.BLACK
    }

    override fun countOfColor(): Int {
        return palette.size
    }

    override fun colorDark(): Int {
        return Color.DKGRAY
    }

    override fun getColor(i: Int): Int {
        return palette[i]
    }

    override fun colorGrayed(): Int {
        return Color.GRAY
    }

    override fun colorHighlight(): Int {
        return Color.LTGRAY
    }

    override fun colorFrame(): Int {
        return Color.rgb(44, 109, 205)
    }


    override fun colorGrid(): Int {
        return Color.rgb(44, 57, 77)
    }

    override fun getConfigDirectory(): File {
        return context.filesDir
    }
}
