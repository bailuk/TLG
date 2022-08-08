package ch.bailu.tlg_android.controller

import android.content.Context
import android.view.MotionEvent
import ch.bailu.tlg.InternalContext
import ch.bailu.tlg.StateHighscore
import ch.bailu.tlg_android.context.NoDrawContext
import ch.bailu.tlg_android.view.GamePreview
import ch.bailu.tlg_android.view.GameView
import ch.bailu.tlg_android.view.NameDialog
import java.io.IOException

class Controller(context: Context) {
    private val tContext = object : NoDrawContext(context) {
            override fun onNewHighscore() {
                setHighscoreName()
            }
        }

    private val iContext = InternalContext(tContext)

    private val gameTimer = GameTimer(this)

    val gameMainView = GameView(iContext, tContext)
    val gamePreview = GamePreview(iContext, tContext)

    fun moveShape(e: Int) {
        if (e == MotionEventTranslater.KEY_LEFT) {
            iContext.moveLeft(tContext)
        } else if (e == MotionEventTranslater.KEY_RIGHT) {
            iContext.moveRight(tContext)
        } else if (e == MotionEventTranslater.KEY_DOWN) {
            iContext.moveDown(tContext)
        } else if (e == MotionEventTranslater.KEY_UP) {
            iContext.moveTurn(tContext)
        }
        gameMainView.update()
        gamePreview.update()
    }


    @Synchronized
    fun newGame() {
        iContext.startNewGame(tContext)
        gameMainView.update()
        gamePreview.update()
        gameTimer.start()
    }

    @Synchronized
    fun pause() {
        iContext.togglePause(tContext)
        gameMainView.update()
        gamePreview.update()
    }


    @Synchronized
    fun pauseOrResume() {
        iContext.togglePause(tContext)
        gameMainView.update()
        gamePreview.update()
        gameTimer.start()
    }

    fun getTimerInterval(): Int {
        return iContext.timerInterval
    }

    fun toggleGrid() {
        iContext.toggleGrid()
        gameMainView.update()
    }

    fun setHighscoreName() {
        if (iContext.id == StateHighscore.ID) {
            NameDialog(iContext, tContext)
        }
    }

    fun onTouchEvent(event: MotionEvent) {
        gameMainView.onTouchEvent(event) { moveShape(it) }

    }

    fun onActivityPause() {
        iContext.writeState(tContext)
        gameTimer.stop()
    }

    fun onActivityResume() {
        gameTimer.start()
    }
}
