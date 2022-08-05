import { MatrixManipulator } from './MatrixManipulator';
import { MatrixShape } from './MatrixShape';
import { StorageContext } from '../context/StorageContext';

export class MatrixWithShape extends MatrixManipulator {

    private movingShape = new MatrixShape();
    
    public init (w: number, h:number) {
        super.init(w,h);
        console.log('MatrixWithShape::Init()');
        this.movingShape = new MatrixShape();
        this.movingShape.init();
    }

    
    public readState(s:StorageContext) : void{
        super.readState(s);
        this.movingShape = new MatrixShape();
        this.movingShape.readState(s);
    }

    public writeState(s:StorageContext) {
        super.writeState(s);
        this.movingShape.writeState(s);
    }
    

    
    public setMovingShape(shape:MatrixShape):boolean {
        this.movingShape = shape;
        return this.autoPlaceShape(this.movingShape);
    }
    
    public getMovingShape() {
        return this.movingShape;
    }
    
    public removeMovingShape() {
        this.removeShape(this.movingShape);
    }
    
    public setRandomMovingShape(level:number, color:number, turn:number) {
        console.log('MatrixWithShape::setRandomShape()');
        var shape = new MatrixShape();
        shape.init();
        shape.initializeFormAndColor(level, color);
        this.movingShape = shape.getRotatedCopy(turn);
        this.autoPlaceShape(this.movingShape);
    }
   
    
    public moveShapeLeft() {
        var pos = this.movingShape.getPos();
        pos.setX(pos.getX()-1); 
        this.moveShape(this.movingShape,pos);
    }

    
    public moveShapeRight() {
        var pos = this.movingShape.getPos();
        pos.setX(pos.getX()+1); 
        this.moveShape(this.movingShape,pos);
        
    }

    public moveShapeDown() {
        var pos = this.movingShape.getPos();
        pos.setY(pos.getY()+1); 
        return this.moveShape(this.movingShape,pos);
    }

    public moveShapeTurn() {
       var temp = this.movingShape.getRotatedCopy(1);
       
       this.removeShape(this.movingShape);
       if (this.placeShape(temp)) this.movingShape=temp;
       else this.placeShape(this.movingShape);
    }
    
    
    public centerMovingShape() {
        var r = this.movingShape.getActiveArea();
        var x = ( (this.getWidth() - r.getWidth()) / 2 ) - r.getL();
        var y = ( (this.getHeight() - r.getHeight()) / 2 ) - r.getT();

        if (x != 0 || y != 0) {
            this.moveShapeXY(this.movingShape, Math.floor(x), Math.floor(y));
        }
    }
}
