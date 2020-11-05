package ch.bailu.tlg;

import java.io.BufferedOutputStream;
import java.io.IOException;

public class InternalContext {
	protected static final String STATE_FILE="state";
    protected final static int STATE_FILE_VERSION=4;
    
    private final static int MARGIN=2;
    private final static int TEXT_HEIGHT=20;

	
	
	protected State                 state;
	
    protected MatrixWithShape       previewMatrix;
    protected MatrixLineManipulator mainMatrix; 
    protected TextBox               textBox;
    protected Score                 currentScore;


    private TlgRectangle geometry=new TlgRectangle();

    
    public InternalContext(PlatformContext c) {
    	state = new StateInit(this).init(c);
    }

    
    public  synchronized void moveLeft(PlatformContext c) {
    	state = state.moveLeft(c);
    }

    
    public  synchronized void moveRight(PlatformContext c) {
    	state = state.moveRight(c);
    }
    
    
    public  synchronized void moveDown(PlatformContext c) {
    	state = state.moveDown(c);
    }

    
    public  synchronized void moveTurn(PlatformContext c) {
    	state = state.moveTurn(c);
    }
    
    
    public  synchronized void toggleGrid() {
    	mainMatrix.toggleGrid();
    }

    
    public  synchronized void togglePause(PlatformContext c) {
    	state = state.togglePause(c);
    }
    
    
    public  synchronized void startNewGame(PlatformContext c) {
    	state = state.startNewGame(c);
    }
    
    
    
    public void writeState(PlatformContext gc) throws IOException {
    	BufferedOutputStream output = gc.getOutputStream(STATE_FILE);
    	
        ByteInteger.wrap(STATE_FILE_VERSION).writeState(output);
        
        previewMatrix.writeState(output);
        mainMatrix.writeState(output);
        currentScore.writeState(output);
        
        ByteInteger.wrap(getID()).writeState(output);
        
        output.close();
    }

    
	protected  void setStatusText(String state) {
		StringBuilder text = new StringBuilder();
		text.append("Score: ");
		text.append(currentScore.getScore());
		text.append(" Level: ");
		text.append(currentScore.getLevel());
		text.append(" | ");
		text.append(state);
		textBox.setText(text.toString());
	}
	
	
    public  synchronized void layout(TlgRectangle r) {
        int mw=(r.getWidth()*3)/4;
        geometry=r;
        
        TlgRectangle mgeo = new TlgRectangle(r);
        TlgRectangle pgeo = new TlgRectangle(r);
        TlgRectangle tgeo = new TlgRectangle(r);
        
        
        mgeo.setWidth(mw);
        mgeo.setHeight(r.getHeight()-TEXT_HEIGHT);
        tgeo.top=mgeo.bottom;
        pgeo.left=mgeo.right;
        pgeo.setHeight(r.getHeight()/8);
        
        mgeo.shrink(MARGIN);
        pgeo.shrink(MARGIN);
        
        
        mainMatrix.setPixelGeometry(mgeo);
        previewMatrix.setPixelGeometry(pgeo);
        textBox.setGeometry(tgeo);
    }

    
    public synchronized void update (PlatformContext gc) {
        mainMatrix.update(gc);
        previewMatrix.update(gc);
        textBox.update(gc);
    }
    
    public  synchronized void updateAll(PlatformContext gc) {
        updateBackground(gc);
        
        previewMatrix.updateAll(gc);
        mainMatrix.updateAll(gc);
        textBox.updateAll(gc);
    }

    private void updateBackground(PlatformContext gc) {
        gc.setDirtyRect(geometry);
        gc.drawFilledRectangle(gc.colorBackground(), geometry);
    }
    
    public  int getTimerInterval() {
        return state.getTimerInterval();
    }

    
	public void setHighscoreName(PlatformContext c, String name) {
		state = state.setHighscoreName(c, name);
	}

    
	public int getID() {
		return state.getID();
	}

}
