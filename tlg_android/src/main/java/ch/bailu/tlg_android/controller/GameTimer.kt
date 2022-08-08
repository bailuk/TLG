package ch.bailu.tlg_android.controller

import android.os.Handler
import android.os.Looper

class GameTimer(private val controller: Controller): Runnable {
    private var timer: Handler? = null

    fun start() {
        val looper = Looper.myLooper()
        if (timer == null && looper is Looper) {
            timer = Handler(looper)
            kick()
        }
    }

    fun stop() {
        val timer = timer
        if (timer is Handler) {
            timer.removeCallbacks(this)
            this.timer = null
        }
    }

    private fun kick() {
        val timer = timer
        if (timer is Handler) {
            val interval = controller.getTimerInterval()
            if (interval == 0) {
                stop()
            } else {
                timer.removeCallbacks(this)
                timer.postDelayed(this, interval.toLong()) // fire in milliseconds
            }
        }
    }

    override fun run() {
        controller.moveShape(MotionEventTranslater.KEY_DOWN)
        kick()
    }
}
