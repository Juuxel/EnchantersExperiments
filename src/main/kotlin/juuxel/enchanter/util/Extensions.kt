package juuxel.enchanter.util

/** Converts seconds to ticks. */
val Int.seconds: Int
    get() = this * 20
