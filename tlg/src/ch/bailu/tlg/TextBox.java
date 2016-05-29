package ch.bailu.tlg;

public class TextBox {
    private TlgRectangle geometry=new TlgRectangle();
    private String text="";
    private boolean dirty=true;
    
    public void setText(String t) {
        text=t;
        dirty=true;
    }
    
    public void setGeometry(TlgRectangle r) {
        geometry=r;
    }
    
    public void updateAll(PlatformContext gc) {
        dirty=true;
        update(gc);
    }
    
    public void update(PlatformContext gc) {
        if (dirty) {
            gc.setDirtyRect(geometry);
            gc.drawFilledRectangle(gc.colorBackground(), geometry);
            gc.drawText(gc.colorGrid(), geometry, text);
            dirty=false;
        }
    }
}
