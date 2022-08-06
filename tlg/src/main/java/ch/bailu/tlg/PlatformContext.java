package ch.bailu.tlg;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


public abstract class PlatformContext
{
    public void setDirtyRect(TlgRectangle rect) {
        /*TetrisRectangle r = new TetrisRectangle(rect);

        r.grow(1);
        drawRectangle(colorGrid(), r);*/
    }

    public void drawRectangle(int c, TlgRectangle r) {
        drawLine(c, r.getTL(), r.getTR());
        drawLine(c, r.getTR(), r.getBR());
        drawLine(c, r.getBR(), r.getBL());
        drawLine(c, r.getBL(), r.getTL());
    }

    public abstract void drawLine(int color, TlgPoint p1, TlgPoint p2);
    public abstract void drawFilledRectangle(int color, TlgRectangle rect);
    public abstract void drawText(int color, TlgRectangle rect, String text);

    // TODO: move color stuff out of context
    public abstract int colorBackground();
    public abstract int colorDark();
    public abstract int colorHighlight();
    public abstract int colorGrayed();
    public abstract int colorFrame();
    public abstract int colorGrid();

    public abstract int countOfColor();
    public abstract int getColor(int i);
    //////

    public File getConfigDirectory() {
        return new File(System.getProperty("user.home"), ".config/tlg");
    }
    public abstract void onNewHighscore();

    public BufferedInputStream  getInputStream(String fileName) throws FileNotFoundException {
        File file = new File(getConfigDirectory(), fileName);
        return new BufferedInputStream( new FileInputStream(file));
    }

    public BufferedOutputStream getOutputStream(String fileName) throws FileNotFoundException {
        File directory = getConfigDirectory();
        directory.mkdirs();
        File file = new File(directory, fileName);
        return new BufferedOutputStream( new FileOutputStream(file));
    }
}
