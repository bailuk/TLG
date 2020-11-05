package ch.bailu.tlg;

public class StatePaused extends State {
    public static final int ID=2;
    
    public StatePaused(InternalContext c) {
        super(c);
        context.setStatusText("Paused");
    }

    
    public State togglePause(PlatformContext c) {
        return new StateRunning(context);
    }


    @Override
    public int getID() {
        return ID;
    }

    
}
