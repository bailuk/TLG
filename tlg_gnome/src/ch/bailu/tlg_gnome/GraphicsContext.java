package ch.bailu.tlg_gnome;

import org.freedesktop.cairo.Antialias;
import org.freedesktop.cairo.Context;
import org.gnome.pango.Layout;

import ch.bailu.tlg.TlgPoint;
import ch.bailu.tlg.TlgRectangle;

public class GraphicsContext extends BaseContext {

	private final Context context;
	
	public GraphicsContext(Context c) {
		context = c;
		context.setLineWidth(1);
		context.setAntialias(Antialias.NONE);
	}

	@Override
	public void drawLine(int color, TlgPoint p1, TlgPoint p2) {
		context.setSource(getGtkColor(color));
		
		context.moveTo(p1.x, p1.y);
		context.lineTo(p2.x, p2.y);
		context.stroke();
	}

	@Override
	public void drawFilledRectangle(int color, TlgRectangle rect) {
		context.setSource(getGtkColor(color));
		context.rectangle(rect.left-1, rect.top-1, rect.getWidth(), rect.getHeight());
		context.fill();
	}

	@Override
	public void drawText(int color, TlgRectangle rect, String text) {
        Layout pango = new Layout(context);
        pango.setText(text);
        pango.setWidth(rect.getWidth());
        
        context.setSource(getGtkColor(this.colorHighlight()));
        context.moveTo(rect.left, rect.top);
        context.showLayout(pango);
	}

	
}
