package tlg.state


import tlg.context.InternalContext
import tlg.context.PlatformContext
import tlg.score.HighScoreList
import java.io.IOException

class StateHighscore(c: InternalContext) : State(c) {
    override fun init(c: PlatformContext): State {
        context.state = this
        c.onNewHighscore()
        return this
    }

    override fun setHighscoreName(c: PlatformContext, name: String): State {
        return if (name.isNotEmpty()) {
            val highscoreList = HighScoreList(c)
            highscoreList.add(name, context.currentScore.score)
            try {
                highscoreList.writeState(c)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            StateLocked(context).init(c)
        } else {
            this
        }
    }

    override fun toString(): String {
        return "New Highscore!"
    }

    override val id = ID

    companion object {
        const val ID = 5
    }
}
