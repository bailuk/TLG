package tlg.state

import tlg.Configuration.SHAPE_PER_LEVEL
import tlg.context.InternalContext
import tlg.context.PlatformContext
import java.util.*

class StateRunning(c: InternalContext) : State(c) {
    override fun init(pContext: PlatformContext): State {
        erase()
        initPreview(pContext)
        return initMovingShape(pContext)
    }

    fun erase() {
        context.previewMatrix.erase()
        context.mainMatrix.erase()
        context.currentScore.reset()
    }

    private fun initPreview(gc: PlatformContext) {
        val random = Random()
        val shape = random.nextInt(context.currentScore.level * SHAPE_PER_LEVEL) + 1
        val color = gc.getColor(shape % gc.countOfColor())
        val turn = random.nextInt(4)
        context.previewMatrix.erase()
        context.previewMatrix.setRandomMovingShape(shape, color, turn)
        context.previewMatrix.centerMovingShape()
    }

    private fun initMovingShape(gc: PlatformContext): State {
        val r = initTarget()
        if (r) {
            initPreview(gc)
        } else {
            return gameOverStateFactory(context, gc)
        }
        return this
    }

    private fun initTarget(): Boolean {
        return context.mainMatrix.setMovingShape(context.previewMatrix.movingShape)
    }

    override fun moveRight(pContext: PlatformContext): State {
        context.mainMatrix.moveShapeRight()
        return this
    }

    override fun moveLeft(pContext: PlatformContext): State {
        context.mainMatrix.moveShapeLeft()
        return this
    }

    override fun moveDown(pContext: PlatformContext): State {
        if (!context.mainMatrix.moveShapeDown()) {
            removeLines()
            return initMovingShape(pContext)
        }
        return this
    }

    override fun moveTurn(pContext: PlatformContext): State {
        context.mainMatrix.moveShapeTurn()
        return this
    }

    private fun removeLines() {
        context.currentScore.addLines(context.mainMatrix.eraseLines())
    }

    override fun togglePause(pContext: PlatformContext): State {
        return StatePaused(context).init(pContext)
    }

    override val timerInterval: Int
        get() = context.currentScore.timerInterval

    override val id = ID

    override fun toString(): String {
        return "Playing"
    }

    companion object {
        const val ID = 3
    }
}
