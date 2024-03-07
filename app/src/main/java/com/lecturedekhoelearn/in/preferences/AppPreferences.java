package com.lecturedekhoelearn.in.preferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;


public class AppPreferences {

    public static final String KEY_FIRST_LOGIN = "FIRSTTIME";
    public static final String KEY_LOGIN = "LOGIN";
    private static final String APP_SHARED_PREFS = AppPreferences.class.getSimpleName();
    private static SharedPreferences _sharedPrefs;
    private static SharedPreferences.Editor _prefsEditor;

    public AppPreferences(Context context) {
        this._sharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
        this._prefsEditor = _sharedPrefs.edit();
    }

    public static void set(String key, String value) {

        _prefsEditor.putString(key, value);
        _prefsEditor.commit();
    }

    public static void saveString(Context context, String key, String value) {
        _prefsEditor = context.getSharedPreferences(context.getPackageName(), MODE_PRIVATE).edit();
        _prefsEditor.putString(key, value).commit();
    }

    public static void saveBollen(Context context, String key, boolean value) {
        _prefsEditor = context.getSharedPreferences(context.getPackageName(), MODE_PRIVATE).edit();
        _prefsEditor.putBoolean(key, value).commit();
    }

    public boolean getFirstTime() {
        return _sharedPrefs.getBoolean(KEY_FIRST_LOGIN, true);
    }

    public void setFirstTime(boolean firstLogin) {
        _prefsEditor.putBoolean(KEY_FIRST_LOGIN, firstLogin);
        _prefsEditor.commit();
    }

    public String getLogin() {
        return _sharedPrefs.getString(KEY_LOGIN, "");
    }

    public void setLogin(String user) {
        _prefsEditor.putString(KEY_LOGIN, user);
        _prefsEditor.commit();
    }
}
