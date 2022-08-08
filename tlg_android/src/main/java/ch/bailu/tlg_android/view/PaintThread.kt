package ch.bailu.tlg_android.view

import android.content.Context
import android.util.Log
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
        paint, paintAll, none
    }

    private val lock = ReentrantLock()
    private val condition = lock.newCondition()

    private var job = Job.none
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
            Log.d("PaintThread", "wait()")
            condition.await()
            Log.d("PaintThread", "wait() -> ok")

        }
    }

    private fun haveJob(): Boolean {
        return job != Job.none
    }

    private fun executeJob(j: Job) {
        if (j == Job.paint) {
            cbUpdate(tContext)
        } else {
            cbUpdateAll(tContext)
        }
        tContext.unlockCanvas()
    }

    private fun jobDone() {
        job = Job.none
    }

    fun updateAll() {
        requestJob(Job.paintAll)
    }

    fun update() {
        requestJob(Job.paint)
    }

    private fun requestJob(j: Job) {
        if (job != Job.paintAll) job = j
        lock.withLock {
            Log.d("PaintThread", " requestJob::signalAll()")
            condition.signalAll()
            Log.d("PaintThread", "::requestJob::signalAll() -> ok")
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
        Log.i("Painter", "Stop")
        runningThread = false

        lock.withLock {
            Log.d("PaintThread", "orderShutdown::signalAll()")
            condition.signalAll()
            Log.d("PaintThread", "orderShutdown::signalAll() -> ok")
        }
    }
}
