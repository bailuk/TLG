package ch.bailu.tlg_android;

import java.io.File;

import android.content.Context;
import android.graphics.Color;
import ch.bailu.tlg.PlatformContext;

public abstract class AndroidContext extends PlatformContext {
	
    private static int palette[] = null;
    
    private final Context context;
    
    
    
    
    public AndroidContext(Context c) {
    	context = c;
   	
        
        if (palette==null) {
            int color_step=360/8;
            float[] hsvbase = {0f, 1f, 1f};
            
            palette=new int[50];
            for (int i=0; i<palette.length; i++) {
                palette[i]=Color.HSVToColor(hsvbase);
                
                hsvbase[0]+=color_step;
                hsvbase[0]%=360;
            }
        }
    }
   
    
    public Context getAndroidContext() {
    	return context;
    }
    
    
    public abstract void unlockCanvas();
    
    
    @Override
    public int colorBackground() {
        return Color.BLACK;
    }

    @Override
    public int countOfColor() {
        return palette.length;
    }

    @Override
    public int colorDark() {
        return Color.DKGRAY;
    }


    @Override
    public int getColor(int i) {
        return palette[i];
    }

    @Override
    public int colorGrayed() {
        return Color.GRAY;
    }

    @Override
    public int colorHighlight() {
        return Color.LTGRAY;
    }

    @Override
    public int colorFrame() {
        return Color.rgb(44, 109, 205);
    }

    
    @Override
    public int colorGrid() {
        return Color.rgb(44, 57, 77);
    }

	@Override
	public File getConfigDirectory() {
		return context.getFilesDir();
	}

}
