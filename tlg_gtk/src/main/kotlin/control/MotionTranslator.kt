package control

import control.TouchDrag.Companion.KEY_LEFT
import control.TouchDrag.Companion.KEY_RIGHT


class MotionTranslator {
    private var delta = 0.0
    private var trigger = 40.0
    private var latestEvent = 0


    fun reset() {
        delta = 0.0
        latestEvent = 0
    }

    fun takeEvent(): Int {
        val result = latestEvent
        latestEvent = 0
        return result
    }

    fun recordEvent(p: Double): Boolean {
        delta += p

        if (delta < -1 * trigger) {
            latestEvent = KEY_LEFT
            delta %= trigger
            return true
        }

        if (delta > trigger) {
            latestEvent = KEY_RIGHT
            delta %= trigger
            return true
        }

        return false
    }
}
