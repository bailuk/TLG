import {TlgPoint} from './TlgPoint';
import {TlgRectangle} from './TlgRectangle';
import {PlatformContext} from './PlatformContext';


export class JScriptContext extends PlatformContext {

    private readonly context:CanvasRenderingContext2D;

    constructor(c:CanvasRenderingContext2D) {
        super();
        this.context = c;
        this.context.lineWidth=1;
        
        this.context.lineCap="square";
    }
    

    public drawLine(color:number, p1:TlgPoint, p2:TlgPoint):void {
        color = Math.floor(color);

        this.context.beginPath();
        this.context.moveTo(p1.getX(), p1.getY());
        this.context.lineTo(p2.getX(), p2.getY());
        this.context.strokeStyle = JScriptContext.COLORS[color];
        this.context.lineWidth=1;
        
        this.context.stroke();
        this.context.closePath(); // <-!!!
    }


    public drawFilledRectangle(color:number, rect:TlgRectangle):void {
        color = Math.floor(color);

        this.context.fillStyle = JScriptContext.COLORS[color];
        this.context.fillRect(rect.getL(), rect.getT(), rect.getWidth(), rect.getHeight());
    } 

    private static readonly T="FF";
    private static readonly COLORS = [
        /*WHITE        */"#FFFFFF" + JScriptContext.T,
        /*SILVER    */"#ebebeb" + JScriptContext.T,
        /*GRAY        */"#808080" + JScriptContext.T,
        /*BLACK        */"#000000" + JScriptContext.T,
        /*RED        */"#FF0000" + JScriptContext.T,
        /*MAROON    */"#800000" + JScriptContext.T,
        /*YELLOW    */"#FFFF00" + JScriptContext.T,
        /*OLIVE        */"#808000" + JScriptContext.T,
        /*LIME        */"#00FF00" + JScriptContext.T,
        /*GREEN        */"#008000" + JScriptContext.T,
        /*AQUA        */"#00FFFF" + JScriptContext.T,
        /*TEAL        */"#008080" + JScriptContext.T,
        /*BLUE        */"#0000FF" + JScriptContext.T,
        /*NAVY        */"#000080" + JScriptContext.T,
        /*FUCHSIA    */"#FF00FF" + JScriptContext.T,
        /*PURPLE    */"#800080" + JScriptContext.T,
    ];

  
    public colorBackground():number {
        return 1;
    }

    public colorDark():number {
        return 3;
    } 

    public colorHighlight():number {
        return 0;
    }

    public colorGrayed():number {
        return 2;
    }

    public colorFrame():number {
        return 3;
    }

    public colorGrid():number {
        return 2;
    }
    
    public countOfColor():number {
        return JScriptContext.COLORS.length-4;
    }

    public getColor(i:number):number {
        return Math.floor(3+i);
    }

    public onNewHighscore() {

    }
}