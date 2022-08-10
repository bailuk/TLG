package tlg.geometry

import tlg.context.ByteInteger
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.IOException

class TlgPoint {
    var x: Int
    var y: Int

    constructor(x: Int = 0, y: Int = 0) {
        this.x = x
        this.y = y
    }

    constructor(p: TlgPoint) {
        x = p.x
        y = p.y
    }

    constructor(reader: BufferedInputStream) {
        x = ByteInteger.read(reader)
        y = ByteInteger.read(reader)
    }

    @Throws(IOException::class)
    fun writeState(writer: BufferedOutputStream) {
        ByteInteger.wrap(x).writeState(writer)
        ByteInteger.wrap(y).writeState(writer)
    }
}
