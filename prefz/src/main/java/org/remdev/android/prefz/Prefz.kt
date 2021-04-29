package org.remdev.android.prefz

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import org.remdev.android.prefz.InternalLogger.log
import org.remdev.android.prefz.InternalLogger.logException
import java.util.*

object Prefz : PrefsHelper {
    private const val PREFERENCES_NAME = "APP_SHARED_PREFS"
    private const val EMPTY_STRING = ""
    internal const val INT_DEFAULT_VALUE = -1
    private lateinit var mSharedPreferences: SharedPreferences

    internal fun setup(context: Context) {
        mSharedPreferences = context.getSharedPreferences(
            PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )
    }

    override fun clearValue(key: String) {
        val editor = mSharedPreferences.edit()
        editor.remove(key)
        log(String.format("clear key %s", key))
        editor.apply()
    }

    override fun putString(key: String, value: String) {
        val editor = mSharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
        log(String.format("put string key %s, value %s", key, value))
    }

    override fun getString(key: String): String {
        val value = mSharedPreferences.getString(key, EMPTY_STRING)
        log(String.format("get string key %s, value %s", key, value))
        return value!!
    }

    override fun getString(key: String, defValue: String): String {
        val value = mSharedPreferences.getString(key, defValue)
        log(String.format("get string key %s, value %s", key, value))
        return value!!
    }

    override fun getStringOrThrow(key: String): String {
        val value = mSharedPreferences.getString(key, EMPTY_STRING)
            ?: throw IllegalStateException("Value by key: $key is null")
        log(String.format("get string key %s, value %s", key, value))
        return value
    }

    override fun putInt(key: String, value: Int) {
        val editor = mSharedPreferences.edit()
        editor.putInt(key, value)
        editor.apply()
        log(String.format("put int key %s, value %s", key, value))
    }

    override fun getInt(key: String): Int {
        val value = mSharedPreferences.getInt(key, INT_DEFAULT_VALUE)
        log(String.format("get int key %s, value %s", key, value))
        return value
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        val value = mSharedPreferences.getInt(key, defaultValue)
        log(String.format("get int key %s, value %s", key, value))
        return value
    }

    override fun putLong(key: String, value: Long) {
        val editor = mSharedPreferences.edit()
        editor.putLong(key, value)
        editor.apply()
        log(String.format("put long key %s, value %s", key, value))
    }

    override fun getLong(key: String, defValue: Long): Long {
        val value = mSharedPreferences.getLong(key, defValue)
        log(String.format("get long key %s, value %s", key, value))
        return value
    }

    override fun putBoolean(key: String, value: Boolean) {
        val editor = mSharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
        log(String.format("put bool key %s, value %s", key, value))
    }

    override fun getBoolean(key: String): Boolean {
        return getBoolean(key, false)
    }

    override fun getBoolean(key: String, defValue: Boolean): Boolean {
        val value = mSharedPreferences.getBoolean(key, defValue)
        log(String.format("get bool key %s, value %s", key, value))
        return value
    }

    override fun putFloat(key: String, value: Float) {
        val editor = mSharedPreferences.edit()
        editor.putFloat(key, value)
        editor.apply()
        log(String.format("put float key %s, value %s", key, value))
    }

    override fun getFloat(key: String, defValue: Float): Float {
        val value = mSharedPreferences.getFloat(key, defValue)
        log(String.format("get float key %s, value %s", key, value))
        return value
    }

    override fun putDouble(key: String, value: Double) {
        val editor = mSharedPreferences.edit()
        editor.putString(key, value.toString())
        editor.apply()
        log(String.format("put double key %s, value %s", key, value))
    }

    override fun getDouble(key: String, defValue: Double): Double {
        var value = defValue
        try {
            value = getString(key, defValue = defValue.toString()).toDouble()
        } catch (ex: NumberFormatException) {
            logException(ex.toString(), ex)
        }
        log(String.format("get double key %s, value %s", key, value))
        return value
    }

    override fun getAllPrefs(): Map<String, Any> {
        val result: MutableMap<String, Any> = HashMap()
        val keys = mSharedPreferences.all
        for ((key, value) in keys) {
            result[key] = value!!
        }
        return result
    }

    override fun flushToLogsAllPrefs() {
        var count = 0
        for ((key, value) in getAllPrefs()) {
            log("Entry #" + count++ + ", key: " + key + ", value: " + value + ", type: ${value.javaClass}")
        }
    }

    @SuppressLint("ApplySharedPref")
    override fun cleanAllPrefs() {
        val editor = mSharedPreferences.edit()
        editor.clear()
        editor.commit()
    }

}