package org.remdev.android.prefz;

import android.annotation.SuppressLint;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Map;

import kotlin.Deprecated;

/**
 * Created by Alexandr.Salin on 7/23/15.
 */
@Deprecated(message = "Use Prefz instead")
public class PrefsUtil implements PrefsHelper {

    private static PrefsUtil instance;

    public PrefsUtil() {
    }

    public static PrefsUtil getInstance() {
        if (instance == null) {
            synchronized (PrefsUtil.class) {
                if (instance == null) {
                    instance = new PrefsUtil();
                }
            }
        }
        return instance;
    }

    public void clearValue(@NotNull String key) {
        Prefz.INSTANCE.clearValue(key);
    }

    public void putString(@NotNull String key, @NotNull String value) {
        Prefz.INSTANCE.putString(key, value);
    }

    @NotNull
    public String getString(@NotNull String key) {
        return Prefz.INSTANCE.getString(key);
    }

    @NotNull
    public String getStringOrThrow(@NotNull String key) {
        return Prefz.INSTANCE.getStringOrThrow(key);
    }

    @NotNull
    public String getString(@NotNull String key, @NotNull String defValue) {
        return Prefz.INSTANCE.getString(key, defValue);
    }

    public void putInt(@NotNull String key, int value) {
        Prefz.INSTANCE.putInt(key, value);
    }

    public int getInt(@NotNull String key) {
        return Prefz.INSTANCE.getInt(key);
    }

    public int getInt(@NotNull String key, int defaultValue) {
        return Prefz.INSTANCE.getInt(key, defaultValue);
    }

    public void putLong(@NotNull String key, long value) {
        Prefz.INSTANCE.putLong(key, value);
    }

    public long getLong(@NotNull String key, long defValue) {
        return Prefz.INSTANCE.getLong(key, defValue);
    }

    public void putBoolean(@NotNull String key, boolean value) {
        Prefz.INSTANCE.putBoolean(key, value);
    }

    public boolean getBoolean(@NotNull String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(@NotNull String key, boolean defValue) {
        return Prefz.INSTANCE.getBoolean(key, defValue);
    }

    public void putFloat(@NotNull String key, float value) {
        Prefz.INSTANCE.putFloat(key, value);
    }

    public float getFloat(@NotNull String key, float defValue) {
        return Prefz.INSTANCE.getFloat(key, defValue);
    }

    @Override
    public void putDouble(@NotNull String key, double value) {
        Prefz.INSTANCE.putDouble(key, value);
    }

    @Override
    public double getDouble(@NotNull String key, double defValue) {
        return Prefz.INSTANCE.getDouble(key, defValue);
    }

    @Override
    public void putBigDecimal(@NotNull String key, @NotNull BigDecimal value) {
        Prefz.INSTANCE.putBigDecimal(key, value);
    }

    @Override
    public BigDecimal getBigDecimal(@NotNull String key, @NotNull BigDecimal defValue) {
        return Prefz.INSTANCE.getBigDecimal(key, defValue);
    }

    @NotNull
    public Map<String, Object> getAllPrefs() {
        return Prefz.INSTANCE.getAllPrefs();
    }

    public void flushToLogsAllPrefs() {
        Prefz.INSTANCE.flushToLogsAllPrefs();
    }

    @SuppressLint("ApplySharedPref")
    public void cleanAllPrefs() {
        Prefz.INSTANCE.cleanAllPrefs();
    }

    @Override
    public void enableInMemoryMode() {
        Prefz.INSTANCE.enableInMemoryMode();
    }

    @Override
    public void configure(boolean writeLogsOnRead, boolean writeLogsOnWrite) {
        Prefz.INSTANCE.configure(writeLogsOnRead, writeLogsOnWrite);
    }
}
