package com.example.dhernandez.vidvintage.repository.LocalStorageRepository;

import android.arch.lifecycle.MutableLiveData;

import com.example.dhernandez.vidvintage.entity.Cocktail;

import java.util.List;

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

    void saveCocktail(Cocktail cocktail);

    MutableLiveData<List<Cocktail>> retrieveCocktails(String userID);

}
