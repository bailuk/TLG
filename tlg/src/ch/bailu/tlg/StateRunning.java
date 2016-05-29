package ch.bailu.tlg;

import java.util.Random;

public class StateRunning extends State {
	public static final int SHAPE_PER_LEVEL = 7;
	
	public static final int ID=3;
	public static final String NAME="Playing"; 
	
	public StateRunning(InternalContext c) {
		super(c);
		context.setStatusText(NAME);
	}

	
	public State init(PlatformContext c) {
		erase();
		initPreview(c);
		context.setStatusText(NAME);
		return initMovingShape(c);
	}
	
	
    public void erase() {
        context.previewMatrix.erase();
        context.mainMatrix.erase();
        context.currentScore.reset();
    }

    
    private void initPreview(PlatformContext gc) {
        Random random = new Random();
        
        int shape=random.nextInt(context.currentScore.getLevel() * SHAPE_PER_LEVEL)+1;
        int color=gc.getColor(shape%gc.countOfColor());
        int turn=random.nextInt(4);
        
        context.previewMatrix.erase();
        context.previewMatrix.setRandomMovingShape(shape, color, turn);
        context.previewMatrix.centerMovingShape();
    }

    private State initMovingShape(PlatformContext gc) {
        boolean r=initTarget();
        if (r) {
            initPreview(gc);
        } else {
            return State.gameOverStateFactory(context, gc);
        }

        return this;
    }
    
    
    
    private boolean initTarget() {
        return context.mainMatrix.setMovingShape(context.previewMatrix.getMovingShape());
    }

    
    @Override
    public State moveRight(PlatformContext c) {
    	context.mainMatrix.moveShapeRight();
    	return this;
    }

    @Override
    public State moveLeft(PlatformContext c) {
    	context.mainMatrix.moveShapeLeft();
    	return this;
    }
    
    @Override
    public State moveDown(PlatformContext c) {
        if (!context.mainMatrix.moveShapeDown()) {
            removeLines();
            return initMovingShape(c);
        }
    	return this;
    }


    @Override
    public State moveTurn(PlatformContext c) {
    	context.mainMatrix.moveShapeTurn();
    	return this;
    }

    
    private void removeLines() {
        context.currentScore.addLines(context.mainMatrix.eraseLines());
        context.setStatusText(NAME);
        
    }
    
    @Override
	public State togglePause(PlatformContext c) {
		return new StatePaused(context).init(c);
	}

	@Override
	public int getTimerInterval() {
		return context.currentScore.getTimerInterval();
	}


	@Override
	public int getID() {
		return ID;
	}

}
