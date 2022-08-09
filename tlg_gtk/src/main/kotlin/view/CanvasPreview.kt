package view

import ch.bailu.gtk.GTK
import ch.bailu.gtk.cairo.Context
import ch.bailu.gtk.gtk.DrawingArea
import ch.bailu.gtk.type.Pointer
import context.AwtGraphicsContext
import context.InternalContext
import geometry.TlgRectangle

class CanvasPreview(private val iContext: InternalContext) {
    val drawingArea = DrawingArea().apply {
        hexpand = GTK.TRUE

        setDrawFunc({ drawing_area: DrawingArea, cr: Context, _: Int, _: Int, _: Pointer? ->
            iContext.previewLayout(
                TlgRectangle(
                    0, 0,
                    drawing_area.allocatedWidth,
                    drawing_area.allocatedHeight
                )
            )
            iContext.updateAllPreview(AwtGraphicsContext(cr))
        }, null) { _: Pointer? -> }

    }

    fun update() {
        drawingArea.queueDraw()
    }
}
