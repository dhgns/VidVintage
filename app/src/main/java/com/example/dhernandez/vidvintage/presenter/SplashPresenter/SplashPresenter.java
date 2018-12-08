package com.example.dhernandez.vidvintage.presenter.SplashPresenter;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.dhernandez.vidvintage.Utils.Constants;
import com.example.dhernandez.vidvintage.entity.LoadedPreferences;
import com.example.dhernandez.vidvintage.repository.LocalStorageRepository.ILocalStorageRepository;

import static com.example.dhernandez.vidvintage.Utils.Constants.Themes.DARK;
import static com.example.dhernandez.vidvintage.Utils.Constants.Themes.LIGHT;

/**
 * Created by dhernandez on 30/08/2018.
 */

public class SplashPresenter extends ViewModel implements ISplashPresenter {

    private final MutableLiveData<Constants.Screens> navigateTo;
    private final MutableLiveData<LoadedPreferences> loadedPreferences;
    private final ILocalStorageRepository localStorageRepository;

    public SplashPresenter(ILocalStorageRepository localStorageRepository,
                           MutableLiveData<LoadedPreferences> loadedPreferencesMutableLiveData) {
        this.localStorageRepository = localStorageRepository;
        this.loadedPreferences = loadedPreferencesMutableLiveData;
        this.navigateTo = new MutableLiveData<>();

        loadPreferences();
    }

    private void loadPreferences() {
        Boolean fullScreen, activeSession, saveSession;
        Constants.Themes theme = null;

        fullScreen = localStorageRepository.loadBoolean(Constants.FULL_SCREEN_KEY);
        activeSession = localStorageRepository.loadBoolean(Constants.USER);
        saveSession = localStorageRepository.loadBoolean(Constants.SESSION_KEY);

        String themeName = localStorageRepository.loadTheme(Constants.THEME_KEY);
        if (themeName != null) {
            if (themeName.equals(DARK.toString()))
                theme = DARK;
            else
                theme = LIGHT;
        }else
            theme = DARK;

        LoadedPreferences loadedPreferences = new LoadedPreferences(activeSession, saveSession, theme, fullScreen);
        this.loadedPreferences.setValue(loadedPreferences);

    }

    @Override
    public void onUILoaded() {
        new android.os.Handler().postDelayed(
                () -> {
                    if(this.loadedPreferences.getValue().getSaveSession())
                        navigateTo.setValue(Constants.Screens.MAIN);
                    else
                        navigateTo.setValue(Constants.Screens.LOGIN);
                }, 2000);
    }

    public MutableLiveData<Constants.Screens> getNavigateTo() {
        return navigateTo;
    }
}
