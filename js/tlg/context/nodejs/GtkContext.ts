declare var require: any
const convert = require('color-convert');

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

 
    private setColor(color: number) {
        color = convert.hex.rgb(GtkContext.COLORS[Math.floor(color)]).map(v => v / 255);
        this.context.setSourceRgb(color[0], color[1], color[2]);
    }
}