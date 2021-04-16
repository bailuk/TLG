import {TlgPoint} from './TlgPoint';
import {TlgRectangle} from './TlgRectangle';
import {PlatformContext} from './PlatformContext';


export class GtkContext extends PlatformContext {

    

    public drawLine(color:number, p1:TlgPoint, p2:TlgPoint):void {
    }


    public drawFilledRectangle(color:number, rect:TlgRectangle):void {
    } 

 

    protected static readonly COLORS = [
        /*WHITE        */"#FFFFFF",
        /*SILVER       */"#F6F5F4",
        /*GRAY         */"#808080",
        /*BLACK        */"#000000",
        /*RED          */"#FF0000",
        /*MAROON       */"#800000",
        /*YELLOW       */"#FFFF00",
        /*OLIVE        */"#808000",
        /*LIME         */"#00FF00",
        /*GREEN        */"#008000",
        /*AQUA         */"#00FFFF",
        /*TEAL         */"#008080",
        /*BLUE         */"#0000FF",
        /*NAVY         */"#000080",
        /*FUCHSIA      */"#FF00FF",
        /*PURPLE       */"#800080",
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
        return GtkContext.COLORS.length-4;
    }

    public getColor(i:number):number {
        return Math.floor(3+i);
    }

    public onNewHighscore() {

    }
}