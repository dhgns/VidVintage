package com.example.dhernandez.vidvintage.di.module;

import android.arch.lifecycle.MutableLiveData;

import com.example.dhernandez.vidvintage.entity.ArticleVO;
import com.example.dhernandez.vidvintage.entity.CocktailVO;
import com.example.dhernandez.vidvintage.entity.LoadedPreferences;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {

    @Provides
    @Singleton
    @Named("feed")
    MutableLiveData<List<ArticleVO>> getArticlesFeed() {
        return new MutableLiveData<>();
    }

    @Provides
    @Singleton
    MutableLiveData<List<CocktailVO>> getCocktailMenu() {
        return new MutableLiveData<>();
    }

    @Provides
    @Singleton
    MutableLiveData<LoadedPreferences> getLoadedPreferences() {
        return new MutableLiveData<>();
    }

    @Provides
    @Singleton
    @Named("favourites")
    MutableLiveData<List<ArticleVO>> getFavouriteArticles() {
        return new MutableLiveData<>();
    }

    @Provides
    @Singleton
    MutableLiveData<ArticleVO> getArticleDetail() {
        return new MutableLiveData<>();
    }

}
