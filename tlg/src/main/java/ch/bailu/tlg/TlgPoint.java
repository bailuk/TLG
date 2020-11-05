package ch.bailu.tlg;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

public class TlgPoint {
    public int x,y;
    
    public TlgPoint() {};
    
    public TlgPoint(int x, int y) {
        this.x=x; 
        this.y=y;
    }

    public TlgPoint(BufferedInputStream reader) throws IOException {
        
        x=ByteInteger.read(reader);
        y=ByteInteger.read(reader);
    }
    
    public void writeState(BufferedOutputStream writer) throws IOException {
        ByteInteger.wrap(x).writeState(writer);
        ByteInteger.wrap(y).writeState(writer);
    }
    public TlgPoint(TlgPoint p) {
        x=p.x; y=p.y;
    }
}
