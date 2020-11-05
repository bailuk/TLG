package ch.bailu.tlg;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

public class HighscoreList {
    private static final String SCORE_FILE = "score";
    private static int MAX_ENTRY=10;
    
  
    private final HighscoreEntry[] highscore = new HighscoreEntry[MAX_ENTRY];
    
    
    public HighscoreList(PlatformContext c) {

        try {
            BufferedInputStream istream;
            istream = c.getInputStream(SCORE_FILE);

            for (int i=0; i<MAX_ENTRY; i++) {
                highscore[i]= new HighscoreEntry(istream);
            }

            istream.close();

        } catch (IOException e) {
            System.err.print("error reading score file\n");

            for (int i=0; i<MAX_ENTRY; i++) {
                highscore[i]= new HighscoreEntry();
            }
        }

    }

    public void writeState(PlatformContext c) throws IOException {
        BufferedOutputStream ostream = c.getOutputStream(SCORE_FILE);

        for (int i=0; i<MAX_ENTRY; i++) {
            highscore[i].writeState(ostream);
        }
        
        ostream.close();
    }
    

    public String getFormatedHTMLText() {
        StringBuilder builder = new StringBuilder();


        builder.append("<h1>Highscore:</h1>");

        for (int i=0; i<MAX_ENTRY; i++) {
            if (highscore[i].score > 0) {
                builder.append("<p>[");
                builder.append(i+1);
                builder.append("] <b>");
                builder.append(highscore[i].score);
                builder.append("</b> == <b>");
                builder.append(highscore[i].name);
                builder.append("</b></p>");
            }
        }

        return builder.toString();
    }
    
    public String getFormatedText() {
        StringBuilder builder = new StringBuilder();

        builder.append("HIGHSCORE:\n");
        for (int i=0; i<MAX_ENTRY; i++) {

            if (highscore[i].score >0 ) {
                builder.append("[");
                builder.append(i+1);
                builder.append("] ");
                builder.append(highscore[i].score);
                builder.append(" <==> ");
                builder.append(highscore[i].name);
                builder.append("\n");
            }
        }

        return builder.toString();
    }

    
    public boolean haveNewHighscore(int score) {
        for (int i=0; i<MAX_ENTRY; i++) {
            if (highscore[i].score < score) {
                return true;
            }
        }
        return false;
    }
    
    
    public void add(String name, int score) {
        if (haveNewHighscore(score)) {
            for (int i=MAX_ENTRY-1; i>0; i--) {
                if (highscore[i-1].score < score) {
                    highscore[i] = highscore[i-1];
                } else {
                    highscore[i] = new HighscoreEntry(name, score);
                    return;
                }

            }
            highscore[0] = new HighscoreEntry(name, score);

        }
    }


    public static void test(PlatformContext c) {
        final String name = "Joe";
        final int score = 20000;

        try {


            HighscoreList list = new HighscoreList(c);

            if (list.haveNewHighscore(score)) {

                HighscoreEntry temp = list.highscore[0];

                testPresence(list, name, score, false);
                list.add(name, score);
                testPresence(list, name, score, true);


                list.writeState(c);
                list = new HighscoreList(c);
                testPresence(list, name, score, true);

                list.highscore[0]=temp;
                testPresence(list, name, score, false);
                list.writeState(c);

                list = new HighscoreList(c);
                testPresence(list, name, score, false);


                System.err.print(list.getFormatedText());

            }
        } catch (IOException e) {
            System.err.print("FAILURE: IOException\n");
        }



    }

    private static void testPresence(HighscoreList list, String name, int score, boolean except) {
        boolean found = false;

        for (int i=0; i<HighscoreList.MAX_ENTRY; i++) {
            found = found || (list.highscore[i].name.equals(name) && list.highscore[i].score == score);
        }

        if (found == except) {
            System.err.print("SUCCESS: finding " + name +"\n");
        } else  {
            System.err.print("FAILURE: finding " + name +"\n");
        }

        if ( (list.highscore[0].name.equals(name) && list.highscore[0].score==score) == except) {
            System.err.print("SUCCESS: " + name + "beeing number one\n");
        } else  {
            System.err.print("FAILURE: " + name + "beeing number one\n");
        }
    }

}
