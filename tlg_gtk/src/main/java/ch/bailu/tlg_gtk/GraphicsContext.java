package ch.bailu.tlg_gtk;


import ch.bailu.gtk.cairo.Antialias;
import ch.bailu.gtk.cairo.Context;
import ch.bailu.gtk.gdk.Gdk;
import ch.bailu.gtk.gtk.Label;
import ch.bailu.gtk.type.Str;
import ch.bailu.tlg.TlgPoint;
import ch.bailu.tlg.TlgRectangle;

public class GraphicsContext extends BaseContext {

    private final Context context;
    private final Label score;

    public GraphicsContext(Context c, Label l) {
        score = l;
        context = c;
        context.setLineWidth(1);
        context.setAntialias(Antialias.NONE);
    }

    @Override
    public void drawLine(int color, TlgPoint p1, TlgPoint p2) {
        Gdk.cairoSetSourceRgba(context, getGtkColor(color));

        context.moveTo(p1.x, p1.y);
        context.lineTo(p2.x, p2.y);
        context.stroke();
    }

    @Override
    public void drawFilledRectangle(int color, TlgRectangle rect) {
        Gdk.cairoSetSourceRgba(context, getGtkColor(color));

        context.rectangle(rect.left-1, rect.top-1, rect.getWidth(), rect.getHeight());
        context.fill();
    }

    @Override
    public void drawText(int color, TlgRectangle rect, String text) {
        score.setLabel(new Str(text));

        /*ch.bailu.gtk.pango.Context context = new ch.bailu.gtk.pango.Context(this.context.toLong());
        Layout pango = new Layout(context);
        pango.setText(text, text.length()-1);
        pango.setWidth(rect.getWidth());

        Gdk.cairoSetSourceRgba(this.context, getGtkColor(this.colorHighlight()));
        this.context.moveTo(rect.left, rect.top);

        Pangocairo.showLayout(this.context, pango);*/
    }

}
