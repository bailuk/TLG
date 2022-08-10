package tlg.context

import tlg.Configuration.STATE_FILE
import tlg.Configuration.STATE_FILE_VERSION
import tlg.geometry.TlgRectangle
import tlg.matrix.MatrixLineManipulator
import tlg.matrix.MatrixWithShape
import tlg.score.Score
import tlg.state.State
import tlg.state.StateInit
import java.io.IOException

class InternalContext(pContext: PlatformContext) {
    var currentScore: Score = Score()
    var previewMatrix: MatrixWithShape = MatrixWithShape(3,3)
    var mainMatrix: MatrixLineManipulator = MatrixLineManipulator(3,3)

    private var previewGeometry: TlgRectangle = TlgRectangle()
    private var mainGeometry: TlgRectangle = TlgRectangle()

    // IMPORTANT: StateInit initializes previewMatrix and mainMatrix (order)
    private var state: State = StateInit(this).init(pContext)

    @Synchronized fun moveLeft(pContext: PlatformContext) {
        setState(pContext, state.moveLeft(pContext))
    }

    @Synchronized fun moveRight(pContext: PlatformContext) {
        setState(pContext, state.moveRight(pContext))
    }

    @Synchronized fun moveDown(pContext: PlatformContext) {
        setState(pContext, state.moveDown(pContext))
    }

    @Synchronized fun moveTurn(pContext: PlatformContext) {
        setState(pContext, state.moveTurn(pContext))
    }

    @Synchronized fun toggleGrid() {
        mainMatrix.toggleGrid()
    }

    @Synchronized fun togglePause(pContext: PlatformContext) {
        setState(pContext, state.togglePause(pContext))
    }

    @Synchronized fun startNewGame(pContext: PlatformContext) {
        setState(pContext, state.startNewGame(pContext))
    }

    @Throws(IOException::class)
    fun writeState(gc: PlatformContext) {
        val output = gc.getOutputStream(STATE_FILE)
        ByteInteger.wrap(STATE_FILE_VERSION).writeState(output)
        previewMatrix.writeState(output)
        mainMatrix.writeState(output)
        currentScore.writeState(output)
        ByteInteger.wrap(id).writeState(output)
        output.close()
    }

    @Synchronized fun mainLayout(rect: TlgRectangle) {
        mainGeometry = rect
        mainMatrix.pixelGeometry = mainGeometry
    }

    @Synchronized fun previewLayout(rect: TlgRectangle) {
        previewGeometry = rect
        previewMatrix.pixelGeometry = previewGeometry
    }

    @Synchronized fun updatePreview(pContext: PlatformContext) {
        previewMatrix.update(pContext)
    }

    @Synchronized fun updateAllPreview(pContext: PlatformContext) {
        updateBackgroundPreview(pContext)
        previewMatrix.updateAll(pContext)
    }

    @Synchronized fun updateMain(pContext: PlatformContext) {
        mainMatrix.update(pContext)
    }

    @Synchronized fun updateAllMain(pContext: PlatformContext) {
        updateBackgroundMain(pContext)
        mainMatrix.updateAll(pContext)
    }

    @Synchronized private fun updateBackgroundMain(pContext: PlatformContext) {
        pContext.setDirtyRect(mainGeometry)
        pContext.drawFilledRectangle(pContext.colorBackground(), mainGeometry)
    }

    @Synchronized private fun updateBackgroundPreview(pContext: PlatformContext) {
        pContext.setDirtyRect(previewGeometry)
        pContext.drawFilledRectangle(pContext.colorBackground(), previewGeometry)
    }

    val timerInterval: Int
        get() = state.timerInterval

    val id: Int
        get() = state.id
    val level: Int
        get() = currentScore.level
    val score: Int
        get() = currentScore.score

    private fun setState(pContext: PlatformContext, state: State) {
        if (this.state.id != state.id) {
            this.state = state
            pContext.onStatusUpdated(this)
        } else {
            this.state = state
        }
    }

    override fun toString(): String {
        return state.toString()
    }
}
