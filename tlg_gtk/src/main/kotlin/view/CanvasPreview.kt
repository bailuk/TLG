package view

import ch.bailu.gtk.GTK
import ch.bailu.gtk.cairo.Context
import ch.bailu.gtk.gtk.DrawingArea
import ch.bailu.gtk.type.Pointer
import context.GtkGraphicsContext
import tlg.context.InternalContext
import tlg.geometry.TlgRectangle

class CanvasPreview(private val iContext: InternalContext) {
    val drawingArea = DrawingArea().apply {
        hexpand = GTK.TRUE

        setDrawFunc({ drawing_area: DrawingArea, cr: Context, _: Int, _: Int, _: Pointer? ->
            iContext.previewLayout(
                TlgRectangle(
                    1, 1,
                    drawing_area.allocatedWidth-2,
                    drawing_area.allocatedHeight-2
                )
            )
            iContext.updateAllPreview(GtkGraphicsContext(cr))
        }, null) { _: Pointer? -> }

    }

    fun update() {
        drawingArea.queueDraw()
    }
}
