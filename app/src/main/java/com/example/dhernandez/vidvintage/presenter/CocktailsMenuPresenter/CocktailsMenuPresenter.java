package com.example.dhernandez.vidvintage.presenter.CocktailsMenuPresenter;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.dhernandez.vidvintage.Utils.Constants;
import com.example.dhernandez.vidvintage.application.MyApplication;
import com.example.dhernandez.vidvintage.entity.CocktailVO;
import com.example.dhernandez.vidvintage.entity.LoadedPreferences;
import com.example.dhernandez.vidvintage.repository.LocalStorageRepository.ILocalStorageRepository;

import java.util.List;

import static com.example.dhernandez.vidvintage.Utils.Constants.FULL_SCREEN_KEY;
import static com.example.dhernandez.vidvintage.Utils.Constants.SESSION_KEY;
import static com.example.dhernandez.vidvintage.Utils.Constants.Screens.GIN_MENU;
import static com.example.dhernandez.vidvintage.Utils.Constants.Screens.LOGIN;
import static com.example.dhernandez.vidvintage.Utils.Constants.Screens.OTHER_MENU;
import static com.example.dhernandez.vidvintage.Utils.Constants.Screens.RUM_MENU;
import static com.example.dhernandez.vidvintage.Utils.Constants.Screens.WHISKY_MENU;
import static com.example.dhernandez.vidvintage.Utils.Constants.THEME_KEY;
import static com.example.dhernandez.vidvintage.Utils.Constants.Themes.DARK;
import static com.example.dhernandez.vidvintage.Utils.Constants.Themes.LIGHT;
import static com.example.dhernandez.vidvintage.Utils.Constants.USER;

/**
 * Created by dhernandez on 31/08/2018.
 */

public class CocktailsMenuPresenter extends ViewModel implements ICocktailsMenuPresenter {

    private final MutableLiveData<LoadedPreferences> loadedPreferences;
    private MutableLiveData<List<CocktailVO>> cocktailListMLD;
    private MutableLiveData<Constants.Screens> navigateTo;
    private MutableLiveData<Boolean> showPreferences;
    private MutableLiveData<Boolean> darkThemeActive;
    private ILocalStorageRepository localStorageRepository;
    private MutableLiveData<Boolean> fullScreen;
    private MutableLiveData<Boolean> storeSession;

    public CocktailsMenuPresenter(ILocalStorageRepository localStorage,
                                  MutableLiveData<LoadedPreferences> loadedPreferencesMutableLiveData) {
        //Import the presenter in the application component to make The job of Dagger
        //a little bit easier by the time it will have to resolve dependencies
        MyApplication.getApplicationComponent().inject(this);

        this.localStorageRepository = localStorage;
        this.loadedPreferences = loadedPreferencesMutableLiveData;

        this.storeSession = new MutableLiveData<>();
        this.cocktailListMLD = new MutableLiveData<>();
        this.showPreferences = new MutableLiveData<>();
        this.darkThemeActive = new MutableLiveData<>();
        this.fullScreen = new MutableLiveData<>();
        this.navigateTo = new MutableLiveData<>();

        setPreferences();
    }

    private void setPreferences() {
        LoadedPreferences loadedPreferences = this.loadedPreferences.getValue();

        if(loadedPreferences.getFullScreen())
            this.fullScreen.setValue(true);
        else
            this.fullScreen.setValue(false);

        if(loadedPreferences.getTheme().equals(DARK))
            this.darkThemeActive.setValue(true);
        else
            this.darkThemeActive.setValue(false);

        this.storeSession.setValue(loadedPreferences.getSaveSession());

    }


    @Override
    public LiveData<List<CocktailVO>> getCocktailsList() {
        return this.cocktailListMLD;
    }

    @Override
    public void onGinSectionClick() {
        this.navigateTo.setValue(GIN_MENU);
    }

    @Override
    public void onRumSectionClick() {
        this.navigateTo.setValue(RUM_MENU);
    }

    @Override
    public void onWhiskySectionClick() {
        this.navigateTo.setValue(WHISKY_MENU);
    }

    @Override
    public void onOtherSectionClick() {
        this.navigateTo.setValue(OTHER_MENU);
    }

    @Override
    public MutableLiveData<Constants.Screens> getNavigateTo() {
        return this.navigateTo;
    }

    @Override
    public void showPreferences() {
        this.showPreferences.setValue(true);
    }

    @Override
    public MutableLiveData<Boolean> getShowPreferences() {
        return this.showPreferences;
    }

    @Override
    public void setDarkTheme(boolean activeDarkTheme) {
        if (this.darkThemeActive.getValue() != activeDarkTheme) {
            this.darkThemeActive.setValue(activeDarkTheme);
            localStorageRepository.saveString(THEME_KEY, Constants.Themes.DARK.toString());
        }
    }

    @Override
    public MutableLiveData<Boolean> getDarkThemeActive() {
        return this.darkThemeActive;
    }

    @Override
    public void setAppFullScreen(boolean fullScreen) {
        if (this.fullScreen.getValue() != null && this.fullScreen.getValue() != fullScreen) {
            this.fullScreen.setValue(fullScreen);
            localStorageRepository.saveBoolean(FULL_SCREEN_KEY, fullScreen);
        }
    }

    @Override
    public MutableLiveData<Boolean> getFullScreen() {
        return this.fullScreen;
    }

    @Override
    public void setStoreSession(boolean saveSession) {
        if (this.storeSession.getValue() != null && this.storeSession.getValue() != saveSession) {
            localStorageRepository.saveBoolean(SESSION_KEY, saveSession);
            this.storeSession.setValue(saveSession);
        }
    }

    @Override
    public void closeSession() {
        localStorageRepository.deleteKey(USER);
        localStorageRepository.saveBoolean(SESSION_KEY, false);
        navigateTo.setValue(LOGIN);
    }

    @Override
    public LiveData<Boolean> getSaveSession() {
        return this.storeSession;
    }

    @Override
    public MutableLiveData<LoadedPreferences> getActivePreferences() {
        return this.loadedPreferences;
    }

    @Override
    public void updateActivePreferences() {
        Constants.Themes theme;
        if(darkThemeActive.getValue())
            theme = DARK;
        else
            theme = LIGHT;
        LoadedPreferences loadedPreferences =
                new LoadedPreferences(
                        true,
                        getSaveSession().getValue(),
                        theme,
                        getFullScreen().getValue());

        this.loadedPreferences.setValue(loadedPreferences);

    }
}
