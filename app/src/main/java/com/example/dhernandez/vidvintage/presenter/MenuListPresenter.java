package com.example.dhernandez.vidvintage.presenter;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.dhernandez.vidvintage.application.MyApplication;
import com.example.dhernandez.vidvintage.entity.Cocktail;
import com.example.dhernandez.vidvintage.entity.ErrorComm;
import com.example.dhernandez.vidvintage.repository.VintageRepository;

import java.util.List;

/**
 * Created by dhernandez on 03/09/2018.
 */

public class MenuListPresenter  extends ViewModel implements IMenuListPresenter{

    private final VintageRepository vintageRepository;
    private MutableLiveData<List<Cocktail>> cocktailListMLD;

    public MenuListPresenter(MutableLiveData<List<Cocktail>> cocktailList){
        //Import the presenter in the application component to make The job of Dagger
        //a little bit easier by the time it will have to resolve dependencies
        MyApplication.getApplicationComponent().inject(this);

        this.cocktailListMLD = cocktailList;
        this.vintageRepository = new VintageRepository();

        this.getCocktailsMenu();
    }

    private void getCocktailsMenu() {
        vintageRepository.getCocktailsMenu().observeForever(cocktailList->{
            if(cocktailList != null){
                if(cocktailList.getErrorComm().getStatus() == ErrorComm.STATUS.NO_ERROR)
                    cocktailListMLD.setValue(cocktailList.getCocktails());
            }
        });
    }

    @Override
    public MutableLiveData<List<Cocktail>> getCocktails() {
        return this.cocktailListMLD;
    }

    @Override
    public void showCocktailDetail(int cocktailIndex) {

    }
}
