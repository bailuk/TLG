import ch.bailu.tlg.InternalContext
import ch.bailu.tlg.TlgRectangle
import java.awt.Dimension
import java.awt.Graphics
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.JPanel
import javax.swing.Timer

class GamePanel(private val iContext: InternalContext, private val bContext: BaseContext) : JPanel() {
    private val timer: Timer = Timer(iContext.timerInterval) {
        iContext.moveDown(bContext)
        update()
        setDelay()
    }.apply { start() }

    override fun paintComponent(g: Graphics) {
        val d: Dimension = size
        iContext.mainLayout(TlgRectangle(0, 0, d.width, d.height))
        iContext.updateAllMain(GraphicsContext(g))
    }

    fun update() {
        iContext.updateMain(GraphicsContext(graphics))
        graphics.dispose()
    }

    fun cleanUp() {
        iContext.writeState(bContext)
    }

    private fun setDelay() {
        if (timer.delay != iContext.timerInterval) {
            timer.delay = iContext.timerInterval
        }
    }
}
