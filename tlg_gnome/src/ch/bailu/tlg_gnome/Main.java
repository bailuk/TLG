package ch.bailu.tlg_gnome;

import java.io.IOException;

import org.gnome.gdk.Event;
import org.gnome.gtk.Align;
import org.gnome.gtk.Gtk;
import org.gnome.gtk.Label;
import org.gnome.gtk.Table;
import org.gnome.gtk.Widget;
import org.gnome.gtk.Window;

import ch.bailu.tlg.Constants;

public class Main  extends Window implements Window.DeleteEvent {

	
	public static void main(String[] args) {
		Gtk.init(args);

		
		try {
			new Main();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		System.exit(0);
	}
	
	
	public Main() throws IOException {
		
		Table table = new Table(0,0,true);
		

		
		
		Label score = createLabel("Score:");
		Label help = createLabel(Constants.HELP_TEXT);
		//Label about = createLabel(Constants.ABOUT_TEXT);

		Canvas canvas = new Canvas(score);
		
		table.attach(canvas, 0, 2, 0, 2);
		table.attach(score,  2, 3, 0, 1);
		table.attach(help,   2, 3, 1, 2);
		
		
		
		add(table);
		connect((Widget.KeyPressEvent)canvas);
		
		setTitle("TLG Gnome");
		setDefaultSize(400, 400);
		setBorderWidth(3);
		
		connect(this);
		showAll();
		
		Gtk.main();
		
		canvas.cleanUp();
	}


	private Label createLabel(String string) {
		Label l = new Label();
		l.setLabel(string);
		l.setAlignHorizontal(Align.START);
		l.setAlignVertical(Align.START);
		
		return l;
	}


	@Override
	public boolean onDeleteEvent(Widget arg0, Event arg1) {
		Gtk.mainQuit();
		return false;
	}
}
