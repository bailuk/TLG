import ch.bailu.tlg.PlatformContext
import ch.bailu.tlg.StateRunning
import ch.bailu.tlg.TlgPoint
import ch.bailu.tlg.TlgRectangle
import java.awt.Color
import java.util.*

open class BaseContext : PlatformContext() {
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
            val colorStep = 1f / StateRunning.SHAPE_PER_LEVEL
            var h = 0f
            for (i in 0 until PALETTE_SIZE) {
                add(Color.getHSBColor(h, 1f, 1f))
                h += colorStep
            }
            this[COLOR_GRID] = Color(44, 67, 77)
            this[COLOR_FRAME] = Color(44, 109, 205)
            this[COLOR_BACKGROUND] = Color.BLACK
            this[COLOR_HIGHLIGHT] = Color.LIGHT_GRAY
            this[COLOR_DARK] = Color.DARK_GRAY
            this[COLOR_GRAYED] = Color.GRAY
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
