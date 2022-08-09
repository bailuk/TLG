import tlg.context.InternalContext
import tlg.context.PlatformContext
import java.util.*

class Controller(private val iContext: InternalContext, private val pContext: PlatformContext) {
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
            iContext.moveDown(pContext)
            previewCanvas.update()
            mainCanvas.update()
            schedule()
        }
    }

    fun cleanUp() {
        iContext.writeState(pContext)
    }
}
