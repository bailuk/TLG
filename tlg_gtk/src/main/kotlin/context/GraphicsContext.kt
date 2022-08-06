package context

import ch.bailu.gtk.cairo.Antialias
import ch.bailu.gtk.cairo.Context
import ch.bailu.gtk.gdk.Gdk
import ch.bailu.gtk.gtk.Label
import ch.bailu.gtk.type.Str
import ch.bailu.tlg.TlgPoint
import ch.bailu.tlg.TlgRectangle

class GraphicsContext(private val context: Context, private val score: Label): BaseContext() {
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

    override fun drawText(color: Int, rect: TlgRectangle, text: String) {
        score.label = Str(text)

        /*ch.bailu.gtk.pango.Context context = new ch.bailu.gtk.pango.Context(this.context.toLong());
        Layout pango = new Layout(context);
        pango.setText(text, text.length()-1);
        pango.setWidth(rect.getWidth());

        Gdk.cairoSetSourceRgba(this.context, getGtkColor(this.colorHighlight()));
        this.context.moveTo(rect.left, rect.top);

        Pangocairo.showLayout(this.context, pango);*/
    }

    init {
        context.setLineWidth(1.0)
        context.setAntialias(Antialias.NONE)
    }
}
