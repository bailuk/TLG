package tlg.score

import tlg.Configuration.SCORE_FILE
import tlg.Configuration.SCORE_FILE_ENTRIES
import tlg.context.PlatformContext
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.IOException

class HighScoreList(pContext: PlatformContext) {
    private val highScore = ArrayList<HighScoreEntry>()

    init {
        try {
            val input: BufferedInputStream = pContext.getInputStream(SCORE_FILE)
            for (i in 0 until SCORE_FILE_ENTRIES) {
                highScore.add(HighScoreEntry(input))
            }
            input.close()
        } catch (e: IOException) {
            System.err.println(e.message)

            highScore.clear()
            for (i in 0 until SCORE_FILE_ENTRIES) {
                highScore.add(HighScoreEntry())
            }
        }
    }

    @Throws(IOException::class)
    fun writeState(pContext: PlatformContext) {
        val out: BufferedOutputStream = pContext.getOutputStream(SCORE_FILE)
        highScore.forEach {
            it.writeState(out)
        }
        out.close()
    }

    val formattedHTMLText: String
        get() {
            val builder = StringBuilder()
            builder.append("<h1>High score:</h1>")
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
            builder.append("High score:\n")

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
        highScore.forEach {
            if (it.score < score) {
                return true
            }
        }
        return false
    }

    fun add(name: String, score: Int) {
        if (haveNewHighScore(score)) {
            for (i in SCORE_FILE_ENTRIES - 1 downTo 1) {
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
}
