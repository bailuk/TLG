package ch.bailu.tlg_android.view

import android.view.SurfaceHolder
import android.view.SurfaceHolder.Callback
import android.view.SurfaceView
import ch.bailu.tlg_android.context.AndroidBaseContext
import context.InternalContext
import geometry.TlgRectangle

class GamePreview(iContext: InternalContext, tContext: AndroidBaseContext) {
    private val paintThread = PaintThread(tContext, { iContext.updatePreview(it) }, { iContext.updateAllPreview(it) })


    val surface = SurfaceView(tContext.androidContext).apply {

        holder.addCallback(object: Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                paintThread.startPainter(context, holder)
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
                iContext.previewLayout(TlgRectangle(0,0,width, height))
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                paintThread.stopPainter()
            }
        })
    }

    fun update() {
        paintThread.update()
    }
}
