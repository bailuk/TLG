package ch.bailu.tlg;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;


public class Square {
    private final static int BACKGROUND=0;
    private final static int COLOR=1;
    private final static int GREYED=2;
    private int mode=BACKGROUND;
    private int color=0;

    public TlgRectangle rect = new TlgRectangle(0,0,0,0);
    
    public Square () {}
    public Square (Square s) {
        set(s);
    }

    public Square(BufferedInputStream reader) throws IOException {
        color = ByteInteger.read(reader);
        mode=reader.read();
    }

    public void writeState(BufferedOutputStream output) throws IOException {
        ByteInteger.wrap(color).writeState(output);
        output.write(mode);
    }
    
    public boolean isVisible() {
        return (mode != BACKGROUND);
    }
    public boolean isInvisible() {
        return !isVisible();
    }

    public void set(Square s) {
        color=s.color;
        mode=s.mode;
    }

    public void setColor(int c) {
        color = c;
    }
    
    public boolean isActivated () {
        return isVisible(); 
    }
    
    public void makeVisible() {
        mode = COLOR;
    }
    
    public void makeInvisible() {
        mode = BACKGROUND;
    }

    public boolean isGreyedOut() {
        return (mode == GREYED);
    }
    
    public void enableGreyedOut() {
        if (isInvisible()) mode = GREYED;
    }
    
    public void disableGreyedOut() {
        if (isGreyedOut()) makeInvisible();
    }

    private void drawGrid(PlatformContext gc) {
        gc.drawFilledRectangle(gc.colorBackground(), rect);
        gc.drawLine(gc.colorGrid(), rect.getTL(), rect.getTR()); 
        gc.drawLine(gc.colorGrid(), rect.getTL(), rect.getBL());
    }
    private void draw3D (PlatformContext gc, int c) {
        TlgRectangle fillRect=new TlgRectangle(rect);
        fillRect.shrink(1);
        gc.drawFilledRectangle(c, fillRect);
        
        gc.drawLine(gc.colorDark(), rect.getTL(), rect.getTR());
        gc.drawLine(gc.colorDark(), rect.getTR(), rect.getBR()); 
        gc.drawLine(gc.colorHighlight(), rect.getBR(), rect.getBL()); 
        gc.drawLine(gc.colorHighlight(), rect.getBL(), rect.getTL());
    }
    
    private void draw2D (PlatformContext gc,  int c) {
        gc.drawFilledRectangle(c,rect);
    }
    

    public void update (PlatformContext gc, boolean drawGrid) {
        switch (mode) {
        case COLOR:
            draw3D(gc, color);
            break;
                
        case GREYED:
            draw3D(gc, gc.colorGrayed());
            break;
                
        case BACKGROUND:
            if (drawGrid) drawGrid(gc);
            else draw2D(gc, gc.colorBackground());
            break;
        }
    }
}
