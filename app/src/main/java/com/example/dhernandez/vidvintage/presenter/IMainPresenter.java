package com.example.dhernandez.vidvintage.presenter;

import android.arch.lifecycle.MutableLiveData;

import com.example.dhernandez.vidvintage.Utils.Constants;

/**
 * Created by dhernandez on 30/08/2018.
 */

public interface IMainPresenter {

    MutableLiveData<Constants.Screens> getNavigateTo();

    void onRss();

    void onCocktails();

    void onProfile();
}

