package ch.bailu.tlg_awt;

import java.awt.Color;
import java.io.File;

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
    
    private static Color palette[] = null;
    
    
        
    public BaseContext() {
        if (palette == null) {
            initPalette();
        }
    }


    private void initPalette() {
        final float color_step=1f/StateRunning.SHAPE_PER_LEVEL;
        float h=0f;
        
        palette=new Color[PALETTE_SIZE];
        for (int i=0; i< (PALETTE_SIZE - PALETTE_RESERVED); i++) {
            palette[i]= Color.getHSBColor(h, 1f, 1f);
            h+=color_step;
        }

        
        palette[COLOR_GRID]= new Color(44,67,77);
        palette[COLOR_FRAME]= new Color(44,109,205);        
        palette[COLOR_BACKGROUND]= Color.BLACK;
        palette[COLOR_HIGHLIGHT]= Color.LIGHT_GRAY;
        palette[COLOR_DARK]= Color.DARK_GRAY;
        palette[COLOR_GRAYED]= Color.GRAY;
        
        
    }

    public Color getAwtColor(int color) {
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
