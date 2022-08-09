package state

import TlgConfiguration.SHAPE_PER_LEVEL
import context.InternalContext
import context.PlatformContext
import java.util.*

class StateRunning(c: InternalContext) : State(c) {
    override fun init(c: PlatformContext): State {
        erase()
        initPreview(c)
        return initMovingShape(c)
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

    override fun moveRight(c: PlatformContext): State {
        context.mainMatrix.moveShapeRight()
        return this
    }

    override fun moveLeft(c: PlatformContext): State {
        context.mainMatrix.moveShapeLeft()
        return this
    }

    override fun moveDown(c: PlatformContext): State {
        if (!context.mainMatrix.moveShapeDown()) {
            removeLines()
            return initMovingShape(c)
        }
        return this
    }

    override fun moveTurn(c: PlatformContext): State {
        context.mainMatrix.moveShapeTurn()
        return this
    }

    private fun removeLines() {
        context.currentScore.addLines(context.mainMatrix.eraseLines())
    }

    override fun togglePause(c: PlatformContext): State {
        return StatePaused(context).init(c)
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
