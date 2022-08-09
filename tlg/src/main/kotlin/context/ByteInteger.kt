package context

import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.IOException
import java.nio.ByteBuffer


class ByteInteger {
    private val buffer = ByteArray(4)

    constructor(i: Int) {
        val wrapper = ByteBuffer.wrap(buffer)
        wrapper.putInt(i)
    }

    constructor(reader: BufferedInputStream) {
        reader.read(buffer)
    }

    val value: Int
        get() = ByteBuffer.wrap(buffer).int

    @Throws(IOException::class)
    fun writeState(output: BufferedOutputStream) {
        output.write(buffer)
    }

    companion object {
        fun wrap(i: Int): ByteInteger {
            return ByteInteger(i)
        }

        @Throws(IOException::class)
        fun read(reader: BufferedInputStream): Int {
            return ByteInteger(reader).value
        }
    }
}
