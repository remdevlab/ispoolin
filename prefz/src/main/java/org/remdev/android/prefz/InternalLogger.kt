package org.remdev.android.prefz

import android.util.Log
import org.slf4j.LoggerFactory

object InternalLogger {
    const val TAG = "PREFZ"
    internal var logger: PrefzLogger = SystemLogger()
    var ENABLED = true
    var DEBUG = false
    fun log(msg: String) {
        if (ENABLED) {
            logger.log(msg)
        }
        if (DEBUG) {
            println("$TAG: $msg")
        }
    }

    fun logException(msg: String, ex: Throwable) {
        if (ENABLED) {
            logger.logException(msg, ex)
        }
        if (DEBUG) {
            println("$TAG: $msg")
            ex.printStackTrace()
        }
    }

    fun setExternalLogger(logger: PrefzLogger) {
        this.logger = logger
    }

    interface PrefzLogger {
        fun log(msg: String)
        fun logException(msg: String, ex: Throwable)
    }

    class BaseLogger : PrefzLogger {
        private val logger = LoggerFactory.getLogger(TAG)
        override fun log(msg: String) {
            logger.info(msg)
        }

        override fun logException(msg: String, ex: Throwable) {
            logger.error(msg, ex)
        }
    }

    class AndroidLogger : PrefzLogger {

        override fun log(msg: String) {
            Log.i(TAG, msg)
        }

        override fun logException(msg: String, ex: Throwable) {
            Log.e(TAG, msg, ex)
        }
    }

    class SystemLogger : PrefzLogger {

        override fun log(msg: String) {
            println("$TAG: $msg")
        }

        override fun logException(msg: String, ex: Throwable) {
            println("$TAG: $msg: $ex")
        }
    }
}
