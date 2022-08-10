package tlg.state

import tlg.Configuration.SHAPE_PER_LEVEL
import tlg.context.InternalContext
import tlg.context.PlatformContext
import tlg.score.HighScoreList
import java.util.*

class StateRunning(iContext: InternalContext) : State(iContext) {
    override fun init(pContext: PlatformContext): State {
        erase()
        initPreview(pContext)
        return initMovingShape(pContext)
    }

    private fun erase() {
        iContext.previewMatrix.erase()
        iContext.mainMatrix.erase()
        iContext.currentScore.reset()
    }

    private fun initPreview(pContext: PlatformContext) {
        val random = Random()
        val shape = random.nextInt(iContext.currentScore.level * SHAPE_PER_LEVEL) + 1
        val color = pContext.getColor(shape % pContext.countOfColor())
        val turn = random.nextInt(4)
        iContext.previewMatrix.erase()
        iContext.previewMatrix.setRandomMovingShape(shape, color, turn)
        iContext.previewMatrix.centerMovingShape()
    }

    private fun initMovingShape(pContext: PlatformContext): State {
        val r = initTarget()
        if (r) {
            initPreview(pContext)
        } else {
            return gameOverStateFactory(iContext, pContext)
        }
        return this
    }

    private fun gameOverStateFactory(iContext: InternalContext, pContext: PlatformContext): State {
        val highScoreList = HighScoreList(pContext)
        return if (highScoreList.haveNewHighScore(iContext.currentScore.score)) {
            StateHighScore(iContext).init(pContext)
        } else {
            StateLocked(iContext).init(pContext)
        }
    }

    private fun initTarget(): Boolean {
        return iContext.mainMatrix.setMovingShape(iContext.previewMatrix.movingShape)
    }

    override fun moveRight(pContext: PlatformContext): State {
        iContext.mainMatrix.moveShapeRight()
        return this
    }

    override fun moveLeft(pContext: PlatformContext): State {
        iContext.mainMatrix.moveShapeLeft()
        return this
    }

    override fun moveDown(pContext: PlatformContext): State {
        if (!iContext.mainMatrix.moveShapeDown()) {
            removeLines()
            pContext.onStatusUpdated(iContext)
            return initMovingShape(pContext)
        }
        return this
    }

    override fun moveTurn(pContext: PlatformContext): State {
        iContext.mainMatrix.moveShapeTurn()
        return this
    }

    private fun removeLines() {
        iContext.currentScore.addLines(iContext.mainMatrix.eraseLines())
    }

    override fun togglePause(pContext: PlatformContext): State {
        return StatePaused(iContext).init(pContext)
    }

    override val timerInterval: Int
        get() = iContext.currentScore.timerInterval

    override val id = ID

    override fun toString(): String {
        return "Playing"
    }

    companion object {
        const val ID = 3
    }
}
