package com.example.dhernandez.vidvintage.presenter.LoginPresenter;

import android.arch.lifecycle.MutableLiveData;

import com.example.dhernandez.vidvintage.Utils.Constants;

/**
 * Created by dhernandez on 30/08/2018.
 */

public interface ILoginPresenter {

    MutableLiveData<Boolean> getFullScreen();

    MutableLiveData<Constants.Themes> getAppTheme();

    MutableLiveData<Boolean> getShowLoginError();


    MutableLiveData<String> getEmailLD();

    MutableLiveData<String> getPasswordLD();

    MutableLiveData<Boolean> getMailError();

    MutableLiveData<Boolean> getPasswordError();

    void doFBLogin();

    void doSignUp();

    void doEmailLogin();

    MutableLiveData<Boolean> getShowFBLogin();

    MutableLiveData<Boolean> getShowFormFields();

    MutableLiveData<Constants.Screens> getNavigateTo();

    void onSignUp();

    MutableLiveData<Boolean> getShowProgress();
}