package org.remdev.android.prefz

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import java.math.BigDecimal
import java.util.HashMap

internal class InternalPrefsHelper : PrefsHelper {

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
        editor.apply()
    }

    override fun putString(key: String, value: String) {
        val editor = mSharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    override fun getString(key: String): String {
        return mSharedPreferences.getString(key, EMPTY_STRING)!!
    }

    override fun getString(key: String, defValue: String): String {
        return mSharedPreferences.getString(key, defValue)!!
    }

    override fun getStringOrThrow(key: String): String {
        return mSharedPreferences.getString(key, EMPTY_STRING)
            ?: throw IllegalStateException("Value by key: $key is null")
    }

    override fun putInt(key: String, value: Int) {
        val editor = mSharedPreferences.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    override fun getInt(key: String): Int {
        return mSharedPreferences.getInt(key, INT_DEFAULT_VALUE)
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        return mSharedPreferences.getInt(key, defaultValue)
    }

    override fun putLong(key: String, value: Long) {
        val editor = mSharedPreferences.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    override fun getLong(key: String, defValue: Long): Long {
        return mSharedPreferences.getLong(key, defValue)
    }

    override fun putBoolean(key: String, value: Boolean) {
        val editor = mSharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    override fun getBoolean(key: String): Boolean {
        return getBoolean(key, false)
    }

    override fun getBoolean(key: String, defValue: Boolean): Boolean {
        return mSharedPreferences.getBoolean(key, defValue)
    }

    override fun putFloat(key: String, value: Float) {
        val editor = mSharedPreferences.edit()
        editor.putFloat(key, value)
        editor.apply()
    }

    override fun getFloat(key: String, defValue: Float): Float {
        return mSharedPreferences.getFloat(key, defValue)
    }

    override fun putDouble(key: String, value: Double) {
        val editor = mSharedPreferences.edit()
        editor.putString(key, value.toString())
        editor.apply()
    }

    override fun getDouble(key: String, defValue: Double): Double {
        var value = defValue
        try {
            value = getString(key, defValue = defValue.toString()).toDouble()
        } catch (ex: NumberFormatException) {
            InternalLogger.logException(ex.toString(), ex)
        }
        return value
    }

    override fun putBigDecimal(key: String, value: BigDecimal) {
        val editor = mSharedPreferences.edit()
        editor.putString(key, value.toString())
        editor.apply()
    }

    override fun getBigDecimal(key: String, defValue: BigDecimal): BigDecimal {
        var value = defValue
        try {
            value = getString(key, defValue = defValue.toString()).toBigDecimal()
        } catch (ex: NumberFormatException) {
            InternalLogger.logException(ex.toString(), ex)
        }
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
            InternalLogger.log("Entry #" + count++ + ", key: " + key + ", value: " + value + ", type: ${value.javaClass}")
        }
    }

    @SuppressLint("ApplySharedPref")
    override fun cleanAllPrefs() {
        val editor = mSharedPreferences.edit()
        editor.clear()
        editor.commit()
    }

    override fun enableInMemoryMode() {
    }

    override fun configure(writeLogsOnRead: Boolean, writeLogsOnWrite: Boolean) {
    }
}
