import ch.bailu.tlg.InternalContext
import ch.bailu.tlg.TlgRectangle
import java.awt.Canvas
import java.awt.Graphics

class PreviewCanvas(private val iContext: InternalContext) : Canvas() {
    init {
        setSize(64,64)
    }

    override fun paint(g: Graphics) {
        val d = this.size
        iContext.previewLayout(TlgRectangle(0, 0, d.width, d.height))
        iContext.updateAllPreview(AwtGraphicsContext(g))
    }

    fun update() {
        val graphics = this.graphics
        if (graphics is Graphics) {
            iContext.updatePreview(AwtGraphicsContext(graphics))
            graphics.dispose()
        }
    }
}
