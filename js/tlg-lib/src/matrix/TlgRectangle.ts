import { TlgPoint } from './TlgPoint'

export class TlgRectangle {
    private left:number
    private right:number
    private top:number
    private bottom:number

    public static copy (r:TlgRectangle):TlgRectangle {
        return new TlgRectangle(r.left, r.top, r.right, r.bottom)
    }

    constructor (l:number, t:number, r:number, b:number) {
        this.left = l; this.right = r; this.top = t; this.bottom = b
    }

    public setL (l:number):void { this.left = Math.floor(l) }
    public setR (r:number):void { this.right = Math.floor(r) }
    public setT (t:number):void { this.top = Math.floor(t) }
    public setB (b:number):void { this.bottom = Math.floor(b) }

    public getL ():number { return this.left }
    public getR ():number { return this.right }
    public getT ():number { return this.top }
    public getB ():number { return this.bottom }

    public grow (margin:number) {
        margin = Math.floor(margin)
        this.left -= margin
        this.right += margin
        this.top -= margin
        this.bottom += margin
    }

    public shrink (margin:number) {
        margin = Math.floor(margin)
        this.left += margin
        this.right -= margin
        this.top += margin
        this.bottom -= margin
    }

    public getMaxLength () { return Math.max(this.getWidth(), this.getHeight()) }
    public getWidth () { return this.right - this.left + 1 }
    public getHeight () { return this.bottom - this.top + 1 }
    public setWidth (w:number) { w = Math.floor(w); this.right = this.left + w - 1 }
    public setHeight (h:number) { h = Math.floor(h); this.bottom = this.top + h - 1 }

    public getTL () { return new TlgPoint(this.left, this.top) }
    public getTR () { return new TlgPoint(this.right, this.top) }
    public getBL () { return new TlgPoint(this.left, this.bottom) }
    public getBR () { return new TlgPoint(this.right, this.bottom) }
}
