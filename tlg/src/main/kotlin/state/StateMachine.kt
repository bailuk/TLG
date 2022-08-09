package state

class StateMachine {
    abstract inner class State {
        abstract fun inputStart(): State?
        abstract fun inputPause(): State?
        abstract fun inputLocked(): State?
    }

    inner class Init : State() {
        override fun inputStart(): State {
            return this
        }

        override fun inputPause(): State {
            return this
        }

        override fun inputLocked(): State {
            return this
        }
    }

    inner class Running : State() {
        override fun inputStart(): State {
            return this
        }

        override fun inputPause(): State {
            return Paused()
        }

        override fun inputLocked(): State {
            return Locked()
        }
    }

    inner class Paused : State() {
        override fun inputStart(): State {
            return this
        }

        override fun inputPause(): State {
            return Running()
        }

        override fun inputLocked(): State {
            return Locked()
        }
    }

    inner class Locked : State() {
        override fun inputStart(): State {
            return Running()
        }

        override fun inputPause(): State {
            return this
        }

        override fun inputLocked(): State {
            return this
        }
    }
}
