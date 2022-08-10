package tlg.state

import tlg.context.InternalContext
import tlg.context.PlatformContext


class StatePaused(pContext: InternalContext) : State(pContext) {
    override fun init(pContext: PlatformContext): State {
        return this
    }

    override fun togglePause(pContext: PlatformContext): State {
        return StateRunning(iContext)
    }

    override val id = ID

    override fun toString(): String {
        return "Paused"
    }

    companion object {
        const val ID = 2
    }
}
