package com.example.dhernandez.vidvintage.presenter.CocktailsMenuPresenter;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.dhernandez.vidvintage.Utils.Constants;
import com.example.dhernandez.vidvintage.entity.CocktailVO;

import java.util.List;

/**
 * Created by dhernandez on 31/08/2018.
 */

public interface ICocktailsMenuPresenter {

    LiveData<List<CocktailVO>> getCocktailsList();

    void onGinSectionClick();

    void onRumSectionClick();

    void onWhiskySectionClick();

    void onOtherSectionClick();

    MutableLiveData<Constants.Screens> getNavigateTo();


}
