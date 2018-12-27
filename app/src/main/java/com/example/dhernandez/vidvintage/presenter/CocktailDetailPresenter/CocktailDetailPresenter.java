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
    private MutableLiveData<Boolean> isFavourite;

    public CocktailDetailPresenter(MutableLiveData<CocktailVO> cocktailDetail,
                                   ILocalStorageRepository localStorageRepository) {
        this.cocktailDetail = cocktailDetail;
        this.localStorageRepository = localStorageRepository;
        this.isFavourite = new MutableLiveData<>();

    }

    @Override
    public void checkFavourite() {
        this.isFavourite.setValue(
                localStorageRepository.getFavouriteCocktail(this.cocktailDetail.getValue())!= null);
    }

    @Override
    public MutableLiveData<CocktailVO> getCocktailDetail() {
        return this.cocktailDetail;
    }

    @Override
    public void onClickFav() {
        if(this.isFavourite.getValue()){
            localStorageRepository.removeFavouriteCocktail(cocktailDetail.getValue());
            this.isFavourite.setValue(false);
        }else{
            localStorageRepository.saveCocktail(cocktailDetail.getValue());
            this.isFavourite.setValue(true);
        }
    }

    @Override
    public MutableLiveData<Boolean> getIsFavourite() {
        return this.isFavourite;
    }

}
