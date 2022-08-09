package tlg.state

import tlg.context.InternalContext
import tlg.context.PlatformContext


class StatePaused(c: InternalContext) : State(c) {
    override fun togglePause(pContext: PlatformContext): State {
        return StateRunning(context)
    }

    override val id = ID

    override fun toString(): String {
        return "Paused"
    }

    companion object {
        const val ID = 2
    }
}
