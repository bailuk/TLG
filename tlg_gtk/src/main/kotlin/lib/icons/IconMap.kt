package lib.icons

import ch.bailu.gtk.gdk.Paintable
import ch.bailu.gtk.gdkpixbuf.Pixbuf
import ch.bailu.gtk.gtk.Image

object IconMap {
    private data class IconId (val name: String, val size: Int)

    private val pixbufs = HashMap<IconId, Pixbuf>()

    fun getPixbuf(name: String, size: Int): Pixbuf {
        return try {
            getPixbufThrows(name, size)
        } catch (e: Exception) {
            println("Image resource not found: $name")
            getPixbufThrows("none", size)
        }
    }

    private fun getPixbufThrows(name: String, size: Int): Pixbuf {
        var result = pixbufs[IconId(name, size)]

        return if (result == null) {
            val input = IconMap.javaClass.getResourceAsStream("/svg/${name}.svg")
            result = ch.bailu.gtk.lib.bridge.Image.load(input, size, size)
            pixbufs[IconId(name, size)] = result
            result.ref()
            result
        } else {
            result
        }
    }

    fun getImage(name: String, size: Int): Image {
        val result = Image.newFromPixbufImage(getPixbuf(name, size))
        result.setSizeRequest(size, size)
        return  result
    }

    fun getPaintable(name: String, size: Int): Paintable {
        return getImage(name, size).paintable
    }
}
