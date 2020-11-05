package ch.bailu.tlg;



public class TlgRectangle {
    public int left,right,top,bottom;

    
    public TlgRectangle(){};
    public TlgRectangle(TlgRectangle r) {left=r.left; right=r.right; top=r.top; bottom=r.bottom;}
    
    public TlgRectangle(int l, int t, int r, int b) {
        left=l; right=r; top=t; bottom=b;
    }
    
    public void grow(int margin) {
        left-=margin; right+=margin; top-=margin; bottom+=margin;
    }
    
    public void shrink(int margin) {
        left+=margin; right-=margin; top+=margin; bottom-=margin;
    }
    
    public int getMaxLength() {return Math.max(getWidth(), getHeight());}
    public int getWidth() {return right-left+1;}
    public int getHeight() {return bottom-top+1;}
    public void setWidth(int w) {right=left+w-1;}
    public void setHeight(int h) {bottom=top+h-1;}
    
    
    public TlgPoint getTL() {return new TlgPoint(left, top);}
    public TlgPoint getTR() {return new TlgPoint(right, top);}
    public TlgPoint getBL() {return new TlgPoint(left, bottom);}
    public TlgPoint getBR() {return new TlgPoint(right, bottom);}
    
}
