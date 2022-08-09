package tlg.state

import tlg.context.InternalContext
import tlg.context.PlatformContext

class StateLocked(c: InternalContext) : State(c) {
    override fun init(c: PlatformContext): State {
        return this
    }

    override val id = ID

    override fun toString(): String {
        return "Game over"
    }

    companion object {
        private const val ID = 4
    }
}
