import {TlgPoint} from '../../matrix/TlgPoint';
import {TlgRectangle} from '../../matrix/TlgRectangle';
import { NonDrawingContext } from '../NonDrawingContext';


export class GtkContext extends NonDrawingContext {

    private readonly context : any

    constructor(c: any) {
        super()
        this.context = c
        this.context.setLineWidth(1)
    }


    public drawLine(color:number, p1:TlgPoint, p2:TlgPoint):void {
        this.setColor(color)
        this.context.moveTo(p1.getX(), p1.getY())
        this.context.lineTo(p2.getX(), p2.getY())
        this.context.stroke();
    }


    public drawFilledRectangle(color:number, rect:TlgRectangle):void {
        this.setColor(color)
        this.context.rectangle(rect.getL(), rect.getT(), rect.getWidth(), rect.getHeight());
        this.context.fill();
    }


    private setColor(colorIndex: number) {
        let rgb = this.hexToRgbA(GtkContext.COLORS[Math.floor(colorIndex)]).map(v => v / 255);
        this.context.setSourceRgb(rgb[0], rgb[1], rgb[2]);
    }


    private hexToRgbA(hex: string): number[ ]{
        if(/^#([A-Fa-f0-9]{3}){1,2}$/.test(hex)){
            let c = hex.substring(1).split('');
            if(c.length == 3){
                c = [c[0], c[0], c[1], c[1], c[2], c[2]];
            }
            let x = Number('0x'+c.join(''));
            return [(x>>16)&255, (x>>8)&255, x&255];
        }
        throw new Error('Bad Hex');
    }
}
