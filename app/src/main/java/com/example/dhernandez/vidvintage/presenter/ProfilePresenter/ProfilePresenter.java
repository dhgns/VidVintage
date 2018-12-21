package com.example.dhernandez.vidvintage.presenter.ProfilePresenter;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.dhernandez.vidvintage.Utils.Constants;
import com.example.dhernandez.vidvintage.entity.ArticleVO;
import com.example.dhernandez.vidvintage.repository.LocalStorageRepository.ILocalStorageRepository;

import java.util.List;

/**
 * Created by dhernandez on 19/12/2018.
 */

public class ProfilePresenter extends ViewModel implements IProfilePresenter {

    private final ILocalStorageRepository localStorageRepository;
    private final MutableLiveData<List<ArticleVO>> favouriteArticles;
    private final MutableLiveData<ArticleVO> articleDetail;
    private MutableLiveData<Constants.Screens> navigateTo;

    public ProfilePresenter(MutableLiveData<List<ArticleVO>> favouriteArticles,
                            MutableLiveData<ArticleVO> articleDetail,
                            ILocalStorageRepository localStorageRepository) {

        this.localStorageRepository = localStorageRepository;
        this.favouriteArticles = favouriteArticles;
        this.articleDetail = articleDetail;
        this.navigateTo = new MutableLiveData<>();

        loadFavouriteArticles();
    }

    private void loadFavouriteArticles() {
        this.favouriteArticles.setValue(
                localStorageRepository.getFavouriteArticles()
        );
    }

    @Override
    public MutableLiveData<List<ArticleVO>> getFavouriteArticles() {
        return this.favouriteArticles;
    }

    @Override
    public void showArticleDetail(int childAdapterPosition) {
        this.articleDetail.setValue(this.favouriteArticles.getValue().get(childAdapterPosition));
        this.navigateTo.setValue(Constants.Screens.ARTICLE_DETAIL);
    }

    @Override
    public MutableLiveData<Constants.Screens> getNavigateTo() {
        return this.navigateTo;
    }

    @Override
    public void refreshFavourites() {
        this.favouriteArticles.setValue(
                localStorageRepository.getFavouriteArticles()
        );
    }
}

