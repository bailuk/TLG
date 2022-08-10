import { StorageContext } from '../context/StorageContext'

export class Score {
    public static readonly LEVEL_MAX = 3
    public static readonly FORM_SIZE = 4
    public static readonly BOARD_SIZE_X = 10
    public static readonly BOARD_SIZE_Y = 25
    public static readonly IDT_TIMER = 222
    public static readonly C_COLOR = 16

    private tlg.score:number = 0
    private level:number = 1

    public readState (s:StorageContext):void {
        this.tlg.score = s.readNumber()
        this.level = s.readNumber()
    }

    public writeState (s:StorageContext):void {
        s.writeNumber(this.tlg.score)
        s.writeNumber(this.level)
    }

    public getScore ():number { return this.tlg.score }
    public getLevel ():number { return this.level }
    public getTimerInterval ():number { return 1000 - (this.tlg.score / 100) }

    public addLines (l:number):void {
        this.tlg.score = Math.round(this.tlg.score + (l * l) * 10)
        this.level = Math.floor((this.tlg.score / 1000) + 1)
        if (this.level > Score.LEVEL_MAX) this.level = Score.LEVEL_MAX - 1
    }

    public reset ():void {
        this.tlg.score = 0
        this.level = 1
    }
}
