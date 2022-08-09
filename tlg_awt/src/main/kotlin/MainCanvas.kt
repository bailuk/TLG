import ch.bailu.tlg.InternalContext
import ch.bailu.tlg.TlgRectangle
import java.awt.Canvas
import java.awt.Graphics

class MainCanvas(private val iContext: InternalContext) : Canvas() {

    override fun paint(g: Graphics) {
        val d = this.size
        iContext.mainLayout(TlgRectangle(0, 0, d.width, d.height))
        iContext.updateAllMain(AwtGraphicsContext(g))
    }

    fun update() {
        val graphics = this.graphics
        if (graphics is Graphics) {
            iContext.updateMain(AwtGraphicsContext(graphics))
            graphics.dispose()
        }
    }
}
