package view

import ch.bailu.gtk.GTK
import ch.bailu.gtk.gtk.HeaderBar

class Header {
    val headerBar = HeaderBar().apply {
        showTitleButtons = GTK.TRUE
    }

}
