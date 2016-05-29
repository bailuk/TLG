package ch.bailu.tlg;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class ByteInteger {
    private byte [] buffer=new byte[4];

    public static ByteInteger wrap(int i) {
        return new ByteInteger(i);
    }
    
    public static int read(BufferedInputStream reader) throws IOException {
        return new ByteInteger(reader).getValue();
    }
    
    public ByteInteger(int i) {
        ByteBuffer wrapper =ByteBuffer.wrap(buffer);
        wrapper.putInt(i);
    }
    
    public ByteInteger(BufferedInputStream reader) throws IOException {
        reader.read(buffer);
    }

    public int getValue() {
        return ByteBuffer.wrap(buffer).getInt();
    }
    
    public void writeState(BufferedOutputStream output) throws IOException {
        output.write(buffer);
    }
    
    
}
