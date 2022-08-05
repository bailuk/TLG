package ch.bailu.tlg_gtk;


import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import ch.bailu.gtk.GTK;
import ch.bailu.gtk.gdk.GdkConstants;
import ch.bailu.gtk.gtk.DrawingArea;
import ch.bailu.gtk.gtk.Label;
import ch.bailu.tlg.InternalContext;
import ch.bailu.tlg.PlatformContext;
import ch.bailu.tlg.TlgRectangle;

public class Canvas  {

    private final InternalContext iContext;
    private final PlatformContext baseContext;

    private final Timer timer;
    private final DrawingArea drawingArea;

    private boolean running = true;



    public Canvas(DrawingArea drawingArea, Label score) {
        this.drawingArea = drawingArea;

        baseContext = new BaseContext();
        iContext = new InternalContext(baseContext);

        drawingArea.setDrawFunc((drawing_area, cr, width, height, user_data) -> {
            iContext.layout(new TlgRectangle(0, 0,
                    drawing_area.getAllocatedWidth(),
                    drawing_area.getAllocatedHeight()));

            iContext.updateAll(new GraphicsContext(cr, score));
        }, null, data -> {});

        timer = new Timer();
        timer.schedule(new Tick(), iContext.getTimerInterval());
    }

/*
    public int onKeyPressEvent(EventKey k) {
        int update = 1;
        Key key = new Key(k);

        if (key.has(GdkConstants.KEY_N, GdkConstants.KEY_n)) {
            iContext.startNewGame(baseContext);

        } else if (key.has(GdkConstants.KEY_Down)) {
            iContext.moveDown(baseContext);

        } else if (key.has(GdkConstants.KEY_Left)) {
            iContext.moveLeft(baseContext);

        } else if (key.has(GdkConstants.KEY_Right)) {
            iContext.moveRight(baseContext);

        } else if (key.has(GdkConstants.KEY_Up)) {
            iContext.moveTurn(baseContext);

        } else if (key.has(GdkConstants.KEY_G, GdkConstants.KEY_g)) {
            iContext.toggleGrid();

        } else if (key.has(GdkConstants.KEY_P, GdkConstants.KEY_p, GdkConstants.KEY_space)) {
            iContext.togglePause(baseContext);

        } else {
            update = 0;
        }

        if (update == 1) {
            update();
        }
        return update;
    }*/


  /*  private class Key {
        private final int key;

        public Key(EventKey key) {
            this.key = key.getFieldKeyval();
        }

        public boolean has(int... keys) {
            for (int k : keys) {
                if (key == k) {
                    return true;
                }
            }
            return false;
        }
    }*/

    private class Tick extends TimerTask {

        @Override
        public void run() {
            if (running) {
                iContext.moveDown(baseContext);
                timer.schedule(new Tick(), iContext.getTimerInterval());
                update();
            }
        }
    }


    private void update() {
        drawingArea.queueDraw();
    }


    public void cleanUp() throws IOException {
        running = false;
        timer.cancel();
        iContext.writeState(baseContext);
    }
}
