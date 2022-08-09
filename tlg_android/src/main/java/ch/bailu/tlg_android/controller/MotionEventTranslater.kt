package ch.bailu.tlg_android.controller

import TlgConfiguration.MATRIX_HEIGHT
import TlgConfiguration.MATRIX_WIDTH
import android.view.MotionEvent


class MotionEventTranslater {
    companion object {
        const val KEY_LEFT = -1
        const val KEY_RIGHT = 1
        const val KEY_UP = KEY_LEFT * 2
        const val KEY_DOWN = KEY_RIGHT * 2
    }


    private class MotionTranslater {
        private var delta = 0f
        private var latestPixel = 0f
        var latestEvent = 0
            private set
        private var trigger = 40f
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
                latestEvent = KEY_LEFT
            } else if ((delta > trigger).also { r = it }) {
                latestEvent = KEY_RIGHT
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

        fun setPosition(p: Float) {
            latestPixel = p
        }
    }

    private val verticalMotion = MotionTranslater()
    private val horizontalMotion = MotionTranslater()
    private var tapEvent = 0
    private var width = 0
    private var height = 0

    fun getLastEvent(): Int {
        val ve = verticalMotion.latestEvent
        val he = horizontalMotion.latestEvent
        var e = tapEvent
        if (ve != 0) e = ve * 2 else if (he != 0) e = he
        return e
    }

    fun setGeometry(w: Int, h: Int) {
        width = w
        height = h
        verticalMotion.setTrigger((h / MATRIX_HEIGHT).toFloat())
        horizontalMotion.setTrigger((w / MATRIX_WIDTH).toFloat())
    }

    fun translateEvent(event: MotionEvent): Boolean {
        var ret = false
        if (event.action == MotionEvent.ACTION_DOWN) {
            initializeMotionEvent(event)
        } else if (event.action == MotionEvent.ACTION_MOVE) {
            ret = recordEvent(event)
        } else if (event.action == MotionEvent.ACTION_UP) {
            ret = translateEventFromTap(event)
        }
        return ret
    }

    private fun translateEventFromTap(event: MotionEvent): Boolean {
        var r: Boolean
        val tline: Int = height / 3
        val bline: Int = height - tline
        val lline = width / 3
        val rline = width - lline
        var he = 0
        var ve = 0
        if (event.x < lline) {
            he = KEY_LEFT
        } else if (event.x > rline) {
            he = KEY_RIGHT
        }
        if (event.y < tline) {
            ve = KEY_UP
        } else if (event.y > bline) {
            ve = KEY_DOWN
        }
        if ((he != 0 && ve == 0).also { r = it }) tapEvent = he else if ((ve != 0 && he == 0).also {
                r = it
            }) tapEvent = ve
        return r
    }

    private fun initializeMotionEvent(event: MotionEvent) {
        horizontalMotion.reset(event.rawX)
        verticalMotion.reset(event.rawY)
    }


    private fun recordEvent(event: MotionEvent): Boolean {
        var r: Boolean
        if (horizontalMotion.recordEvent(event.rawX).also { r = it }) {
            verticalMotion.reset()
        } else if (verticalMotion.recordEvent(event.rawY).also { r = it }) {
            horizontalMotion.reset()
        }
        return r
    }
}
