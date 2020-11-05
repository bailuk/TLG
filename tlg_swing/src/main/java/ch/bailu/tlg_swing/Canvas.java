package ch.bailu.tlg_swing;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.Timer;


import ch.bailu.tlg.InternalContext;
import ch.bailu.tlg.PlatformContext;
import ch.bailu.tlg.TlgRectangle;
import ch.bailu.tlg_awt.BaseContext;
import ch.bailu.tlg_awt.GraphicsContext;



public class Canvas extends JPanel implements KeyListener, ActionListener {
	
	private static final long serialVersionUID = -5687686137839387410L;
	
	
	private final InternalContext iContext;
	private final PlatformContext baseContext;


	private final Timer timer;
	
	
	public Canvas() {
		baseContext = new BaseContext();
		iContext = new InternalContext(baseContext);
		
		timer = new Timer(iContext.getTimerInterval(), this);
		timer.start();
	}

	

	
	@Override
    public void paintComponent(Graphics g) {
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

	@Override
	public void actionPerformed(ActionEvent x) {
		iContext.moveDown(baseContext);
		update();
		
		if (timer.getDelay() != iContext.getTimerInterval()) {
			timer.setDelay(iContext.getTimerInterval());
		}
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
