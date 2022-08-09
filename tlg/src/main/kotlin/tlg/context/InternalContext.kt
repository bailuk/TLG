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

class InternalContext(c: PlatformContext) {
    var currentScore: Score = Score()
    var previewMatrix: MatrixWithShape = MatrixWithShape(3,3)
    var mainMatrix: MatrixLineManipulator = MatrixLineManipulator(3,3)

    private var previewGeometry: TlgRectangle = TlgRectangle()
    private var mainGeometry: TlgRectangle = TlgRectangle()

    // IMPORTANT: StateInit initializes previewMatrix and mainMatrix (order)
    var state: State = StateInit(this).init(c)


    @Synchronized
    fun moveLeft(c: PlatformContext) {
        state = state.moveLeft(c)
    }

    @Synchronized
    fun moveRight(c: PlatformContext) {
        state = state.moveRight(c)
    }

    @Synchronized
    fun moveDown(c: PlatformContext) {
        state = state.moveDown(c)
    }

    @Synchronized
    fun moveTurn(c: PlatformContext) {
        state = state.moveTurn(c)
    }

    @Synchronized
    fun toggleGrid() {
        mainMatrix.toggleGrid()
    }

    @Synchronized
    fun togglePause(c: PlatformContext) {
        state = state.togglePause(c)
    }

    @Synchronized
    fun startNewGame(c: PlatformContext) {
        state = state.startNewGame(c)
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

    @Synchronized
    fun mainLayout(r: TlgRectangle) {
        mainGeometry = r
        mainMatrix.pixelGeometry = mainGeometry
    }

    @Synchronized
    fun previewLayout(r: TlgRectangle) {
        previewGeometry = r
        previewMatrix.pixelGeometry = previewGeometry
    }

    @Synchronized
    fun updatePreview(pContext: PlatformContext) {
        previewMatrix.update(pContext)
    }

    @Synchronized
    fun updateAllPreview(pContext: PlatformContext) {
        updateBackgroundPreview(pContext)
        previewMatrix.updateAll(pContext)
    }

    @Synchronized
    fun updateMain(pContext: PlatformContext) {
        mainMatrix.update(pContext)
    }

    @Synchronized
    fun updateAllMain(pContext: PlatformContext) {
        updateBackgroundMain(pContext)
        mainMatrix.updateAll(pContext)
    }

    private fun updateBackgroundMain(gc: PlatformContext) {
        gc.setDirtyRect(mainGeometry)
        gc.drawFilledRectangle(gc.colorBackground(), mainGeometry)
    }

    private fun updateBackgroundPreview(gc: PlatformContext) {
        gc.setDirtyRect(previewGeometry)
        gc.drawFilledRectangle(gc.colorBackground(), previewGeometry)
    }

    val timerInterval: Int
        get() = state.timerInterval

    fun setHighScoreName(pContext: PlatformContext, name: String) {
        state = state.setHighScoreName(pContext, name)
    }

    val id: Int
        get() = state.id
    val stateText: String
        get() = state.toString()
    val level: Int
        get() = currentScore.level
    val score: Int
        get() = currentScore.score
}
