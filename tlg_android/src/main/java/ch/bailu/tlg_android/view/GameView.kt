package ch.bailu.tlg_android.view

import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import ch.bailu.tlg.InternalContext
import ch.bailu.tlg.TlgRectangle
import ch.bailu.tlg_android.context.AndroidBaseContext
import ch.bailu.tlg_android.controller.MotionEventTranslater

class GameView(iContext: InternalContext, tContext: AndroidBaseContext) {
    private val motionTranslator = MotionEventTranslater()
    private var paintThread = PaintThread(tContext, {}, {})

    val surface = SurfaceView(tContext.androidContext).apply {

        holder.addCallback(object: SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                paintThread = PaintThread(tContext, {iContext.updateMain(it)}, {iContext.updateAllMain(it)})
                paintThread.startPainter(context, holder)
            }

            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
                iContext.mainLayout(TlgRectangle(0,0,width, height))
                motionTranslator.setGeometry(width, height)
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                paintThread.stopPainter()
            }
        })
    }

    fun onTouchEvent(event: MotionEvent, cb: (event: Int)->Unit) {
        if (motionTranslator.translateEvent(event)) {
            cb(motionTranslator.getLastEvent())
        }
    }

    fun update() {
        paintThread.update()
    }
}
