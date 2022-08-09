package tlg.state

import tlg.context.InternalContext
import tlg.context.PlatformContext
import tlg.score.HighScoreList

abstract class State(val context: InternalContext) {
    open fun init(pContext: PlatformContext): State {
        return this
    }

    open fun moveLeft(pContext: PlatformContext): State {
        return this
    }

    open fun moveRight(pContext: PlatformContext): State {
        return this
    }

    open fun moveDown(pContext: PlatformContext): State {
        return this
    }

    open fun moveTurn(pContext: PlatformContext): State {
        return this
    }

    fun startNewGame(pContext: PlatformContext): State {
        return StateRunning(context).init(pContext)
    }

    open fun togglePause(pContext: PlatformContext): State {
        return this
    }

    open val timerInterval: Int
        get() = 500

    abstract val id: Int

    open fun setHighScoreName(pContext: PlatformContext, name: String): State {
        return this
    }

    companion object {
        fun restoreContextFactory(iContext: InternalContext, id: Int): State {
            return if (id == StateRunning.ID) {
                StateRunning(iContext)
            } else if (id == StatePaused.ID) {
                StatePaused(iContext)
            } else if (id == StateHighScore.ID) {
                StateHighScore(iContext)
            } else {
                StateLocked(iContext)
            }
        }

        fun gameOverStateFactory(iContext: InternalContext, pContext: PlatformContext): State {
            val highScoreList = HighScoreList(pContext)
            return if (highScoreList.haveNewHighScore(iContext.currentScore.score)) {
                StateHighScore(iContext).init(pContext)
            } else {
                StateLocked(iContext).init(pContext)
            }
        }
    }
}
