package com.example.dhernandez.vidvintage.presenter;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.dhernandez.vidvintage.Utils.Constants;

/**
 * Created by dhernandez on 30/08/2018.
 */

public class MainPresenter extends ViewModel implements IMainPresenter{

    private final MutableLiveData<Constants.Screens> navigateTo;

    public MainPresenter(){

        this.navigateTo = new MutableLiveData<>();
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
    public void onCocktails() {
        this.navigateTo.setValue(Constants.Screens.COCKTAILS);
    }

    @Override
    public void onProfile() {
        this.navigateTo.setValue(Constants.Screens.PROFILE);
    }
}
