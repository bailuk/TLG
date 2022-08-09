package ch.bailu.tlg_android.controller

import android.content.Context
import android.view.MotionEvent
import ch.bailu.tlg_android.context.AndroidBaseContext
import ch.bailu.tlg_android.view.GamePreview
import ch.bailu.tlg_android.view.GameView
import tlg.context.InternalContext

class Controller(context: Context) {
    private val tContext = AndroidBaseContext(context)

    private val iContext = InternalContext(tContext)

    private val gameTimer = GameTimer(this)

    val gameMainView = GameView(iContext, tContext)
    val gamePreview = GamePreview(iContext, tContext)

    fun moveShape(e: Int) {
        when(e) {
            MotionEventTranslator.KEY_LEFT -> iContext.moveLeft(tContext)
            MotionEventTranslator.KEY_RIGHT -> iContext.moveRight(tContext)
            MotionEventTranslator.KEY_DOWN -> iContext.moveDown(tContext)
            MotionEventTranslator.KEY_UP -> iContext.moveTurn(tContext)
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
