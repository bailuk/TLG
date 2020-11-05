package ch.bailu.tlg_swt;


import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import ch.bailu.tlg.Constants;




public class Main{ 

	
    public static void main(String[] args) {
    	new Main();
    }

    
    public Main() {
        Display display = new Display();
        Shell shell = new Shell(display, SWT.SHELL_TRIM);
        TlgCanvas canvas = new TlgCanvas(display, shell);
        
        GridData gridData = new GridData();
        gridData.widthHint=400;
        gridData.heightHint=500;
        gridData.verticalSpan=3;
        gridData.horizontalSpan=1;
        
        canvas.setLayoutData(gridData);
        
        
        Label highscore = new Label(shell, 0);
        new Label(shell, 0).setText(Constants.HELP_TEXT);
        new Label(shell, 0).setText(Constants.ABOUT_TEXT);
        canvas.setHighscoreLabel(highscore);
        
        shell.addKeyListener(canvas);
        shell.setLayout(new GridLayout(2,false));
        shell.setText("TLG - Swt");
        shell.pack();
        shell.open();
        
        while (!display.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }


}
