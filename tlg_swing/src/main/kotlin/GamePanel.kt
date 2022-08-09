import ch.bailu.tlg.InternalContext
import ch.bailu.tlg.TlgRectangle
import java.awt.Dimension
import java.awt.Graphics
import javax.swing.JPanel
import javax.swing.Timer

class GamePanel(private val iContext: InternalContext, private val bContext: GtkBaseContext) : JPanel() {
    private val timer: Timer = Timer(iContext.timerInterval) {
        iContext.moveDown(bContext)
        update()
        setDelay()
    }.apply { start() }

    override fun paintComponent(g: Graphics) {
        val d: Dimension = size
        iContext.mainLayout(TlgRectangle(0, 0, d.width, d.height))
        iContext.updateAllMain(GtkGraphicsContext(g))
    }

    fun update() {
        iContext.updateMain(GtkGraphicsContext(graphics))
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
