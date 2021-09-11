package ch.bailu.tlg_gtk;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import org.freedesktop.cairo.Context;
import org.gnome.gdk.EventKey;
import org.gnome.gdk.Keyval;
import org.gnome.gtk.DrawingArea;
import org.gnome.gtk.Label;
import org.gnome.gtk.Widget;

import ch.bailu.tlg.HighscoreList;
import ch.bailu.tlg.InternalContext;
import ch.bailu.tlg.PlatformContext;
import ch.bailu.tlg.TlgRectangle;

public class Canvas extends DrawingArea implements Widget.Draw, Widget.KeyPressEvent{

    private final InternalContext iContext;
    private final PlatformContext baseContext;
    
    private final Label           score;
    private final Timer           timer;
    private boolean               running=true;
    
    public Canvas(Label s) {
        score = s;
        baseContext = new BaseContext();
        iContext = new InternalContext(baseContext);
        
        connect((Widget.Draw)this);
        
        timer = new Timer();
        
        timer.schedule(new Tick(), iContext.getTimerInterval());
        
        displayScore();
        
    }

    
    private void displayScore() {
        HighscoreList list = new HighscoreList(baseContext);
        score.setLabel(list.getFormatedText());
    }


    class Tick extends TimerTask {

        @Override
        public void run() {
            if (running) {
                iContext.moveDown(baseContext);
                timer.schedule(new Tick(), iContext.getTimerInterval());
                
                
                //update();
            }
        }
    }

    
    @Override
    public boolean onDraw(Widget w, Context c) {
        iContext.layout(new TlgRectangle(0,0,w.getAllocatedWidth(), w.getAllocatedHeight()));
        iContext.updateAll(new GraphicsContext(c));
        return true;
    }


    @Override
    public boolean onKeyPressEvent(Widget arg0, EventKey key) {
        boolean update=true;
        
        if (key.getKeyval()==Keyval.n) {
            iContext.startNewGame(baseContext);
        } else if (key.getKeyval()==Keyval.Down) {
            iContext.moveDown(baseContext);
        } else if (key.getKeyval()==Keyval.Left) {
            iContext.moveLeft(baseContext);
        } else if (key.getKeyval()==Keyval.Right) {
            iContext.moveRight(baseContext);
        } else if (key.getKeyval()==Keyval.Up) {
            iContext.moveTurn(baseContext);
        } else if (key.getKeyval()==Keyval.g) {
            iContext.toggleGrid();
        } else if (key.getKeyval()==Keyval.p ||
                key.getKeyval()==Keyval.Space) {
            iContext.togglePause(baseContext);
        } else {
            update = false;
        }
        
        if (update) {
            update();
        }
        return update;
    }


    
    private void update() {
        GraphicsContext gContext = new GraphicsContext(new Context(getWindow()));
        iContext.update(gContext);
    }

    
    public void cleanUp() throws IOException {
        running=false;
        iContext.writeState(baseContext);
    }
}
