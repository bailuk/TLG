import ch.bailu.tlg.InternalContext
import ch.bailu.tlg.PlatformContext
import ch.bailu.tlg.TlgRectangle
import java.awt.Canvas
import java.awt.Graphics
import java.util.*

class Canvas(private val iContext: InternalContext, private val bContext: PlatformContext) : Canvas() {

    private val timer = Timer()


    init {
        schedule()
    }

    private fun schedule() {
        timer.schedule(Task(), iContext.timerInterval.toLong())
    }

    private inner class Task(): TimerTask() {
        override fun run() {
            iContext.moveDown(bContext)
            update()
            schedule()
        }
    }
    override fun paint(g: Graphics) {
        val d = this.size
        iContext.mainLayout(TlgRectangle(0, 0, d.width, d.height))
        iContext.updateAllMain(GtkGraphicsContext(g))
    }

    fun update() {
        val g = graphics
        if (g != null) {
            iContext.updateMain(GtkGraphicsContext(g))
            g.dispose()
        }
    }

    fun cleanUp() {
        iContext.writeState(bContext)
    }
}
