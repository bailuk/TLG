package ch.bailu.tlg;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

public class Matrix {
    private int width, height;
    private Square[] squares;

    public Matrix(int w, int h) {
        width=w;
        height=h;
        squares = new Square[w*h];
        
        for (int i=0; i<squares.length; i++)
            squares[i] = new Square();
    }
    
    public Matrix(Matrix b) {
        width=b.getWidth();
        height=b.getHeight();
        squares = new Square[b.squares.length];
        
        for (int i=0; i < squares.length; i++)
            squares[i] = new Square(b.squares[i]);
    }
    
    protected int count() {
        return squares.length;
    }
    
    protected Square get(int i) {
        return squares[i];
    }
    
    protected Square get(int x, int y) {
        return get((y * width) + x);
    }

    protected int getWidth()  {return width;}
    protected int getHeight() {return height;}
    protected int getSize() {return Math.max(getWidth(), getHeight());}
    
    public void writeState(BufferedOutputStream output) throws IOException {
        output.write(width);
        output.write(height);
        
        for (int i=0; i< count(); i++) {
            get(i).writeState(output);
        }
    }
    
    public Matrix(BufferedInputStream reader) throws IOException {
        width=reader.read();
        height=reader.read();
        
        squares = new Square[width*height];
        
        for (int i=0; i<count(); i++) {
            squares[i] = new Square(reader);
        }
    }
}
