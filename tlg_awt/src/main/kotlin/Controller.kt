import ch.bailu.tlg.InternalContext
import ch.bailu.tlg.PlatformContext
import java.util.*

class Controller(private val iContext: InternalContext, private val bContext: PlatformContext) {
    private val timer = Timer()
    val previewCanvas = PreviewCanvas(iContext)
    val mainCanvas = MainCanvas(iContext)

    init {
        schedule()
    }

    private fun schedule() {
        timer.schedule(Task(), iContext.timerInterval.toLong())
    }

    private inner class Task(): TimerTask() {
        override fun run() {
            iContext.moveDown(bContext)
            previewCanvas.update()
            mainCanvas.update()
            schedule()
        }
    }

    fun cleanUp() {
        iContext.writeState(bContext)
    }
}
