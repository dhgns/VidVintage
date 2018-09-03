package com.example.dhernandez.vidvintage.application;

/**
 * Created by dhernandez on 30/08/2018.
 */

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.example.dhernandez.vidvintage.di.component.DaggerIApplicationComponent;
import com.example.dhernandez.vidvintage.di.component.IApplicationComponent;
import com.example.dhernandez.vidvintage.di.module.ApplicationModule;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MyApplication extends Application implements HasActivityInjector, HasSupportFragmentInjector {

    //We are going to let Dagger the providing of activities and fragments

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;
    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidFragmentInjector;

    private static IApplicationComponent applicationComponent;

    public MyApplication() {

        MyApplication.applicationComponent = DaggerIApplicationComponent.builder()
                .applicationModule(
                        new ApplicationModule(
                                this)
                ).build();

        applicationComponent.inject(this);
    }

    @Override
    public void onCreate() {

        super.onCreate();

    }

    public static IApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }


    //We need to override these methods to set Dagger as the owner of activities and fragments providing
    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidFragmentInjector;
    }
}
