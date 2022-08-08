package ch.bailu.tlg_android.view

import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import ch.bailu.tlg.InternalContext
import ch.bailu.tlg.TlgRectangle
import ch.bailu.tlg_android.context.AndroidContext
import ch.bailu.tlg_android.controller.MotionEventTranslater

class GameView(iContext: InternalContext, tContext: AndroidContext) {
    private val motionTranslator = MotionEventTranslater()
    private val paintThread = PaintThread(tContext, { iContext.updateMain(it) }, { iContext.updateAllMain(it) })

    val surface = SurfaceView(tContext.getAndroidContext()).apply {
        holder.addCallback(object: SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
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
