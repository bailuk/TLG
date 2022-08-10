package ch.bailu.tlg_android.context

import android.content.Context
import android.graphics.Color
import ch.bailu.tlg_android.Configuration
import tlg.Configuration.SHAPE_PER_LEVEL
import tlg.context.InternalContext
import tlg.context.PlatformContext
import tlg.geometry.TlgPoint
import tlg.geometry.TlgRectangle
import tlg.lib.color.ColorInterface
import tlg.lib.color.HSV
import java.io.File

open class AndroidBaseContext(val androidContext: Context, val cbUpdateStatus: (iContext: InternalContext) -> Unit = {}) : PlatformContext() {
    companion object {
        private const val PALETTE_SIZE = SHAPE_PER_LEVEL * 3

        private val palette = java.util.ArrayList<Int>().apply {
            val colorStep = 1.0 / SHAPE_PER_LEVEL
            var h = 0.0

            for (i in 0 until PALETTE_SIZE) {
                add(androidColor(HSV(h)))
                h += colorStep
                h %= 1.0
            }
        }

        private fun androidColor(color: ColorInterface): Int {
            return color.toInt()
        }
    }

    override fun drawLine(color: Int, p1: TlgPoint, p2: TlgPoint) {}
    override fun drawFilledRectangle(color: Int, rect: TlgRectangle) {}
    open fun unlockCanvas() {}

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
        return Configuration.frameColor
    }


    override fun colorGrid(): Int {
        return Configuration.gridColor
    }

    override val configDirectory
        get() = run {
            val file: File? = androidContext.filesDir
            if (file is File) {
                file
            } else {
                super.configDirectory
            }
        }

    override fun onStatusUpdated(iContext: InternalContext) {
        cbUpdateStatus(iContext)
    }
}
