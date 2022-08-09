import { TlgRectangle } from '../tlg.matrix/TlgRectangle'
import { TlgPoint } from '../tlg.matrix/TlgPoint'

export abstract class PlatformContext {
    public drawRectangle (c:number, r:TlgRectangle) {
        this.drawLine(c, r.getTL(), r.getTR())
        this.drawLine(c, r.getTR(), r.getBR())
        this.drawLine(c, r.getBR(), r.getBL())
        this.drawLine(c, r.getBL(), r.getTL())
    }

    public abstract drawLine(color:number, p1:TlgPoint, p2:TlgPoint):void;
    public abstract drawFilledRectangle(color:number, rect:TlgRectangle):void;

    public abstract colorBackground():number;
    public abstract colorDark():number;
    public abstract colorHighlight():number;
    public abstract colorGrayed():number;
    public abstract colorFrame():number;
    public abstract colorGrid():number;

    public abstract countOfColor():number;
    public abstract getColor(i:number):number;
}
