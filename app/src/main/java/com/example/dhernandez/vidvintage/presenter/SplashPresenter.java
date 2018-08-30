package com.example.dhernandez.vidvintage.presenter;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.dhernandez.vidvintage.Utils.Constants;

/**
 * Created by dhernandez on 30/08/2018.
 */

public class SplashPresenter extends ViewModel implements ISplashPresenter {

    private final MutableLiveData<Constants.Screens> navigateTo;

    public SplashPresenter() {
        this.navigateTo = new MutableLiveData<>();
    }

    @Override
    public void onUILoaded() {
        new android.os.Handler().postDelayed(
                () -> navigateTo.setValue(Constants.Screens.LOGIN), 2000);
    }

    public MutableLiveData<Constants.Screens> getNavigateTo() {
        return navigateTo;
    }
}
