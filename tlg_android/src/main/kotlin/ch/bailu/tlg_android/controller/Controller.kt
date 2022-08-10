package ch.bailu.tlg_android.controller

import android.content.Context
import android.view.MotionEvent
import ch.bailu.tlg_android.context.AndroidBaseContext
import ch.bailu.tlg_android.view.GamePreview
import ch.bailu.tlg_android.view.GameView
import tlg.context.InternalContext

class Controller(context: Context) {
    private val pContext = AndroidBaseContext(context)
    private val iContext = InternalContext(pContext)

    private val gameTimer = GameTimer(this)

    val gameMainView = GameView(iContext, pContext)
    val gamePreview = GamePreview(iContext, pContext)

    fun moveShape(e: Int) {
        when(e) {
            MotionEventTranslator.KEY_LEFT -> iContext.moveLeft(pContext)
            MotionEventTranslator.KEY_RIGHT -> iContext.moveRight(pContext)
            MotionEventTranslator.KEY_DOWN -> iContext.moveDown(pContext)
            MotionEventTranslator.KEY_UP -> iContext.moveTurn(pContext)
        }
        gameMainView.update()
        gamePreview.update()
    }

    fun newGame() {
        iContext.startNewGame(pContext)
        gameMainView.update()
        gamePreview.update()
        gameTimer.start()
    }

    fun pauseOrResume() {
        iContext.togglePause(pContext)
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
        iContext.writeState(pContext)
        gameTimer.stop()
    }

    fun onActivityResume() {
        gameTimer.start()
    }
}
