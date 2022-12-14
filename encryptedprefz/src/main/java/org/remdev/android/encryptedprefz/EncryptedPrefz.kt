package org.remdev.android.encryptedprefz

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import org.remdev.android.prefz.InternalLogger
import org.remdev.android.prefz.PrefsHelper

object EncryptedPrefz : PrefsHelper {

    private const val PREFERENCES_NAME = "ENCRYPTED_APP_SHARED_PREFS"
    private const val EMPTY_STRING = ""
    internal const val INT_DEFAULT_VALUE = -1
    private lateinit var mSharedPreferences: SharedPreferences

    internal fun setup(context: Context) {
        mSharedPreferences = EncryptedSharedPreferences.create(
            PREFERENCES_NAME,
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    override fun clearValue(key: String) {
        mSharedPreferences.edit().remove(key).apply()
    }

    override fun putString(key: String, value: String) {
        mSharedPreferences.edit().putString(key, value).apply()
    }

    override fun getString(key: String): String {
        return getString(key, EMPTY_STRING)
    }

    override fun getString(key: String, defValue: String): String {
        return mSharedPreferences.getString(key, defValue)!!
    }

    override fun getStringOrThrow(key: String): String {
        return mSharedPreferences.getString(key, EMPTY_STRING) ?: throw IllegalStateException("Value by key: $key is null")
    }

    override fun putInt(key: String, value: Int) {
        mSharedPreferences.edit().putInt(key, value).apply()
    }

    override fun getInt(key: String): Int {
        return getInt(key, INT_DEFAULT_VALUE)
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        return mSharedPreferences.getInt(key, defaultValue)
    }

    override fun putLong(key: String, value: Long) {
        mSharedPreferences.edit().putLong(key, value).apply()
    }

    override fun getLong(key: String, defValue: Long): Long {
        return mSharedPreferences.getLong(key, defValue)
    }

    override fun putBoolean(key: String, value: Boolean) {
        mSharedPreferences.edit().putBoolean(key, value).apply()
    }

    override fun getBoolean(key: String): Boolean {
        return getBoolean(key, false)
    }

    override fun getBoolean(key: String, defValue: Boolean): Boolean {
        return mSharedPreferences.getBoolean(key, defValue)
    }

    override fun putFloat(key: String, value: Float) {
        mSharedPreferences.edit().putFloat(key, value).apply()
    }

    override fun getFloat(key: String, defValue: Float): Float {
        return mSharedPreferences.getFloat(key, defValue)
    }

    override fun putDouble(key: String, value: Double) {
        putString(key, value.toString())
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

    override fun getAllPrefs(): Map<String, Any> {
        return emptyMap()
    }

    override fun flushToLogsAllPrefs() {
        // Encrypted prefz doesn't support logs
    }

    @SuppressLint("ApplySharedPref")
    override fun cleanAllPrefs() {
        mSharedPreferences.edit().clear().commit()
    }

}