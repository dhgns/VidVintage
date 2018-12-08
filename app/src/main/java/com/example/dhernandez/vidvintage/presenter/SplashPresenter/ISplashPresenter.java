package com.example.dhernandez.vidvintage.presenter.SplashPresenter;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.dhernandez.vidvintage.Utils.Constants;

/**
 * Created by dhernandez on 30/08/2018.
 */

public interface ISplashPresenter {

    MutableLiveData<Constants.Screens> getNavigateTo();

    void onUILoaded();
}
