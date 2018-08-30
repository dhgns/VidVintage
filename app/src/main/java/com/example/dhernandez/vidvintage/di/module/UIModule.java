package com.example.dhernandez.vidvintage.di.module;

import com.example.dhernandez.vidvintage.ui.SplashActivity;
import com.example.dhernandez.vidvintage.ui.LoginActivity;
import com.example.dhernandez.vidvintage.ui.MainActivity;

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


}
