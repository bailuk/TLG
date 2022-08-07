package ch.bailu.tlg_android.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.*
import ch.bailu.tlg_android.R
import ch.bailu.tlg_android.controller.MotionEventTranslater
import ch.bailu.tlg_android.view.GameView
import java.io.BufferedOutputStream

class GameActivity : Activity(), Runnable, SurfaceHolder.Callback {
    private var timer: Handler? = null
    private var tetris: GameView? = null
    private var motionTranslater: MotionEventTranslater? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val surface = SurfaceView(this)
        surface.holder.addCallback(this)
        setContentView(surface)
        motionTranslater = MotionEventTranslater()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        inflateMenu(R.menu.menu, menu)
        return true
    }

    private fun inflateMenu(id: Int, menu: Menu?) {
        val inflater: MenuInflater = getMenuInflater()
        inflater.inflate(id, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_grid -> {
                tetris!!.toggleGrid()
                true
            }
            R.id.menu_pause -> {
                tetris!!.pauseOrResume()
                startTimer()
                true
            }
            R.id.menu_start -> {
                tetris!!.newGame()
                startTimer()
                true
            }
            R.id.menu_about -> {
                start(AboutActivity::class.java)
                true
            }
            R.id.menu_score -> {
                start(HighscoreActivity::class.java)
                true
            }
            R.id.menu_name -> {
                tetris!!.setHighscoreName()
                false
            }
            else -> false
        }
    }


    private fun start(activityClass: Class<*>) {
        val intent = Intent()
        intent.setClass(this, activityClass)
        intent.action = activityClass.name
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }


    private fun startTimer() {
        if (timer == null) {
            timer = Handler()
            kickTimer()
        }
    }

    private fun stopTimer() {
        if (timer != null) {
            timer!!.removeCallbacks(this)
            timer = null
        }
    }

    private fun kickTimer() {
        val timer = timer
        if (timer is Handler) {
            val interval = tetris!!.timerInterval
            if (interval == 0) {
                stopTimer()
            } else {
                timer.removeCallbacks(this)
                timer.postDelayed(this, interval.toLong()) // fire in milliseconds
            }
        }
    }

    override fun run() {
        tetris!!.moveShape(MotionEventTranslater.KEY_DOWN)
        kickTimer()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        tetris!!.setPixelGeometry(width, height)
        motionTranslater!!.setGeometry(width, height)
    }


    override fun surfaceCreated(holder: SurfaceHolder) {
        tetris = GameView(this)
        startTimer()
        tetris!!.startPainter(this, holder)
    }


    override fun surfaceDestroyed(holder: SurfaceHolder) {
        tetris!!.stopPainter()
        stopTimer()
        writeState()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val motionTranslater = motionTranslater
        
        if (motionTranslater is MotionEventTranslater && motionTranslater.translateEvent(event)) tetris!!.moveShape(motionTranslater.getLastEvent())
        return true
    }

    private val STATE_FILE = "app_state"

    private fun writeState() {
        try {
            val output = BufferedOutputStream(openFileOutput(STATE_FILE, Context.MODE_PRIVATE))
            tetris!!.writeState()
            output.close()
        } catch (e: Exception) {
            System.err.println(e.message)
        }
    }
}
