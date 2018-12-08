package com.example.dhernandez.vidvintage.di.module;

import android.arch.lifecycle.MutableLiveData;

import com.example.dhernandez.vidvintage.entity.ArticleVO;
import com.example.dhernandez.vidvintage.entity.Cocktail;
import com.example.dhernandez.vidvintage.entity.LoadedPreferences;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {

    @Provides
    @Singleton
    MutableLiveData<List<ArticleVO>> getArticlesFeed(){ return new MutableLiveData<>(); }

    @Provides
    @Singleton
    MutableLiveData<List<Cocktail>> getCocktailMenu(){ return new MutableLiveData<>(); }

    @Provides
    @Singleton
    MutableLiveData<LoadedPreferences> getLoadedPreferences(){
        return new MutableLiveData<>();
    }


}
