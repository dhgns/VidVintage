package com.example.dhernandez.vidvintage.di.module;

import android.arch.lifecycle.MutableLiveData;

import com.example.dhernandez.vidvintage.entity.ArticleVO;
import com.example.dhernandez.vidvintage.entity.Cocktail;
import com.example.dhernandez.vidvintage.repository.IVintageRepository;
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

}
