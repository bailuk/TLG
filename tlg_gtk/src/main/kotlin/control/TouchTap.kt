package control

import ch.bailu.gtk.gtk.DrawingArea
import ch.bailu.gtk.gtk.GestureClick
import context.GtkBaseContext
import tlg.context.InternalContext

class TouchTap(private val drawingArea: DrawingArea,
               private val iContext: InternalContext,
               private val bContext: GtkBaseContext,
               private val update: ()->Unit) {
    private var singleClick = false

    init {
        GestureClick().apply {
            drawingArea.addController(this)
            onPressed { i, _, _ -> singleClick = i == 1}
            onStopped { singleClick = false }
            onReleased { n_press: Int, x: Double, y: Double ->
                if (n_press == 1 && singleClick) {
                    singleClick(x, y)
                }
            }
        }
    }

    private fun singleClick(x: Double, y: Double) {
        val height = drawingArea.height / 3
        val width = drawingArea.width / 3

        if (y < height) {
            iContext.moveTurn(bContext)
            update()
        } else if (y > height * 2) {
            iContext.moveDown(bContext)
            update()
        } else if (x < width) {
            iContext.moveLeft(bContext)
            update()
        } else if (x > width * 2) {
            iContext.moveRight(bContext)
            update()
        }
    }
}
