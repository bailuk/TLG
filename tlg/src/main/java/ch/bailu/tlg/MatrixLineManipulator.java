package ch.bailu.tlg;

import java.io.BufferedInputStream;
import java.io.IOException;

public class MatrixLineManipulator extends MatrixWithShape {

    private int greyedLines=0;
    
    public MatrixLineManipulator(int w, int h) {
        super(w, h);
    }

    public MatrixLineManipulator(BufferedInputStream reader) throws IOException {
        super(reader);
    }

    public int eraseLines() {
        int count = 0;

        for (int y=0; y<getHeight(); y++) {
            if (canLineBeRemoved(y)) {
                removeLine(y); 
                count ++;
            }
        }
        return count;
     }
     
     
     public void insertGreyedLine() {
        int y = getHeight() - greyedLines - 1;

        if (y>0) {
            for (int x = 0; x < getWidth(); x++) getD(x,y).enableGreyedOut();
            greyedLines ++;
        }
     }
     
     
     public void removeGreyedLine() {
         int y = getHeight() - greyedLines;

         if (y < getHeight() && y > 0) {
             for (int x = 0; x < getWidth(); x++) getD(x,y).disableGreyedOut();
             greyedLines --;
         }
     }
    
     private boolean canLineBeRemoved(int y) {
         boolean r=true;
         for (int x = 0; r && x < getWidth(); x++)
             r=get(x,y).isActivated();
        
        return r;
    }

    private void removeLine(int l) {
        for (int y = l; y > 0; y--) {
            moveLineOneDown(y);
        }
        eraseTopLine();
    }

    private void moveLineOneDown(int l) {
        for (int x = 0; x < getWidth(); x++) getD(x,l).set(get(x,l-1));
    }
    private void eraseTopLine() {
        for (int x = 0; x < getWidth(); x++) getD(x,0).makeInvisible();
    }
}
