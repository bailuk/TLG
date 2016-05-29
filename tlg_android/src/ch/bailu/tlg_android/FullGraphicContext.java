package ch.bailu.tlg_android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import ch.bailu.tlg.TlgPoint;
import ch.bailu.tlg.TlgRectangle;

public class FullGraphicContext extends AndroidContext {

    SurfaceHolder surfaceHolder;
    Canvas        canvas=null;
    Paint paint = new Paint();
    
    public FullGraphicContext(Context c, SurfaceHolder holder) {
        super(c);
        surfaceHolder=holder;
        paint.setStrokeWidth(0);
        paint.setStrokeCap(Cap.BUTT);
        paint.setStyle(Style.FILL);
    }

    @Override
    public void setDirtyRect(TlgRectangle rect) {
        unlockCanvas();
        canvas=surfaceHolder.lockCanvas(createAndroidRect(rect));
    }
    
    public void unlockCanvas() {
        if (canvas!=null) {
            surfaceHolder.unlockCanvasAndPost(canvas);
            canvas=null;
        }
    }
    
    @Override
    public void drawLine(int c, TlgPoint p1, TlgPoint p2) {
        if (canvas != null) {
            paint.setColor(c);
            canvas.drawLine(p1.x, p1.y, p2.x, p2.y, paint);
        }
    }

    @Override
    public void drawFilledRectangle(int color, TlgRectangle r) {
        if (canvas != null) {
            paint.setColor(color);
            canvas.drawRect(createAndroidRect(r), paint);
            //drawText(colorBackground(),r, "T");
        }
    }

    private Rect createAndroidRect(TlgRectangle r) {
        return new Rect(r.left,r.top,r.right+1,r.bottom+1);
    }
    
    @Override
    public void drawText(int color, TlgRectangle rect, String text) {
        if (canvas != null) {
            paint.setColor(Color.WHITE);
            paint.setTextSize(rect.getHeight()-2);
            paint.setAntiAlias(true);
            canvas.drawText(text, rect.left, rect.bottom-3, paint);
            paint.setAntiAlias(false);
        }
    }

	@Override
	public void onNewHighscore() {
	}
}
