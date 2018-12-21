package com.example.dhernandez.vidvintage.presenter.CocktailDetailPresenter;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.dhernandez.vidvintage.entity.CocktailVO;
import com.example.dhernandez.vidvintage.repository.LocalStorageRepository.ILocalStorageRepository;

/**
 * Created by dhernandez on 21/12/2018.
 */

public class CocktailDetailPresenter extends ViewModel implements ICocktailDetailPresenter {

    private final MutableLiveData<CocktailVO> cocktailDetail;
    private final ILocalStorageRepository localStorageRepository;

    public CocktailDetailPresenter(MutableLiveData<CocktailVO> cocktailDetail,
                                   ILocalStorageRepository localStorageRepository) {
        this.cocktailDetail = cocktailDetail;
        this.localStorageRepository = localStorageRepository;

    }

    @Override
    public MutableLiveData<CocktailVO> getCocktailDetail() {
        return this.cocktailDetail;
    }

}
