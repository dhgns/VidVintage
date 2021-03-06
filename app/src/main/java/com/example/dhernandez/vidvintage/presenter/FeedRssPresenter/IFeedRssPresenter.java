package com.example.dhernandez.vidvintage.presenter.FeedRssPresenter;

import android.arch.lifecycle.MutableLiveData;

import com.example.dhernandez.vidvintage.Utils.Constants;
import com.example.dhernandez.vidvintage.entity.ArticleVO;

import java.util.List;

/**
 * Created by dhernandez on 30/08/2018.
 */

public interface IFeedRssPresenter {

    void showArticleDetail(ArticleVO articleVO);

    MutableLiveData<Boolean> getIsFavourite();

    MutableLiveData<List<ArticleVO>> getFeedArticles();

    MutableLiveData<ArticleVO> getArticleClicked();

    MutableLiveData<Constants.Screens> getNavigateTo();

    MutableLiveData<Boolean> showReadingProgress();

    MutableLiveData<Boolean> showFeedReadError();

    void readFeed();

    void refreshFeed();

    boolean checkFavouriteArticle(String url);

    void removeFavouriteArticle();

    void addFavouriteArticle();

    void toogleFavourite();
}
