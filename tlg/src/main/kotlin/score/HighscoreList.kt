package score

import context.PlatformContext
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.IOException

class HighscoreList(c: PlatformContext) {
    private val highscore = arrayOfNulls<HighscoreEntry>(MAX_ENTRY)
    @Throws(IOException::class)
    fun writeState(c: PlatformContext) {
        val ostream: BufferedOutputStream = c.getOutputStream(SCORE_FILE)
        for (i in 0 until MAX_ENTRY) {
            highscore[i]!!.writeState(ostream)
        }
        ostream.close()
    }

    val formatedHTMLText: String
        get() {
            val builder = StringBuilder()
            builder.append("<h1>Highscore:</h1>")
            for (i in 0 until MAX_ENTRY) {
                if (highscore[i]!!.score > 0) {
                    builder.append("<p>[")
                    builder.append(i + 1)
                    builder.append("] <b>")
                    builder.append(highscore[i]!!.score)
                    builder.append("</b> == <b>")
                    builder.append(highscore[i]!!.name)
                    builder.append("</b></p>")
                }
            }
            return builder.toString()
        }
    val formatedText: String
        get() {
            val builder = StringBuilder()
            builder.append("HIGHSCORE:\n")
            for (i in 0 until MAX_ENTRY) {
                if (highscore[i]!!.score > 0) {
                    builder.append("[")
                    builder.append(i + 1)
                    builder.append("] ")
                    builder.append(highscore[i]!!.score)
                    builder.append(" <==> ")
                    builder.append(highscore[i]!!.name)
                    builder.append("\n")
                }
            }
            return builder.toString()
        }

    fun haveNewHighscore(score: Int): Boolean {
        for (i in 0 until MAX_ENTRY) {
            if (highscore[i]!!.score < score) {
                return true
            }
        }
        return false
    }

    fun add(name: String?, score: Int) {
        if (haveNewHighscore(score)) {
            for (i in MAX_ENTRY - 1 downTo 1) {
                if (highscore[i - 1]!!.score < score) {
                    highscore[i] = highscore[i - 1]
                } else {
                    highscore[i] = HighscoreEntry(name!!, score)
                    return
                }
            }
            highscore[0] = HighscoreEntry(name!!, score)
        }
    }

    companion object {
        private const val SCORE_FILE = "score"
        private const val MAX_ENTRY = 10
    }

    init {
        try {
            val input: BufferedInputStream = c.getInputStream(SCORE_FILE)
            for (i in 0 until MAX_ENTRY) {
                highscore[i] = HighscoreEntry(input)
            }
            input.close()
        } catch (e: IOException) {
            System.err.print("error reading score file\n")
            var i = 0
            while (i < MAX_ENTRY) {
                highscore[i] = HighscoreEntry()
                i++
            }
        }
    }
}
