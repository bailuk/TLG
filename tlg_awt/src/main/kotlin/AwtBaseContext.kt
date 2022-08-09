import ch.bailu.tlg.PlatformContext
import ch.bailu.tlg.StateRunning
import ch.bailu.tlg.TlgPoint
import ch.bailu.tlg.TlgRectangle
import lib.color.ARGB
import lib.color.ColorInterface
import lib.color.HSV
import java.awt.Color
import java.util.*

open class AwtBaseContext : PlatformContext() {
    companion object {
        private const val PALETTE_RESERVED = 5
        private const val PALETTE_SIZE = StateRunning.SHAPE_PER_LEVEL * 3 + PALETTE_RESERVED
        private const val COLOR_GRID = PALETTE_SIZE - PALETTE_RESERVED - 1
        private const val COLOR_BACKGROUND = COLOR_GRID + 1
        private const val COLOR_HIGHLIGHT = COLOR_GRID + 2
        private const val COLOR_DARK = COLOR_GRID + 3
        private const val COLOR_FRAME = COLOR_GRID + 4
        private const val COLOR_GRAYED = COLOR_GRID + 5

        private val palette = ArrayList<Color>().apply {
            val colorStep = 1.0 / StateRunning.SHAPE_PER_LEVEL
            var h = 0.0

            for (i in 0 until PALETTE_SIZE) {
                add(awtColor(HSV(h)))
                h += colorStep
                h %= 1.0
            }

            this[COLOR_GRID] = awtColor(ARGB.decode("#8309c4"))
            this[COLOR_FRAME] = awtColor(ARGB.decode("#09c4b7"))
            this[COLOR_BACKGROUND] = awtColor(ARGB.decode("#000000"))
            this[COLOR_HIGHLIGHT] = awtColor(ARGB.decode("#ffffff"))
            this[COLOR_DARK] = awtColor(ARGB.decode("#4e4f4e"))
            this[COLOR_GRAYED] = awtColor(ARGB.decode("#a3a3a3"))
        }

        private fun awtColor(color: ColorInterface): Color {
            return Color(color.red(), color.green(), color.blue(), color.alpha())
        }
    }



    fun getAwtColor(color: Int): Color {
        return palette[color]
    }

    override fun drawLine(color: Int, p1: TlgPoint, p2: TlgPoint) {}
    override fun drawFilledRectangle(color: Int, rect: TlgRectangle) {}
    override fun drawText(color: Int, rect: TlgRectangle, text: String) {}
    override fun colorBackground(): Int {
        return COLOR_BACKGROUND
    }

    override fun colorDark(): Int {
        return COLOR_DARK
    }

    override fun colorHighlight(): Int {
        return COLOR_HIGHLIGHT
    }

    override fun colorGrayed(): Int {
        return COLOR_GRAYED
    }

    override fun colorFrame(): Int {
        return COLOR_FRAME
    }

    override fun colorGrid(): Int {
        return COLOR_GRID
    }

    override fun countOfColor(): Int {
        return PALETTE_SIZE
    }

    override fun getColor(i: Int): Int {
        return i
    }

    override fun onNewHighscore() {}
}
