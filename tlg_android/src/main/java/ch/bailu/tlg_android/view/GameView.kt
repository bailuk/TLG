package ch.bailu.tlg_android.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.SurfaceHolder
import android.widget.EditText
import ch.bailu.tlg.InternalContext
import ch.bailu.tlg.StateHighscore
import ch.bailu.tlg.TlgRectangle
import ch.bailu.tlg_android.context.AndroidContext
import ch.bailu.tlg_android.context.NoDrawContext
import ch.bailu.tlg_android.controller.MotionEventTranslater
import java.io.IOException

class GameView(context: Context) {
    private val tContext: AndroidContext
    private val paintThread: PaintThread
    private val iContext: InternalContext

    private inner class GetNameDialog : DialogInterface.OnClickListener {
        private val edit: EditText
        override fun onClick(dialog: DialogInterface, which: Int) {
            iContext.setHighscoreName(tContext, edit.text.toString())
        }

        init {
            val title = "Your name?"
            edit = EditText(tContext.getAndroidContext())
            val builder = AlertDialog.Builder(tContext.getAndroidContext())
            val dialog: Dialog
            builder.setTitle(title)
            builder.setView(edit)
            builder.setCancelable(true)
            builder.setPositiveButton(
                "ok",
                this
            )
            builder.setNegativeButton(
                "cancel"
            ) { _, _ -> }
            dialog = builder.create()
            dialog.show()
        }
    }

    init {
        tContext = object : NoDrawContext(context) {
            override fun onNewHighscore() {
                Log.e("GameView", "onNewHighscore()")
                GetNameDialog()
            }
        }
        iContext = InternalContext(tContext)
        paintThread = PaintThread(iContext, tContext)
    }

    @Synchronized
    fun moveShape(e: Int) {
        if (e == MotionEventTranslater.KEY_LEFT) iContext.moveLeft(tContext) else if (e == MotionEventTranslater.KEY_RIGHT) iContext.moveRight(
            tContext
        ) else if (e == MotionEventTranslater.KEY_DOWN) iContext.moveDown(tContext) else if (e == MotionEventTranslater.KEY_UP) iContext.moveTurn(
            tContext
        )
        paintThread.update()
    }


    @Synchronized
    fun newGame() {
        iContext.startNewGame(tContext)
        paintThread.update()
    }


    @Synchronized
    fun pause() {
        iContext.togglePause(tContext)
        paintThread.update()
    }


    @Synchronized
    fun pauseOrResume() {
        iContext.togglePause(tContext)
        paintThread.update()
    }


    @Synchronized
    fun getTimerInterval(): Int {
        return iContext.timerInterval
    }


    @Synchronized
    fun toggleGrid() {
        iContext.toggleGrid()
        paintThread.update()
    }


    @Synchronized
    fun setPixelGeometry(width: Int, height: Int) {
        iContext.layout(TlgRectangle(0, 0, width - 1, height - 1))
        paintThread.updateAll()
    }


    @Synchronized
    @Throws(IOException::class)
    fun writeState() {
        iContext.writeState(tContext)
    }


    fun startPainter(context: Context, surfaceHolder: SurfaceHolder) {
        paintThread.startPainter(context, surfaceHolder)
    }

    fun stopPainter() {
        paintThread.stopPainter()
    }

    fun setHighscoreName() {
        if (iContext.id == StateHighscore.ID) GetNameDialog()
    }
}
