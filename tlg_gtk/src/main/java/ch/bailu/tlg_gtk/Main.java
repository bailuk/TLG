package ch.bailu.tlg_gtk;


import java.io.IOException;

import ch.bailu.gtk.GTK;
import ch.bailu.gtk.gio.ApplicationFlags;
import ch.bailu.gtk.gtk.Application;
import ch.bailu.gtk.gtk.ApplicationWindow;
import ch.bailu.gtk.gtk.Box;
import ch.bailu.gtk.gtk.DrawingArea;
import ch.bailu.gtk.gtk.HeaderBar;
import ch.bailu.gtk.gtk.IconSize;
import ch.bailu.gtk.gtk.Image;
import ch.bailu.gtk.gtk.Label;
import ch.bailu.gtk.gtk.MenuButton;
import ch.bailu.gtk.gtk.Orientation;
import ch.bailu.tlg.Constants;

public class Main {


    public static void main(String[] args) throws IOException {
        GTK.init();
        new Main(args);
    }

    Canvas canvas;


    public Main(String args[]) throws IOException {
        Application app = new Application("ch.bailu.tlg", ApplicationFlags.FLAGS_NONE);


        app.onActivate(() -> {
            ApplicationWindow window = new ApplicationWindow(app);
            canvas = init(window);
        });

        app.run(args.length, args);
        canvas.cleanUp();
    }


    public Canvas init(ApplicationWindow window) {
        Label score = createLabel("Score:");
        Label help = createLabel(Constants.HELP_TEXT);
        DrawingArea drawingArea = new DrawingArea();

        canvas = new Canvas(drawingArea, score);

        HeaderBar header = new HeaderBar();

        header.setShowCloseButton(1);
        header.setTitle("TLG");
        header.setHasSubtitle(0);
        header.setDecorationLayout("menu:close");


        MenuButton menubar = new MenuButton();
        menubar.add(Image.newFromIconNameImage("open-menu-symbolic", IconSize.BUTTON));
        header.packStart(menubar);

        var vbox = new Box(Orientation.VERTICAL, 2);
        vbox.packStart(drawingArea, GTK.TRUE, GTK.TRUE, 2);
        vbox.packEnd(help, GTK.FALSE, GTK.TRUE, 2);

        window.add(vbox);
        window.onKeyPressEvent(eventKey -> canvas.onKeyPressEvent(eventKey));

        header.add(score);
        window.setTitlebar(header);
        window.setDefaultSize(400, 800);
        window.setBorderWidth(0);

        window.showAll();
        return canvas;
    }


    private Label createLabel(String string) {
        Label result = new Label(string);
        result.setXalign(0);
        return result;
    }
}
