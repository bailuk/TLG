import {TlgPoint} from './TlgPoint';
import {TlgRectangle} from './TlgRectangle'
import { Matrix } from './Matrix';
import { StorageContext } from './StorageContext';

export class MatrixShape extends Matrix {
    private static readonly SHAPE_SIZE:number=5;
    
    private offset:TlgPoint;
    


    public initM(s:MatrixShape):void{
        super.initM(s);
        this.offset=TlgPoint.copy(s.offset);
    }

    public init():void {
        super.init(MatrixShape.SHAPE_SIZE, MatrixShape.SHAPE_SIZE);
        this.offset=new TlgPoint(0,0);
    }

    public readState(s:StorageContext):void {
        super.readState(s);
        this.offset = new TlgPoint(0,0);
        this.offset.readState(s);
    }
    
    public writeState(s:StorageContext):void{
        super.writeState(s);
        this.offset.writeState(s);
    }
    
    
    public getPos() {return TlgPoint.copy(this.offset);}
    public setPos(p:TlgPoint) {this.offset=TlgPoint.copy(p); }
    
    public setX(x:number) {
        this.offset.setX(x);
    }
    public getX() {
        return this.offset.getX();
    }
    
    public getY() {
        return this.offset.getY();
    }
    
    public getRotatedCopy(direction:number) {
        var copy:MatrixShape;
        
        if (direction==0) {
            copy=new MatrixShape()
            copy.initM(this);
            
        } else {
            copy = new MatrixShape();
            copy.init();
            copy.copyAndRotateMatrix(this,direction);
            copy.adjustOffset(this);
            
        }
        return copy;
    }
    
    private copyAndRotateMatrix(source:MatrixShape, direction:number) {
        var size=this.getSize();
        var max=size-1;
        
        if (direction==1) {
            for (var x = 0; x < size; x++)
                for (var y = 0; y < size; y++)
                    this.getXY(x,y).set( source.getXY(y,max-x));
        } else if (direction == 2) {
            for (var x = 0; x < size; x++)
                for (var y = 0; y < size; y++)
                    this.getXY(x,y).set( source.getXY(max-x,max-y));
        } else {
            for (var x = 0; x < size; x++)
                for (var y = 0; y < size; y++)
                    this.getXY(x,y).set( source.getXY(max-y,x));
        }
    }
    
    private adjustOffset(source:MatrixShape) {
        var sourceRect:TlgRectangle = source.getActiveArea();
        var destRect:TlgRectangle = this.getActiveArea();
        
        this.offset=TlgPoint.copy(source.offset);
        this.offset.setX(this.offset.getX( )+ sourceRect.getL() - destRect.getL());
        this.offset.setY(this.offset.getY() + sourceRect.getT() - destRect.getT());
        this.offset.setX(this.offset.getX() + Math.floor((sourceRect.getWidth()-destRect.getWidth())/2));
        this.offset.setY(this.offset.getY() + Math.floor((sourceRect.getHeight()-destRect.getHeight())/2));
    }
    
    public setColor(c:number) {
        for (var i = 0; i < this.count(); i++)
            this.get(i).setColor(c);
    }
    
    
    public getActiveArea() {
        var r:TlgRectangle = new TlgRectangle(this.getWidth()-1,this.getHeight()-1,0,0);

        for (var x = 0; x < this.getWidth(); x++) {
           for (var y = 0; y < this.getHeight(); y++) {
               
              if (this.getXY(x,y).isActivated()) {
                  if (x < r.getL()) r.setL(x);
                  if (r.getR() < x) r.setR(x);
                  
                  if (y < r.getT()) r.setT(y);
                  if (r.getB() < y) r.setB(y);
              }
           }
        }
        return r;
    }
  
    public autoOffset() {
        var r:TlgRectangle = this.getActiveArea();
        this.offset = new TlgPoint(-1*r.getL(), -1*r.getT());
    }

