package state

import context.InternalContext
import context.PlatformContext


class StatePaused(c: InternalContext) : State(c) {
    override fun togglePause(c: PlatformContext): State {
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
