package ch.bailu.tlg;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

public class HighscoreEntry {
    private final static int NAMEBUFFER_LIMIT=20;
    private final static String DEFAULT_NAME="-";

    public final String name;
    public final int score;

    
    
    public HighscoreEntry() {
        score = 0;
        name = DEFAULT_NAME;
    }

    
    public HighscoreEntry(String n, int s) {
        name = n;
        score = s;
        
    }
    
    
    public HighscoreEntry(BufferedInputStream reader) throws IOException {
        score = ByteInteger.read(reader);
        name = readName(reader);
    }

    
    private String readName(BufferedInputStream reader) throws IOException {
        byte[] buf;
        int len = ByteInteger.read(reader);

        if (len > NAMEBUFFER_LIMIT || len < 1) throw new IOException();
        buf= new byte[len];
        reader.read(buf);
        return new String(buf);
    }



    public void writeState(BufferedOutputStream output) throws IOException {
        System.err.print("write entry");
        System.err.print(name + "\n");

        
        byte[] buf=name.getBytes();
        ByteInteger.wrap(score).writeState(output);
        ByteInteger.wrap(buf.length).writeState(output);
        output.write(buf);
    }
}
