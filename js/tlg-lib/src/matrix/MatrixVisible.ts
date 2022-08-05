import { Matrix } from './Matrix'
import { TlgRectangle } from './TlgRectangle'
import { PlatformContext } from '../context/PlatformContext'
import { Square } from './Square'
import { StorageContext } from '../context/StorageContext'

export class MatrixVisible extends Matrix {
    private dirtyArea:TlgRectangle = new TlgRectangle(0, 0, 0, 0)
    private drawGrid:boolean = false

    public init (w:number, h:number):void {
        super.init(w, h)
        this.markAllAsDirty()
    }

    public initM (m:Matrix):void {
        super.initM(m)
        this.markAllAsDirty()
    }

    public readState (s:StorageContext) {
        super.readState(s)
        if (s.readNumber() > 0) this.drawGrid = true
    }

    public writeState (s:StorageContext) {
        super.writeState(s)
        if (this.drawGrid) s.writeNumber(1)
        else s.writeNumber(0)
    }

    public erase () {
        for (let i = 0; i < this.count(); i++) {
            this.get(i).makeInvisible()
        }
        this.markAllAsDirty()
    }

    protected markAllAsDirty () {
        this.dirtyArea.setL(0)
        this.dirtyArea.setT(0)
        this.dirtyArea.setWidth(this.getWidth())
        this.dirtyArea.setHeight(this.getHeight())
    }

    protected markAllAsClean () {
        this.dirtyArea.setL(this.getWidth() - 1)
        this.dirtyArea.setT(this.getHeight() - 1)
        this.dirtyArea.setB(0) // setWidth(0);
        this.dirtyArea.setR(0) // setHeight(0);
    }

    protected getD (x:number, y:number) {
        this.markSquareAsDirty(x, y)
        return this.getXY(x, y)
    }

    private markSquareAsDirty (x:number, y:number) {
        if (x < this.dirtyArea.getL()) this.dirtyArea.setL(x)
        if (y < this.dirtyArea.getT()) this.dirtyArea.setT(y)

        if (x > this.dirtyArea.getR()) this.dirtyArea.setR(x)
        if (y > this.dirtyArea.getB()) this.dirtyArea.setB(y)
    }

    public setPixelGeometry (rect:TlgRectangle):void {
        const squareSize:number =
            Math.floor(
                Math.min(rect.getWidth() / this.getWidth(), rect.getHeight() / this.getHeight()))

        // center
        const xoffset:number = Math.floor(rect.getL() + (rect.getWidth() - (squareSize * this.getWidth())) / 2)
        const yoffset:number = Math.floor(rect.getT() + (rect.getHeight() - (squareSize * this.getHeight())) / 2)

        let xpos:number = xoffset
        for (let x = 0; x < this.getWidth(); x++) {
            let ypos:number = yoffset
            for (let y = 0; y < this.getHeight(); y++) {
                const s:Square = this.getXY(x, y)
                s.rect = new TlgRectangle(xpos, ypos, xpos + squareSize - 1, ypos + squareSize - 1)

                ypos += squareSize
            }
            xpos += squareSize
        }
        this.markAllAsDirty()
    }

    public toggleGrid ():void {
        this.drawGrid = !this.drawGrid
        this.markAllAsDirty()
    }

    public getPixelGeometry ():TlgRectangle {
        return this.getPixelArea(new TlgRectangle(0, 0, this.getWidth() - 1, this.getHeight() - 1))
    }

    private getDirtyPixelArea ():TlgRectangle {
        return this.getPixelArea(this.dirtyArea)
    }

    private getPixelArea (rect:TlgRectangle):TlgRectangle {
        const ret = new TlgRectangle(0, 0, 0, 0)

        if (rect.getWidth() > 0 && rect.getHeight() > 0) {
            const tl = this.getXY(rect.getL(), rect.getT())
            const br = this.getXY(rect.getR(), rect.getB())

            ret.setL(tl.rect.getL())
            ret.setT(tl.rect.getT())
            ret.setR(br.rect.getR())
            ret.setB(br.rect.getB())
        }
        return ret
    }

    private isDirty ():boolean {
        return this.dirtyArea.getWidth() > 0 && this.dirtyArea.getHeight() > 0
    }

    public update (gc:PlatformContext):void {
        if (this.isDirty()) {
            for (let x = this.dirtyArea.getL(); x <= this.dirtyArea.getR(); x++) {
                for (let y = this.dirtyArea.getT(); y <= this.dirtyArea.getB(); y++) {
                    this.getXY(x, y).update(gc, this.drawGrid)
                }
            }
        }
        this.markAllAsClean()
    }

    public updateAll (gc:PlatformContext):void {
        const r:TlgRectangle = TlgRectangle.copy(this.getPixelGeometry())
        r.grow(1)
        gc.drawRectangle(gc.colorFrame(), r)

        this.markAllAsDirty()
        this.update(gc)
    }
}
