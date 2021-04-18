declare var require: any

const gi = require('node-gtk')
const Gtk = gi.require('Gtk', '3.0')

import { GtkContext } from '../tlg/context/nodejs/GtkContext'
import { NonDrawingContext } from '../tlg/context/NonDrawingContext'
import { InternalContext} from '../tlg/context/InternalContext'
import { StateContext } from '../tlg/state/StateContext'
import { TlgRectangle } from '../tlg/matrix/TlgRectangle'
import { FileStorageContext } from '../tlg/context/nodejs/FileStorageContext'


gi.startLoop()
Gtk.init()


const platformContext = new NonDrawingContext()
const internalContext = new InternalContext()
const stateContext = new StateContext(internalContext, platformContext, new FileStorageContext())

const KEY_SPC   = 32
const KEY_G     = 103
const KEY_N     = 110
const KEY_P     = 112
const KEY_DOWN  = 65364
const KEY_LEFT  = 65361
const KEY_RIGHT = 65363
const KEY_UP    = 65362

const win = new Gtk.Window({
    title: 'TLG node.js (node-gtk)',
    type: Gtk.WindowType.TOPLEVEL,
    window_position: Gtk.WindowPosition.CENTER
})
win.setDefaultSize(600, 800)
win.on('destroy', Gtk.mainQuit)
win.setDefaultSize(400, 800)


win.on('key-press-event', (key : any) => {
    let update : boolean = true
   
    if (key.keyval == KEY_N) {
        stateContext.startNewGame(platformContext)

    } else if (key.keyval == KEY_DOWN) {
        stateContext.moveDown(platformContext)

    } else if (key.keyval == KEY_LEFT) {
        stateContext.moveLeft(platformContext)

    } else if (key.keyval == KEY_RIGHT) {
        stateContext.moveRight(platformContext)

    } else if (key.keyval == KEY_UP) {
        stateContext.moveTurn(platformContext)

    } else if (key.keyval == KEY_G) {
        internalContext.toggleGrid()

    } else if (key.keyval == KEY_P || key.keyval == KEY_SPC) {
        stateContext.togglePause(platformContext)

    } else {
        update = false
    }
    
    if (update) {
        status.setText(internalContext.getStatusText())
        canvas.queueDraw()
    }
    return update
})


const vbox = new Gtk.VBox()
win.add(vbox)

const status = new Gtk.Label()
vbox.packStart(status, false, true, 5)

const canvas = new Gtk.DrawingArea()
canvas.on('draw', (context : any) => {
    const cairoPlatformContext = new GtkContext(context)

   internalContext.updateAll(cairoPlatformContext)    

    return true
})

canvas.on('size-allocate', (rect : any) => {
    internalContext.layout(new TlgRectangle(0, 0, rect.width, rect.height))
    return true
})

vbox.add(canvas)

const link = new Gtk.LinkButton()
vbox.packEnd(link, false, true, 1)
link.setUri('https://github.com/bailuk/TLG')
link.setLabel('TLG')

const info = new Gtk.Label()
vbox.packEnd(info, false, true, 1)
info.setText('arrow: move, up: turn, g: toggle grid, p: pause, n: new game')

let running = true

function timeout() {
    setTimeout(onTimeout, stateContext.getTimerInterval(), 'timer')
}

function onTimeout() {
    if (running == true) {
        stateContext.moveDown(platformContext)
        status.setText(internalContext.getStatusText())
        canvas.queueDraw()
        timeout()
    }
}

timeout()

win.showAll()
Gtk.main()
running = false

stateContext.writeState()
console.log('bye')
