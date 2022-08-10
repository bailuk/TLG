package tlg.lib


object Limit {
    fun clamp(value: Int, min: Int, max: Int): Int {
        var result = Math.max(value, min)
        result = Math.min(result, max)
        return result
    }

    fun smallest(value: Int, vararg values: Int): Int {
        var result = value
        for (v in values) {
            result = Math.min(result, v)
        }
        return result
    }

    fun biggest(value: Int, vararg values: Int): Int {
        var result = value
        for (v in values) {
            result = Math.max(result, v)
        }
        return result
    }

    fun smallest(value: Double, vararg values: Double): Double {
        var result = value
        for (v in values) {
            result = Math.min(result, v)
        }
        return result
    }

    fun biggest(value: Double, vararg values: Double): Double {
        var result = value
        for (v in values) {
            result = Math.max(result, v)
        }
        return result
    }
}
