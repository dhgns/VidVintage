package com.example.dhernandez.vidvintage.di.module;

import com.example.dhernandez.vidvintage.presenter.MenuListPresenter;
import com.example.dhernandez.vidvintage.repository.IVintageRepository;
import com.example.dhernandez.vidvintage.repository.VintageRepository;
import com.example.dhernandez.vidvintage.repository.VintageService;
import com.example.dhernandez.vidvintage.ui.ArticleDetailFragment;
import com.example.dhernandez.vidvintage.ui.CocktailsMenuFragment;
import com.example.dhernandez.vidvintage.ui.FeedRssFragment;
import com.example.dhernandez.vidvintage.ui.MenuListFragment;
import com.example.dhernandez.vidvintage.ui.SplashActivity;
import com.example.dhernandez.vidvintage.ui.LoginActivity;
import com.example.dhernandez.vidvintage.ui.MainActivity;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by dhernandez on 30/08/2018.
 */


@Module
public abstract class UIModule {

    @ContributesAndroidInjector
    abstract SplashActivity bindSplashActivity();

    @ContributesAndroidInjector
    abstract LoginActivity bindLoginActivity();

    @ContributesAndroidInjector
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector
    abstract FeedRssFragment bindFeedRssFragment();

    @ContributesAndroidInjector
    abstract ArticleDetailFragment bindArticleDetailFragment();

    @ContributesAndroidInjector
    abstract CocktailsMenuFragment bindCocktailsMenuFragment();

    @ContributesAndroidInjector
    abstract MenuListFragment bindMenuListFragment();

}
