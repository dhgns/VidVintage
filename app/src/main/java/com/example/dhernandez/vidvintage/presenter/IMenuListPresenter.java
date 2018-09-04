package com.example.dhernandez.vidvintage.presenter;

import android.arch.lifecycle.MutableLiveData;

import com.example.dhernandez.vidvintage.entity.Cocktail;

import java.util.List;

/**
 * Created by dhernandez on 03/09/2018.
 */

public interface IMenuListPresenter {
    MutableLiveData<List<Cocktail>> getCocktails();
    void showCocktailDetail(int cocktailIndex);
}
