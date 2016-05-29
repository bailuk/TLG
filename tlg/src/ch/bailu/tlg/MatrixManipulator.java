package ch.bailu.tlg;

import java.io.BufferedInputStream;
import java.io.IOException;

public class MatrixManipulator extends MatrixVisible {

	public MatrixManipulator(int w, int h) {
		super(w, h);
	}

	public MatrixManipulator(Matrix m) {
		super(m);
	}    

	public MatrixManipulator(BufferedInputStream reader) throws IOException {
		super(reader);
	}

	public boolean  placeShape(MatrixShape b) {
		boolean r;
		if (r=isShapePlaceable(b)) {
			for (int x = 0; x < b.getWidth(); x++) {
				for (int y = 0; y < b.getHeight(); y++) {
					if (b.get(x,y).isActivated()) {
						getD(b.getX() + x,b.getY() + y).set(b.get(x,y));
					}
				}
			}
		}
		return r;
	}

	public void removeShape(MatrixShape b) {
		for (int x = 0; x < b.getWidth(); x++) {
			for (int y = 0; y < b.getHeight(); y++) {
				if (b.get(x,y).isActivated()) {
					int vx=b.getX() + x;
					int vy=b.getY() + y;

					if (isPositionValid(vx, vy)) 
						getD(vx,vy).makeInvisible();
				}
			}
		}
	}

	private boolean isPositionFree(int x, int y) {
		return (isPositionValid(x,y) && get(x,y).isInvisible());
	}

	private boolean isPositionValid(int x, int y) {
		return ( (x < getWidth()) && (y < getHeight()) && (x > -1) &&  (y > -1) );
	}

	public boolean moveShape(MatrixShape shape, TlgPoint pos) {
		boolean r;
		TlgPoint oldPos=shape.getPos();

		removeShape(shape);
		r=placeShapeAtPos(shape, pos);
		if (!(r)) {
			placeShapeAtPos(shape,oldPos);
		}
		return r;
	}

	private boolean placeShapeAtPos(MatrixShape shape, TlgPoint pos) {
		shape.setPos(pos);
		return placeShape(shape);
	}

	public boolean  moveShape(MatrixShape b, int x, int y) {
		return moveShape(b, new TlgPoint(x,y));
	}

	public boolean  isShapePlaceable(MatrixShape shape) {
		boolean r=true;

		for (int x = 0; r && x < shape.getWidth(); x++) {
			for (int y = 0; r && y < shape.getHeight(); y++) {
				if (shape.get(x,y).isVisible()){
					r = isPositionFree(shape.getX()+x, shape.getY()+y);
				}
			}
		}
		return r;
	}

	boolean autoPlaceShape(MatrixShape shape) {
		boolean placed=false;
		int center=(getWidth() / 2) - (shape.getWidth() / 2);
		shape.autoOffset();

		for (int xoffset=0; !placed && xoffset < (getWidth() - center); xoffset++) {
			placed=(   placeShapeHorizontal(shape, center+xoffset) 
					|| placeShapeHorizontal(shape, center-xoffset) );
		}
		return placed;
	}

	private boolean placeShapeHorizontal(MatrixShape shape, int xpos) {
		shape.setX(xpos);
		return placeShape(shape);
	}

}
