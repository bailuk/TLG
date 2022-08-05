import { StorageContext } from '../context/StorageContext';

export class TlgPoint {
    private x = 0;
    private y = 0;

    public static copy(p:TlgPoint):TlgPoint {
        return new TlgPoint(p.x,p.y);
    }

    constructor(x:number, y:number) {
        this.setX(x); 
        this.setY(y);
    }

    public readState(s:StorageContext):void {
        this.setX(s.readNumber());
        this.setY(s.readNumber());
    }
    
    public writeState(s:StorageContext):void {
        s.writeNumber(this.x);
        s.writeNumber(this.y);
    }

    public getX(): number {
        return this.x;
    }

    public getY(): number {
        return this.y;
    }

    public setX(n:number) {
        this.x = Math.floor(n);
    }

    public setY(n:number) {
        this.y = Math.floor(n);
    }
}

