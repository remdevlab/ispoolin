package org.remdev.android.prefz

import org.junit.Test
import java.math.BigDecimal
import java.util.Date

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class PrefzUnitTest {

    @Test
    fun test_string() {
        val value = "value1"
        Prefz.clearValue(KEY)
        assert(value == Prefz.getString(KEY, value))
        Prefz.putString(KEY, value)
        assert(value == Prefz.getString(KEY))
        Prefz.clearValue(KEY)
        assert(Prefz.getString(KEY) == "")
    }

    @Test
    fun test_boolean() {
        val value = true
        Prefz.clearValue(KEY)
        assert(value == Prefz.getBoolean(KEY, value))
        Prefz.putBoolean(KEY, value)
        assert(value == Prefz.getBoolean(KEY))
        Prefz.clearValue(KEY)
        assert(!Prefz.getBoolean(KEY))
    }

    @Test
    fun test_int() {
        val value = 77897
        Prefz.clearValue(KEY)
        assert(value == Prefz.getInt(KEY, value))
        Prefz.putInt(KEY, value)
        assert(value == Prefz.getInt(KEY))
        Prefz.clearValue(KEY)
        assert(Prefz.getInt(KEY) == Prefz.INT_DEFAULT_VALUE)
    }

    @Test
    fun test_float() {
        val value = 22.001F
        Prefz.clearValue(KEY)
        assert(value == Prefz.getFloat(KEY, value))
        Prefz.putFloat(KEY, value)
        assert(value == Prefz.getFloat(KEY, 1.001F))
        Prefz.clearValue(KEY)
        assert(Prefz.getFloat(KEY, 11F) == 11F)
    }

    @Test
    fun test_long() {
        val value = Date().time
        Prefz.clearValue(KEY)
        assert(value == Prefz.getLong(KEY, value))
        Prefz.putLong(KEY, value)
        assert(value == Prefz.getLong(KEY, 123L))
        Prefz.clearValue(KEY)
        assert(Prefz.getLong(KEY, 7L) == 7L)
    }

    @Test
    fun test_double() {
        val value = 20.001
        Prefz.clearValue(KEY)
        assert(value == Prefz.getDouble(KEY, value))
        Prefz.putDouble(KEY, value)
        assert(value == Prefz.getDouble(KEY, 123.321))
        Prefz.clearValue(KEY)
        assert(Prefz.getDouble(KEY, 7.1) == 7.1)
    }

    @Test
    fun test_common() {
        Prefz.putString("key1", "string value")
        Prefz.putInt("key2", 123654)
        Prefz.putLong("key3", Date().time)
        Prefz.putFloat("key4", 15.3F)
        Prefz.putDouble("key5", 27.034)
        Prefz.putBigDecimal("key6", BigDecimal.valueOf(27.034))
        Prefz.flushToLogsAllPrefs()
        Prefz.cleanAllPrefs()
        Prefz.flushToLogsAllPrefs()
    }

    @Test
    fun test_enable_test_mode() {
        Prefz.putString("key0", "string value")
        Prefz.enableInMemoryMode()
        assert(Prefz.getString("key0") == "")
        Prefz.putString("key1", "string value")
        assert(Prefz.getString("key1") == "string value")
        Prefz.putInt("key2", 123654)
        assert(Prefz.getInt("key2") == 123654)
        val date = Date().time
        Prefz.putLong("key3", date)
        assert(Prefz.getLong("key3", 123) == date)
        Prefz.putFloat("key4", 15.3F)
        assert(Prefz.getFloat("key4", 123F) == 15.3F)
        Prefz.putDouble("key5", 27.034)
        assert(Prefz.getDouble("key5", 123.123) == 27.034)
        Prefz.putBigDecimal("key6", BigDecimal.valueOf(27.034))
        assert(Prefz.getBigDecimal("key6", BigDecimal.valueOf(123.123)) == BigDecimal.valueOf(27.034))
        Prefz.flushToLogsAllPrefs()
        Prefz.cleanAllPrefs()
        Prefz.flushToLogsAllPrefs()
    }

    companion object {
        const val KEY = "KEY"
    }
}
