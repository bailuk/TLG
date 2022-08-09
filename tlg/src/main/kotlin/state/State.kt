package state

import context.InternalContext
import context.PlatformContext
import score.HighscoreList

abstract class State(val context: InternalContext) {
    open fun init(c: PlatformContext): State {
        return this
    }

    open fun moveLeft(c: PlatformContext): State {
        return this
    }

    open fun moveRight(c: PlatformContext): State {
        return this
    }

    open fun moveDown(c: PlatformContext): State {
        return this
    }

    open fun moveTurn(c: PlatformContext): State {
        return this
    }

    fun startNewGame(c: PlatformContext): State {
        return StateRunning(context).init(c)
    }

    open fun togglePause(c: PlatformContext): State {
        return this
    }

    open val timerInterval: Int
        get() = 500

    abstract val id: Int

    open fun setHighscoreName(c: PlatformContext, name: String): State {
        return this
    }

    companion object {
        fun restoreContextFactory(iContext: InternalContext, id: Int): State {
            return if (id == StateRunning.ID) {
                StateRunning(iContext)
            } else if (id == StatePaused.ID) {
                StatePaused(iContext)
            } else if (id == StateHighscore.ID) {
                StateHighscore(iContext)
            } else {
                StateLocked(iContext)
            }
        }

        fun gameOverStateFactory(iContext: InternalContext, pContext: PlatformContext): State {
            val highscoreList = HighscoreList(pContext)
            return if (highscoreList.haveNewHighscore(iContext.currentScore.score)) {
                StateHighscore(iContext).init(pContext)
            } else {
                StateLocked(iContext).init(pContext)
            }
        }
    }
}
