


package ch.bailu.tlg_android;

import android.content.Context;
import ch.bailu.tlg.TlgPoint;
import ch.bailu.tlg.TlgRectangle;

public class NoDrawContext extends AndroidContext {

	public NoDrawContext(Context c) {
		super(c);
	}

	
	@Override
	public void drawLine(int color, TlgPoint p1, TlgPoint p2) {
	}

	@Override
	public void drawFilledRectangle(int color, TlgRectangle rect) {
	}

	@Override
	public void drawText(int color, TlgRectangle rect, String text) {
	}


	@Override
	public void unlockCanvas() {
	}


	@Override
	public void onNewHighscore() {
		
	}
}
