package com.example.dhernandez.vidvintage.di.module;

import com.example.dhernandez.vidvintage.ui.Fragments.ArticleDetailFragment;
import com.example.dhernandez.vidvintage.ui.Fragments.CocktailDetailFragment;
import com.example.dhernandez.vidvintage.ui.Fragments.CocktailsMenuFragment;
import com.example.dhernandez.vidvintage.ui.Fragments.FeedRssFragment;
import com.example.dhernandez.vidvintage.ui.Activities.LoginActivity;
import com.example.dhernandez.vidvintage.ui.Activities.MainActivity;
import com.example.dhernandez.vidvintage.ui.Fragments.MenuListFragment;
import com.example.dhernandez.vidvintage.ui.Fragments.ProfileFragment;
import com.example.dhernandez.vidvintage.ui.Activities.SplashActivity;

import dagger.Module;
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

    @ContributesAndroidInjector
    abstract ProfileFragment bindProfileFragment();

    @ContributesAndroidInjector
    abstract CocktailDetailFragment bindCocktailDetailFragment();

}
