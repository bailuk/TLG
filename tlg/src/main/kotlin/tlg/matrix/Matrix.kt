package tlg.matrix

import tlg.Square
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.IOException

open class Matrix {
    var width: Int
        private set
    var height: Int
        private set
    private var squares = ArrayList<Square>()

    constructor(w: Int, h: Int) {
        width = w
        height = h
        val size = width * height

        for (i in 0 until size) {
            squares.add(Square())
        }
    }

    constructor(b: Matrix) {
        width = b.width
        height = b.height

        b.squares.forEach {
            squares.add(Square(it))
        }
    }


    constructor(reader: BufferedInputStream) {
        width = reader.read()
        height = reader.read()

        for (i in 0 until count()) {
            squares.add(Square(reader))
        }
    }

    fun count(): Int {
        return width * height
    }

    protected operator fun get(i: Int): Square {
        return squares[i]
    }

    operator fun get(x: Int, y: Int): Square {
        return get(y * width + x)
    }

    protected val size: Int
        get() = Math.max(width, height)

    @Throws(IOException::class)
    open fun writeState(out: BufferedOutputStream) {
        out.write(width)
        out.write(height)

        squares.forEach {
            it.writeState(out)
        }
    }
}
