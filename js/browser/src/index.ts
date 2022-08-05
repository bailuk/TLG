import { JScriptContext } from "../../tlg-lib/src/context/browser/JScriptContext";
import { LocalStorageContext } from "../../tlg-lib/src/context/browser/LocalStorageContext";
import { InternalContext } from "../../tlg-lib/src/context/InternalContext";
import { PlatformContext } from "../../tlg-lib/src/context/PlatformContext";
import { TlgRectangle } from "../../tlg-lib/src/matrix/TlgRectangle";
import { StateContext } from "../../tlg-lib/src/state/StateContext";

class App {

    private readonly canvas;
    private readonly statusText;

    private readonly iContext: InternalContext;
    private readonly pContext: PlatformContext;
    private readonly sContext: StateContext;

    private width = 400;
    private height = 600;

    constructor() {
        this.canvas = <HTMLCanvasElement>document.getElementById('canvas')!;
        this.statusText = document.getElementById('status')!;

        const ctx = this.canvas.getContext('2d')!;

        this.pContext = new JScriptContext(ctx);
        this.iContext = new InternalContext();
        this.sContext = new StateContext(
                this.iContext, this.pContext,
                LocalStorageContext.factory());

        this.onResize();

        setTimeout(App.onTimeout, this.sContext.getTimerInterval(), this)
        
        window.onresize = ()=> { this.onResize()}
        window.onkeydown = (event)=>{this.key(event.code)}
        window.onbeforeunload = ()=> { this.sContext.writeState()};
        document.getElementById('btn-pause')!.onclick = () => {this.key('keyP')};
        document.getElementById('btn-grid')!.onclick = () => {this.key('keyG')};
        document.getElementById('btn-up')!.onclick = () => {this.key('ArrowUp')};
        document.getElementById('btn-down')!.onclick = () => {this.key('ArrowDown')};
        document.getElementById('btn-left')!.onclick = () => {this.key('ArrowLeft')};
        document.getElementById('btn-right')!.onclick = () => {this.key('ArrowRight')};
        document.getElementById('btn-new-game')!.onclick = () => {this.newGame()};
    }

    private key(code: String): void {
        code = code.toLowerCase();
        console.log(code)
        if (code == "arrowup") {
            this.sContext.moveTurn(this.pContext);

        } else if (code == "arrowright") {
            this.sContext.moveRight(this.pContext);

        } else if (code == "arrowleft") {
            this.sContext.moveLeft(this.pContext);

        } else if (code == "arrowdown") {
            this.sContext.moveDown(this.pContext);

        } else if (code == "keyg") {
            this.iContext.toggleGrid();

        } else if (code == "keyp") {
            this.sContext.togglePause(this.pContext);

        }
        this.iContext.update(this.pContext);
        this.updateStatusText();
    }

    private newGame(): void {
        this.sContext.startNewGame(this.pContext);
        this.iContext.update(this.pContext);
    }

    public onResize(): void {
        var w = window.innerWidth/4*3;
        var h = window.innerHeight/4*3;

        var w1 = h/600*400;
        var h1 = w/400*600;

        this.width = Math.round(Math.min(w,w1));
        this.height = Math.round(Math.min(h,h1));

        this.canvas.width = this.width;
        this.canvas.height = this.height;
        this.iContext.layout(new TlgRectangle(0,0,this.width,this.height));
        this.iContext.update(this.pContext);
        this.updateStatusText();
    }

    private static onTimeout(appInstance:App) {
        appInstance.sContext.moveDown(appInstance.pContext);
        appInstance.iContext.update(appInstance.pContext);
        setTimeout(App.onTimeout, appInstance.sContext.getTimerInterval(), appInstance);
        appInstance.updateStatusText();
    }

    private updateStatusText(): void {
        this.statusText.innerHTML=`${this.width}x${this.height} ${this.iContext.getStatusText()}`
    }
}

new App()
