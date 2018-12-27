package com.example.dhernandez.vidvintage.presenter;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.dhernandez.vidvintage.application.MyApplication;
import com.example.dhernandez.vidvintage.entity.ArticleVO;
import com.example.dhernandez.vidvintage.entity.CocktailVO;
import com.example.dhernandez.vidvintage.entity.UserPreferences;
import com.example.dhernandez.vidvintage.presenter.ArticlePresenter.ArticlePresenter;
import com.example.dhernandez.vidvintage.presenter.CocktailDetailPresenter.CocktailDetailPresenter;
import com.example.dhernandez.vidvintage.presenter.CocktailsMenuPresenter.CocktailsMenuPresenter;
import com.example.dhernandez.vidvintage.presenter.FeedRssPresenter.FeedRssPresenter;
import com.example.dhernandez.vidvintage.presenter.LoginPresenter.LoginPresenter;
import com.example.dhernandez.vidvintage.presenter.MainPresenter.MainPresenter;
import com.example.dhernandez.vidvintage.presenter.MenuListPresenter.MenuListPresenter;
import com.example.dhernandez.vidvintage.presenter.ProfilePresenter.ProfilePresenter;
import com.example.dhernandez.vidvintage.presenter.SplashPresenter.SplashPresenter;
import com.example.dhernandez.vidvintage.repository.LocalStorageRepository.ILocalStorageRepository;
import com.example.dhernandez.vidvintage.repository.VintageRepository.IVintageRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by lsroa on 8/5/18.
 */

public class PresenterFactory extends ViewModelProvider.AndroidViewModelFactory {


    //We can inject the Shared data between presenters from here, providing it from the DataModule
    //The share data should be wrapped in LiveData object to preserve the lifecicle of the viewmodels presenters
    @Inject
    @Named("feed")
    MutableLiveData<List<ArticleVO>> feedArticles;

    @Inject
    MutableLiveData<List<CocktailVO>> cocktailList;

    @Inject
    ILocalStorageRepository localStorageRepository;

    @Inject
    MutableLiveData<UserPreferences> loadedPreferencesMutableLiveData;

    @Inject
    IVintageRepository vintageRepository;

    @Inject
    @Named("favourites")
    MutableLiveData<List<ArticleVO>> favouriteArticles;

    @Inject
    MutableLiveData<ArticleVO> articleDetail;

    @Inject
    MutableLiveData<CocktailVO> cocktailDetail;

    @Inject
    @Named("favourites")
    MutableLiveData<List<CocktailVO>> favouriteCocktails;

    public PresenterFactory(@NonNull Application application) {
        super(application);
        MyApplication.getApplicationComponent().inject(this);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SplashPresenter.class)) {
            //noinspection unchecked
            return (T) new SplashPresenter(localStorageRepository, loadedPreferencesMutableLiveData);
        }
        if (modelClass.isAssignableFrom(LoginPresenter.class)) {
            //noinspection unchecked
            return (T) new LoginPresenter(localStorageRepository, loadedPreferencesMutableLiveData);
        }
        if (modelClass.isAssignableFrom(MainPresenter.class)) {
            //noinspection unchecked
            return (T) new MainPresenter(localStorageRepository, loadedPreferencesMutableLiveData, cocktailDetail);
        }
        if (modelClass.isAssignableFrom(FeedRssPresenter.class)) {
            //noinspection unchecked
            return (T) new FeedRssPresenter(feedArticles, localStorageRepository, articleDetail);
        }
        if (modelClass.isAssignableFrom(CocktailsMenuPresenter.class)) {
            //noinspection unchecked
            return (T) new CocktailsMenuPresenter(localStorageRepository, loadedPreferencesMutableLiveData);
        }
        if (modelClass.isAssignableFrom(MenuListPresenter.class)) {
            //noinspection unchecked
            return (T) new MenuListPresenter(cocktailList, cocktailDetail, vintageRepository);
        }
        if (modelClass.isAssignableFrom(ProfilePresenter.class)) {
            //noinspection unchecked
            return (T) new ProfilePresenter(
                    favouriteArticles,
                    favouriteCocktails,
                    articleDetail,
                    cocktailDetail,
                    localStorageRepository,
                    vintageRepository);
        }
        if (modelClass.isAssignableFrom(ArticlePresenter.class)) {
            //noinspection unchecked
            return (T) new ArticlePresenter(articleDetail, localStorageRepository);
        }
        if (modelClass.isAssignableFrom(CocktailDetailPresenter.class)) {
            //noinspection unchecked
            return (T) new CocktailDetailPresenter(cocktailDetail, localStorageRepository);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class".concat(modelClass.getCanonicalName()));
        }
    }
}
