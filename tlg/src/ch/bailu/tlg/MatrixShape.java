package ch.bailu.tlg;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

public class MatrixShape extends Matrix {
    private final static int SHAPE_SIZE=5;
    
    private TlgPoint offset;
    
    public MatrixShape (MatrixShape s) {
        super(s);
        offset=new TlgPoint(s.offset);
    }

    public MatrixShape () {
        super(SHAPE_SIZE, SHAPE_SIZE);
        offset=new TlgPoint();
    }

    public MatrixShape(BufferedInputStream reader) throws IOException {
        super(reader);
        offset = new TlgPoint(reader);
    }
    
    public void writeState(BufferedOutputStream writer) throws IOException {
        super.writeState(writer);
        offset.writeState(writer);
    }
    
    
    public TlgPoint getPos() {return new TlgPoint(offset);}
    public void  setPos(TlgPoint p) {offset=new TlgPoint(p); }
    
    public void setX(int x) {
        offset.x=x;
    }
    public int getX() {
        return offset.x;
    }
    
    public int getY() {
        return offset.y;
    }
    
    public MatrixShape getRotatedCopy(int direction) {
        MatrixShape copy;
        
        if (direction==0) {
            copy=new MatrixShape(this);
            
        } else {
            copy = new MatrixShape();
            copy.copyAndRotateMatrix(this,direction);
            copy.adjustOffset(this);
            
        }
        return copy;
    }
    
    private void copyAndRotateMatrix(MatrixShape source, int direction) {
        int size=getSize();
        int max=size-1;
        
        if (direction==1) {
            for (int x = 0; x < size; x++)
                for (int y = 0; y < size; y++)
                    get(x,y).set( source.get(y,max-x));
        } else if (direction == 2) {
            for (int x = 0; x < size; x++)
                for (int y = 0; y < size; y++)
                    get(x,y).set( source.get(max-x,max-y));
        } else {
            for (int x = 0; x < size; x++)
                for (int y = 0; y < size; y++)
                    get(x,y).set( source.get(max-y,x));
        }
    }
    
    private void adjustOffset(MatrixShape source) {
        TlgRectangle sourceRect = source.getActiveArea();
        TlgRectangle destRect = getActiveArea();
        
        offset=new TlgPoint(source.offset);
        offset.x+=sourceRect.left - destRect.left;
        offset.y+=sourceRect.top - destRect.top;
        offset.x+=(sourceRect.getWidth()-destRect.getWidth())/2;
        offset.y+=(sourceRect.getHeight()-destRect.getHeight())/2;
    }
    
    public void setColor(int c) {
        for (int i = 0; i < count(); i++)
            get(i).setColor(c);
    }
    
    
    public TlgRectangle getActiveArea() {
        TlgRectangle r = new TlgRectangle(getWidth()-1,getHeight()-1,0,0);

        for (int x = 0; x < getWidth(); x++) {
           for (int y = 0; y < getHeight(); y++) {
               
              if (get(x,y).isActivated()) {
                  if (x < r.left) r.left = x;
                  if (r.right < x) r.right=x;
                  
                  if (y < r.top) r.top = y;
                  if (r.bottom < y) r.bottom = y;
              }
           }
        }
        return r;
    }
  
    public void autoOffset() {
        TlgRectangle r= getActiveArea();
        offset = new TlgPoint(-1*r.left, -1*r.top);
    }

    void initializeFormAndColor (int form, int color) {
        setColor(color);
        
        switch (form)  {
        case 1: //  O
        get(1,1).makeVisible();
        get(1,2).makeVisible();
        get(2,1).makeVisible();
        get(2,2).makeVisible();
        break;

        case 2: // Z
        get(1,1).makeVisible();
        get(1,2).makeVisible();
        get(2,0).makeVisible();
        get(2,1).makeVisible();
        break;

        case 3: // S
        get(2,1).makeVisible();
        get(2,2).makeVisible();
        get(1,0).makeVisible();
        get(1,1).makeVisible();
        break;

        case 4: // L
        get(1,0).makeVisible();
        get(2,0).makeVisible();
        get(2,1).makeVisible();
        get(2,2).makeVisible();
        break;

        case 5: // J
        get(2,0).makeVisible();
        get(1,0).makeVisible();
        get(1,1).makeVisible();
        get(1,2).makeVisible();
        break;

        case 6: // T
        get(1,1).makeVisible();
        get(2,0).makeVisible();
        get(2,1).makeVisible();
        get(2,2).makeVisible();
        break;

        // Level 2 //////////////////////////////////////
        case 8:
        get(1,1).makeVisible();
        get(1,2).makeVisible();
        get(2,1).makeVisible();
        get(2,2).makeVisible();
        get(3,1).makeVisible();
        break;

        case 9:
        get(1,1).makeVisible();
        get(1,2).makeVisible();
        get(2,1).makeVisible();
        get(2,2).makeVisible();
        get(3,2).makeVisible();
        break;

        case 10:
        get(1,1).makeVisible();
        get(2,1).makeVisible();
        get(2,2).makeVisible();
        break;

        case 11:
        get(2,0).makeVisible();
        get(1,0).makeVisible();
        get(1,1).makeVisible();
        get(1,2).makeVisible();
        get(1,3).makeVisible();
        break;

        case 12:
        get(2,1).makeVisible();
        get(1,0).makeVisible();
        get(1,1).makeVisible();
        get(1,2).makeVisible();
        get(1,3).makeVisible();
        break;

        case 13:
        get(2,2).makeVisible();
        get(1,0).makeVisible();
        get(1,1).makeVisible();
        get(1,2).makeVisible();
        get(1,3).makeVisible();
        break;

        // Level 3 ///////////////////////////////////////////////////
        case 14:
        get(0,1).makeVisible();
        get(0,2).makeVisible();
        get(1,2).makeVisible();
        get(2,2).makeVisible();
        get(2,1).makeVisible();
        break;

        case 15:
        get(0,1).makeVisible();
        get(1,1).makeVisible();
        get(2,1).makeVisible();
        get(0,2).makeVisible();
        get(1,2).makeVisible();
        break;

        case 16:
        get(0,1).makeVisible();
        get(1,1).makeVisible();
        get(2,1).makeVisible();
        get(2,2).makeVisible();
        get(1,2).makeVisible();
        break;

        case 7: // I
        default:
        get(1,0).makeVisible();
        get(1,1).makeVisible();
        get(1,2).makeVisible();
        get(1,3).makeVisible();
        break;
    }
    }
}
