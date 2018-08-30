package com.example.dhernandez.vidvintage.presenter;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;


import com.example.dhernandez.vidvintage.application.MyApplication;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by lsroa on 8/5/18.
 */

public class PresenterFactory extends ViewModelProvider.AndroidViewModelFactory  {





    public PresenterFactory(@NonNull Application application) {
        super(application);
        MyApplication.getApplicationComponent().inject(this);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SplashPresenter.class)) {
            //noinspection unchecked
            return (T) new SplashPresenter();
        }if (modelClass.isAssignableFrom(LoginPresenter.class)) {
            //noinspection unchecked
            return (T) new LoginPresenter();
        }if (modelClass.isAssignableFrom(MainPresenter.class)) {
            //noinspection unchecked
            return (T) new MainPresenter();
        }else{
            throw new IllegalArgumentException("Unknown ViewModel class".concat(modelClass.getCanonicalName()));
        }
    }
}
