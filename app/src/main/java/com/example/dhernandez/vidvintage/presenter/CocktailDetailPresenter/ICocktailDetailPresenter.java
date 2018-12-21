package com.example.dhernandez.vidvintage.presenter.CocktailDetailPresenter;

import android.arch.lifecycle.MutableLiveData;

import com.example.dhernandez.vidvintage.entity.CocktailVO;
import com.example.dhernandez.vidvintage.ui.CocktailDetailFragment;

/**
 * Created by dhernandez on 21/12/2018.
 */

public interface ICocktailDetailPresenter {


    public MutableLiveData<CocktailVO> getCocktailDetail();
}
