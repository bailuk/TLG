package view

import context.GraphicsContext
import ch.bailu.gtk.GTK
import ch.bailu.gtk.cairo.Context
import ch.bailu.gtk.gtk.DrawingArea
import ch.bailu.gtk.gtk.Label
import ch.bailu.gtk.type.Pointer
import ch.bailu.tlg.InternalContext
import ch.bailu.tlg.TlgRectangle

class CanvasMain(private val iContext: InternalContext, score: Label) {
    val drawingArea = DrawingArea().apply {
        hexpand = GTK.TRUE
        vexpand = GTK.TRUE

        setDrawFunc({ drawing_area: DrawingArea, cr: Context, _: Int, _: Int, _: Pointer? ->
            iContext.mainLayout(
                TlgRectangle(
                    0, 0,
                    drawing_area.allocatedWidth,
                    drawing_area.allocatedHeight
                )
            )
            iContext.updateAllMain(GraphicsContext(cr, score))
        }, null) { _: Pointer? -> }

    }

    fun update() {
        drawingArea.queueDraw()
    }
}
