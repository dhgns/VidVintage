package com.example.dhernandez.vidvintage.presenter;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.dhernandez.vidvintage.entity.ArticleVO;
import com.example.dhernandez.vidvintage.repository.LocalStorageRepository.ILocalStorageRepository;

/**
 * Created by dhernandez on 21/12/2018.
 */

public class ArticlePresenter extends ViewModel implements IArticlePresenter {
    private final MutableLiveData<ArticleVO> articleDetail;
    private final ILocalStorageRepository localStorageRepository;
    private MutableLiveData<Boolean> isFavourite;

    public ArticlePresenter(MutableLiveData<ArticleVO> articleDetail, ILocalStorageRepository localStorageRepository) {
        this.articleDetail = articleDetail;
        this.localStorageRepository = localStorageRepository;
        this.isFavourite = new MutableLiveData<>();
    }

    @Override
    public LiveData<ArticleVO> getArticleDetail() {
        return this.articleDetail;
    }

    @Override
    public MutableLiveData<Boolean> getIsFavourite() {
        return this.isFavourite;
    }

    @Override
    public void toogleFavourite() {
        if(this.isFavourite.getValue()){
            this.removeFavouriteArticle();
        }else{
            this.addFavouriteArticle();
        }
        this.isFavourite.setValue(!this.isFavourite.getValue());
    }

    @Override
    public boolean checkFavouriteArticle() {
        this.isFavourite.setValue(localStorageRepository.getFavouriteArticle(this.articleDetail.getValue().getUrl()) != null);
        return this.isFavourite.getValue();
    }

    @Override
    public void removeFavouriteArticle() {
        localStorageRepository.removeFavourite(this.articleDetail.getValue());
    }

    @Override
    public void addFavouriteArticle() {
        localStorageRepository.addArticleFavourite(articleDetail.getValue());
    }

}
