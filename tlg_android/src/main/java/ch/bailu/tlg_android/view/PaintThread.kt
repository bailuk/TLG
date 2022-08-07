package ch.bailu.tlg_android.view

import android.content.Context
import android.util.Log
import android.view.SurfaceHolder
import ch.bailu.tlg.InternalContext
import ch.bailu.tlg_android.context.AndroidContext
import ch.bailu.tlg_android.context.FullGraphicContext
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class PaintThread(private val iContext: InternalContext, private var tContext: AndroidContext): Thread() {
    private enum class Job {
        paint, paintAll, none
    }

    private val lock = ReentrantLock()
    private val condition = lock.newCondition()

    private var job = Job.none
    private var runningThread = false

    @Synchronized
    override fun run() {
        while (runningThread) waitAndExecuteJob()
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
        return job != Job.none
    }

    private fun executeJob(j: Job) {
        if (j == Job.paint) {
            iContext.update(tContext)
        } else {
            iContext.updateAll(tContext)
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

    @Synchronized
    private fun requestJob(j: Job) {
        if (job != Job.paintAll) job = j
        lock.withLock {
            condition.signal()
        }
    }

    @Synchronized
    fun startPainter(context: Context, surfaceHolder: SurfaceHolder) {
        if (!runningThread) {
            Log.i("Painter", "Start")
            tContext = FullGraphicContext(context, surfaceHolder)
            updateAll()
            runningThread = true
            start()
        }
    }

    fun stopPainter() {
        orderShutdown()
    }

    @Synchronized
    private fun orderShutdown() {
        Log.i("Painter", "Stop")
        runningThread = false

        lock.withLock {
            condition.signal()
        }
    }
}
