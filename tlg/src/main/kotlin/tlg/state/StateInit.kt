package tlg.state

import tlg.Configuration.MATRIX_HEIGHT
import tlg.Configuration.MATRIX_WIDTH
import tlg.Configuration.PREVIEW_SIZE
import tlg.Configuration.STATE_FILE
import tlg.Configuration.STATE_FILE_VERSION
import tlg.context.ByteInteger
import tlg.context.InternalContext
import tlg.context.PlatformContext
import tlg.matrix.MatrixLineManipulator
import tlg.matrix.MatrixWithShape
import tlg.score.Score
import java.io.IOException

class StateInit(c: InternalContext) : State(c) {
    override fun init(pContext: PlatformContext): State {
        return try {
            restore(pContext)
        } catch (e: IOException) {
            create(pContext)
        }
    }

    private fun create(gc: PlatformContext): State {
        context.previewMatrix = MatrixWithShape(PREVIEW_SIZE, PREVIEW_SIZE)
        context.mainMatrix = MatrixLineManipulator(MATRIX_WIDTH, MATRIX_HEIGHT)
        context.currentScore = Score()
        val state: State = StateRunning(context)
        state.init(gc)
        return state
    }

    @Throws(IOException::class)
    private fun restore(gc: PlatformContext): State {
        val input = gc.getInputStream(STATE_FILE)
        val version = ByteInteger.read(input)
        if (version != STATE_FILE_VERSION) throw IOException()
        context.previewMatrix = MatrixWithShape(input)
        context.mainMatrix = MatrixLineManipulator(input)
        context.currentScore = Score(input)
        val state = restoreContextFactory(context, ByteInteger.read(input))
        input.close()
        return state
    }

    override val id =  ID

    companion object {
        const val ID = 0
    }
}
