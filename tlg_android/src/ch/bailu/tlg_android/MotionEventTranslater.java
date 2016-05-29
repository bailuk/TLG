package ch.bailu.tlg_android;

import android.view.MotionEvent;
import ch.bailu.tlg.StateInit;

public class MotionEventTranslater {
    public final static int KEY_LEFT=-1;
    public final static int KEY_RIGHT=1;
    public final static int KEY_UP=KEY_LEFT*2;
    public final static int KEY_DOWN=KEY_RIGHT*2;

    
    private class MotionTranslater {
        private float delta;
        private float latestPixel;
        private int latestEvent;
        private float trigger=40;
        
        public void setTrigger(float t) {
            trigger=t;
        }
        
        
        public void reset(float p) {
            latestPixel = p;
            reset();
        }
        
        public void reset() {
            delta=0;
            latestEvent=0;
        }

        
        public boolean recordEvent(float p) {
            boolean r=false;
            recordMotionEvent(p);
            
            if (r = (delta < -1 * trigger) ) {
                latestEvent=KEY_LEFT;
            } else if (r= (delta > trigger) ) {
                latestEvent=KEY_RIGHT;
            }
            
            if (r) {
                delta %= trigger;
            }
            return r;
        }
        private void recordMotionEvent(float p) {
            delta += p - latestPixel;
            setPosition(p);
        }

        public void setPosition(float p) {
            latestPixel=p;
        }
        
        public int getLatestEvent() {
            return latestEvent;
        }
    }
    
    private MotionTranslater verticalMotion = new MotionTranslater();
    private MotionTranslater horizontalMotion = new MotionTranslater();;
    private int tapEvent=0;
    private int width,height;
    
    public int getLastEvent() {
        int ve = verticalMotion.getLatestEvent();
        int he = horizontalMotion.getLatestEvent();
        int e=tapEvent;
        if (ve!=0) e= ve*2;
        else if (he!=0) e=he;
        return e;
    }
    
    public void setGeometry(int w, int h) {
        width=w;
        height=h;
        verticalMotion.setTrigger(h/StateInit.MATRIX_HEIGHT);
        horizontalMotion.setTrigger(w/StateInit.MATRIX_WIDTH);
    }
    
    public boolean translateEvent(MotionEvent event) {
        boolean ret=false;
        
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            initializeMotionEvent(event);
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            ret = recordEvent(event);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            ret = translateEventFromTap(event);
        }
        return ret;
    }
    
    private boolean translateEventFromTap(MotionEvent event) {
        boolean r=false;
        int tline=height/3;
        int bline=height-tline;
        
        int lline=width/3;
        int rline=width-lline;
        
        int he=0,ve=0;
        
        if ( event.getX()<lline ) {
            he=KEY_LEFT;
        } else if ( event.getX()> rline ){
            he=KEY_RIGHT;
        }
        
        if ( event.getY()<tline ) {
            ve=KEY_UP;
        } else if ( event.getY() > bline ){
            ve=KEY_DOWN;
        }

        if ( r = (he != 0 && ve==0) ) tapEvent=he;
        else if (r = (ve != 0 && he == 0) ) tapEvent=ve;
        return r;
    }

    private void initializeMotionEvent(MotionEvent event) {
        horizontalMotion.reset(event.getRawX());
        verticalMotion.reset(event.getRawY());
    }

    
    private boolean recordEvent(MotionEvent event) {
        boolean r=false;
        
        if ( (r=horizontalMotion.recordEvent(event.getRawX())) ) {
            verticalMotion.reset();
        } else if ( (r=verticalMotion.recordEvent(event.getRawY())) )  {
            horizontalMotion.reset();
        }
        return r;
    }

}
