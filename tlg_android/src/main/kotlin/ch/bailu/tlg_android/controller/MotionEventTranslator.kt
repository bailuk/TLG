package ch.bailu.tlg_android.controller

import android.view.MotionEvent
import tlg.Configuration.MATRIX_HEIGHT
import tlg.Configuration.MATRIX_WIDTH

class MotionEventTranslator {
    companion object {
        const val KEY_LEFT = -1
        const val KEY_RIGHT = 1
        const val KEY_UP = KEY_LEFT * 2
        const val KEY_DOWN = KEY_RIGHT * 2
    }

    private val verticalMotion = MotionTranslator()
    private val horizontalMotion = MotionTranslator()
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

        val tLine: Int = height / 3
        val bLine: Int = height - tLine
        val lLine = width / 3
        val rLine = width - lLine

        var he = 0
        var ve = 0

        if (event.x < lLine) {
            he = KEY_LEFT
        } else if (event.x > rLine) {
            he = KEY_RIGHT
        }

        if (event.y < tLine) {
            ve = KEY_UP
        } else if (event.y > bLine) {
            ve = KEY_DOWN
        }

        if ((he != 0 && ve == 0).also { r = it }) {
            tapEvent = he
        } else if ((ve != 0 && he == 0).also { r = it }) {
            tapEvent = ve
        }

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
