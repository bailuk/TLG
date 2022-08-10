package ch.bailu.tlg_android.controller


class MotionTranslator {
    private var delta = 0f
    private var latestPixel = 0f
    private var trigger = 40f
    var latestEvent = 0
        private set

    fun setTrigger(t: Float) {
        trigger = t
    }

    fun reset(p: Float) {
        latestPixel = p
        reset()
    }

    fun reset() {
        delta = 0f
        latestEvent = 0
    }

    fun recordEvent(p: Float): Boolean {
        var r: Boolean
        recordMotionEvent(p)
        if ((delta < -1 * trigger).also { r = it }) {
            latestEvent = MotionEventTranslator.KEY_LEFT
        } else if ((delta > trigger).also { r = it }) {
            latestEvent = MotionEventTranslator.KEY_RIGHT
        }
        if (r) {
            delta %= trigger
        }
        return r
    }

    private fun recordMotionEvent(p: Float) {
        delta += p - latestPixel
        setPosition(p)
    }

    private fun setPosition(p: Float) {
        latestPixel = p
    }
}
