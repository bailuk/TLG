import Configuration.windowHeight
import Configuration.windowTitle
import Configuration.windowWidth
import ch.bailu.tlg.InternalContext
import share.Keyboard
import java.awt.Frame
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.io.IOException

class Window internal constructor() : Frame() {
    private val bContext = BaseContext()
    private val iContext = InternalContext(bContext)
    private val canvas: Canvas = Canvas(iContext, bContext)

    private fun cleanUp() {
        canvas.cleanUp()
    }

    init {
        add(canvas)
        addKeyListener(Keyboard(iContext, bContext) {canvas.update()})
        addWindowListener(object : WindowAdapter() {
            override fun windowClosing(w: WindowEvent) {
                try {
                    cleanUp()
                    System.exit(0)
                } catch (e: IOException) {
                    e.printStackTrace()
                    System.exit(1)
                }
            }
        })

        title = windowTitle
        setSize(windowWidth, windowHeight)
        isVisible = true
    }
}
