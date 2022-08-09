package tlg.score

import tlg.context.PlatformContext
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.IOException

class HighScoreList(c: PlatformContext) {
    private val highScore = ArrayList<HighScoreEntry>()

    init {
        try {
            val input: BufferedInputStream = c.getInputStream(SCORE_FILE)
            for (i in 0 until MAX_ENTRY) {
                highScore.add(HighScoreEntry(input))
            }
            input.close()
        } catch (e: IOException) {
            System.err.println(e.message)
            highScore.clear()

            for (i in 0 until MAX_ENTRY) {
                highScore.add(HighScoreEntry())
            }
        }
    }

    @Throws(IOException::class)
    fun writeState(c: PlatformContext) {
        val out: BufferedOutputStream = c.getOutputStream(SCORE_FILE)
        highScore.forEach {
            it.writeState(out)
        }
        out.close()
    }

    val formattedHTMLText: String
        get() {
            val builder = StringBuilder()
            builder.append("<h1>Highscore:</h1>")
            highScore.forEachIndexed { index, value ->
                if (value.score > 0) {
                    builder.append("<p>[")
                    builder.append(index + 1)
                    builder.append("] <b>")
                    builder.append(value.score)
                    builder.append("</b> == <b>")
                    builder.append(value.name)
                    builder.append("</b></p>")

                }
            }
            return builder.toString()
        }

    val formattedText: String
        get() {
            val builder = StringBuilder()
            builder.append("HIGHSCORE:\n")

            highScore.forEachIndexed { index, value ->
                if (value.score > 0) {
                    builder.append("[")
                    builder.append(index + 1)
                    builder.append("] ")
                    builder.append(value.score)
                    builder.append(" <==> ")
                    builder.append(value.name)
                    builder.append("\n")
                }
            }
            return builder.toString()
        }

    fun haveNewHighScore(score: Int): Boolean {
        for (i in 0 until MAX_ENTRY) {
            if (highScore[i].score < score) {
                return true
            }
        }
        return false
    }

    fun add(name: String, score: Int) {
        if (haveNewHighScore(score)) {
            for (i in MAX_ENTRY - 1 downTo 1) {
                if (highScore[i - 1].score < score) {
                    highScore[i] = highScore[i - 1]
                } else {
                    highScore[i] = HighScoreEntry(name, score)
                    return
                }
            }
            highScore[0] = HighScoreEntry(name, score)
        }
    }

    companion object {
        private const val SCORE_FILE = "score"
        private const val MAX_ENTRY = 10
    }
}
