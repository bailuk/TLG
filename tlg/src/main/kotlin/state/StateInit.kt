package state

import TlgConfiguration.MATRIX_HEIGHT
import TlgConfiguration.MATRIX_WIDTH
import TlgConfiguration.STATE_FILE
import TlgConfiguration.STATE_FILE_VERSION
import context.ByteInteger
import context.InternalContext
import context.PlatformContext
import matrix.MatrixLineManipulator
import matrix.MatrixWithShape
import score.Score
import java.io.IOException

class StateInit(c: InternalContext) : State(c) {
    override fun init(c: PlatformContext): State {
        return try {
            restore(c)
        } catch (e: IOException) {
            create(c)
        }
    }

    private fun create(gc: PlatformContext): State {
        context.previewMatrix = MatrixWithShape(5, 5)
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
