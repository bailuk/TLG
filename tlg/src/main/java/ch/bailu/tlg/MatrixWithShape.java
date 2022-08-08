package ch.bailu.tlg;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

public class MatrixWithShape extends MatrixManipulator {

    private MatrixShape movingShape;

    public MatrixWithShape(int w, int h) {
        super(w, h);
        movingShape = new MatrixShape();
    }

    public MatrixWithShape(BufferedInputStream reader) throws IOException {
        super(reader);
        movingShape = new MatrixShape(reader);
    }

    public void writeState(BufferedOutputStream output) throws IOException {
        super.writeState(output);
        movingShape.writeState(output);
    }



    public boolean setMovingShape(MatrixShape shape) {
        movingShape = shape;
        return autoPlaceShape(movingShape);
    }

    public MatrixShape getMovingShape() {
        return movingShape;
    }

    public void removeMovingShape() {
        removeShape(movingShape);
    }

    public void setRandomMovingShape(int level, int color, int turn) {
        MatrixShape shape = new MatrixShape();
        shape.initializeFormAndColor(level, color);
        movingShape = shape.getRotatedCopy(turn);
        autoPlaceShape(movingShape);
    }


    public void moveShapeLeft() {
        TlgPoint pos = movingShape.getPos();
        pos.x -= 1;
        moveShape(movingShape,pos);
    }


    public void moveShapeRight() {
        TlgPoint pos = movingShape.getPos();
        pos.x += 1;
        moveShape(movingShape,pos);

    }

    public boolean moveShapeDown() {
        TlgPoint pos = movingShape.getPos();
        pos.y += 1;
        return moveShape(movingShape,pos);
    }

    public void moveShapeTurn() {
       MatrixShape temp = movingShape.getRotatedCopy(1);

       removeShape(movingShape);
       if (placeShape(temp)) movingShape=temp;
       else placeShape(movingShape);
    }


    public void centerMovingShape() {
        TlgRectangle r = movingShape.getActiveArea();
        int x = ( (getWidth() - r.getWidth()) / 2 ) - r.left;
        int y = ( (getHeight() - r.getHeight()) / 2 ) - r.top;

        if (x != 0 || y != 0) {
            moveShape(movingShape,x, y);
        }
    }
}
