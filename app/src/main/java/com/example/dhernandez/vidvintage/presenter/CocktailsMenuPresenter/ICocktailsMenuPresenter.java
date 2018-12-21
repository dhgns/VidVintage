package com.example.dhernandez.vidvintage.presenter.CocktailsMenuPresenter;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.dhernandez.vidvintage.Utils.Constants;
import com.example.dhernandez.vidvintage.entity.CocktailVO;
import com.example.dhernandez.vidvintage.entity.LoadedPreferences;

import java.util.List;

/**
 * Created by dhernandez on 31/08/2018.
 */

public interface ICocktailsMenuPresenter {

    LiveData<List<CocktailVO>> getCocktailsList();

    void onGinSectionClick();

    void onRumSectionClick();

    void onWhiskySectionClick();

    void onOtherSectionClick();

    MutableLiveData<Constants.Screens> getNavigateTo();

    void showPreferences();

    MutableLiveData<Boolean> getShowPreferences();

    void setDarkTheme(boolean activeDarkTheme);

    MutableLiveData<Boolean> getDarkThemeActive();

    void setAppFullScreen(boolean b);

    MutableLiveData<Boolean> getFullScreen();

    void setStoreSession(boolean b);

    void closeSession();

    LiveData<Boolean> getSaveSession();

    MutableLiveData<LoadedPreferences> getActivePreferences();

    void updateActivePreferences();
}
