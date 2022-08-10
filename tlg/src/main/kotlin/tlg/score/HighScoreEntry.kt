package tlg.score

import tlg.context.ByteInteger
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.IOException


class HighScoreEntry {
    val name: String
    val score: Int

    companion object {
        private const val NAME_BUFFER_LIMIT = 50
        private const val DEFAULT_NAME = "-"
    }

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
        if (len > NAME_BUFFER_LIMIT || len < 1) throw IOException()
        buf = ByteArray(len)
        reader.read(buf)
        return String(buf)
    }

    @Throws(IOException::class)
    fun writeState(output: BufferedOutputStream) {
        println("write entry: $name - $score")
        ByteInteger.wrap(score).writeState(output)
        val nameBuffer = name.toByteArray()
        ByteInteger.wrap(nameBuffer.size).writeState(output)
        output.write(nameBuffer)
    }
}
