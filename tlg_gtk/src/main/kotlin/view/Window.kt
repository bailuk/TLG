package view

import ch.bailu.gtk.gtk.*
import ch.bailu.gtk.type.Str
import config.CSS
import config.Layout
import config.Strings
import config.Strings.appTitle
import context.GtkBaseContext
import control.Keyboard
import control.TouchDrag
import control.TouchTap
import lib.extension.setMarkup
import tlg.context.InternalContext
import java.util.*

class Window(app: Application) {
    private val pContext = GtkBaseContext { updateStatusText() }
    private val iContext = InternalContext(pContext)
    private val timer = Timer().apply {
        schedule(Tick(), iContext.timerInterval.toLong())
    }
    private var running = true

    private val status = Label(Str.NULL)
    private val level = Label(Str.NULL)
    private val score = Label(Str.NULL)

    private val canvasMain = CanvasMain(iContext)
    private val canvasPreview = CanvasPreview(iContext)

    init {
        ApplicationWindow(app).apply {
            val mainBox = Box(Orientation.VERTICAL, Layout.margin)
            val topBox = Box(Orientation.HORIZONTAL, Layout.margin)
            val bottomBox = Box(Orientation.HORIZONTAL, Layout.margin)

            level.marginStart = Layout.margin * 2
            level.marginEnd = Layout.margin * 2
            topBox.marginTop = Layout.margin
            topBox.marginEnd = Layout.margin
            topBox.marginStart = Layout.margin
            bottomBox.marginEnd = Layout.margin
            bottomBox.marginBottom = Layout.margin
            bottomBox.marginStart = Layout.margin

            mainBox.addCssClass(Strings.darkBackground)
            mainBox.append(topBox)
            mainBox.append(canvasMain.drawingArea)
            mainBox.append(bottomBox)
            bottomBox.append(canvasPreview.drawingArea)

            topBox.append(score)
            topBox.append(level)
            topBox.append(status)

            bottomBox.append(Buttons(iContext, pContext) { update() }.grid)
            TouchTap(canvasMain.drawingArea, iContext, pContext) { update() }
            TouchDrag(canvasMain.drawingArea, iContext, pContext) { update() }
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
                updateStatusText()
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
    }

    private fun updateStatusText() {
        level.setMarkup("<b>Level:</b> ${iContext.level}")
        score.setMarkup("<b>Score:</b> ${iContext.score}")
        status.setMarkup(iContext.toString())
    }
}
