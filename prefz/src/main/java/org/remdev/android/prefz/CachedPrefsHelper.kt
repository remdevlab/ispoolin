package org.remdev.android.prefz

import android.annotation.SuppressLint
import android.content.Context
import java.math.BigDecimal
import java.util.concurrent.ConcurrentHashMap

internal class CachedPrefsHelper : PrefsHelper {

    @Volatile
    private var registry = ConcurrentHashMap<String, Any>()
    private var helper: PrefsHelper = InMemoryPrefsHelper()

    internal fun setup(context: Context) {
        helper = InternalPrefsHelper().apply { setup(context) }
    }

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
        helper.clearValue(key)
    }

    override fun putString(key: String, value: String) {
        registry[key] = value
        helper.putString(key, value)
    }

    override fun getString(key: String): String {
        return getValue(key) ?: helper.getString(key).also { registry[key] = it }
    }

    override fun getString(key: String, defValue: String): String {
        return getValue(key) ?: helper.getString(key, defValue)
    }

    override fun getStringOrThrow(key: String): String {
        return getValue(key) ?: helper.getStringOrThrow(key).also { registry[key] = it }
    }

    override fun putInt(key: String, value: Int) {
        registry[key] = value
        helper.putInt(key, value)
    }

    override fun getInt(key: String): Int {
        return getValue(key) ?: helper.getInt(key).also { registry[key] = it }
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        return getValue(key) ?: helper.getInt(key, defaultValue)
    }

    override fun putLong(key: String, value: Long) {
        registry[key] = value
        helper.putLong(key, value)
    }

    override fun getLong(key: String, defValue: Long): Long {
        return getValue(key) ?: helper.getLong(key, defValue)
    }

    override fun putBoolean(key: String, value: Boolean) {
        registry[key] = value
        helper.putBoolean(key, value)
    }

    override fun getBoolean(key: String): Boolean {
        return getBoolean(key, false)
    }

    override fun getBoolean(key: String, defValue: Boolean): Boolean {
        return getValue(key) ?: helper.getBoolean(key, defValue)
    }

    override fun putFloat(key: String, value: Float) {
        registry[key] = value
        helper.putFloat(key, value)
    }

    override fun getFloat(key: String, defValue: Float): Float {
        return getValue(key) ?: helper.getFloat(key, defValue)
    }

    override fun putDouble(key: String, value: Double) {
        registry[key] = value
        helper.putDouble(key, value)
    }

    override fun getDouble(key: String, defValue: Double): Double {
        return getValue(key) ?: helper.getDouble(key, defValue)
    }

    override fun putBigDecimal(key: String, value: BigDecimal) {
        registry[key] = value.toString()
        helper.putBigDecimal(key, value)
    }

    override fun getBigDecimal(key: String, defValue: BigDecimal): BigDecimal {
        var value = defValue
        val valueFromCache: String? = getValue(key)
        if (valueFromCache == null) {
            value = helper.getBigDecimal(key, defValue)
        } else {
            try {
                value = valueFromCache.toBigDecimal()
            } catch (ex: NumberFormatException) {
                InternalLogger.logException(ex.toString(), ex)
            }
        }
        return value
    }

    override fun getAllPrefs(): Map<String, Any> {
        return helper.getAllPrefs()
    }

    override fun flushToLogsAllPrefs() {
        helper.flushToLogsAllPrefs()
    }

    @SuppressLint("ApplySharedPref")
    override fun cleanAllPrefs() {
        registry.clear()
        helper.cleanAllPrefs()
    }

    override fun enableInMemoryMode() {
    }

    override fun configure(writeLogsOnRead: Boolean, writeLogsOnWrite: Boolean) {
    }
}