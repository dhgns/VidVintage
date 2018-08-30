package com.example.dhernandez.vidvintage.presenter;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.dhernandez.vidvintage.Utils.Constants;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by dhernandez on 30/08/2018.
 */

public class LoginPresenter extends ViewModel implements ILoginPresenter{

    private String email;
    private String password;

    private MutableLiveData<Boolean> mailError;
    private MutableLiveData<Boolean> passwordError;

    private MutableLiveData<Constants.Screens> navigateTo;

    private MutableLiveData<Boolean> showLoginError;
    private MutableLiveData<Boolean> showProgress;
    private MutableLiveData<Boolean> showFormFields;

    public LoginPresenter(){

        this.showFormFields = new MutableLiveData<>();
        this.showLoginError = new MutableLiveData<>();
        this.navigateTo = new MutableLiveData<>();
        this.showProgress = new MutableLiveData<>();
        this.passwordError = new MutableLiveData<>();
        this.mailError = new MutableLiveData<>();

        this.mailError.setValue(false);
        this.passwordError.setValue(false);
        this.showLoginError.setValue(false);
        this.showFormFields.setValue(false);
        this.showProgress.setValue(false);

        checkUserSession();

    }
    @Override
    public void checkUserSession() {

    }

    @Override
    public MutableLiveData<Boolean> getShowLoginError() {
        return this.showLoginError;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
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

    private boolean isValidateForm() {

        if (email == null || email.equals("")) {
            this.mailError.setValue(true);
            return false;
        }

        if (password == null || password.equals("")) {
            this.passwordError.setValue(true);
            return false;
        }

        return true;
    }

}

