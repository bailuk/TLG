package awt

import awt.share.Keyboard
import tlg.context.InternalContext
import java.awt.BorderLayout
import java.awt.Frame
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.io.IOException

class Window internal constructor() : Frame() {
    private val bContext = AwtBaseContext()
    private val iContext = InternalContext(bContext)
    private val controller: Controller = Controller(iContext, bContext)

    private val layout = BorderLayout()

    private fun cleanUp() {
        controller.cleanUp()
    }

    init {
        setLayout(layout)
        add(controller.previewCanvas, BorderLayout.NORTH)
        add(controller.mainCanvas, BorderLayout.CENTER)
        addKeyListener(Keyboard(iContext, bContext) { controller.mainCanvas.update() })
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

        title = Configuration.windowTitle
        setSize(Configuration.windowWidth, Configuration.windowHeight)
        isVisible = true
    }
}
