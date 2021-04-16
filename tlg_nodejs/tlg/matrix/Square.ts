import { TlgRectangle } from './TlgRectangle';
import { PlatformContext } from '../context/PlatformContext';
import { StorageContext } from '../context/StorageContext';

export class Square {
    private static readonly BACKGROUND=0;
    private static readonly COLOR=1;
    private static readonly GREYED=2;

    private mode:number=Square.BACKGROUND;
    private color:number=0;

    public rect:TlgRectangle = new TlgRectangle(0,0,0,0);
    
    
    public set(s:Square) {
        this.color=s.color;
        this.mode=s.mode;
    }

    public readState(s:StorageContext):void{
        this.color = s.readNumber();
        this.mode = s.readNumber();
    }

    public writeState(s:StorageContext):void {
        s.writeNumber(this.color);
        s.writeNumber(this.mode);
    }
    
    public isVisible() {
        return (this.mode != Square.BACKGROUND);
    }
    public isInvisible() {
        return !this.isVisible();
    }


    public setColor(c:number) {
        this.color = c;
    }
    
    public isActivated () {
        return this.isVisible(); 
    }
    
    public makeVisible() {
        this.mode = Square.COLOR;
    }
    
    public makeInvisible() {
        this.mode = Square.BACKGROUND;
    }

    public isGreyedOut() {
        return (this.mode == Square.GREYED);
    }
    
    public enableGreyedOut() {
        if (this.isInvisible()) this.mode = Square.GREYED;
    }
    
    public disableGreyedOut() {
        if (this.isGreyedOut()) this.makeInvisible();
    }


    private draw3D (gc:PlatformContext, c:number, c1:number) {
        let r=TlgRectangle.copy(this.rect);
        
        gc.drawFilledRectangle(c1,r);
        r.shrink(1);
        gc.drawFilledRectangle(c, r);
        
/*
        gc.drawLine(gc.colorDark(), this.rect.getTL(), this.rect.getTR());
        gc.drawLine(gc.colorDark(), this.rect.getTR(), this.rect.getBR()); 
        gc.drawLine(gc.colorHighlight(), this.rect.getBR(), this.rect.getBL()); 
        gc.drawLine(gc.colorHighlight(), this.rect.getBL(), this.rect.getTL());
        */
    }
    
    private draw2D (gc:PlatformContext,  c:number) {
        gc.drawFilledRectangle(c,this.rect);
    }
    

    public update (gc:PlatformContext, drawGrid:boolean) {
        switch (this.mode) {
        case Square.COLOR:
            this.draw3D(gc, this.color, gc.colorDark());
            break;
           
        case Square.GREYED:
            this.draw2D(gc, gc.colorGrayed());
            break;
                
        case Square.BACKGROUND:
            if (drawGrid) this.draw3D(gc, gc.colorBackground(), gc.colorGrayed());
            else this.draw2D(gc, gc.colorBackground());
            break;
        }
    }
}