    public initializeFormAndColor (form:number, color:number):void {
        this.setColor(color);
        
        switch (form)  {
        case 1: //  O
        this.getXY(1,1).makeVisible();
        this.getXY(1,2).makeVisible();
        this.getXY(2,1).makeVisible();
        this.getXY(2,2).makeVisible();
        break;

        case 2: // Z
        this.getXY(1,1).makeVisible();
        this.getXY(1,2).makeVisible();
        this.getXY(2,0).makeVisible();
        this.getXY(2,1).makeVisible();
        break;

        case 3: // S
        this.getXY(2,1).makeVisible();
        this.getXY(2,2).makeVisible();
        this.getXY(1,0).makeVisible();
        this.getXY(1,1).makeVisible();
        break;

        case 4: // L
        this.getXY(1,0).makeVisible();
        this.getXY(2,0).makeVisible();
        this.getXY(2,1).makeVisible();
        this.getXY(2,2).makeVisible();
        break;

        case 5: // J
        this.getXY(2,0).makeVisible();
        this.getXY(1,0).makeVisible();
        this.getXY(1,1).makeVisible();
        this.getXY(1,2).makeVisible();
        break;

        case 6: // T
        this.getXY(1,1).makeVisible();
        this.getXY(2,0).makeVisible();
        this.getXY(2,1).makeVisible();
        this.getXY(2,2).makeVisible();
        break;

        // Level 2 //////////////////////////////////////
        case 8:
        this.getXY(1,1).makeVisible();
        this.getXY(1,2).makeVisible();
        this.getXY(2,1).makeVisible();
        this.getXY(2,2).makeVisible();
        this.getXY(3,1).makeVisible();
        break;

        case 9:
        this.getXY(1,1).makeVisible();
        this.getXY(1,2).makeVisible();
        this.getXY(2,1).makeVisible();
        this.getXY(2,2).makeVisible();
        this.getXY(3,2).makeVisible();
        break;

        case 10:
        this.getXY(1,1).makeVisible();
        this.getXY(2,1).makeVisible();
        this.getXY(2,2).makeVisible();
        break;

        case 11:
        this.getXY(2,0).makeVisible();
        this.getXY(1,0).makeVisible();
        this.getXY(1,1).makeVisible();
        this.getXY(1,2).makeVisible();
        this.getXY(1,3).makeVisible();
        break;

        case 12:
        this.getXY(2,1).makeVisible();
        this.getXY(1,0).makeVisible();
        this.getXY(1,1).makeVisible();
        this.getXY(1,2).makeVisible();
        this.getXY(1,3).makeVisible();
        break;

        case 13:
        this.getXY(2,2).makeVisible();
        this.getXY(1,0).makeVisible();
        this.getXY(1,1).makeVisible();
        this.getXY(1,2).makeVisible();
        this.getXY(1,3).makeVisible();
        break;

        // Level 3 ///////////////////////////////////////////////////
        case 14:
        this.getXY(0,1).makeVisible();
        this.getXY(0,2).makeVisible();
        this.getXY(1,2).makeVisible();
        this.getXY(2,2).makeVisible();
        this.getXY(2,1).makeVisible();
        break;

        case 15:
        this.getXY(0,1).makeVisible();
        this.getXY(1,1).makeVisible();
        this.getXY(2,1).makeVisible();
        this.getXY(0,2).makeVisible();
        this.getXY(1,2).makeVisible();
        break;

        case 16:
        this.getXY(0,1).makeVisible();
        this.getXY(1,1).makeVisible();
        this.getXY(2,1).makeVisible();
        this.getXY(2,2).makeVisible();
        this.getXY(1,2).makeVisible();
        break;

        case 7: // I
        default:
        this.getXY(1,0).makeVisible();
        this.getXY(1,1).makeVisible();
        this.getXY(1,2).makeVisible();
        this.getXY(1,3).makeVisible();
        break;
    }
    }
}
