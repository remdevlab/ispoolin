package org.remdev.android.prefz

interface PrefsHelper {
    fun clearValue(key: String)

    fun putString(key: String, value: String)
    fun getString(key: String): String
    fun getString(key: String, defValue: String): String
    fun getStringOrThrow(key: String): String

    fun putInt(key: String, value: Int)
    fun getInt(key: String): Int
    fun getInt(key: String, defaultValue: Int): Int

    fun putLong(key: String, value: Long)
    fun getLong(key: String, defValue: Long): Long

    fun putBoolean(key: String, value: Boolean)
    fun getBoolean(key: String): Boolean
    fun getBoolean(key: String, defValue: Boolean): Boolean

    fun putFloat(key: String, value: Float)
    fun getFloat(key: String, defValue: Float): Float

    fun putDouble(key: String, value: Double)
    fun getDouble(key: String, defValue: Double): Double

    fun getAllPrefs(): Map<String, Any>
    fun flushToLogsAllPrefs()
    fun cleanAllPrefs()
}