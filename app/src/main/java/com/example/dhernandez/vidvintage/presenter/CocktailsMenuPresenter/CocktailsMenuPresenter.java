package com.example.dhernandez.vidvintage.presenter.CocktailsMenuPresenter;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.dhernandez.vidvintage.Utils.Constants;
import com.example.dhernandez.vidvintage.application.MyApplication;
import com.example.dhernandez.vidvintage.entity.CocktailVO;
import com.example.dhernandez.vidvintage.entity.UserPreferences;
import com.example.dhernandez.vidvintage.repository.LocalStorageRepository.ILocalStorageRepository;

import java.util.List;

import static com.example.dhernandez.vidvintage.Utils.Constants.Screens.GIN_MENU;
import static com.example.dhernandez.vidvintage.Utils.Constants.Screens.OTHER_MENU;
import static com.example.dhernandez.vidvintage.Utils.Constants.Screens.RUM_MENU;
import static com.example.dhernandez.vidvintage.Utils.Constants.Screens.WHISKY_MENU;
import static com.example.dhernandez.vidvintage.Utils.Constants.Themes.DARK;

/**
 * Created by dhernandez on 31/08/2018.
 */

public class CocktailsMenuPresenter extends ViewModel implements ICocktailsMenuPresenter {

    private final MutableLiveData<UserPreferences> loadedPreferences;
    private MutableLiveData<List<CocktailVO>> cocktailListMLD;
    private MutableLiveData<Constants.Screens> navigateTo;
    private MutableLiveData<Boolean> showPreferences;
    private MutableLiveData<Boolean> darkThemeActive;
    private ILocalStorageRepository localStorageRepository;
    private MutableLiveData<Boolean> fullScreen;
    private MutableLiveData<Boolean> storeSession;

    public CocktailsMenuPresenter(ILocalStorageRepository localStorage,
                                  MutableLiveData<UserPreferences> loadedPreferencesMutableLiveData) {
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
        UserPreferences userPreferences = this.loadedPreferences.getValue();

        if(userPreferences.getFullScreen())
            this.fullScreen.setValue(true);
        else
            this.fullScreen.setValue(false);

        if(userPreferences.getTheme().equals(DARK))
            this.darkThemeActive.setValue(true);
        else
            this.darkThemeActive.setValue(false);

        this.storeSession.setValue(userPreferences.getSaveSession());

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


}
