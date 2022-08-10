package tlg.lib.color

import tlg.lib.Limit

/**
 * H, S and V input range = 0 ÷ 1.0
 * R, G and B output range = 0 ÷ 255
 * http://www.easyrgb.com/en/math.php#text20
 */
class HSV : ColorInterface {
    private val alpha: Int
    private var H: Double
    private var S = 0.0
    private var V: Double

    constructor(color: ColorInterface) {
        //R, G and B input range = 0 ÷ 255
        //H, S and V output range = 0 ÷ 1.0
        alpha = color.alpha()
        val varR = color.red().toDouble() / 255.0
        val varG = color.green().toDouble() / 255.0
        val varB = color.blue().toDouble() / 255.0
        val varMin: Double = Limit.smallest(varR, varG, varB)
        val varMax: Double = Limit.biggest(varR, varG, varB)
        val delMax = varMax - varMin
        V = varMax
        H = 0.0
        if (delMax == 0.0) {
            S = 0.0
        } else {
            S = delMax / varMax
            val delR = ((varMax - varR) / 6.0 + delMax / 2.0) / delMax
            val delG = ((varMax - varG) / 6.0 + delMax / 2.0) / delMax
            val delB = ((varMax - varB) / 6.0 + delMax / 2.0) / delMax
            if (varR == varMax) H = delB - delG
            else if (varG == varMax) H = 1.0 / 3.0 + delR - delB
            else if (varB == varMax) H = 2.0 / 3.0 + delG - delR
            if (H < 0.0) H += 1.0
            if (H > 1.0) H -= 1.0
        }
    }

    constructor(h: Double, s: Double = 1.0, v: Double =1.0, a: Int = 255) {
        S = s
        H = h
        V = v
        alpha = a
    }


    override fun toInt(): Int {
        return toARGB().toInt()
    }

    fun toARGB(): ARGB {
        val R: Double
        val G: Double
        val B: Double
        if (S == 0.0) {
            R = V * 255.0
            G = V * 255.0
            B = V * 255.0
        } else {
            var varH = H * 6.0
            if (varH == 6.0) varH = 0.0
            val varI = varH.toInt()
            val var1 = V * (1.0 - S)
            val var2 = V * (1.0 - S * (varH - varI))
            val var3 = V * (1.0 - S * (1.0 - (varH - varI)))
            val varR: Double
            val varG: Double
            val varB: Double
            if (varI == 0) {
                varR = V
                varG = var3
                varB = var1
            } else if (varI == 1) {
                varR = var2
                varG = V
                varB = var1
            } else if (varI == 2) {
                varR = var1
                varG = V
                varB = var3
            } else if (varI == 3) {
                varR = var1
                varG = var2
                varB = V
            } else if (varI == 4) {
                varR = var3
                varG = var1
                varB = V
            } else {
                varR = V
                varG = var1
                varB = var2
            }
            R = varR * 255.0
            G = varG * 255.0
            B = varB * 255.0
        }
        return ARGB(alpha, R.toInt(), G.toInt(), B.toInt())
    }
    override fun red(): Int {
        return ARGB(toInt()).red()
    }

    override fun green(): Int {
        return ARGB(toInt()).green()
    }

    override fun blue(): Int {
        return ARGB(toInt()).blue()
    }

    override fun alpha(): Int {
        return alpha
    }

    fun setSaturation(saturation: Float) {
        S = saturation.toDouble()
    }

    fun setValue(value: Float) {
        V = value.toDouble()
    }
}
