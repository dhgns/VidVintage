package com.example.dhernandez.vidvintage.di.module;

import android.arch.lifecycle.MutableLiveData;
import android.content.SharedPreferences;

import com.example.dhernandez.vidvintage.entity.ArticleVO;
import com.example.dhernandez.vidvintage.entity.Cocktail;
import com.example.dhernandez.vidvintage.entity.LoadedPreferences;
import com.example.dhernandez.vidvintage.repository.ILocalStorageRepository;
import com.example.dhernandez.vidvintage.repository.IVintageRepository;
import com.example.dhernandez.vidvintage.repository.LocalStorageRepository;
import com.example.dhernandez.vidvintage.repository.VintageRepository;
import com.example.dhernandez.vidvintage.repository.VintageService;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class UIDataModule {

    @Provides
    @Singleton
    MutableLiveData<List<ArticleVO>> getArticlesFeed(){ return new MutableLiveData<>(); }

    @Provides
    @Singleton
    MutableLiveData<List<Cocktail>> getCocktailMenu(){ return new MutableLiveData<>(); }

    @Provides
    ILocalStorageRepository getLocalStorageRepository(SharedPreferences sharedPreferences){
        return new LocalStorageRepository(sharedPreferences);
    }

    @Provides
    @Singleton
    MutableLiveData<LoadedPreferences> getLoadedPreferences(){
        return new MutableLiveData<>();
    }


}
