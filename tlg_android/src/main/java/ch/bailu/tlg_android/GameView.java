package ch.bailu.tlg_android;


import java.io.IOException;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.EditText;
import ch.bailu.tlg.InternalContext;
import ch.bailu.tlg.StateHighscore;
import ch.bailu.tlg.TlgRectangle;




public class GameView  {
    
    private final AndroidContext tContext;
    private final PaintThread paintThread;
    private final InternalContext iContext;

    private class GetNameDialog  implements DialogInterface.OnClickListener{
        private EditText edit;


        public GetNameDialog() {
            String title = "Your name?";
            edit = new EditText(tContext.getAndroidContext());
            
            AlertDialog.Builder builder = new AlertDialog.Builder(tContext.getAndroidContext());
            Dialog dialog;
            
            builder.setTitle(title);
            builder.setView(edit);
            builder.setCancelable(true);
            builder.setPositiveButton("ok", 
                    this);
            builder.setNegativeButton("cancel", 
                    new DialogInterface.OnClickListener () {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {}
                    });
            
            dialog = builder.create();
            dialog.show();
        }

        
        @Override
        public void onClick(DialogInterface dialog, int which) {
            iContext.setHighscoreName(tContext, edit.getText().toString());
        }

    }
    
    public GameView(Context c) {
        tContext = new NoDrawContext(c) {
            @Override
            public void onNewHighscore() {
                Log.e("GameView", "onNewHighscore()");
                new GetNameDialog();
            }
        };
        
        
        iContext = new InternalContext(tContext);
        paintThread=new PaintThread(iContext, tContext);
    }
    
    
    public synchronized void moveShape(int e) {
        if (e == MotionEventTranslater.KEY_LEFT) 
            iContext.moveLeft(tContext);
            
        else if (e== MotionEventTranslater.KEY_RIGHT)
            iContext.moveRight(tContext);

        else if (e==MotionEventTranslater.KEY_DOWN)
            iContext.moveDown(tContext);

        else if (e==MotionEventTranslater.KEY_UP)
            iContext.moveTurn(tContext);
        
        paintThread.update();
    }
    
    
    public synchronized void newGame() {
        iContext.startNewGame(tContext);
        paintThread.update();
    }

    
    public synchronized void pause() {
        iContext.togglePause(tContext);
        paintThread.update();
    }
    
    
    public synchronized void pauseOrResume() {
        iContext.togglePause(tContext);
        paintThread.update();
    }
    
    
    public synchronized int getTimerInterval() {
        return iContext.getTimerInterval();
    }
    
    
    public synchronized void toggleGrid() {
        iContext.toggleGrid();
        paintThread.update();
    }

    
    public synchronized void setPixelGeometry(int width, int height) {
        iContext.layout(new TlgRectangle(0,0,width-1, height-1));
        paintThread.updateAll();
    }
    
    
    public synchronized void writeState() throws IOException {
        iContext.writeState(tContext);
    }


    public void startPainter(Context context, SurfaceHolder surfaceHolder) {
        paintThread.startPainter(context, surfaceHolder);
    }


    public void stopPainter() {
        paintThread.stopPainter();
    }


    public void setHighscoreName() {
        if (iContext.getID() == StateHighscore.ID) new GetNameDialog();
    }
}
