package org.remdev.android.prefz

import java.util.logging.Level
import java.util.logging.Logger

object InternalLogger {
    const val TAG = "PREFZ"
    internal val logger = Logger.getLogger(TAG)
    var ENABLED = true
    var DEBUG = false
    fun log(msg: String) {
        if (ENABLED) {
            logger.log(Level.INFO, msg)
        }
        if (DEBUG) {
            println("$TAG: $msg")
        }
    }

    fun logException(msg: String, ex: Throwable) {
        if (ENABLED) {
            logger.log(Level.SEVERE, msg, ex)
        }
        if (DEBUG) {
            println("$TAG: $msg")
            ex.printStackTrace()
        }
    }
}