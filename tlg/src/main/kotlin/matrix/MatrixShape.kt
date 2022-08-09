package matrix

import geometry.TlgPoint
import geometry.TlgRectangle
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.IOException


class MatrixShape : Matrix {
    private var offset: TlgPoint

    constructor(s: MatrixShape) : super(s) {
        offset = TlgPoint(s.offset)
    }

    constructor() : super(SHAPE_SIZE, SHAPE_SIZE) {
        offset = TlgPoint()
    }

    constructor(reader: BufferedInputStream) : super(reader) {
        offset = TlgPoint(reader)
    }

    @Throws(IOException::class)
    override fun writeState(out: BufferedOutputStream) {
        super.writeState(out)
        offset.writeState(out)
    }

    var pos: TlgPoint
        get() = TlgPoint(offset)
        set(p) {
            offset = TlgPoint(p)
        }
    var x: Int
        get() = offset.x
        set(x) {
            offset.x = x
        }
    val y: Int
        get() = offset.y

    fun getRotatedCopy(direction: Int): MatrixShape {
        val copy: MatrixShape
        if (direction == 0) {
            copy = MatrixShape(this)
        } else {
            copy = MatrixShape()
            copy.copyAndRotateMatrix(this, direction)
            copy.adjustOffset(this)
        }
        return copy
    }

    private fun copyAndRotateMatrix(source: MatrixShape, direction: Int) {
        val size = size
        val max = size - 1
        if (direction == 1) {
            for (x in 0 until size) for (y in 0 until size) get(x, y).set(source[y, max - x])
        } else if (direction == 2) {
            for (x in 0 until size) for (y in 0 until size) get(x, y).set(source[max - x, max - y])
        } else {
            for (x in 0 until size) for (y in 0 until size) get(x, y).set(source[max - y, x])
        }
    }

    private fun adjustOffset(source: MatrixShape) {
        val sourceRect: TlgRectangle = source.activeArea
        val destRect: TlgRectangle = activeArea
        offset = TlgPoint(source.offset)
        offset.x += sourceRect.left - destRect.left
        offset.y += sourceRect.top - destRect.top
        offset.x += (sourceRect.width - destRect.width) / 2
        offset.y += (sourceRect.height - destRect.height) / 2
    }

    fun setColor(c: Int) {
        for (i in 0 until count()) get(i).setColor(c)
    }

    val activeArea: TlgRectangle
        get() {
            val r = TlgRectangle(width - 1, height - 1, 0, 0)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    if (get(x, y).isActivated) {
                        if (x < r.left) r.left = x
                        if (r.right < x) r.right = x
                        if (y < r.top) r.top = y
                        if (r.bottom < y) r.bottom = y
                    }
                }
            }
            return r
        }

    fun autoOffset() {
        val r: TlgRectangle = activeArea
        offset = TlgPoint(-1 * r.left, -1 * r.top)
    }

    fun initializeFormAndColor(form: Int, color: Int) {
        setColor(color)
        when (form) {
            1 -> {
                get(1, 1).makeVisible()
                get(1, 2).makeVisible()
                get(2, 1).makeVisible()
                get(2, 2).makeVisible()
            }
            2 -> {
                get(1, 1).makeVisible()
                get(1, 2).makeVisible()
                get(2, 0).makeVisible()
                get(2, 1).makeVisible()
            }
            3 -> {
                get(2, 1).makeVisible()
                get(2, 2).makeVisible()
                get(1, 0).makeVisible()
                get(1, 1).makeVisible()
            }
            4 -> {
                get(1, 0).makeVisible()
                get(2, 0).makeVisible()
                get(2, 1).makeVisible()
                get(2, 2).makeVisible()
            }
            5 -> {
                get(2, 0).makeVisible()
                get(1, 0).makeVisible()
                get(1, 1).makeVisible()
                get(1, 2).makeVisible()
            }
            6 -> {
                get(1, 1).makeVisible()
                get(2, 0).makeVisible()
                get(2, 1).makeVisible()
                get(2, 2).makeVisible()
            }
            8 -> {
                get(1, 1).makeVisible()
                get(1, 2).makeVisible()
                get(2, 1).makeVisible()
                get(2, 2).makeVisible()
                get(3, 1).makeVisible()
            }
            9 -> {
                get(1, 1).makeVisible()
                get(1, 2).makeVisible()
                get(2, 1).makeVisible()
                get(2, 2).makeVisible()
                get(3, 2).makeVisible()
            }
            10 -> {
                get(1, 1).makeVisible()
                get(2, 1).makeVisible()
                get(2, 2).makeVisible()
            }
            11 -> {
                get(2, 0).makeVisible()
                get(1, 0).makeVisible()
                get(1, 1).makeVisible()
                get(1, 2).makeVisible()
                get(1, 3).makeVisible()
            }
            12 -> {
                get(2, 1).makeVisible()
                get(1, 0).makeVisible()
                get(1, 1).makeVisible()
                get(1, 2).makeVisible()
                get(1, 3).makeVisible()
            }
            13 -> {
                get(2, 2).makeVisible()
                get(1, 0).makeVisible()
                get(1, 1).makeVisible()
                get(1, 2).makeVisible()
                get(1, 3).makeVisible()
            }
            14 -> {
                get(0, 1).makeVisible()
                get(0, 2).makeVisible()
                get(1, 2).makeVisible()
                get(2, 2).makeVisible()
                get(2, 1).makeVisible()
            }
            15 -> {
                get(0, 1).makeVisible()
                get(1, 1).makeVisible()
                get(2, 1).makeVisible()
                get(0, 2).makeVisible()
                get(1, 2).makeVisible()
            }
            16 -> {
                get(0, 1).makeVisible()
                get(1, 1).makeVisible()
                get(2, 1).makeVisible()
                get(2, 2).makeVisible()
                get(1, 2).makeVisible()
            }
            7 -> {
                get(1, 0).makeVisible()
                get(1, 1).makeVisible()
                get(1, 2).makeVisible()
                get(1, 3).makeVisible()
            }
            else -> {
                get(1, 0).makeVisible()
                get(1, 1).makeVisible()
                get(1, 2).makeVisible()
                get(1, 3).makeVisible()
            }
        }
    }

    companion object {
        private const val SHAPE_SIZE = 5
    }
}
