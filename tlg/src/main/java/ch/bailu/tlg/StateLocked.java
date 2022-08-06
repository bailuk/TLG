package ch.bailu.tlg;

public class StateLocked extends State {
    private final static int ID = 4;

    public StateLocked(InternalContext c) {
        super(c);
    }

    @Override
    public State init(PlatformContext c) {
        return this;
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public String toString() {
        return "Game over";
    }
}
