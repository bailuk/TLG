package control

import ch.bailu.gtk.gtk.DrawingArea
import ch.bailu.gtk.gtk.GestureDrag
import context.GtkBaseContext
import tlg.context.InternalContext

/**
 *  Mouse and touch drag
 */
class TouchDrag(drawingArea: DrawingArea,
                private val iContext: InternalContext,
                private val pContext: GtkBaseContext,
                private val updateView: () -> Unit) {

    companion object {
        const val KEY_LEFT = -1
        const val KEY_RIGHT = 1
        const val KEY_UP = KEY_LEFT * 2
        const val KEY_DOWN = KEY_RIGHT * 2
    }

    private var dragX = 0.0
    private var dragY = 0.0

    private val verticalMotion = MotionTranslator()
    private val horizontalMotion = MotionTranslator()

    init {
        drawingArea.addController(GestureDrag().apply {
            button = 0
            onDragBegin { _: Double, _: Double ->
                dragX = 0.0
                dragY = 0.0
                horizontalMotion.reset()
                verticalMotion.reset()
            }

            onDragUpdate { offset_x: Double, offset_y: Double ->
                val deltaX: Double = offset_x - dragX
                val deltaY: Double = offset_y - dragY

                var update = false

                if (horizontalMotion.recordEvent(deltaX)) {
                    executeEvent(horizontalMotion.takeEvent())
                    update = true
                }

                if (verticalMotion.recordEvent(deltaY)) {
                    executeEvent(verticalMotion.takeEvent() * 2)
                    update = true
                }

                if (update) {
                    updateView()
                }

                dragX += deltaX
                dragY += deltaY
            }
        })
    }

    private fun executeEvent(event: Int) {
        when (event) {
            KEY_DOWN -> iContext.moveDown(pContext)
            KEY_RIGHT -> iContext.moveRight(pContext)
            KEY_LEFT -> iContext.moveLeft(pContext)
            KEY_UP -> iContext.moveTurn(pContext)
        }
    }
}
