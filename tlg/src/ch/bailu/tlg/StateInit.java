package ch.bailu.tlg;

import java.io.BufferedInputStream;
import java.io.IOException;

public class StateInit extends State {
    public final static int MATRIX_WIDTH=10;
    public final static int MATRIX_HEIGHT=20;

	public StateInit(InternalContext c) {
		super(c);
	}


	
	public State init(PlatformContext c) {
		test(c);
		
	    try {
			return restore(c);
		} catch (IOException e) {
			return create(c);
		}
	    
		
	}
	
	public static void test(PlatformContext c) {
		//HighscoreList.test(c);
	}



	private State create(PlatformContext gc) {
	    context.previewMatrix  = new MatrixWithShape(5,5);
	    context.mainMatrix     = new MatrixLineManipulator(MATRIX_WIDTH,MATRIX_HEIGHT);
	    context.currentScore   = new Score();
	    context.textBox        = new TextBox();
	    
	    
	    State state = new StateRunning(context);
	    state.init(gc);
	    return state;
	}

	
	private State restore(PlatformContext gc) throws IOException {
		BufferedInputStream input = gc.getInputStream(InternalContext.STATE_FILE);
	
		int version = ByteInteger.read(input);
		if (version != InternalContext.STATE_FILE_VERSION) throw new IOException();


		context.previewMatrix = new MatrixWithShape(input);
		context.mainMatrix    = new MatrixLineManipulator(input);
		context.currentScore  = new Score(input);
		context.textBox       = new TextBox();
		
		State state = State.restoreContextFactory(context, ByteInteger.read(input));
		
		input.close();
		return state;
	}



	@Override
	public int getID() {
		return 0;
	}

}
