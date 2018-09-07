package com.example.dhernandez.vidvintage.repository;

import android.content.SharedPreferences;

import com.example.dhernandez.vidvintage.Utils.Constants;

/**
 * Created by dhernandez on 06/09/2018.
 */

public class LocalStorageRepository implements ILocalStorageRepository {

    private final SharedPreferences sharedPreferences;

    public LocalStorageRepository(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void saveTheme(String key,String value) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putString(key,value);
        editor.apply();
    }

    @Override
    public String loadTheme(String key) {
        return sharedPreferences.getString(key, "");
    }

    @Override
    public void saveString(String key, String value) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putString(key,value);
        editor.apply();
    }

    @Override
    public String loadString(String key, String defaultValue) {
        return sharedPreferences.getString(key,defaultValue);
    }

    @Override
    public void deleteKey(String key) {
        sharedPreferences.edit().remove(key).apply();
    }

    @Override
    public void saveBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }

    @Override
    public boolean loadBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }
}
