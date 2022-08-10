package tlg.matrix

import java.io.BufferedInputStream


class MatrixLineManipulator : MatrixWithShape {
    private var greyedLines = 0

    constructor(w: Int, h: Int) : super(w, h)
    constructor(reader: BufferedInputStream) : super(reader)

    fun eraseLines(): Int {
        var count = 0
        for (y in 0 until height) {
            if (canLineBeRemoved(y)) {
                removeLine(y)
                count++
            }
        }
        return count
    }

    fun insertGreyedLine() {
        val y = height - greyedLines - 1
        if (y > 0) {
            for (x in 0 until width) getDirty(x, y).enableGreyedOut()
            greyedLines++
        }
    }

    fun removeGreyedLine() {
        val y = height - greyedLines
        if (y in 1 until height) {
            for (x in 0 until width) getDirty(x, y).disableGreyedOut()
            greyedLines--
        }
    }

    private fun canLineBeRemoved(y: Int): Boolean {
        var r = true
        var x = 0
        while (r && x < width) {
            r = get(x, y).isActivated
            x++
        }
        return r
    }

    private fun removeLine(l: Int) {
        for (y in l downTo 1) {
            moveLineOneDown(y)
        }
        eraseTopLine()
    }

    private fun moveLineOneDown(l: Int) {
        for (x in 0 until width) getDirty(x, l).set(get(x, l - 1))
    }

    private fun eraseTopLine() {
        for (x in 0 until width) getDirty(x, 0).makeInvisible()
    }
}
