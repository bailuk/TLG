package tlg.state


import tlg.context.InternalContext
import tlg.context.PlatformContext
import tlg.score.HighScoreList
import java.io.IOException

class StateHighScore(iContext: InternalContext) : State(iContext) {
    override fun init(pContext: PlatformContext): State {
        context.state = this
        pContext.onNewHighscore()
        return this
    }

    override fun setHighScoreName(pContext: PlatformContext, name: String): State {
        return if (name.isNotEmpty()) {
            val highScoreList = HighScoreList(pContext)
            highScoreList.add(name, context.currentScore.score)
            try {
                highScoreList.writeState(pContext)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            StateLocked(context).init(pContext)
        } else {
            this
        }
    }

    override fun toString(): String {
        return "New High Score!"
    }

    override val id = ID

    companion object {
        const val ID = 5
    }
}
