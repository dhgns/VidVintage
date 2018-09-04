package com.example.dhernandez.vidvintage.presenter;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.dhernandez.vidvintage.Utils.Constants;
import com.example.dhernandez.vidvintage.entity.Cocktail;

import java.util.List;

/**
 * Created by dhernandez on 31/08/2018.
 */

public interface ICocktailsMenuPresenter {

    LiveData<List<Cocktail>> getCocktailsList();

    void onGinSectionClick();

    void onRumSectionClick();

    void onWhiskySectionClick();

    void onOtherSectionClick();

    MutableLiveData<Constants.Screens> getNavigateTo();
}
