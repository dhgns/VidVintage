package com.example.dhernandez.vidvintage.presenter;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.dhernandez.vidvintage.Utils.Constants;
import com.example.dhernandez.vidvintage.entity.ArticleVO;

import java.util.List;

/**
 * Created by dhernandez on 30/08/2018.
 */

public class FeedRssPresenter extends ViewModel implements IFeedRssPresenter  {

    private MutableLiveData<List<ArticleVO>> feedArticles;
    private final MutableLiveData<ArticleVO> articleClicked;
    private MutableLiveData<Constants.Screens> navigateTo;

    public FeedRssPresenter(MutableLiveData<List<ArticleVO>> feedArticles){
        this.feedArticles = feedArticles;
        this.articleClicked = new MutableLiveData<>();
        this.navigateTo = new MutableLiveData<>();
    }

    @Override
    public void showArticleDetail(ArticleVO articleVO) {
        this.articleClicked.setValue(articleVO);
        this.navigateTo.setValue(Constants.Screens.ARTICLE_DETAIL);

    }

    @Override
    public MutableLiveData<List<ArticleVO>> getFeedArticles(){
        return this.feedArticles;
    }

    @Override
    public MutableLiveData<ArticleVO> getArticleClicked(){
        return this.articleClicked;
    }

    @Override
    public MutableLiveData<Constants.Screens> getNavigateTo(){
        return this.navigateTo;
    }
}