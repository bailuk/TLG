import ch.bailu.tlg.InternalContext
import ch.bailu.tlg.PlatformContext
import ch.bailu.tlg.TlgRectangle
import java.awt.Canvas
import java.awt.Graphics
import java.util.*

class Canvas(private val iContext: InternalContext, private val bContext: PlatformContext) : Canvas() {
    init {
        Timer().apply {
            schedule(object : TimerTask() {
                override fun run() {
                    iContext.moveDown(bContext)
                    update()
                    schedule(this, iContext.timerInterval.toLong())
                }
            }, iContext.timerInterval.toLong())
        }
    }

    override fun paint(g: Graphics) {
        val d = this.size
        iContext.mainLayout(TlgRectangle(0, 0, d.width, d.height))
        iContext.updateAllMain(GraphicsContext(g))
    }

    fun update() {
        val g = graphics
        if (g != null) {
            iContext.updateMain(GraphicsContext(g))
            g.dispose()
        }
    }

    fun cleanUp() {
        iContext.writeState(bContext)
    }
}
