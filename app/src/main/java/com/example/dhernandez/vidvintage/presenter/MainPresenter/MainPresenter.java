package com.example.dhernandez.vidvintage.presenter.MainPresenter;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.dhernandez.vidvintage.Utils.Constants;
import com.example.dhernandez.vidvintage.application.MyApplication;
import com.example.dhernandez.vidvintage.entity.CocktailVO;
import com.example.dhernandez.vidvintage.entity.UserPreferences;
import com.example.dhernandez.vidvintage.repository.LocalStorageRepository.ILocalStorageRepository;

import static com.example.dhernandez.vidvintage.Utils.Constants.FULL_SCREEN_KEY;
import static com.example.dhernandez.vidvintage.Utils.Constants.SESSION_KEY;
import static com.example.dhernandez.vidvintage.Utils.Constants.Screens.COCKTAIL_DETAIL;
import static com.example.dhernandez.vidvintage.Utils.Constants.Screens.GIN_MENU;
import static com.example.dhernandez.vidvintage.Utils.Constants.Screens.LOGIN;
import static com.example.dhernandez.vidvintage.Utils.Constants.Screens.OTHER_MENU;
import static com.example.dhernandez.vidvintage.Utils.Constants.Screens.RUM_MENU;
import static com.example.dhernandez.vidvintage.Utils.Constants.Screens.WHISKY_MENU;
import static com.example.dhernandez.vidvintage.Utils.Constants.THEME_KEY;
import static com.example.dhernandez.vidvintage.Utils.Constants.USER;

/**
 * Created by dhernandez on 30/08/2018.
 */

public class MainPresenter extends ViewModel implements IMainPresenter {

    private final MutableLiveData<Constants.Screens> navigateTo;
    private final MutableLiveData<UserPreferences> loadedPreferences;
    private final MutableLiveData<CocktailVO> cocktailDetail;

    ILocalStorageRepository localStorageRepository;
    private MutableLiveData<Boolean> showPreferences;

    public MainPresenter(ILocalStorageRepository localStorageRepository,
                         MutableLiveData<UserPreferences> loadedPreferencesMutableLiveData,
                         MutableLiveData<CocktailVO> cocktailDetail) {

        MyApplication.getApplicationComponent().inject(this);

        this.localStorageRepository = localStorageRepository;
        this.loadedPreferences = loadedPreferencesMutableLiveData;
        this.cocktailDetail = cocktailDetail;

        this.navigateTo = new MutableLiveData<>();
        this.showPreferences = new MutableLiveData<>();

        this.navigateTo.setValue(Constants.Screens.COCKTAILS_MENU);
    }

    @Override
    public MutableLiveData<Constants.Screens> getNavigateTo() {
        return this.navigateTo;
    }

    @Override
    public void onRss() {
        this.navigateTo.setValue(Constants.Screens.RSS);
    }

    @Override
    public void onCocktailsMenu() {
        this.navigateTo.setValue(Constants.Screens.COCKTAILS_MENU);
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
    public void onProfile() {
        this.navigateTo.setValue(Constants.Screens.PROFILE);
    }

    @Override
    public void setDarkTheme(boolean activeDarkTheme) {
        UserPreferences userPreferences = this.loadedPreferences.getValue();
        userPreferences.setTheme((activeDarkTheme)?Constants.Themes.DARK: Constants.Themes.LIGHT);
        this.loadedPreferences.setValue(userPreferences);
    }

    @Override
    public void setAppFullScreen(boolean fullScreen) {
        UserPreferences userPreferences = this.loadedPreferences.getValue();
        userPreferences.setFullScreen(fullScreen);
        this.loadedPreferences.setValue(userPreferences);
    }

    @Override
    public void saveSession(boolean saveSession) {
        UserPreferences userPreferences = this.loadedPreferences.getValue();
        userPreferences.setSaveSession(saveSession);
        this.loadedPreferences.setValue(userPreferences);
        localStorageRepository.saveBoolean(SESSION_KEY, saveSession);
    }

    @Override
    public void closeSession() {
        localStorageRepository.deleteKey(USER);
        localStorageRepository.saveBoolean(SESSION_KEY, false);
        navigateTo.setValue(LOGIN);
    }

    @Override
    public MutableLiveData<UserPreferences> getPreferences() {
        return this.loadedPreferences;
    }

    @Override
    public void updateActivePreferences(UserPreferences newPreferences) {
        this.loadedPreferences.setValue(newPreferences);

    }

    @Override
    public void showCocktailDetail() {
        this.navigateTo.setValue(COCKTAIL_DETAIL);
    }

}
