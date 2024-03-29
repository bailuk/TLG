package ch.bailu.tlg_android.context

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.Cap
import android.graphics.Rect
import android.view.SurfaceHolder

import tlg.geometry.TlgPoint
import tlg.geometry.TlgRectangle

class AndroidGraphicsContext(context: Context, private val surfaceHolder: SurfaceHolder): AndroidBaseContext(context) {

    var canvas: Canvas? = null
    private val paint = Paint()

    init {
        paint.strokeWidth = 0f
        paint.strokeCap = Cap.BUTT
        paint.style = Paint.Style.FILL
    }

    override fun setDirtyRect(rect: TlgRectangle) {
        unlockCanvas()
        canvas = surfaceHolder.lockCanvas(createAndroidRect(rect))
    }

    override fun unlockCanvas() {
        if (canvas != null) {
            surfaceHolder.unlockCanvasAndPost(canvas)
            canvas = null
        }
    }

    override fun drawLine(color: Int, p1: TlgPoint, p2: TlgPoint) {
        val canvas = canvas
        if (canvas is Canvas) {
            paint.color = color
            canvas.drawLine(p1.x.toFloat(), p1.y.toFloat(), p2.x.toFloat(), p2.y.toFloat(), paint)
        }
    }

    override fun drawFilledRectangle(color: Int, rect: TlgRectangle) {
        val canvas = canvas
        if (canvas is Canvas) {
            paint.color = color
            canvas.drawRect(createAndroidRect(rect), paint)
        }
    }

    private fun createAndroidRect(r: TlgRectangle): Rect {
        return Rect(r.left, r.top, r.right + 1, r.bottom + 1)
    }
}
