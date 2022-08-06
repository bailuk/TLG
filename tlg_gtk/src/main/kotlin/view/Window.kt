package view

import context.BaseContext
import ch.bailu.gtk.gtk.*
import ch.bailu.gtk.type.Str
import ch.bailu.tlg.InternalContext
import config.Layout
import config.Strings.appTitle
import control.Mouse
import lib.extension.setMarkup
import java.util.*

class Window(app: Application) {
    private val bContext = BaseContext()
    private val iContext = InternalContext(bContext)
    private val timer = Timer().apply {
        schedule(Tick(), iContext.timerInterval.toLong())
    }
    private var running = true

    private val status = Label(Str.NULL)
    private val canvasMain = CanvasMain(iContext)
    private val canvasPreview = CanvasPreview(iContext)

    init {
        ApplicationWindow(app).apply {
            val mainBox = Box(Orientation.VERTICAL, Layout.margin)
            val topBox = Box(Orientation.HORIZONTAL, Layout.margin)
            val bottomBox = Box(Orientation.HORIZONTAL, Layout.margin)

            mainBox.marginTop = Layout.margin
            mainBox.marginEnd = Layout.margin
            mainBox.marginBottom = Layout.margin
            mainBox.marginStart = Layout.margin

            mainBox.append(topBox)
            mainBox.append(canvasMain.drawingArea)
            mainBox.append(bottomBox)
            bottomBox.append(canvasPreview.drawingArea)

            topBox.append(status)

            bottomBox.append(Buttons(iContext, bContext) { update() }.grid)
            Mouse(canvasMain.drawingArea, iContext, bContext) { update() }
            addController(Keyboard(iContext, bContext) { update() }.eventControllerKey)

            child = mainBox
            title =  appTitle
            titlebar = Header(this, app, iContext, bContext) { update() }.headerBar
            setDefaultSize(Layout.windowWidth, Layout.windowHeight)

            onDestroy {
                running = false
                timer.cancel()
                iContext.writeState(bContext)
            }

            onShow {
                println("window::onShow()")
            }

            show()
        }
    }

    private inner class Tick : TimerTask() {
        override fun run() {
            if (running) {
                iContext.moveDown(bContext)
                timer.schedule(Tick(), iContext.timerInterval.toLong())
                update()
            }
        }
    }

    private fun update() {
        canvasMain.update()
        canvasPreview.update()
        status.setMarkup("<b>Score:</b> ${iContext.score} - <b>Level:</b> ${iContext.level} - ${iContext.stateText}")
    }
}
