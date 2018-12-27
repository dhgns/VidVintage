package com.example.dhernandez.vidvintage.presenter.CocktailDetailPresenter;

import android.arch.lifecycle.MutableLiveData;

import com.example.dhernandez.vidvintage.entity.CocktailVO;

/**
 * Created by dhernandez on 21/12/2018.
 */

public interface ICocktailDetailPresenter {


    void checkFavourite();

    public MutableLiveData<CocktailVO> getCocktailDetail();

    void onClickFav();

    MutableLiveData<Boolean> getIsFavourite();
}
