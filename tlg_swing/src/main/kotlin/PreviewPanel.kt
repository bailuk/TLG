import awt.AwtGraphicsContext
import tlg.context.InternalContext
import tlg.geometry.TlgRectangle
import java.awt.Dimension
import java.awt.Graphics
import javax.swing.JPanel

class PreviewPanel (private val iContext: InternalContext) : JPanel() {

    override fun getMinimumSize(): Dimension {
        return Dimension(64,64)
    }

    override fun getPreferredSize(): Dimension {
        return minimumSize
    }

    override fun getMaximumSize(): Dimension {
        return minimumSize
    }

    override fun paintComponent(g: Graphics) {
        val d: Dimension = size
        iContext.previewLayout(TlgRectangle(0, 0, d.width, d.height))
        iContext.updateAllPreview(AwtGraphicsContext(g))
    }

    fun update() {
        iContext.updatePreview(AwtGraphicsContext(graphics))
        graphics.dispose()
    }
}
