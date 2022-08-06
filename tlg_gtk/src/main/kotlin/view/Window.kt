package view

import BaseContext
import ch.bailu.gtk.gtk.*
import ch.bailu.gtk.type.Str
import ch.bailu.tlg.InternalContext
import config.Layout
import config.Strings.appTitle
import control.Mouse
import java.util.*

class Window(app: Application) {
    private val baseContext = BaseContext()
    private val iContext = InternalContext(baseContext)
    private val timer = Timer().apply {
        schedule(Tick(), iContext.timerInterval.toLong())
    }
    private var running = true

    private val score = createLabel("Score:")
    private val canvasMain = CanvasMain(iContext, score)
    private val canvasPreview = CanvasPreview(iContext, score)


    init {

        ApplicationWindow(app).apply {
            val grid = Grid()

            grid.attach(score, 0,0,10,1)
            grid.attach(canvasMain.drawingArea, 0, 1, 7, 10)
            grid.attach(canvasPreview.drawingArea, 7, 1, 3, 3)

            grid.attach(Button().apply {
                iconName  = Str("go-previous-symbolic")
                onClicked {
                    iContext.moveLeft(baseContext)
                    update()
                }
            }, 7, 6, 1,1)

            grid.attach(Button().apply {
                iconName  = Str("go-next-symbolic")
                onClicked {
                    iContext.moveRight(baseContext)
                    update()
                }
            }, 9, 6, 1,1)

            grid.attach(Button().apply {
                iconName  = Str("go-down-symbolic")
                onClicked {
                    iContext.moveDown(baseContext)
                    update()
                }
            }, 8, 6, 1,1)

            grid.attach(Button().apply {
                iconName  = Str("go-up-symbolic")
                onClicked {
                    iContext.moveTurn(baseContext)
                    update()
                }
            }, 8, 5, 1,1)


            grid.attach(Button().apply {
                label = Str("New Game")
                onClicked {
                    iContext.startNewGame(baseContext)
                    update()
                }
            }, 7, 8, 3,1)

            grid.attach(Button().apply {
                label = Str("Pause")
                onClicked {
                    iContext.togglePause(baseContext)
                    update()
                }
            }, 7, 9, 3,1)

            grid.attach(Button().apply {
                label = Str("Grid")
                onClicked {
                    iContext.toggleGrid()
                    update()
                }
            }, 7, 10, 3,1)


            Mouse(canvasMain.drawingArea, iContext, baseContext) {
                update()
            }

            child = grid
            title=  appTitle
            titlebar = Header().headerBar
            setDefaultSize(Layout.windowWidth, Layout.windowHeight)

            onDestroy {
                running = false
                timer.cancel()
                iContext.writeState(baseContext)
            }
            show()
        }
    }

    private fun createLabel(string: String): Label {
        val result = Label(Str(string))
        result.xalign = 0f
        return result
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
    }
}
