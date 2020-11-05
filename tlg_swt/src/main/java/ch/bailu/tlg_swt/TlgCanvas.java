package ch.bailu.tlg_swt;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import ch.bailu.tlg.HighscoreList;
import ch.bailu.tlg.InternalContext;
import ch.bailu.tlg.PlatformContext;
import ch.bailu.tlg.TlgRectangle;

public class TlgCanvas extends Canvas implements PaintListener, KeyListener, Runnable, DisposeListener{

    private final InternalContext  iContext;
    private final PlatformContext  pContext;
    
    private final Display display;

	TlgCanvas(Display d, Composite parent) {
		super(parent, SWT.NO_BACKGROUND);
		addPaintListener(this);
		addDisposeListener(this);
		
		display=d;
        pContext = new Context(display, new GC(this)) {
        	@Override
        	public void onNewHighscore() {
            	System.err.print("onNewHighscore()\n");

        		iContext.setHighscoreName(pContext, "test");
        	}
        };
        
        
        iContext = new InternalContext(pContext);

        kickTimer();
        
	}
	

	public void paintControl(PaintEvent e) {
        Rectangle r = getBounds();
        
        iContext.layout(new TlgRectangle(0,0,r.width-1,r.height-1));
		iContext.updateAll(new Context(display, e.gc));
	}



	public void keyPressed(KeyEvent e) {
		boolean update=true;

		switch (e.keyCode) {
		case 'n':  
			iContext.startNewGame(pContext); 
			break;

		case ' ':
			iContext.togglePause(pContext);
			break;

		case 'g':
			iContext.toggleGrid();
			break;

		case SWT.ARROW_LEFT:
			iContext.moveLeft(pContext);
			break;

		case SWT.ARROW_RIGHT:
			iContext.moveRight(pContext);
			break;

		case SWT.ARROW_UP:
			iContext.moveTurn(pContext);
			break;
		case SWT.ARROW_DOWN:  
			iContext.moveDown(pContext);
			break;
		default:
			update=false;
		}
		
		if (update) {
			updateScreen();
		}
	}



	public void keyReleased(KeyEvent e) {}
	

    public void run() {
    	if (!display.isDisposed()) {
    		iContext.moveDown(pContext);
    		if (!isDisposed())updateScreen();
    		kickTimer();
    	}
    }
    
    private void updateScreen() {
		GC gc = new GC(this);
		iContext.update(new Context(display, gc));
		gc.dispose();
	}



	private void kickTimer() {
    	display.timerExec(iContext.getTimerInterval(), this);
    }
    

	public void widgetDisposed(DisposeEvent e) {
		try {
			iContext.writeState(pContext);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}


	public void setHighscoreLabel(Label h) {
		HighscoreList score = new HighscoreList(pContext);
		h.setText("HIGHSCORE:\n"+score.getFormatedText());
	}
	
}