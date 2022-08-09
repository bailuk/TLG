package lib.color

import java.awt.Color

class ARGB : ColorInterface {
    private var a: Int
    private var r: Int
    private var g: Int
    private var b: Int

    constructor(color: Int) {
        a = color shr 24 and 0xff
        r = red(color)
        g = green(color)
        b = blue(color)
    }

    constructor(alpha: Int, color: Int) {
        a = alpha
        r = red(color)
        g = green(color)
        b = blue(color)
    }

    constructor(r: Int, g: Int, b: Int) : this(255, r, g, b) {}
    constructor(a: Int, r: Int, g: Int, b: Int) {
        this.a = a
        this.r = r
        this.g = g
        this.b = b
    }

    override fun red(): Int {
        return r
    }

    override fun green(): Int {
        return g
    }

    override fun blue(): Int {
        return b
    }

    override fun alpha(): Int {
        return a
    }

    override fun toInt(): Int {
        return a and 0xff shl 24 or (r and 0xff shl 16) or (g and 0xff shl 8) or (b and 0xff)
    }

    companion object {
        fun decode(color: String): ARGB {
            val c = Color.decode(color)
            return ARGB(c.alpha, c.red, c.green, c.blue)
        }

        fun red(color: Int): Int {
            return color shr 16 and 0xff
        }

        fun green(color: Int): Int {
            return color shr 8 and 0xff
        }

        fun blue(color: Int): Int {
            return color and 0xff
        }
    }
}
