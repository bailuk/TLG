package ch.bailu.tlg;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

public class MatrixVisible extends Matrix {

    private TlgRectangle dirtyArea = new TlgRectangle(0,0,0,0);
    private boolean drawGrid=false;

    public MatrixVisible(int w, int h) {
        super(w, h);
        markAllAsDirty();
    }

    public MatrixVisible(Matrix m) {
        super(m);
        markAllAsDirty();
    }
    
    public MatrixVisible(BufferedInputStream reader) throws IOException {
        super(reader);
        if (reader.read() > 0) drawGrid=true;
    }
    
    public void writeState(BufferedOutputStream writer) throws IOException {
        super.writeState(writer);
        if (drawGrid) writer.write(1);
        else writer.write(0);
    }

    public void erase() {
        for (int i=0; i<count(); i++) {
            get(i).makeInvisible();
        }
        markAllAsDirty();
    }
    
    protected void markAllAsDirty() {
        dirtyArea.left=0;
        dirtyArea.top=0;
        dirtyArea.setWidth(getWidth());
        dirtyArea.setHeight(getHeight());
    }

    protected void markAllAsClean() {
        dirtyArea.left=getWidth()-1;
        dirtyArea.top=getHeight()-1;
        dirtyArea.bottom=0; //setWidth(0);
        dirtyArea.right=0; //setHeight(0);
    }
    
    
    protected Square getD(int x, int y) {
        markSquareAsDirty(x,y);
        return get(x,y);
    }
    
    private void markSquareAsDirty(int x, int y) {
        if (x < dirtyArea.left)  dirtyArea.left = x;
        if (y < dirtyArea.top)  dirtyArea.top = y;
     
        if (x > dirtyArea.right)  dirtyArea.right  = x;
        if (y > dirtyArea.bottom) dirtyArea.bottom = y;
    }
    
    void  setPixelGeometry(TlgRectangle rect) {
        int   xoffset,yoffset,xpos,ypos;

        int squareSize = rect.getWidth() / getWidth();
        if (squareSize > rect.getHeight() / getHeight()) squareSize = rect.getHeight() / getHeight();

        // center
        xoffset = rect.left + (rect.getWidth()  - (squareSize * getWidth()) ) / 2;  
        yoffset = rect.top + (rect.getHeight() - (squareSize * getHeight()) ) / 2;  

        xpos=xoffset;
        for (int x = 0; x < getWidth(); x++) {

            ypos=yoffset;
            for (int y = 0; y < getHeight(); y++) {
                
                get(x,y).rect = new TlgRectangle(xpos, ypos, xpos+squareSize-1, ypos+squareSize-1);
                ypos+=squareSize;
            }
            xpos+=squareSize;
        }
        markAllAsDirty();
    }
    

    public void toggleGrid() {
        drawGrid = !drawGrid;
        markAllAsDirty();
    }
    
    public TlgRectangle getPixelGeometry() {
        return getPixelArea(new TlgRectangle(0,0,getWidth()-1,getHeight()-1));
    }
    
    private TlgRectangle getDirtyPixelArea() {
        return getPixelArea(dirtyArea);
    }
    
    private TlgRectangle getPixelArea(TlgRectangle rect) {
        TlgRectangle ret= new TlgRectangle(0,0,0,0);
        
        if (rect.getWidth() > 0 && rect.getHeight() > 0) {
            Square tl=get(rect.left,rect.top);
            Square br=get(rect.right, rect.bottom);
            
            ret.left=tl.rect.left;
            ret.top=tl.rect.top;
            ret.right=br.rect.right;
            ret.bottom=br.rect.bottom;
        }
        return ret;
    }
    
    private boolean isDirty() {
        return dirtyArea.getWidth() > 0 && dirtyArea.getHeight() > 0;
    }
    
    public void update(PlatformContext gc) {
        if (isDirty()) {
            gc.setDirtyRect(getDirtyPixelArea());
        
            for (int x=dirtyArea.left; x <= dirtyArea.right; x++) { 
                for (int y=dirtyArea.top; y <= dirtyArea.bottom; y++) {
                    get(x,y).update(gc, drawGrid);
                }
            }
        }
        markAllAsClean();
    }
    
    public void updateAll(PlatformContext gc) {
        TlgRectangle r=getPixelGeometry();
        r.grow(1);
        gc.drawRectangle(gc.colorFrame(), r);
        
        markAllAsDirty();
        update(gc);
    }
}
