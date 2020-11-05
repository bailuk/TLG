package ch.bailu.tlg_awt;

import java.awt.Graphics;

import ch.bailu.tlg.TlgPoint;
import ch.bailu.tlg.TlgRectangle;

public class GraphicsContext extends BaseContext{
	private final Graphics graphics;
	
    
	public GraphicsContext(Graphics g) {
		super();
		graphics=g;
	}


	@Override
	public void drawLine(int color, TlgPoint p1, TlgPoint p2) {
		graphics.setColor(getAwtColor(color));
		graphics.drawLine(p1.x, p1.y, p2.x, p2.y);
	}

	@Override
	public void drawFilledRectangle(int color, TlgRectangle rect) {
		graphics.setColor(getAwtColor(color));
		graphics.fillRect(rect.left, rect.top, rect.getWidth(), rect.getHeight());
		
	}

	@Override
	public void drawText(int color, TlgRectangle rect, String text) {
		graphics.setColor(getAwtColor(color));
		graphics.drawString(text, rect.left, rect.top+rect.getHeight()/2);
	}
}
