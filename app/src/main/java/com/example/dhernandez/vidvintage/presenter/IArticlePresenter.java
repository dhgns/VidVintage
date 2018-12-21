package com.example.dhernandez.vidvintage.presenter;

import android.arch.lifecycle.LiveData;

import com.example.dhernandez.vidvintage.entity.ArticleVO;

/**
 * Created by dhernandez on 21/12/2018.
 */

public interface IArticlePresenter {

    LiveData<ArticleVO> getArticleDetail();

    LiveData<Boolean> getIsFavourite();

    void toogleFavourite();

    boolean checkFavouriteArticle();

    void removeFavouriteArticle();

    void addFavouriteArticle();
}
