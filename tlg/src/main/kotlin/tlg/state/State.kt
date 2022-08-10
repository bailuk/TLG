package tlg.state

import tlg.context.InternalContext
import tlg.context.PlatformContext
import tlg.score.HighScoreList

abstract class State(val iContext: InternalContext) {
    abstract fun init(pContext: PlatformContext): State

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
        return StateRunning(iContext).init(pContext)
    }

    open fun togglePause(pContext: PlatformContext): State {
        return this
    }

    open val timerInterval: Int
        get() = 500

    abstract val id: Int
}
