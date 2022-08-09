package awt

import awt.share.Keyboard
import tlg.context.InternalContext
import java.awt.BorderLayout
import java.awt.Frame
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.io.IOException

class Window internal constructor() : Frame() {
    private val pContext = AwtBaseContext()
    private val iContext = InternalContext(pContext)
    private val controller: Controller = Controller(iContext, pContext)

    private val layout = BorderLayout()

    init {
        setLayout(layout)
        add(controller.previewCanvas, BorderLayout.NORTH)
        add(controller.mainCanvas, BorderLayout.CENTER)
        addKeyListener(Keyboard(iContext, pContext) { controller.mainCanvas.update() })
        addWindowListener(object : WindowAdapter() {
            override fun windowClosing(w: WindowEvent) {
                try {
                    controller.cleanUp()
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
