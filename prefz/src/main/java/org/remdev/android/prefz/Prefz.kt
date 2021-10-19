package org.remdev.android.prefz

import android.annotation.SuppressLint
import android.content.Context
import org.remdev.android.prefz.InternalLogger.log
import java.math.BigDecimal

object Prefz : PrefsHelper {
    private const val PREFERENCES_NAME = "APP_SHARED_PREFS"
    private const val EMPTY_STRING = ""
    internal const val INT_DEFAULT_VALUE = -1
    private var helper: PrefsHelper = InMemoryPrefsHelper()

    internal fun setup(context: Context) {
        helper = InternalPrefsHelper().apply { setup(context) }
    }

    override fun clearValue(key: String) {
        helper.clearValue(key)
        log(String.format("clear key %s", key))
    }

    override fun putString(key: String, value: String) {
        helper.putString(key, value)
        log(String.format("put string key %s, value %s", key, value))
    }

    override fun getString(key: String): String {
        val value = helper.getString(key)
        log(String.format("get string key %s, value %s", key, value))
        return value
    }

    override fun getString(key: String, defValue: String): String {
        val value = helper.getString(key, defValue)
        log(String.format("get string key %s, value %s", key, value))
        return value
    }

    override fun getStringOrThrow(key: String): String {
        val value = helper.getStringOrThrow(key)
        log(String.format("get string key %s, value %s", key, value))
        return value
    }

    override fun putInt(key: String, value: Int) {
        helper.putInt(key, value)
        log(String.format("put int key %s, value %s", key, value))
    }

    override fun getInt(key: String): Int {
        val value = helper.getInt(key)
        log(String.format("get int key %s, value %s", key, value))
        return value
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        val value = helper.getInt(key, defaultValue)
        log(String.format("get int key %s, value %s", key, value))
        return value
    }

    override fun putLong(key: String, value: Long) {
        helper.putLong(key, value)
        log(String.format("put long key %s, value %s", key, value))
    }

    override fun getLong(key: String, defValue: Long): Long {
        val value = helper.getLong(key, defValue)
        log(String.format("get long key %s, value %s", key, value))
        return value
    }

    override fun putBoolean(key: String, value: Boolean) {
        helper.putBoolean(key, value)
        log(String.format("put bool key %s, value %s", key, value))
    }

    override fun getBoolean(key: String): Boolean {
        val value = helper.getBoolean(key, false)
        log(String.format("get bool key %s, value %s", key, value))
        return value
    }

    override fun getBoolean(key: String, defValue: Boolean): Boolean {
        val value = helper.getBoolean(key, defValue)
        log(String.format("get bool key %s, value %s", key, value))
        return value
    }

    override fun putFloat(key: String, value: Float) {
        helper.putFloat(key, value)
        log(String.format("put float key %s, value %s", key, value))
    }

    override fun getFloat(key: String, defValue: Float): Float {
        val value = helper.getFloat(key, defValue)
        log(String.format("get float key %s, value %s", key, value))
        return value
    }

    override fun putDouble(key: String, value: Double) {
        helper.putDouble(key, value)
        log(String.format("put double key %s, value %s", key, value))
    }

    override fun getDouble(key: String, defValue: Double): Double {
        val value = helper.getDouble(key, defValue)
        log(String.format("get double key %s, value %s", key, value))
        return value
    }

    override fun putBigDecimal(key: String, value: BigDecimal) {
        helper.putBigDecimal(key, value)
        log(String.format("put big decimal key %s, value %s", key, value))
    }

    override fun getBigDecimal(key: String, defValue: BigDecimal): BigDecimal {
        val value = helper.getBigDecimal(key, defValue)
        log(String.format("get big decimal key %s, value %s", key, value))
        return value
    }

    override fun getAllPrefs(): Map<String, Any> {
        return helper.getAllPrefs()
    }

    override fun flushToLogsAllPrefs() {
        var count = 0
        for ((key, value) in getAllPrefs()) {
            log("Entry #" + count++ + ", key: " + key + ", value: " + value + ", type: ${value.javaClass}")
        }
    }

    @SuppressLint("ApplySharedPref")
    override fun cleanAllPrefs() {
        helper.cleanAllPrefs()
    }

    override fun enableInMemoryMode() {
        synchronized(helper) {
            helper = InMemoryPrefsHelper()
        }
    }
}
