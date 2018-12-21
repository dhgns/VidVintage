package com.example.dhernandez.vidvintage.presenter.MenuListPresenter;

import android.arch.lifecycle.MutableLiveData;

import com.example.dhernandez.vidvintage.Utils.Constants;
import com.example.dhernandez.vidvintage.entity.CocktailVO;

import java.util.List;

/**
 * Created by dhernandez on 03/09/2018.
 */

public interface IMenuListPresenter {
    MutableLiveData<List<CocktailVO>> getCocktails();
    void showCocktailDetail(int cocktailIndex);

    MutableLiveData<Constants.Screens> getNavigateTo();
}
