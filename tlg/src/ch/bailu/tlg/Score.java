package ch.bailu.tlg;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

public class Score {
    
    public static final int LEVEL_MAX=3;    
    public static final int FORM_SIZE=4;
    public static final int BOARD_SIZE_X=10;
    public static final int BOARD_SIZE_Y=25;
    public static final int IDT_TIMER=222;
    public static final int C_COLOR=16;
    
    private int score=0;
    private int level=1;
    
    public Score() {};
    public Score(BufferedInputStream reader) throws IOException {
        score=ByteInteger.read(reader);
        level=reader.read();
    }
    
    public void writeState(BufferedOutputStream output) throws IOException {
        ByteInteger.wrap(score).writeState(output);
        output.write(level);
    }
    
    public int getScore() {return score;}

    public int getLevel() {return level;}
    public int getTimerInterval() {return 1000-(score/100);}

    public void addLines(int l) {
        score += (l * l) * 10;
        level = (score / 1000) + 1;
        if (level > LEVEL_MAX) level = LEVEL_MAX - 1;
    }

    public void reset() {
        score=0;
        level=1;
    }
}
