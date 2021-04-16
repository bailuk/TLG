declare var require: any


const gi = require('node-gtk');
const Gtk = gi.require('Gtk', '3.0')
const Cairo = gi.require('cairo')

import { GtkCairoContext } from './tlg/GtkCairoContext';
import {GtkContext } from './tlg/GtkContext';
import {InternalContext} from './tlg/InternalContext';
import { StateContext } from './tlg/StateContext';
import { StorageContext } from './tlg/StorageContext';
import {TlgRectangle } from './tlg/TlgRectangle';


gi.startLoop()
Gtk.init()

const win = new Gtk.Window()


win.on('destroy', () => Gtk.mainQuit())
win.on('delete-event', () => false)

win.setDefaultSize(400, 800)


//const table = new Gtk.Table(0, 0, true);
//const score = createLabel("Score:");
//const help = createLabel("Constants.HELP_TEXT");


const canvas = new Gtk.DrawingArea();

const platformContext = new GtkContext()
const internalContext = new InternalContext()
const stateContext = new StateContext(internalContext, platformContext, new StorageContext());


canvas.on('draw', (context) => {
    const cairoPlatformContext = new GtkCairoContext(context);
    internalContext.updateAll(cairoPlatformContext);
    return true;
})


win.on('size-allocate', (rect) => {
    internalContext.layout(new TlgRectangle(0, 0, rect.width, rect.height))
    return true
})


const KEY_SPC   = 32
const KEY_G     = 103
const KEY_N     = 110
const KEY_P     = 112
const KEY_DOWN  = 65364
const KEY_LEFT  = 65361
const KEY_RIGHT = 65363
const KEY_UP    = 65362



win.on('key-press-event', (key) => {
    let update:boolean=true
   
    if (key.keyval == KEY_N) {
        stateContext.startNewGame(platformContext);
    } else if (key.keyval == KEY_DOWN) {
        stateContext.moveDown(platformContext);
    } else if (key.keyval == KEY_LEFT) {
        stateContext.moveLeft(platformContext);
    } else if (key.keyval == KEY_RIGHT) {
        stateContext.moveRight(platformContext);
    } else if (key.keyval == KEY_UP) {
        stateContext.moveTurn(platformContext);
    } else if (key.keyval == KEY_G) {
        internalContext.toggleGrid();
    } else if (key.keyval == KEY_P || key.keyval == KEY_SPC) {
        stateContext.togglePause(platformContext);
    } else {
        update = false;
    }
    
    if (update) {
        canvas.queueDraw()
    }
    return update
});


function timeout() {
    setTimeout(onTimeout, stateContext.getTimerInterval(), 'timer')
    
}

function onTimeout() {
    stateContext.moveDown(platformContext)
    canvas.queueDraw()
    timeout()
}



win.add(canvas);
internalContext.layout(new TlgRectangle(0,0,400,800));

timeout()


//win.add(container)
//container.add(new Gtk.Label({ label: 'Hello Gtk+' , angle: 25}))


//const button = new Gtk.Button({ label: 'Hello Gtk+'})
//container.add(button)
//button.connect('clicked', () => {console.log('Test');
//})

win.showAll()
Gtk.main()


function createLabel(text: String): any {
    //l.setAlignHorizontal(Align.START);
    // l.setAlignVertical(Align.START);
    const result = new gi.Gtk.Label({label: text}) 
    return result
}