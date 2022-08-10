import { State } from './State'
import { InternalContext } from '../context/InternalContext'
import { PlatformContext } from '../context/PlatformContext'
import { HighscoreList } from '../tlg.score/HighscoreList'
import { StateID } from './StateID'
import { StorageContext } from '../context/StorageContext'

export class StateRunning extends State {
    public static readonly SHAPE_PER_LEVEL = 7

    public static readonly NAME = 'Playing'

    constructor (c:InternalContext, s:StorageContext) {
        super(c, s)
        this.context.setStatusText(StateRunning.NAME)
        console.log('StateRunning::constructor()')
    }

    public getID (): number {
        return StateID.ID_RUNNING
    }

    public init (c:PlatformContext):StateID {
        console.log('StateRunning::Init()')
        this.erase()
        this.initPreview(c)
        return this.initMovingShape(c)
    }

    public erase () {
        this.context.previewMatrix.erase()
        this.context.mainMatrix.erase()
        this.context.currentScore.reset()
    }

    private initPreview (gc:PlatformContext) {
        const shape = this.random(this.context.currentScore.getLevel() * StateRunning.SHAPE_PER_LEVEL) + 1
        const color = gc.getColor(shape % gc.countOfColor())
        const turn = this.random(4)

        this.context.previewMatrix.erase()
        this.context.previewMatrix.setRandomMovingShape(shape, color, turn)
        this.context.previewMatrix.centerMovingShape()
    }

    private initMovingShape (gc:PlatformContext):StateID {
        if (this.initTarget()) {
            this.initPreview(gc)
            return StateID.IGNORE
        }
        return this.gameOverState(gc)
    }

    private gameOverState (p:PlatformContext):StateID {
        const highscoreList = new HighscoreList(p)

        if (highscoreList.haveNewHighscore(this.context.currentScore.getScore())) {
            return new StateID(StateID.ID_HIGHSCORE).toReset()
        } else {
            return new StateID(StateID.ID_LOCKED).toReset()
        }
    }

    private initTarget ():boolean {
        return this.context.mainMatrix.setMovingShape(this.context.previewMatrix.getMovingShape())
    }

    public moveRight (c:PlatformContext) {
        this.context.mainMatrix.moveShapeRight()
        return StateID.IGNORE
    }

    public moveLeft (c:PlatformContext) {
        this.context.mainMatrix.moveShapeLeft()
        return StateID.IGNORE
    }

    public moveDown (c:PlatformContext):StateID {
        if (!this.context.mainMatrix.moveShapeDown()) {
            this.removeLines()
            return this.initMovingShape(c)
        }
        return StateID.IGNORE
    }

    public moveTurn (c:PlatformContext) {
        this.context.mainMatrix.moveShapeTurn()
        return StateID.IGNORE
    }

    private removeLines ():void {
        this.context.currentScore.addLines(this.context.mainMatrix.eraseLines())
        this.context.setStatusText(StateRunning.NAME)
    }

    public togglePause (c:PlatformContext):StateID {
        return new StateID(StateID.ID_PAUSED)
    }

    public getTimerInterval ():number {
        return this.context.currentScore.getTimerInterval()
    }

    public random (max:number):number {
        return Math.floor(Math.random() * max)
    }
}
