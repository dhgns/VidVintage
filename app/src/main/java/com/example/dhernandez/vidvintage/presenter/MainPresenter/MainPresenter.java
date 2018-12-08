package com.example.dhernandez.vidvintage.presenter.MainPresenter;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.dhernandez.vidvintage.Utils.Constants;
import com.example.dhernandez.vidvintage.application.MyApplication;
import com.example.dhernandez.vidvintage.entity.LoadedPreferences;
import com.example.dhernandez.vidvintage.repository.LocalStorageRepository.ILocalStorageRepository;

import static com.example.dhernandez.vidvintage.Utils.Constants.Themes.DARK;
import static com.example.dhernandez.vidvintage.Utils.Constants.Themes.LIGHT;

/**
 * Created by dhernandez on 30/08/2018.
 */

public class MainPresenter extends ViewModel implements IMainPresenter{

    private final MutableLiveData<Constants.Screens> navigateTo;
    private final MutableLiveData<LoadedPreferences> loadedPreferences;

    ILocalStorageRepository localStorageRepository;
    private MutableLiveData<Boolean> fullScreen;
    private MutableLiveData<Constants.Themes> appTheme;

    public MainPresenter(ILocalStorageRepository localStorageRepository,
                         MutableLiveData<LoadedPreferences> loadedPreferencesMutableLiveData){

        MyApplication.getApplicationComponent().inject(this);

        this.localStorageRepository = localStorageRepository;
        this.loadedPreferences = loadedPreferencesMutableLiveData;

        this.appTheme = new MutableLiveData<>();
        this.fullScreen = new MutableLiveData<>();
        this.navigateTo = new MutableLiveData<>();

        setPreferences();

        this.navigateTo.setValue(Constants.Screens.COCKTAILS_MENU);
    }

    private void setPreferences() {
        LoadedPreferences loadedPreferences = this.loadedPreferences.getValue();

        if(loadedPreferences.getFullScreen())
            this.fullScreen.setValue(true);
        else
            this.fullScreen.setValue(false);

        if(loadedPreferences.getTheme().equals(DARK))
            this.appTheme.setValue(DARK);
        else if(loadedPreferences.getTheme().equals(LIGHT))
            this.appTheme.setValue(LIGHT);

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
    public void onProfile() {
        this.navigateTo.setValue(Constants.Screens.PROFILE);
    }

    @Override
    public LiveData<Boolean> getFullScreen() {
        return this.fullScreen;
    }
}
