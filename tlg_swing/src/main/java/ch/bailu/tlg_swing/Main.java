package ch.bailu.tlg_swing;

import ch.bailu.tlg_swing.Canvas;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;

public class Main extends JFrame {

	private static final long serialVersionUID = 3390718892752728176L;
	private final Canvas canvas;

	public static void main(String args[]) {
		new Main();
	}

	Main() {
		canvas = new Canvas();
		
		add(canvas);
		addKeyListener(canvas);
		
	    addWindowListener(new WindowAdapter() {
	        public void windowClosing(WindowEvent w) {
	            try {
					cleanUp();
					System.exit(0);
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				}
	            
	        }
	    });

		setTitle("TLG - Swing");
		setSize(400, 500);
		setVisible(true);
	}
	

	private void cleanUp() throws IOException {
		canvas.cleanUp();
	}
	
}

