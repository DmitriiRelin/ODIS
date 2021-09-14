package com.odis.odis.Utils

import kotlin.math.roundToInt

fun randBetween(start: Int, end: Int): Int {
    return start + (Math.random() * (end - start)).roundToInt()
}