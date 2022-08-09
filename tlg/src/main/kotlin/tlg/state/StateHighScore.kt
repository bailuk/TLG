package tlg.state


import tlg.context.InternalContext
import tlg.context.PlatformContext
import tlg.score.HighScoreList
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class StateHighScore(iContext: InternalContext) : State(iContext) {
    override fun init(pContext: PlatformContext): State {
        context.state = this
        setHighScoreName(pContext, getDateTimeString())
        return this
    }

    private fun setHighScoreName(pContext: PlatformContext, name: String) {
        if (name.isNotEmpty()) {
            val highScoreList = HighScoreList(pContext)
            highScoreList.add(name, context.currentScore.score)
            try {
                highScoreList.writeState(pContext)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun getDateTimeString() : String {
        return SimpleDateFormat().format(Date())
    }

    override fun toString(): String {
        return "New High Score!"
    }

    override val id = ID

    companion object {
        const val ID = 5
    }
}
