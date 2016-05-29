package ch.bailu.tlg;

public abstract class State {
	protected final InternalContext context;
	
	
	
	public State(InternalContext c) {
		context=c;
	}
	
	
	public State init(PlatformContext c) {
		return this;
	}

	public State moveLeft(PlatformContext c) {
		return this;
	}

	
	public State moveRight(PlatformContext c) {
		return this;
	}
	
	public State moveDown(PlatformContext c) {
		return this;
	}

	public State moveTurn(PlatformContext c) {
		return this;
	}

	
	public State startNewGame(PlatformContext c) {
		return new StateRunning(context).init(c);
	}

	
	public State togglePause(PlatformContext c) {
		return this;
	}


	public static State restoreContextFactory(InternalContext c, int id) {
		if (id == StateRunning.ID) {
			return new StateRunning(c);
			
		} else if (id == StatePaused.ID) {
			return new StatePaused(c);
			
		} else if (id == StateHighscore.ID) {
			return new StateHighscore(c);
			
		} else {
			return new StateLocked(c);
		}
	}

	
	public static State gameOverStateFactory(InternalContext i, PlatformContext p) {
		HighscoreList highscoreList = new HighscoreList(p);
		
		if (highscoreList.haveNewHighscore(i.currentScore.getScore())) {
			return new StateHighscore(i).init(p);
			
		} else {
			return new StateLocked(i).init(p);
		}
		
	}
	

	public int getTimerInterval() {
		return 500;
	}


	
	public abstract int getID();


	public State setHighscoreName(PlatformContext c, String name) {
		return this;
	}

}
