package com.example.dhernandez.vidvintage.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;

import com.example.dhernandez.vidvintage.application.MyApplication;
import com.example.dhernandez.vidvintage.presenter.PresenterFactory;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dhernandez on 30/08/2018.
 */

@Module
public class ApplicationModule {

    private final MyApplication myApplication;
    private static final String SHARED_PREFERENCES = "sp";

    public ApplicationModule(MyApplication myApplication) {
        this.myApplication = myApplication;
    }

    @Provides
    @Singleton
    MyApplication getMyApplication(){
        return myApplication;
    }

    @Provides
    @Singleton
    Application getApplication(){
        return myApplication;
    }

    @Provides
    @Singleton
    public PresenterFactory getPresenterFactory(){
        return new PresenterFactory(this.myApplication);
    }

    @Provides
    @Singleton
    SharedPreferences getSharedPreferences(Application application){
        return application.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    AssetManager getAssetManager(){
        return myApplication.getAssets();
    }
}
