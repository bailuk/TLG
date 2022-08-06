package view

import ch.bailu.gtk.GTK
import context.BaseContext
import ch.bailu.gtk.gtk.*
import ch.bailu.gtk.type.Str
import ch.bailu.tlg.InternalContext
import config.CSS
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
    private var statusVal = ""
    private val level = Label(Str.NULL)
    private var levelVal = -1
    private val score = Label(Str.NULL)
    private var scoreVal = -1

    private val canvasMain = CanvasMain(iContext)
    private val canvasPreview = CanvasPreview(iContext)

    init {
        ApplicationWindow(app).apply {
            val mainBox = Box(Orientation.VERTICAL, Layout.margin)
            val topBox = Box(Orientation.HORIZONTAL, Layout.margin)
            val bottomBox = Box(Orientation.HORIZONTAL, Layout.margin)

            level.marginStart = Layout.margin * 2
            level.marginEnd = Layout.margin * 2
            mainBox.marginTop = Layout.margin
            mainBox.marginEnd = Layout.margin
            mainBox.marginBottom = Layout.margin
            mainBox.marginStart = Layout.margin

            mainBox.append(topBox)
            mainBox.append(canvasMain.drawingArea)
            mainBox.append(bottomBox)
            bottomBox.append(canvasPreview.drawingArea)

            topBox.append(score)
            topBox.append(level)
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

            CSS.addStyleProvider(this)
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

        if (levelVal != iContext.level) {
            levelVal = iContext.level
            level.setMarkup("<b>Level:</b> $levelVal")
        }

        if (scoreVal != iContext.score) {
            scoreVal = iContext.score
            score.setMarkup("<b>Score:</b> $scoreVal")
        }

        if (statusVal != iContext.stateText) {
            statusVal = iContext.stateText
            status.setMarkup(statusVal)
        }
    }
}
