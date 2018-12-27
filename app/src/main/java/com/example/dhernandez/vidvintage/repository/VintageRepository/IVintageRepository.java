package com.example.dhernandez.vidvintage.repository.VintageRepository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.dhernandez.vidvintage.entity.CocktailVO;
import com.example.dhernandez.vidvintage.entity.CocktailsMenuResponse;

/**
 * Created by dhernandez on 03/09/2018.
 */

public interface IVintageRepository {

    LiveData<CocktailsMenuResponse> getCocktailsMenu();

    MutableLiveData<CocktailVO> addNewCocktail(CocktailVO cocktailVO);

}
