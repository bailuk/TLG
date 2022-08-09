import context.InternalContext
import javax.swing.Timer

class Controller(private val iContext: InternalContext, private val bContext: AwtBaseContext) {
    val mainPanel = MainPanel(iContext)
    val previewPanel = PreviewPanel(iContext)

    private val timer: Timer = Timer(iContext.timerInterval) {
        iContext.moveDown(bContext)
        mainPanel.update()
        previewPanel.update()
        setDelay()
    }.apply { start() }

    private fun setDelay() {
        if (timer.delay != iContext.timerInterval) {
            timer.delay = iContext.timerInterval
        }
    }

    fun cleanUp() {
        iContext.writeState(bContext)
    }
}
