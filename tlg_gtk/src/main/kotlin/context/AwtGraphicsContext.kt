package context

import ch.bailu.gtk.cairo.Antialias
import ch.bailu.gtk.cairo.Context
import ch.bailu.gtk.gdk.Gdk
import tlg.geometry.TlgPoint
import tlg.geometry.TlgRectangle

class AwtGraphicsContext(private val context: Context): AwtBaseContext() {
    override fun drawLine(color: Int, p1: TlgPoint, p2: TlgPoint) {
        Gdk.cairoSetSourceRgba(context, getGtkColor(color))
        context.moveTo(p1.x.toDouble(), p1.y.toDouble())
        context.lineTo(p2.x.toDouble(), p2.y.toDouble())
        context.stroke()
    }

    override fun drawFilledRectangle(color: Int, rect: TlgRectangle) {
        Gdk.cairoSetSourceRgba(context, getGtkColor(color))
        context.rectangle(
            (rect.left - 1).toDouble(),
            (rect.top - 1).toDouble(),
            rect.width.toDouble(),
            rect.height.toDouble()
        )
        context.fill()
    }

    init {
        context.setLineWidth(1.0)
        context.setAntialias(Antialias.NONE)
    }
}
