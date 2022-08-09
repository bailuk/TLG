import Configuration.windowHeight
import Configuration.windowTitle
import Configuration.windowWidth
import ch.bailu.tlg.InternalContext
import share.Keyboard
import java.awt.BorderLayout
import java.awt.Color
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.io.IOException
import javax.swing.BoxLayout
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JPanel

class Window : JFrame() {
    private val bContext = AwtBaseContext()
    private val iContext = InternalContext(bContext)
    private val controller = Controller(iContext, bContext)
    private val layout = BorderLayout()
    private val topPanel = JPanel()
    private val topLayout = BoxLayout(topPanel, BoxLayout.LINE_AXIS)

    init {
        setLayout(layout)
        topPanel.layout = topLayout

        contentPane.background = Color.BLACK

        layout.hgap = Configuration.margin
        layout.vgap = Configuration.margin
        add(controller.mainPanel, BorderLayout.CENTER)
        add(topPanel, BorderLayout.NORTH)

        topPanel.add(JPanel().apply { background = Color.BLACK })
        topPanel.add(controller.previewPanel)

        addKeyListener(Keyboard(iContext, bContext){controller.mainPanel.update()})
        addWindowListener(object : WindowAdapter() {
            override fun windowClosing(w: WindowEvent) {
                try {
                    cleanUp()
                    System.exit(0)
                } catch (e: IOException) {
                    print(e.message)
                    System.exit(1)
                }
            }
        })
        title = windowTitle
        setSize(windowWidth, windowHeight)
        isVisible = true
    }

    private fun cleanUp() {
        controller.cleanUp()
    }
}
