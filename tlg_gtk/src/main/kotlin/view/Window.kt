package view

import context.AwtBaseContext
import ch.bailu.gtk.gtk.*
import ch.bailu.gtk.type.Str
import config.CSS
import config.Layout
import config.Strings.appTitle
import context.InternalContext
import control.Keyboard
import control.Mouse
import lib.extension.setMarkup
import java.util.*

class Window(app: Application) {
    private val pContext = AwtBaseContext()
    private val iContext = InternalContext(pContext)
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

            bottomBox.append(Buttons(iContext, pContext) { update() }.grid)
            Mouse(canvasMain.drawingArea, iContext, pContext) { update() }
            addController(Keyboard(iContext, pContext) { update() }.eventControllerKey)

            child = mainBox
            title =  appTitle
            titlebar = Header(this, app, iContext, pContext) { update() }.headerBar
            setDefaultSize(Layout.windowWidth, Layout.windowHeight)

            onDestroy {
                running = false
                timer.cancel()
                iContext.writeState(pContext)
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
                iContext.moveDown(pContext)
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
