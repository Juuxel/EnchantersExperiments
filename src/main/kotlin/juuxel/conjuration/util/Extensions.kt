package juuxel.conjuration.util

/** Converts seconds to ticks. */
val Int.seconds: Int
    get() = this * 20
