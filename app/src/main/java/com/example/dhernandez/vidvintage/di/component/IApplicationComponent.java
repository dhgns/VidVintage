package com.example.dhernandez.vidvintage.di.component;

import com.example.dhernandez.vidvintage.application.MyApplication;
import com.example.dhernandez.vidvintage.di.module.ApplicationModule;
import com.example.dhernandez.vidvintage.di.module.RepositoryModule;
import com.example.dhernandez.vidvintage.di.module.DataModule;
import com.example.dhernandez.vidvintage.di.module.UIModule;
import com.example.dhernandez.vidvintage.presenter.ArticlePresenter;
import com.example.dhernandez.vidvintage.presenter.CocktailsMenuPresenter.CocktailsMenuPresenter;
import com.example.dhernandez.vidvintage.presenter.FeedRssPresenter.FeedRssPresenter;
import com.example.dhernandez.vidvintage.presenter.LoginPresenter.LoginPresenter;
import com.example.dhernandez.vidvintage.presenter.MainPresenter.MainPresenter;
import com.example.dhernandez.vidvintage.presenter.MenuListPresenter.MenuListPresenter;
import com.example.dhernandez.vidvintage.presenter.PresenterFactory;
import com.example.dhernandez.vidvintage.presenter.ProfilePresenter.ProfilePresenter;
import com.example.dhernandez.vidvintage.repository.LocalStorageRepository.LocalStorageRepository;
import com.example.dhernandez.vidvintage.repository.VintageRepository.VintageRepository;

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
                DataModule.class,
                RepositoryModule.class,
                AndroidInjectionModule.class,
                AndroidSupportInjectionModule.class
        }
)
public interface IApplicationComponent {

    //Application
    void inject(MyApplication myApplication);

    //Presenters
    void inject(PresenterFactory presenterFactory);

    void inject(LoginPresenter loginPresenter);

    void inject(MainPresenter mainPresenter);

    void inject(FeedRssPresenter feedRssPresenter);

    void inject(MenuListPresenter menuListPresenter);

    void inject(CocktailsMenuPresenter cocktailsMenuPresenter);

    void inject(ProfilePresenter profilePresenter);

    void inject(ArticlePresenter articlePresenter);

    //Repositories
    void inject(LocalStorageRepository localStorageRepository);

    void inject(VintageRepository vintageRepository);

    MyApplication getApplication();

}
