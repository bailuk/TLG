package ch.bailu.tlg_gtk;


import java.io.IOException;

import ch.bailu.gtk.GTK;
import ch.bailu.gtk.gio.ApplicationFlags;
import ch.bailu.gtk.gtk.Application;
import ch.bailu.gtk.gtk.ApplicationWindow;
import ch.bailu.gtk.gtk.Box;
import ch.bailu.gtk.gtk.DrawingArea;
import ch.bailu.gtk.gtk.HeaderBar;
import ch.bailu.gtk.gtk.Label;
import ch.bailu.gtk.gtk.MenuButton;
import ch.bailu.gtk.gtk.Orientation;
import ch.bailu.gtk.type.Str;
import ch.bailu.gtk.type.Strs;
import ch.bailu.tlg.Constants;

public class Main {


    public static void main(String[] args) throws IOException {
        new Main(args);
    }

    Canvas canvas;


    public Main(String[] args) throws IOException {
        Application app = new Application(new Str("ch.bailu.tlg"), ApplicationFlags.FLAGS_NONE);


        app.onActivate(() -> {
            ApplicationWindow window = new ApplicationWindow(app);
            canvas = init(window);
        });

        app.run(args.length, new Strs(args));
        canvas.cleanUp();
    }


    public Canvas init(ApplicationWindow window) {
        Label score = createLabel("Score:");
        Label help = createLabel(Constants.HELP_TEXT);
        DrawingArea drawingArea = new DrawingArea();

        canvas = new Canvas(drawingArea, score);

        HeaderBar header = new HeaderBar();
        header.setShowTitleButtons(GTK.TRUE);

        MenuButton menubar = new MenuButton();
        //menubar.(Image.newFromIconNameImage("open-menu-symbolic", IconSize.BUTTON));
        header.packStart(menubar);

        var vbox = new Box(Orientation.VERTICAL, 2);
        vbox.append(drawingArea);
        vbox.append(help);

        window.setChild(vbox);

        //window.onKeyPressEvent(eventKey -> canvas.onKeyPressEvent(eventKey));

        header.packEnd(score);
        window.setTitlebar(header);
        window.setDefaultSize(400, 800);

        window.show();
        return canvas;
    }


    private Label createLabel(String string) {
        Label result = new Label(new Str(string));
        result.setXalign(0);
        return result;
    }
}
