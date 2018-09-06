package com.example.dhernandez.vidvintage.presenter;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.dhernandez.vidvintage.Utils.Constants;
import com.example.dhernandez.vidvintage.application.MyApplication;
import com.example.dhernandez.vidvintage.entity.Cocktail;
import com.example.dhernandez.vidvintage.entity.ErrorComm;
import com.example.dhernandez.vidvintage.repository.IVintageRepository;
import com.example.dhernandez.vidvintage.repository.VintageRepository;

import java.util.List;

import static com.example.dhernandez.vidvintage.Utils.Constants.Screens.GIN_MENU;
import static com.example.dhernandez.vidvintage.Utils.Constants.Screens.OTHER_MENU;
import static com.example.dhernandez.vidvintage.Utils.Constants.Screens.RUM_MENU;
import static com.example.dhernandez.vidvintage.Utils.Constants.Screens.WHISKY_MENU;

/**
 * Created by dhernandez on 31/08/2018.
 */

public class CocktailsMenuPresenter extends ViewModel implements ICocktailsMenuPresenter {

    private MutableLiveData<List<Cocktail>> cocktailListMLD;
    private MutableLiveData<Constants.Screens> navigateTo;

    public CocktailsMenuPresenter(){
        //Import the presenter in the application component to make The job of Dagger
        //a little bit easier by the time it will have to resolve dependencies
        MyApplication.getApplicationComponent().inject(this);

        this.cocktailListMLD = new MutableLiveData<>();
        this.navigateTo = new MutableLiveData<>();
    }

    @Override
    public LiveData<List<Cocktail>> getCocktailsList() {
        return this.cocktailListMLD;
    }

    @Override
    public void onGinSectionClick() {
        this.navigateTo.setValue(GIN_MENU);
    }

    @Override
    public void onRumSectionClick() {
        this.navigateTo.setValue(RUM_MENU);
    }

    @Override
    public void onWhiskySectionClick() {
        this.navigateTo.setValue(WHISKY_MENU);
    }

    @Override
    public void onOtherSectionClick() {
        this.navigateTo.setValue(OTHER_MENU);
    }

    @Override
    public MutableLiveData<Constants.Screens> getNavigateTo(){
        return this.navigateTo;
    }
}
