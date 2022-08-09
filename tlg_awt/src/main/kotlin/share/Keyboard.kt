package share

import AwtBaseContext
import tlg.context.InternalContext
import java.awt.event.KeyEvent
import java.awt.event.KeyListener

class Keyboard(private val iContext: InternalContext, private val bContext: AwtBaseContext, private val update: () -> Unit) : KeyListener {

    override fun keyReleased(key: KeyEvent) {}

    override fun keyPressed(key: KeyEvent) {
        when(key.keyCode) {
            KeyEvent.VK_N -> {
                iContext.startNewGame(bContext)
                update()
            }
            KeyEvent.VK_DOWN -> {
                iContext.moveDown(bContext)
                update()
            }
            KeyEvent.VK_LEFT -> {
                iContext.moveLeft(bContext)
                update()
            }
            KeyEvent.VK_RIGHT -> {
                iContext.moveRight(bContext)
                update()
            }
            KeyEvent.VK_UP -> {
                iContext.moveTurn(bContext)
                update()
            }
            KeyEvent.VK_G -> {
                iContext.toggleGrid()
                update()
            }
            KeyEvent.VK_P -> {
                iContext.togglePause(bContext)
                update()
            }
            KeyEvent.VK_SPACE -> {
                iContext.togglePause(bContext)
                update()
            }
        }
    }

    override fun keyTyped(key: KeyEvent) {}
}
