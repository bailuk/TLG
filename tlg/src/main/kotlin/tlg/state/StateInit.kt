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

class StateInit(pContext: InternalContext) : State(pContext) {
    override fun init(pContext: PlatformContext): State {
        return try {
            restore(pContext)
        } catch (e: IOException) {
            create(pContext)
        }
    }

    private fun create(pContext: PlatformContext): State {
        iContext.previewMatrix = MatrixWithShape(PREVIEW_SIZE, PREVIEW_SIZE)
        iContext.mainMatrix = MatrixLineManipulator(MATRIX_WIDTH, MATRIX_HEIGHT)
        iContext.currentScore = Score()
        return StateRunning(iContext).init(pContext)
    }

    @Throws(IOException::class)
    private fun restore(pContext: PlatformContext): State {
        val input = pContext.getInputStream(STATE_FILE)
        val version = ByteInteger.read(input)
        if (version != STATE_FILE_VERSION) throw IOException()
        iContext.previewMatrix = MatrixWithShape(input)
        iContext.mainMatrix = MatrixLineManipulator(input)
        iContext.currentScore = Score(input)
        val state = restoreContextFactory(iContext, ByteInteger.read(input))
        input.close()
        return state
    }

    private fun restoreContextFactory(iContext: InternalContext, id: Int): State {
        return when(id) {
            StateRunning.ID -> StateRunning(iContext)
            StatePaused.ID -> StatePaused(iContext)
            StateHighScore.ID -> StateHighScore(iContext)
            else -> StateLocked(iContext)
        }
    }

    override val id =  ID

    companion object {
        const val ID = 0
    }
}
