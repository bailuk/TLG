package ch.bailu.tlg_android;

import android.content.Context;
import android.util.Log;
import android.view.SurfaceHolder;
import ch.bailu.tlg.InternalContext;

public class PaintThread extends Thread {
    private enum Job {paint, paintAll, none};
    private Job  job = Job.none;
    private AndroidContext tContext;
    private boolean runningThread=false;
    
    private final InternalContext iContext;
    
        
    public PaintThread(InternalContext t, AndroidContext c) {
    	iContext = t;
    	tContext = c;
    }
    
    
    
    public synchronized void run() {
        while(runningThread) waitAndExecuteJob();
    }

    
    private void waitAndExecuteJob() {
        if (haveJob()) {
            executeJob(job);
            jobDone();
        }
        waitForNextJob();
    }

    private void waitForNextJob() {
        try {
            wait();
        } catch (InterruptedException e) {
            runningThread=false;
        }
    }
    
    private boolean haveJob() {
        return (job!=Job.none);
    }
        
    private void executeJob(Job j) {
        if (j==Job.paint) {
            iContext.update(tContext);
        } else {
            iContext.updateAll(tContext);
        }
        tContext.unlockCanvas();
    }


    private void jobDone() {
        job=Job.none;
    }
        
    public void updateAll() {
        requestJob(Job.paintAll);
    }
    
    public void update() {
        requestJob(Job.paint);
    }
    
    private synchronized void requestJob(Job j) {
        if (job != Job.paintAll) job=j;
        notify();
    }

    public synchronized void startPainter(Context context, SurfaceHolder surfaceHolder) {
        if (!runningThread) {
            Log.i("Painter", "Start");
            tContext = new FullGraphicContext(context, surfaceHolder);
            updateAll();
            runningThread=true;
            start();
        }
    }
    
    public void stopPainter() {
        orderShutdown();
    }
    
    private synchronized void orderShutdown() {
        Log.i("Painter", "Stop");
        runningThread=false;
        notify();
    }
}

