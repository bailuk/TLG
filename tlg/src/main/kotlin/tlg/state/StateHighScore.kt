package tlg.state


import tlg.context.InternalContext
import tlg.context.PlatformContext
import tlg.score.HighScoreList
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class StateHighScore(iContext: InternalContext) : State(iContext) {
    override fun init(pContext: PlatformContext): State {
        setHighScoreName(pContext, getDateTimeString())
        return this
    }

    private fun setHighScoreName(pContext: PlatformContext, name: String) {
        if (name.isNotEmpty()) {
            val highScoreList = HighScoreList(pContext)
            highScoreList.add(name, iContext.currentScore.score)
            try {
                highScoreList.writeState(pContext)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun getDateTimeString() : String {
        // IMPORTANT: java date formatting function supported by android
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
