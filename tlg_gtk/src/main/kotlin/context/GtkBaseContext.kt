package context

import tlg.Configuration.SHAPE_PER_LEVEL
import ch.bailu.gtk.gdk.RGBA
import tlg.context.InternalContext
import tlg.context.PlatformContext
import tlg.geometry.TlgPoint
import tlg.geometry.TlgRectangle
import tlg.lib.color.ARGB
import tlg.lib.color.ColorInterface
import tlg.lib.color.HSV

open class GtkBaseContext(private val cbStatusUpdated: (iContext: InternalContext)->Unit = {}) : PlatformContext() {
    companion object {
        private const val PALETTE_RESERVED = 5
        private const val PALETTE_SIZE = SHAPE_PER_LEVEL * 3 + PALETTE_RESERVED
        private const val COLOR_GRID = PALETTE_SIZE - PALETTE_RESERVED - 1
        private const val COLOR_BACKGROUND = COLOR_GRID + 1
        private const val COLOR_HIGHLIGHT = COLOR_GRID + 2
        private const val COLOR_DARK = COLOR_GRID + 3
        private const val COLOR_FRAME = COLOR_GRID + 4
        private const val COLOR_GRAYED = COLOR_GRID + 5

        private val palette = ArrayList<RGBA>().apply {
            val colorStep = 1.0 / SHAPE_PER_LEVEL
            var h = colorStep

            for (i in 0 until PALETTE_SIZE) {
                add(gtkColor(HSV(h)))
                h += colorStep
                h %= 1.0
            }

            this[COLOR_GRID] = gtkColor(ARGB.decode("#5d006e"))
            this[COLOR_FRAME] = gtkColor(ARGB.decode("#5d006e"))
            this[COLOR_BACKGROUND] = gtkColor(ARGB.decode("#000000"))
            this[COLOR_HIGHLIGHT] = gtkColor(ARGB.decode("#ffffff"))
            this[COLOR_DARK] = gtkColor(ARGB.decode("#4e4f4e"))
            this[COLOR_GRAYED] = gtkColor(ARGB.decode("#a3a3a3"))
        }

        private fun gtkColor(color: ColorInterface): RGBA {
            return RGBA().apply {
                fieldRed = (color.red() / 255.0).toFloat()
                fieldGreen = (color.green() / 255.0).toFloat()
                fieldBlue = (color.blue() / 255.0).toFloat()
                fieldAlpha = (color.alpha() / 255.0).toFloat()
            }
        }
    }

    fun getGtkColor(color: Int): RGBA {
        return palette[color]
    }

    override fun drawLine(color: Int, p1: TlgPoint, p2: TlgPoint) {}
    override fun drawFilledRectangle(color: Int, rect: TlgRectangle) {}
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

    override fun onStatusUpdated(iContext: InternalContext) {
        cbStatusUpdated(iContext)
    }
}
