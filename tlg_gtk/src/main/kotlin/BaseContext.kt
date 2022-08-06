import ch.bailu.gtk.gdk.RGBA
import ch.bailu.tlg.PlatformContext
import ch.bailu.tlg.StateRunning
import ch.bailu.tlg.TlgPoint
import ch.bailu.tlg.TlgRectangle

open class BaseContext : PlatformContext() {
    fun getGtkColor(color: Int): RGBA {
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

    companion object {
        private const val PALETTE_RESERVED = 5
        private const val PALETTE_SIZE = StateRunning.SHAPE_PER_LEVEL * 3 + PALETTE_RESERVED
        private const val COLOR_GRID = PALETTE_SIZE - PALETTE_RESERVED - 1
        private const val COLOR_BACKGROUND = COLOR_GRID + 1
        private const val COLOR_HIGHLIGHT = COLOR_GRID + 2
        private const val COLOR_DARK = COLOR_GRID + 3
        private const val COLOR_FRAME = COLOR_GRID + 4
        private const val COLOR_GRAYED = COLOR_GRID + 5
        private val palette = ArrayList<RGBA>().apply {
            var h = 0f
            for (i in 0 until PALETTE_SIZE - PALETTE_RESERVED) {
                val rgb = ColorHelper.HSVtoRGB(h, 1f, 1f)
                add(newColor(rgb[0], rgb[1], rgb[2]))
                h++
                h %= 6f
            }
            val x = 1f / 256f
            add(newColor(x * 44, x * 67, x * 77))
            add(newColor(x * 44, x * 109, x * 205))
            add(newColor(x * 10, x * 10, x * 10))
            add(newColor(1f, 1f, 1f))
            add(newColor(0f, 0f, 0f))
            add(newColor(x * 208, x * 208, x * 208))
        }

        private fun newColor(r: Float, g: Float, b: Float): RGBA {
            val result = RGBA()
            result.fieldRed = r
            result.fieldGreen = g
            result.fieldBlue = b
            result.fieldAlpha = 1f
            return result
        }

    }

}
