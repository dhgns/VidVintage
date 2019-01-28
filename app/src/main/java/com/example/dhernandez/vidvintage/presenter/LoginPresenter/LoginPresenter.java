package com.example.dhernandez.vidvintage.presenter.LoginPresenter;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.dhernandez.vidvintage.Utils.Constants;
import com.example.dhernandez.vidvintage.application.MyApplication;
import com.example.dhernandez.vidvintage.entity.UserPreferences;
import com.example.dhernandez.vidvintage.repository.LocalStorageRepository.ILocalStorageRepository;

/**
 * Created by dhernandez on 30/08/2018.
 */

public class LoginPresenter extends ViewModel implements ILoginPresenter {

    private final MutableLiveData<UserPreferences> loadedPreferences;
    private MutableLiveData<String> emailMLD;
    private MutableLiveData<String> passwordMLD;

    private MutableLiveData<Boolean> mailError;
    private MutableLiveData<Boolean> passwordError;

    private MutableLiveData<Constants.Screens> navigateTo;

    private MutableLiveData<Boolean> showLoginError;
    private MutableLiveData<Boolean> showProgress;
    private MutableLiveData<Boolean> showFormFields;

    ILocalStorageRepository localStorageRepository;

    private MutableLiveData<Boolean> fullScreen;
    private MutableLiveData<Constants.Themes> appTheme;

    public LoginPresenter(ILocalStorageRepository localStorageRepository,
                          MutableLiveData<UserPreferences> loadedPreferencesMutableLiveData) {
        //Import the presenter in the application component to make The job of Dagger
        //a little bit easier by the time it will have to resolve dependencies
        MyApplication.getApplicationComponent().inject(this);

        this.localStorageRepository = localStorageRepository;
        this.loadedPreferences = loadedPreferencesMutableLiveData;

        this.fullScreen = new MutableLiveData<>();
        this.appTheme = new MutableLiveData<>();
        this.showFormFields = new MutableLiveData<>();
        this.showLoginError = new MutableLiveData<>();
        this.navigateTo = new MutableLiveData<>();
        this.showProgress = new MutableLiveData<>();
        this.passwordError = new MutableLiveData<>();
        this.mailError = new MutableLiveData<>();
        this.emailMLD = new MutableLiveData();
        this.passwordMLD = new MutableLiveData<>();

        this.mailError.setValue(false);
        this.passwordError.setValue(false);
        this.showLoginError.setValue(false);
        this.showFormFields.setValue(true);
        this.showProgress.setValue(false);

    }

    @Override
    public MutableLiveData<Boolean> getFullScreen() {
        return this.fullScreen;
    }

    @Override
    public MutableLiveData<Constants.Themes> getAppTheme() {
        return this.appTheme;
    }

    @Override
    public MutableLiveData<Boolean> getShowLoginError() {
        return this.showLoginError;
    }

    @Override
    public MutableLiveData<String> getEmailLD() {
        return this.emailMLD;
    }

    @Override
    public MutableLiveData<String> getPasswordLD() {
        return this.passwordMLD;
    }

    @Override
    public MutableLiveData<Boolean> getMailError() {
        return this.mailError;
    }

    @Override
    public MutableLiveData<Boolean> getPasswordError() {
        return this.passwordError;
    }

    @Override
    public void doFBLogin() {

    }

    @Override
    public void doSignUp() {
        if (!isValidateForm()) {
            return;
        }
    }

    @Override
    public void doEmailLogin() {
        this.showFormFields.setValue(true);
    }

    @Override
    public MutableLiveData<Boolean> getShowFBLogin() {
        return null;
    }

    @Override
    public MutableLiveData<Boolean> getShowFormFields() {
        return this.showFormFields;
    }

    @Override
    public MutableLiveData<Constants.Screens> getNavigateTo() {
        return this.navigateTo;
    }

    @Override
    public void onSignUp() {
        this.showProgress.setValue(true);
    }

    @Override
    public MutableLiveData<Boolean> getShowProgress() {
        return showProgress;
    }

    private boolean isValidateForm() {

        if (emailMLD == null || emailMLD.equals("")) {
            this.mailError.setValue(true);
            return false;
        }

        if (passwordMLD == null || passwordMLD.equals("")) {
            this.passwordError.setValue(true);
            return false;
        }

        return true;
    }

}

