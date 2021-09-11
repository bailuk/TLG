package ch.bailu.tlg_gtk;


import ch.bailu.gtk.gdk.RGBA;
import ch.bailu.tlg.PlatformContext;
import ch.bailu.tlg.StateRunning;
import ch.bailu.tlg.TlgPoint;
import ch.bailu.tlg.TlgRectangle;

public class BaseContext extends PlatformContext {
    private static final int PALETTE_RESERVED=5;
    private static final int PALETTE_SIZE=(StateRunning.SHAPE_PER_LEVEL*3)+PALETTE_RESERVED;
    
    private static final int COLOR_GRID=PALETTE_SIZE-PALETTE_RESERVED-1;
    private static final int COLOR_BACKGROUND=COLOR_GRID+1;
    private static final int COLOR_HIGHLIGHT=COLOR_GRID+2;
    private static final int COLOR_DARK=COLOR_GRID+3;
    private static final int COLOR_FRAME=COLOR_GRID+4;
    private static final int COLOR_GRAYED=COLOR_GRID+5;
    
    private static RGBA palette[] = null;
    
    
        
    public BaseContext() {
        if (palette == null) {
            initPalette();
            
        }
    }


    private void initPalette() {
        float h=0f;
        
        palette=new RGBA[PALETTE_SIZE];
        for (int i=0; i< (PALETTE_SIZE - PALETTE_RESERVED); i++) {
            
            float[] rgb = ColorHelper.HSVtoRGB(h, 1f, 1f);
            
            palette[i]=newColor(rgb[0], rgb[1], rgb[2]);
            
            h++;
            h%=6;
        }

        double x=1d/256d;
        palette[COLOR_GRID]=newColor(x*44,x*67,x*77);
        palette[COLOR_FRAME]=newColor(x*44,x*109,x*205);
        palette[COLOR_BACKGROUND]=newColor(x*10,x*10,x*10);
        palette[COLOR_HIGHLIGHT]=newColor(1,1,1);
        palette[COLOR_DARK]=newColor(0,0,0);
        palette[COLOR_GRAYED]=newColor(x*208,x*208,x*208);
        
        
    }

    private RGBA newColor(double r, double g, double b) {
        RGBA result = new RGBA();
        result.setFieldRed(r);
        result.setFieldGreen(g);
        result.setFieldBlue(b);
        result.setFieldAlpha(1);
        return result;
    }
    
    public RGBA getGtkColor(int color) {
        return palette[color];
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
    public int colorBackground() {
        return COLOR_BACKGROUND;
    }

    @Override
    public int colorDark() {
        return COLOR_DARK;
    }

    @Override
    public int colorHighlight() {
        return COLOR_HIGHLIGHT;
    }

    @Override
    public int colorGrayed() {
        return COLOR_GRAYED;
    }

    @Override
    public int colorFrame() {
        return COLOR_FRAME;
    }

    @Override
    public int colorGrid() {
        return COLOR_GRID;
    }

    @Override
    public int countOfColor() {
        return PALETTE_SIZE;
    }

    @Override
    public int getColor(int i) {
        return i;
    }



    @Override
    public void onNewHighscore() {
    }


}
