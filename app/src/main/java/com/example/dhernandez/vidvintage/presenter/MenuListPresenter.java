package com.example.dhernandez.vidvintage.presenter;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

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
