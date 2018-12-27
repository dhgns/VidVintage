package com.example.dhernandez.vidvintage.presenter.MainPresenter;

import android.arch.lifecycle.MutableLiveData;

import com.example.dhernandez.vidvintage.Utils.Constants;
import com.example.dhernandez.vidvintage.entity.UserPreferences;

/**
 * Created by dhernandez on 30/08/2018.
 */

public interface IMainPresenter {

    MutableLiveData<Constants.Screens> getNavigateTo();

    void onRss();

    void onCocktailsMenu();

    void onGinSectionClick();

    void onRumSectionClick();

    void onWhiskySectionClick();

    void onOtherSectionClick();

    void onProfile();

    void setDarkTheme(boolean activeDarkTheme);

    void setAppFullScreen(boolean b);

    void saveSession(boolean b);

    void closeSession();

    MutableLiveData<UserPreferences> getPreferences();

    void updateActivePreferences(UserPreferences newPreferences);

    void showCocktailDetail();
}

