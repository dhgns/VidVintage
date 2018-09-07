package com.example.dhernandez.vidvintage.repository;

import com.example.dhernandez.vidvintage.Utils.Constants;

/**
 * Created by dhernandez on 06/09/2018.
 */

public interface ILocalStorageRepository {

    void saveTheme(String key, String value);
    String loadTheme(String key);

    void saveString(String key, String value);
    String loadString(String key, String defaultValue);

    void deleteKey(String key);

    void saveBoolean(String sessionKey, boolean fullScreen);
    boolean loadBoolean(String key);
}

