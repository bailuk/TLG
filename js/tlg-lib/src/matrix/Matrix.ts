import { Square } from './Square'
import { StorageContext } from '../context/StorageContext'

export class Matrix {
    private width = 0
    private height = 0
    private squares:Square[] = []

    private readonly SQUARE = new Square()

    constructor () {
        this.initP(1, 1)
    }

    public init (w:number, h:number):void {
        this.initP(w, h)
    }

    private initP (w:number, h:number):void {
        this.width = w
        this.height = h
        this.squares = []

        for (let i = 0; i < this.count(); i++) {
            this.squares.push(new Square())
        }
    }

    public initM (b:Matrix):void {
        this.width = b.getWidth()
        this.height = b.getHeight()
        this.squares = []

        for (let i = 0; i < b.count(); i++) {
            const s:Square = new Square()
            s.set(b.squares[i])
            this.squares.push(s)
        }
    }

    public get (i:number):Square {
        i = Math.floor(i)

        if (i > -1 && i < this.squares.length) {
            return this.squares[i]
        }
        return this.SQUARE
    }

    public getXY (x:number, y:number):Square {
        return this.get((y * this.width) + x)
    }

    public count ():number { return this.width * this.height }
    public getWidth ():number { return this.width }
    public getHeight ():number { return this.height }
    public getSize ():number { return Math.max(this.getWidth(), this.getHeight()) }

    public writeState (s:StorageContext):void {
        s.writeNumber(this.width)
        s.writeNumber(this.height)

        for (let i = 0; i < this.count(); i++) {
            this.get(i).writeState(s)
        }
    }

    public readState (s:StorageContext):void {
        this.width = s.readNumber()
        this.height = s.readNumber()
        this.squares = []

        for (let i = 0; i < this.count(); i++) {
            const q = new Square()
            q.readState(s)
            this.squares.push(q)
        }
    }
}
