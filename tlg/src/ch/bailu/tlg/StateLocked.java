package ch.bailu.tlg;

public class StateLocked extends State {
	private final static int ID = 4; 
	
	public StateLocked(InternalContext c) {
		super(c);
		context.setStatusText("Game over");
	}
	
	@Override
	public State init(PlatformContext c) {
		return this;
		
	}


	@Override
	public int getID() {
		return ID;
	}

}
