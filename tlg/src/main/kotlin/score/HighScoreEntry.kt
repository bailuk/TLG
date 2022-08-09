package score

import context.ByteInteger
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.IOException


class HighscoreEntry {
    val name: String
    val score: Int

    constructor() {
        score = 0
        name = DEFAULT_NAME
    }

    constructor(n: String, s: Int) {
        name = n
        score = s
    }

    constructor(reader: BufferedInputStream) {
        score = ByteInteger.read(reader)
        name = readName(reader)
    }

    @Throws(IOException::class)
    private fun readName(reader: BufferedInputStream): String {
        val buf: ByteArray
        val len: Int = ByteInteger.read(reader)
        if (len > NAMEBUFFER_LIMIT || len < 1) throw IOException()
        buf = ByteArray(len)
        reader.read(buf)
        return String(buf)
    }

    @Throws(IOException::class)
    fun writeState(output: BufferedOutputStream) {
        System.err.print("write entry")
        System.err.print(
            """
                $name
                
                """.trimIndent()
        )
        val buf = name.toByteArray()
        ByteInteger.wrap(score).writeState(output)
        ByteInteger.wrap(buf.size).writeState(output)
        output.write(buf)
    }

    companion object {
        private const val NAMEBUFFER_LIMIT = 20
        private const val DEFAULT_NAME = "-"
    }
}
