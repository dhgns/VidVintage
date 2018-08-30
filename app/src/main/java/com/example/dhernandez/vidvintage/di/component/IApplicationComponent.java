package com.example.dhernandez.vidvintage.di.component;

import com.example.dhernandez.vidvintage.application.MyApplication;
import com.example.dhernandez.vidvintage.di.module.ApplicationModule;
import com.example.dhernandez.vidvintage.di.module.UIDataModule;
import com.example.dhernandez.vidvintage.di.module.UIModule;
import com.example.dhernandez.vidvintage.presenter.PresenterFactory;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by dhernandez on 30/08/2018.
 */


@Singleton
@Component(
        modules = {
                ApplicationModule.class,
                UIModule.class,
                UIDataModule.class,
                AndroidInjectionModule.class,
                AndroidSupportInjectionModule.class
        }
)
public interface IApplicationComponent {

    void inject(MyApplication myApplication);

    void inject(PresenterFactory presenterFactory);

    //void inject(LoginPresenter loginPresenter);

}
