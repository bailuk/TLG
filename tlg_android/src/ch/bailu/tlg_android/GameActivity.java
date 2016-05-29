package ch.bailu.tlg_android;


import java.io.BufferedOutputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameActivity extends Activity implements Runnable, SurfaceHolder.Callback {
    
    private Handler               timer=null;
    private GameView   tetris;
    private MotionEventTranslater motionTranslater;
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        SurfaceView surface = new SurfaceView(this);
        surface.getHolder().addCallback(this);
        setContentView(surface);
        
        motionTranslater=new MotionEventTranslater();
   }

 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        inflateMenu(R.menu.menu, menu);
        return true;
    }
    
    protected void inflateMenu(int id, Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(id, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        
        switch (item.getItemId()) {
        case R.id.menu_grid:
            tetris.toggleGrid();
            return true;
        case R.id.menu_pause:
            tetris.pauseOrResume();
            startTimer();
            return true;
        case R.id.menu_start:
            tetris.newGame();
            startTimer();
            return true;
        case R.id.menu_about:
        	start(AboutActivity.class);
        	return true;
        case R.id.menu_score:
        	start(HighscoreActivity.class);
        	return true;
        case R.id.menu_name:
            tetris.setHighscoreName();
        default:
            return false;
        }
    }

    
    private void start(Class<?> activityClass) {
    	Intent intent = new Intent();
        intent.setClass(this, activityClass); 
        intent.setAction(activityClass.getName());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);         
    }

    
    private void startTimer() {
        if (timer==null) {
            timer = new Handler();
            kickTimer();
        }
    }
    
    private void stopTimer() {
        if (timer != null) {
            timer.removeCallbacks(this);
            timer=null;
        }
    }
    
    private void kickTimer() {
        if (timer != null) {
            int interval = tetris.getTimerInterval();
            if (interval==0) {
                stopTimer();
            } else {
                timer.removeCallbacks(this);
                timer.postDelayed(this, interval); // fire in milliseconds
            }
        }
    }

    @Override
    public void run() {
        tetris.moveShape(MotionEventTranslater.KEY_DOWN);
        kickTimer();
    }
   
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height) {
        tetris.setPixelGeometry(width,height);
        motionTranslater.setGeometry(width,height);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        tetris = new GameView(this);
        startTimer();
        tetris.startPainter(this,holder);
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        tetris.stopPainter();
        stopTimer();
        writeState();
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (motionTranslater.translateEvent(event))
            tetris.moveShape(motionTranslater.getLastEvent());
        return true;
    }
 
    
    private final static String STATE_FILE = "app_state";
    
    private void writeState() {
        try {
            BufferedOutputStream output = new BufferedOutputStream( openFileOutput(STATE_FILE, Context.MODE_PRIVATE));
            tetris.writeState();
            output.close();
        } catch (Exception e) {
            // FIXME do something
        }
        
    }

}