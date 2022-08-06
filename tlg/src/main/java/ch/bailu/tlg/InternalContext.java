package ch.bailu.tlg;

import java.io.BufferedOutputStream;
import java.io.IOException;

public class InternalContext {
    protected static final String STATE_FILE = "state";
    protected final static int STATE_FILE_VERSION = 4;

    private final static int MARGIN = 2;
    private final static int TEXT_HEIGHT = 20;


    protected State state;

    protected MatrixWithShape previewMatrix;
    protected MatrixLineManipulator mainMatrix;
    protected TextBox textBox;
    protected Score currentScore;


    private TlgRectangle previewGeometry = new TlgRectangle();
    private TlgRectangle mainGeometry = new TlgRectangle();


    public InternalContext(PlatformContext c) {
        state = new StateInit(this).init(c);
    }


    public synchronized void moveLeft(PlatformContext c) {
        state = state.moveLeft(c);
    }


    public synchronized void moveRight(PlatformContext c) {
        state = state.moveRight(c);
    }


    public synchronized void moveDown(PlatformContext c) {
        state = state.moveDown(c);
    }


    public synchronized void moveTurn(PlatformContext c) {
        state = state.moveTurn(c);
    }


    public synchronized void toggleGrid() {
        mainMatrix.toggleGrid();
    }


    public synchronized void togglePause(PlatformContext c) {
        state = state.togglePause(c);
    }


    public synchronized void startNewGame(PlatformContext c) {
        state = state.startNewGame(c);
    }


    public void writeState(PlatformContext gc) throws IOException {
        BufferedOutputStream output = gc.getOutputStream(STATE_FILE);

        ByteInteger.wrap(STATE_FILE_VERSION).writeState(output);

        previewMatrix.writeState(output);
        mainMatrix.writeState(output);
        currentScore.writeState(output);

        ByteInteger.wrap(getID()).writeState(output);

        output.close();
    }


    protected void setStatusText(String state) {
        String text = "Score: " +
                currentScore.getScore() +
                " Level: " +
                currentScore.getLevel() +
                " | " +
                state;
        textBox.setText(text);
    }


    public synchronized void mainLayout(TlgRectangle r) {
        mainGeometry = r;
        mainMatrix.setPixelGeometry(mainGeometry);
    }


    public synchronized void previewLayout(TlgRectangle r) {
        previewGeometry = r;
        previewMatrix.setPixelGeometry(previewGeometry);
    }


    public synchronized void updatePreview(PlatformContext gc) {
        previewMatrix.update(gc);
    }

    public synchronized void updateAllPreview(PlatformContext gc) {
        updateBackgroundPreview(gc);
        previewMatrix.updateAll(gc);
    }


    public synchronized void updateMain(PlatformContext gc) {
        mainMatrix.update(gc);
    }

    public synchronized void updateAllMain(PlatformContext gc) {
        updateBackgroundMain(gc);
        mainMatrix.updateAll(gc);
    }

    private void updateBackgroundMain(PlatformContext gc) {
        gc.setDirtyRect(mainGeometry);
        gc.drawFilledRectangle(gc.colorBackground(), mainGeometry);
    }


    private void updateBackgroundPreview(PlatformContext gc) {
        gc.setDirtyRect(previewGeometry);
        gc.drawFilledRectangle(gc.colorBackground(), previewGeometry);
    }

    public int getTimerInterval() {
        return state.getTimerInterval();
    }


    public void setHighscoreName(PlatformContext c, String name) {
        state = state.setHighscoreName(c, name);
    }


    public int getID() {
        return state.getID();
    }
}
