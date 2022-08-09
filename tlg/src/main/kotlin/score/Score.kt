package score

import context.ByteInteger
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.IOException

class Score {
    var score = 0
        private set
    var level = 1
        private set

    constructor() {}
    constructor(reader: BufferedInputStream) {
        score = ByteInteger.read(reader)
        level = reader.read()
    }

    @Throws(IOException::class)
    fun writeState(output: BufferedOutputStream) {
        ByteInteger.wrap(score).writeState(output)
        output.write(level)
    }

    val timerInterval: Int
        get() = 1000 - score / 100

    fun addLines(l: Int) {
        score += l * l * 10
        level = score / 1000 + 1
        if (level > LEVEL_MAX) level = LEVEL_MAX - 1
    }

    fun reset() {
        score = 0
        level = 1
    }

    companion object {
        const val LEVEL_MAX = 3
        const val FORM_SIZE = 4
        const val BOARD_SIZE_X = 10
        const val BOARD_SIZE_Y = 25
        const val IDT_TIMER = 222
        const val C_COLOR = 16
    }
}
