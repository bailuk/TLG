import { MatrixWithShape } from '../matrix/MatrixWithShape'
import { MatrixLineManipulator } from '../matrix/MatrixLineManipulator'
import { Score } from '../score/Score'
import { TlgRectangle } from '../matrix/TlgRectangle'
import { PlatformContext } from './PlatformContext'
import { StorageContext } from './StorageContext'

export class InternalContext {
    public static readonly MATRIX_WIDTH = 10
    public static readonly MATRIX_HEIGHT = 20

    private static readonly MARGIN = 2

    public previewMatrix = new MatrixWithShape()
    public mainMatrix = new MatrixLineManipulator()
    public statusText = ''
    public currentScore = new Score()

    private geometry:TlgRectangle = new TlgRectangle(0, 0, 0, 0)

    constructor () {
        this.init()
    }

    public init ():void {
        console.log('InternalContext::Init()')
        this.previewMatrix = new MatrixWithShape()
        this.previewMatrix.init(5, 5)

        this.mainMatrix = new MatrixLineManipulator()
        this.mainMatrix.init(InternalContext.MATRIX_WIDTH, InternalContext.MATRIX_HEIGHT)

        this.currentScore = new Score()
    }

    public toggleGrid ():void {
        this.mainMatrix.toggleGrid()
    }

    public readState (s:StorageContext): void {
        this.previewMatrix = new MatrixWithShape()
        this.previewMatrix.readState(s)

        this.mainMatrix = new MatrixLineManipulator()
        this.mainMatrix.readState(s)

        this.currentScore = new Score()
        this.currentScore.readState(s)
    }

    public writeState (s:StorageContext) {
        this.previewMatrix.writeState(s)
        this.mainMatrix.writeState(s)
        this.currentScore.writeState(s)
    }

    public setStatusText (state:string):void {
        this.statusText = 'Score: ' +
            this.currentScore.getScore() +
            ' Level: ' +
            this.currentScore.getLevel() +
            ' | ' +
            state
    }

    public getStatusText ():string {
        return this.statusText
    }

    public layout (r:TlgRectangle):void {
        this.geometry = r

        const mw = Math.floor((r.getWidth() * 3) / 4)

        const mgeo = TlgRectangle.copy(r)
        const pgeo = TlgRectangle.copy(r)

        mgeo.setWidth(mw)
        pgeo.setL(mgeo.getR())
        pgeo.setHeight(Math.floor(r.getHeight() / 8))

        mgeo.shrink(InternalContext.MARGIN)
        pgeo.shrink(InternalContext.MARGIN)

        this.mainMatrix.setPixelGeometry(mgeo)
        this.previewMatrix.setPixelGeometry(pgeo)
    }

    public update (gc:PlatformContext) {
        this.mainMatrix.update(gc)
        this.previewMatrix.update(gc)
    }

    public updateAll (gc:PlatformContext) {
        this.updateBackground(gc)

        this.previewMatrix.updateAll(gc)
        this.mainMatrix.updateAll(gc)
    }

    private updateBackground (gc:PlatformContext) {
        gc.drawFilledRectangle(gc.colorBackground(), this.geometry)
    }
}
