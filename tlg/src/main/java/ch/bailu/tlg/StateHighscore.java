package ch.bailu.tlg;

import java.io.IOException;

public class StateHighscore extends State {
    public static final int ID=1;

    public StateHighscore(InternalContext c) {
        super(c);
    }

    @Override
    public State init(PlatformContext c) {
        context.state=this;
        c.onNewHighscore();
        return this;
    }

    @Override
    public State setHighscoreName(PlatformContext c, String name) {
        if (name.length()>0) {
            HighscoreList highscoreList = new HighscoreList(c);

            highscoreList.add(name, context.currentScore.getScore());
            try {
                highscoreList.writeState(c);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return new StateLocked(context).init(c);

        } else {
            return this;
        }
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public String toString() {
        return "New Highscore!";
    }
}
