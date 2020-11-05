package ch.bailu.tlg_awt;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import ch.bailu.tlg.InternalContext;
import ch.bailu.tlg.PlatformContext;
import ch.bailu.tlg.TlgRectangle;




public class Canvas extends java.awt.Canvas implements KeyListener {
	private static final long serialVersionUID = -5806163437915202761L;
	private final InternalContext iContext;
	private final PlatformContext baseContext;


	private final Timer timer;
	
	
	public Canvas() {
		baseContext = new BaseContext();
		iContext = new InternalContext(baseContext);
		
		timer = new Timer();
		timer.schedule(new Tick(), iContext.getTimerInterval());
	}

	class Tick extends TimerTask {

		@Override
		public void run() {
			iContext.moveDown(baseContext);
			update();
				
			timer.schedule(new Tick(), iContext.getTimerInterval());
		}
	}

	
	@Override
    public void paint(Graphics g) {
		Dimension d = this.getSize();
		iContext.layout(new TlgRectangle(0,0,d.width,d.height));
    	iContext.updateAll(new GraphicsContext(g));
     }


	@Override
	public void keyPressed(KeyEvent key) {}


	@Override
	public void keyReleased(KeyEvent key) {
		if (key.getKeyCode()==KeyEvent.VK_N) {
			iContext.startNewGame(baseContext);
			update();
		} else if (key.getKeyCode()==KeyEvent.VK_DOWN) {
			iContext.moveDown(baseContext);
			update();
		} else if (key.getKeyCode()==KeyEvent.VK_LEFT) {
			iContext.moveLeft(baseContext);
			update();
		} else if (key.getKeyCode()==KeyEvent.VK_RIGHT) {
			iContext.moveRight(baseContext);
			update();
		} else if (key.getKeyCode()==KeyEvent.VK_UP) {
			iContext.moveTurn(baseContext);
			update();
		} else if (key.getKeyCode()==KeyEvent.VK_G) {
			iContext.toggleGrid();
			update();
		} else if (key.getKeyCode()==KeyEvent.VK_P ||
				   key.getKeyCode()==KeyEvent.VK_SPACE) {
			iContext.togglePause(baseContext);
			update();
		}

	}


	@Override
	public void keyTyped(KeyEvent key) {
		
	}


	

	private void update() {
		Graphics g=getGraphics();
		if (g != null) {
			iContext.update(new GraphicsContext(g));
			g.dispose();
		}
	}




	public void cleanUp() throws IOException {
		iContext.writeState(baseContext);
	}
}
