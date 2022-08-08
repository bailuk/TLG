import Configuration.windowHeight
import Configuration.windowTitle
import Configuration.windowWidth
import ch.bailu.tlg.InternalContext
import share.Keyboard
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.io.IOException
import javax.swing.JFrame

class Window : JFrame() {
    private val bContext = BaseContext()
    private val iContext = InternalContext(bContext)
    private val gamePanel = GamePanel(iContext, bContext)

    init {
        add(gamePanel)
        addKeyListener(Keyboard(iContext, bContext){gamePanel.update()})
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
        gamePanel.cleanUp()
    }
}
