package view

import BaseContext
import ch.bailu.gtk.gtk.*
import ch.bailu.gtk.type.Str
import ch.bailu.tlg.InternalContext
import config.Layout
import config.Strings.appTitle
import control.Mouse
import lib.extension.setMarkup
import java.util.*

class Window(app: Application) {
    private val baseContext = BaseContext()
    private val iContext = InternalContext(baseContext)
    private val timer = Timer().apply {
        schedule(Tick(), iContext.timerInterval.toLong())
    }
    private var running = true

    private val status = Label(Str.NULL)
    private val canvasMain = CanvasMain(iContext, status)
    private val canvasPreview = CanvasPreview(iContext, status)

    init {
        ApplicationWindow(app).apply {
            val mainBox = Box(Orientation.VERTICAL, Layout.margin)
            val topBox = Box(Orientation.HORIZONTAL, Layout.margin)
            val bottomBox = Box(Orientation.HORIZONTAL, Layout.margin)
            val buttonGrid = Grid()

            mainBox.marginTop = Layout.margin
            mainBox.marginEnd = Layout.margin
            mainBox.marginBottom = Layout.margin
            mainBox.marginStart = Layout.margin

            mainBox.append(topBox)
            mainBox.append(canvasMain.drawingArea)
            mainBox.append(bottomBox)
            bottomBox.append(canvasPreview.drawingArea)

            topBox.append(status)

            bottomBox.append(buttonGrid)

            buttonGrid.attach(Button().apply {
                iconName  = Str("go-previous-symbolic")
                onClicked {
                    iContext.moveLeft(baseContext)
                    update()
                }
            }, 0, 1, 1,1)

            buttonGrid.attach(Button().apply {
                iconName  = Str("go-next-symbolic")
                onClicked {
                    iContext.moveRight(baseContext)
                    update()
                }
            }, 2, 1, 1,1)

            buttonGrid.attach(Button().apply {
                iconName  = Str("go-down-symbolic")
                onClicked {
                    iContext.moveDown(baseContext)
                    update()
                }
            }, 1, 1, 1,1)

            buttonGrid.attach(Button().apply {
                iconName  = Str("go-up-symbolic")
                onClicked {
                    iContext.moveTurn(baseContext)
                    update()
                }
            }, 1, 0, 1,1)


            Mouse(canvasMain.drawingArea, iContext, baseContext) {
                update()
            }

            child = mainBox
            title =  appTitle
            titlebar = Header(this, app, iContext, baseContext) { update() }.headerBar
            setDefaultSize(Layout.windowWidth, Layout.windowHeight)

            onDestroy {
                running = false
                timer.cancel()
                iContext.writeState(baseContext)
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
                iContext.moveDown(baseContext)
                timer.schedule(Tick(), iContext.timerInterval.toLong())
                update()
            }
        }
    }

    private fun update() {
        println("Update called")
        canvasMain.update()
        canvasPreview.update()
        status.setMarkup("<b>Score:</b> ${iContext.score} - <b>Level:</b> ${iContext.level} - ${iContext.stateText}")
    }
}
