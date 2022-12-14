package org.remdev.android.prefz

import android.annotation.SuppressLint
import java.math.BigDecimal

internal class InMemoryPrefsHelper : PrefsHelper {

    private var registry = mutableMapOf<String, Any>()

    private inline fun <reified T> getValue(key: String): T? {
        return if (registry.containsKey(key) && registry[key] is T) {
            registry[key] as T
        } else {
            null
        }
    }

    private inline fun <reified T> getValue(key: String, defValue: T): T {
        return if (registry.containsKey(key) && registry[key] is T) {
            registry[key] as T
        } else {
            defValue
        }
    }

    override fun clearValue(key: String) {
        registry.remove(key)
    }

    override fun putString(key: String, value: String) {
        registry[key] = value
    }

    override fun getString(key: String): String {
        return getValue(key) ?: EMPTY_STRING
    }

    override fun getString(key: String, defValue: String): String {
        return getValue(key, defValue)
    }

    override fun getStringOrThrow(key: String): String {
        return getValue(key)
            ?: throw IllegalStateException("Value by key: $key is null")
    }

    override fun putInt(key: String, value: Int) {
        registry[key] = value
    }

    override fun getInt(key: String): Int {
        return getValue(key, INT_DEFAULT_VALUE)
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        return getValue(key, defaultValue)
    }

    override fun putLong(key: String, value: Long) {
        registry[key] = value
    }

    override fun getLong(key: String, defValue: Long): Long {
        return getValue(key, defValue)
    }

    override fun putBoolean(key: String, value: Boolean) {
        registry[key] = value
    }

    override fun getBoolean(key: String): Boolean {
        return getBoolean(key, false)
    }

    override fun getBoolean(key: String, defValue: Boolean): Boolean {
        return getValue(key, defValue)
    }

    override fun putFloat(key: String, value: Float) {
        registry[key] = value
    }

    override fun getFloat(key: String, defValue: Float): Float {
        return getValue(key, defValue)
    }

    override fun putDouble(key: String, value: Double) {
        registry[key] = value
    }

    override fun getDouble(key: String, defValue: Double): Double {
        return getValue(key, defValue)
    }

    override fun putBigDecimal(key: String, value: BigDecimal) {
        registry[key] = value.toString()
    }

    override fun getBigDecimal(key: String, defValue: BigDecimal): BigDecimal {
        var value = defValue
        try {
            value = getValue(key, defValue.toString()).toBigDecimal()
        } catch (ex: NumberFormatException) {
            InternalLogger.logException(ex.toString(), ex)
        }
        return value
    }

    override fun getAllPrefs(): Map<String, Any> {
        return registry
    }

    override fun flushToLogsAllPrefs() {
        var count = 0
        for ((key, value) in getAllPrefs()) {
            InternalLogger.log("Entry #" + count++ + ", key: " + key + ", value: " + value + ", type: ${value.javaClass}")
        }
    }

    @SuppressLint("ApplySharedPref")
    override fun cleanAllPrefs() {
        registry.clear()
    }

    override fun enableInMemoryMode() {
    }

    override fun configure(writeLogsOnRead: Boolean, writeLogsOnWrite: Boolean) {
    }
}
