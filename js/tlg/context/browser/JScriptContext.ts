import {TlgPoint} from '../../matrix/TlgPoint';
import {TlgRectangle} from '../../matrix/TlgRectangle';
import {NonDrawingContext} from '../NonDrawingContext';


export class JScriptContext extends NonDrawingContext {

    private readonly context:CanvasRenderingContext2D;

    constructor(c:CanvasRenderingContext2D) {
        super();
        this.context = c;
        this.context.lineWidth=1;
        
        this.context.lineCap="square";
    }
    

    public drawLine(color:number, p1:TlgPoint, p2:TlgPoint):void {
        this.setFillStyle(color)

        this.context.beginPath();
        this.context.moveTo(p1.getX(), p1.getY());
        this.context.lineTo(p2.getX(), p2.getY());
        
        this.context.stroke();
        this.context.closePath(); // <-!!!
    }


    public drawFilledRectangle(color:number, rect:TlgRectangle):void {
        this.setFillStyle(color)

        this.context.fillRect(rect.getL(), rect.getT(), rect.getWidth(), rect.getHeight());
    } 

    private setFillStyle(color:number) {
        color = Math.floor(color);
        this.context.fillStyle = NonDrawingContext.COLORS[color] + 'FF';
    }
}