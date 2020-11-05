package ch.bailu.tlg_swt;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

import ch.bailu.tlg.PlatformContext;
import ch.bailu.tlg.TlgPoint;
import ch.bailu.tlg.TlgRectangle;


public class Context extends PlatformContext {
    private GC gc;
    private Display display;
    
    public Context (Display display, GC gc)
    {
        this.gc = gc;
        this.display = display;
    }
    
    public final int colorBackground() {
        return SWT.COLOR_BLACK; 
    }

    public final int colorDark() {
        return SWT.COLOR_BLACK; 
    }
    
    public final int colorHighlight() {
        return SWT.COLOR_GRAY; 
    }
    
    public final int colorGrayed() {
        return SWT.COLOR_DARK_GRAY; 
    }
    
    public int countOfColor() {return 14;};
    public int getColor(int i) {
        // return one of the 14 SWT.COLOR_ values
        return ((i % countOfColor()) + SWT.COLOR_RED); // starts with red 
    }

    @Override
    public int colorFrame() {
        return colorGrayed();
    }

    @Override
    public int colorGrid() {
        return colorFrame();
    }


    @Override
    public void drawFilledRectangle(int color, TlgRectangle r) {
        gc.setBackground(display.getSystemColor(color));
        gc.setLineWidth(0);
        gc.fillRectangle(new Rectangle(r.left,r.top,r.getWidth(),r.getHeight()));
    }
        
        
    
    @Override
    public void drawLine(int color, TlgPoint p1, TlgPoint p2) {
        gc.setForeground(display.getSystemColor(color));
        gc.setLineWidth(1);
        gc.drawLine(p1.x, p1.y, p2.x, p2.y);
    }

    @Override
    public void drawText(int color, TlgRectangle rect, String text) {
        gc.setForeground(display.getSystemColor(color));
        gc.drawText(text, rect.left, rect.top);
    }

    @Override
    public File getConfigDirectory() {
        return new File(System.getProperty("user.home"), ".tlg");
    }

    @Override
    public void onNewHighscore() {
    
    }

}
