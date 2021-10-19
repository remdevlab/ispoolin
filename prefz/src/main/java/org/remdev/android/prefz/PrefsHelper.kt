package org.remdev.android.prefz

import java.math.BigDecimal

interface PrefsHelper {
    /**
     * Clear value by key
     *
     * @param key
     */
    fun clearValue(key: String)

    /**
     * Put string value by key
     *
     * @param key
     * @param value
     */
    fun putString(key: String, value: String)

    /**
     * Get string value by key or empty string if found nothing
     *
     * @param key
     * @return string value or empty string if null
     */
    fun getString(key: String): String

    /**
     * Get string value by key or default value if found nothing
     *
     * @param key
     * @param defValue
     * @return string value
     */
    fun getString(key: String, defValue: String): String

    /**
     * Get string value or throw IllegalStateException otherwise
     *
     * @param key
     * @return string value
     */
    fun getStringOrThrow(key: String): String

    /**
     * Put int value
     *
     * @param key
     * @param value
     */
    fun putInt(key: String, value: Int)

    /**
     * Get int value or -1 if found nothing
     *
     * @param key
     * @return value or -1
     */
    fun getInt(key: String): Int

    /**
     * Get int value or default value if found nothing
     *
     * @param key
     * @param defaultValue
     * @return int value
     */
    fun getInt(key: String, defaultValue: Int): Int

    /**
     * Put long value
     *
     * @param key
     * @param value
     */
    fun putLong(key: String, value: Long)

    /**
     * Get long value or default value if found nothing
     *
     * @param key
     * @param defValue
     * @return long value
     */
    fun getLong(key: String, defValue: Long): Long

    /**
     * Put boolean value
     *
     * @param key
     * @param value
     */
    fun putBoolean(key: String, value: Boolean)

    /**
     * Get boolean value or false if found nothing
     *
     * @param key
     * @return boolean value
     */
    fun getBoolean(key: String): Boolean

    /**
     * Get boolean value or default value if found nothing
     *
     * @param key
     * @param defValue
     * @return boolean value
     */
    fun getBoolean(key: String, defValue: Boolean): Boolean

    /**
     * Put float value
     *
     * @param key
     * @param value
     */
    fun putFloat(key: String, value: Float)

    /**
     * Get float value or default value if found nothing
     *
     * @param key
     * @param defValue
     * @return float value
     */
    fun getFloat(key: String, defValue: Float): Float

    /**
     * Put double value
     *
     * @param key
     * @param value
     */
    fun putDouble(key: String, value: Double)

    /**
     * Get double value or default value if found nothing
     *
     * @param key
     * @param defValue
     * @return double value
     */
    fun getDouble(key: String, defValue: Double): Double

    /**
     * Put big decimal
     *
     * @param key
     * @param value
     */
    fun putBigDecimal(key: String, value: BigDecimal)

    /**
     * Get big decimal value or default value if found nothing
     *
     * @param key
     * @param defValue
     * @return
     */
    fun getBigDecimal(key: String, defValue: BigDecimal): BigDecimal

    /**
     * Get map with all prefs
     *
     * @return
     */
    fun getAllPrefs(): Map<String, Any>

    /**
     * Flush to logs all stored preferences
     *
     */
    fun flushToLogsAllPrefs()

    /**
     * Clear all prefs
     */
    fun cleanAllPrefs()

    /**
     * Enable in memory mode. switch to map in memory.
     * Useful for unit tests
     */
    fun enableInMemoryMode()
}
