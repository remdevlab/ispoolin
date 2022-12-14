package org.remdev.android.encryptedprefz

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class EncryptedPrefzTest {

    @Test
    fun test_string() {
        val value = "value1"
        EncryptedPrefz.clearValue(KEY)
        assert(value == EncryptedPrefz.getString(KEY, value))
        EncryptedPrefz.putString(KEY, value)
        assert(value == EncryptedPrefz.getString(KEY))
        EncryptedPrefz.clearValue(KEY)
        assert(EncryptedPrefz.getString(KEY) == "")
    }

    @Test
    fun test_boolean() {
        val value = true
        EncryptedPrefz.clearValue(KEY)
        assert(value == EncryptedPrefz.getBoolean(KEY, value))
        EncryptedPrefz.putBoolean(KEY, value)
        assert(value == EncryptedPrefz.getBoolean(KEY))
        EncryptedPrefz.clearValue(KEY)
        assert(!EncryptedPrefz.getBoolean(KEY))
    }

    @Test
    fun test_int() {
        val value = 77897
        EncryptedPrefz.clearValue(KEY)
        assert(value == EncryptedPrefz.getInt(KEY, value))
        EncryptedPrefz.putInt(KEY, value)
        assert(value == EncryptedPrefz.getInt(KEY))
        EncryptedPrefz.clearValue(KEY)
        assert(EncryptedPrefz.getInt(KEY) == EncryptedPrefz.INT_DEFAULT_VALUE)
    }

    @Test
    fun test_float() {
        val value = 22.001F
        EncryptedPrefz.clearValue(KEY)
        assert(value == EncryptedPrefz.getFloat(KEY, value))
        EncryptedPrefz.putFloat(KEY, value)
        assert(value == EncryptedPrefz.getFloat(KEY, 1.001F))
        EncryptedPrefz.clearValue(KEY)
        assert(EncryptedPrefz.getFloat(KEY, 11F) == 11F)
    }

    @Test
    fun test_long() {
        val value = Date().time
        EncryptedPrefz.clearValue(KEY)
        assert(value == EncryptedPrefz.getLong(KEY, value))
        EncryptedPrefz.putLong(KEY, value)
        assert(value == EncryptedPrefz.getLong(KEY, 123L))
        EncryptedPrefz.clearValue(KEY)
        assert(EncryptedPrefz.getLong(KEY, 7L) == 7L)
    }

    @Test
    fun test_double() {
        val value = 20.001
        EncryptedPrefz.clearValue(KEY)
        assert(value == EncryptedPrefz.getDouble(KEY, value))
        EncryptedPrefz.putDouble(KEY, value)
        assert(value == EncryptedPrefz.getDouble(KEY, 123.321))
        EncryptedPrefz.clearValue(KEY)
        assert(EncryptedPrefz.getDouble(KEY, 7.1) == 7.1)
    }

    @Test
    fun test_common() {
        EncryptedPrefz.putString("key1", "string value")
        EncryptedPrefz.putInt("key2", 123654)
        EncryptedPrefz.putLong("key3", Date().time)
        EncryptedPrefz.putFloat("key4", 15.3F)
        EncryptedPrefz.putDouble("key5", 27.034)
        EncryptedPrefz.flushToLogsAllPrefs()
        EncryptedPrefz.cleanAllPrefs()
        EncryptedPrefz.flushToLogsAllPrefs()
    }

    companion object {
        const val KEY = "KEY"
    }
}