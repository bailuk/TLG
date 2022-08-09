package geometry

class TlgRectangle {
    var left = 0
    var right = 0
    var top = 0
    var bottom = 0


    constructor(r: TlgRectangle) {
        left = r.left
        right = r.right
        top = r.top
        bottom = r.bottom
    }

    constructor(l: Int = 0, t: Int = 0, r: Int = 0, b: Int = 0) {
        left = l
        right = r
        top = t
        bottom = b
    }

    fun grow(margin: Int) {
        left -= margin
        right += margin
        top -= margin
        bottom += margin
    }

    fun shrink(margin: Int) {
        left += margin
        right -= margin
        top += margin
        bottom -= margin
    }

    val maxLength: Int
        get() = Math.max(width, height)

    var width: Int
        get() = right - left + 1
        set(w) {
            right = left + w - 1
        }

    var height: Int
        get() = bottom - top + 1
        set(h) {
            bottom = top + h - 1
        }

    val tL: TlgPoint
        get() = TlgPoint(left, top)
    val tR: TlgPoint
        get() = TlgPoint(right, top)
    val bL: TlgPoint
        get() = TlgPoint(left, bottom)
    val bR: TlgPoint
        get() = TlgPoint(right, bottom)
}
