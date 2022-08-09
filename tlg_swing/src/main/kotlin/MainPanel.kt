import context.InternalContext
import geometry.TlgRectangle
import java.awt.Dimension
import java.awt.Graphics
import javax.swing.JPanel

class MainPanel(private val iContext: InternalContext) : JPanel() {

    override fun paintComponent(graphics: Graphics) {
        val d: Dimension = size
        iContext.mainLayout(TlgRectangle(0, 0, d.width, d.height))
        iContext.updateAllMain(AwtGraphicsContext(graphics))
    }

    fun update() {
        iContext.updateMain(AwtGraphicsContext(graphics))
        graphics.dispose()
    }
}
