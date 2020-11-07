import { Component, OnInit, ViewChild, ElementRef, HostListener } from '@angular/core';
import { PlatformContext } from 'src/tlg/PlatformContext'
import { InternalContext } from 'src/tlg/InternalContext';
import { JScriptContext } from 'src/tlg/JScriptContext';
import { TlgRectangle } from 'src/tlg/TlgRectangle';
import { StateContext } from 'src/tlg/StateContext';
import { LocalStorageContext } from 'src/tlg/LocalStorageContext';



@Component({
    selector: 'app-tlg-canvas',
    templateUrl: './tlg-canvas.component.html',
    styleUrls: ['./tlg-canvas.component.css']
})

export class TlgCanvasComponent implements OnInit {
    @ViewChild('canvas', { static: true })
    canvas: ElementRef<HTMLCanvasElement>;

    private iContext: InternalContext;
    private pContext: PlatformContext;
    private sContext: StateContext;

    public WIDTH=400;
    public HEIGHT=600;

    ngOnInit() {
        console.log('ngOnInit()');
        var ctx=this.canvas.nativeElement.getContext('2d');

        this.pContext = new JScriptContext(ctx);
        this.iContext = new InternalContext();
        this.sContext = new StateContext(
                this.iContext, this.pContext,
                LocalStorageContext.factory());

        this.onResize();

        setTimeout(TlgCanvasComponent.onTimeout, this.sContext.getTimerInterval(), this)

    }


    @HostListener('window:mousedown', ['$event'])
    public onMouseDown(event:MouseEvent):void {
        console.log('onMouseDown()');
        var x = event.offsetX;
        var y = event.offsetY;
        var x1 = this.WIDTH / 3; var x2 = this.WIDTH / 3 * 2;
        var y1 = this.HEIGHT / 3; var y2 = this.HEIGHT / 3 * 2;


        if (x > x2) {
            if (y > y2) {

            } else if (y > y1) {
                this.key("ArrowRight");
            } else {

            }
        } else if (x > x1) {
            if (y > y2) {
                this.key("ArrowDown");
            } else if (y > y1) {

            } else {
                this.key("ArrowUp");
            }
        } else {
            if (y > y2) {

            } else if (y > y1) {
                this.key("ArrowLeft");
            } else {
            }
        }
    }


    public readState() {
        this.sContext.readState();
        this.onResize();
    }

    public writeState() {
        this.sContext.writeState();
    }


    private static onTimeout(x:TlgCanvasComponent) {
        x.sContext.moveDown(x.pContext);
        x.iContext.update(x.pContext);

        console.log('onTimeout()');

        setTimeout(TlgCanvasComponent.onTimeout, x.sContext.getTimerInterval(), x);

    }


    public getStatusText() {
        return this.iContext.getStatusText();
    }


    @HostListener('window:keydown', ['$event'])
    onKeyEvent(event: KeyboardEvent) {
        this.key(event.code);
    }


    public key(code: String) {
        console.log('key()');

        if (code == "ArrowUp") {
            this.sContext.moveTurn(this.pContext);

        } else if (code == "ArrowRight") {
            this.sContext.moveRight(this.pContext);

        } else if (code == "ArrowLeft") {
            this.sContext.moveLeft(this.pContext);

        } else if (code == "ArrowDown") {
            this.sContext.moveDown(this.pContext);

        } else if (code == "KeyG") {
            this.iContext.toggleGrid();

        } else if (code == "KeyP") {
            this.sContext.togglePause(this.pContext);

        }
        this.iContext.update(this.pContext);

    }


    public newGame():void {
        this.sContext.startNewGame(this.pContext);
        this.iContext.update(this.pContext);
    }

    public onResize(): void {
        var w = window.innerWidth/4*3;
        var h = window.innerHeight/4*3;

        var w1 = h/600*400;
        var h1 = w/400*600;

        this.WIDTH = Math.round(Math.min(w,w1));
        this.HEIGHT = Math.round(Math.min(h,h1));

        this.canvas.nativeElement.width = this.WIDTH;
        this.canvas.nativeElement.height = this.HEIGHT;
        this.iContext.layout(new TlgRectangle(0,0,this.WIDTH,this.HEIGHT));
        this.iContext.update(this.pContext);
    }

    public onBeforeUnload(): void {
        this.sContext.writeState();
    }
}
