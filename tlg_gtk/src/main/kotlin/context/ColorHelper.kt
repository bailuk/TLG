package context

import kotlin.math.floor

/**
 *
 * originally from
 * http://www.java2s.com/Code/Java/2D-Graphics-GUI/HSVtoRGB.htm
 */
object ColorHelper {
    fun convertHSVtoRGB(h: Float, s: Float, v: Float): FloatArray {
        // H is given on [0->6] or -1. S and V are given on [0->1].
        // RGB are each returned on [0->1].
        val m: Float
        val n: Float
        var f: Float
        val i: Int
        val hsv = FloatArray(3)
        val rgb = FloatArray(3)
        hsv[0] = h
        hsv[1] = s
        hsv[2] = v
        if (hsv[0] == -1f) {
            rgb[2] = hsv[2]
            rgb[1] = rgb[2]
            rgb[0] = rgb[1]
            return rgb
        }
        i = floor(hsv[0].toDouble()).toInt()
        f = hsv[0] - i
        if (i % 2 == 0) {
            f = 1 - f // if i is even
        }
        m = hsv[2] * (1 - hsv[1])
        n = hsv[2] * (1 - hsv[1] * f)
        when (i) {
            6, 0 -> {
                rgb[0] = hsv[2]
                rgb[1] = n
                rgb[2] = m
            }
            1 -> {
                rgb[0] = n
                rgb[1] = hsv[2]
                rgb[2] = m
            }
            2 -> {
                rgb[0] = m
                rgb[1] = hsv[2]
                rgb[2] = n
            }
            3 -> {
                rgb[0] = m
                rgb[1] = n
                rgb[2] = hsv[2]
            }
            4 -> {
                rgb[0] = n
                rgb[1] = m
                rgb[2] = hsv[2]
            }
            5 -> {
                rgb[0] = hsv[2]
                rgb[1] = m
                rgb[2] = n
            }
        }
        return rgb
    }
}
