package ch.bailu.tlg_android.view

import android.content.Context
import android.view.SurfaceHolder
import ch.bailu.tlg_android.context.AndroidContext
import ch.bailu.tlg_android.context.FullGraphicContext
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class PaintThread(
    private var tContext: AndroidContext,
    private val cbUpdate: (tContext: AndroidContext)->Unit,
    private val cbUpdateAll: (tContext: AndroidContext)->Unit) : Thread() {

    private enum class Job {
        PAINT, PAINT_ALL, NONE
    }

    private val lock = ReentrantLock()
    private val condition = lock.newCondition()

    private var job = Job.NONE
    private var runningThread = false

    override fun run() {
        while (runningThread) {
            waitAndExecuteJob()
        }
    }

    private fun waitAndExecuteJob() {
        if (haveJob()) {
            executeJob(job)
            jobDone()
        }
        waitForNextJob()
    }

    private fun waitForNextJob() {
        lock.withLock {
            condition.await()
        }
    }

    private fun haveJob(): Boolean {
        return job != Job.NONE
    }

    private fun executeJob(j: Job) {
        if (j == Job.PAINT) {
            cbUpdate(tContext)
        } else {
            cbUpdateAll(tContext)
        }
        tContext.unlockCanvas()
    }

    private fun jobDone() {
        job = Job.NONE
    }

    fun updateAll() {
        requestJob(Job.PAINT_ALL)
    }

    fun update() {
        if (job == Job.NONE) {
            requestJob(Job.PAINT)
        }
    }

    private fun requestJob(j: Job) {
        job = j
        lock.withLock {
            condition.signalAll()
        }
    }

    fun startPainter(context: Context, surfaceHolder: SurfaceHolder) {
        if (!runningThread) {
            tContext = FullGraphicContext(context, surfaceHolder)
            updateAll()
            runningThread = true
            start()
        }
    }

    fun stopPainter() {
        orderShutdown()
    }

    private fun orderShutdown() {
        runningThread = false
        lock.withLock {
            condition.signalAll()
        }
    }
}
