package com.example.dhernandez.vidvintage.presenter;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;


import com.example.dhernandez.vidvintage.application.MyApplication;
import com.example.dhernandez.vidvintage.entity.ArticleVO;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by lsroa on 8/5/18.
 */

public class PresenterFactory extends ViewModelProvider.AndroidViewModelFactory  {


    //We can inject the Shared data between presenters from here, providing it from the UIDataModule
    //The share data should be wrapped in LiveData object to preserve the lifecicle of the viewmodels presenters
    @Inject
    MutableLiveData<List<ArticleVO>> feedArticles;

    public PresenterFactory(@NonNull Application application) {
        super(application);
        MyApplication.getApplicationComponent().inject(this);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SplashPresenter.class)) {
            //noinspection unchecked
            return (T) new SplashPresenter();
        }if (modelClass.isAssignableFrom(LoginPresenter.class)) {
            //noinspection unchecked
            return (T) new LoginPresenter();
        }if (modelClass.isAssignableFrom(MainPresenter.class)) {
            //noinspection unchecked
            return (T) new MainPresenter();
        }if (modelClass.isAssignableFrom(FeedRssPresenter.class)) {
            //noinspection unchecked
            return (T) new FeedRssPresenter(feedArticles);
        }else{
            throw new IllegalArgumentException("Unknown ViewModel class".concat(modelClass.getCanonicalName()));
        }
    }
}
