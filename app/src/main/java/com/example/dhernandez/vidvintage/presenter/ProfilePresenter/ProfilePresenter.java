package com.example.dhernandez.vidvintage.presenter.ProfilePresenter;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.dhernandez.vidvintage.Utils.Constants;
import com.example.dhernandez.vidvintage.entity.ArticleVO;
import com.example.dhernandez.vidvintage.entity.CocktailVO;
import com.example.dhernandez.vidvintage.entity.LoadedPreferences;
import com.example.dhernandez.vidvintage.repository.LocalStorageRepository.ILocalStorageRepository;

import java.util.List;

import static com.example.dhernandez.vidvintage.Utils.Constants.Themes.DARK;
import static com.example.dhernandez.vidvintage.Utils.Constants.Themes.LIGHT;

/**
 * Created by dhernandez on 19/12/2018.
 */

public class ProfilePresenter extends ViewModel implements IProfilePresenter {

    private final ILocalStorageRepository localStorageRepository;

    private final MutableLiveData<ArticleVO> articleDetail;

    private MutableLiveData<List<CocktailVO>> favouriteCocktails;
    private MutableLiveData<List<ArticleVO>> favouriteArticles;

    private MutableLiveData<Constants.SubSections> activeSection;
    private MutableLiveData<Constants.Screens> navigateTo;

    public ProfilePresenter(MutableLiveData<List<ArticleVO>> favouriteArticles,
                            MutableLiveData<List<CocktailVO>> favouriteCocktails,
                            MutableLiveData<ArticleVO> articleDetail,
                            ILocalStorageRepository localStorageRepository) {

        this.localStorageRepository = localStorageRepository;
        this.favouriteArticles = favouriteArticles;
        this.favouriteCocktails = favouriteCocktails;
        this.articleDetail = articleDetail;

        this.navigateTo = new MutableLiveData<>();
        this.activeSection = new MutableLiveData<>();

        activeSection.setValue(Constants.SubSections.ARTICLES);

        loadFavouriteArticles();
        loadFavouriteCocktails();
    }

    private void loadFavouriteCocktails() {
        this.favouriteCocktails.setValue(
                localStorageRepository.getFavouriteCocktails()
        );
    }

    private void loadFavouriteArticles() {
        this.favouriteArticles.setValue(
                localStorageRepository.getFavouriteArticles()
        );
    }

    @Override
    public MutableLiveData<List<ArticleVO>> getFavouriteArticles() {
        return this.favouriteArticles;
    }

    @Override
    public void showArticleDetail(int childAdapterPosition) {
        this.articleDetail.setValue(this.favouriteArticles.getValue().get(childAdapterPosition));
        this.navigateTo.setValue(Constants.Screens.ARTICLE_DETAIL);
    }

    @Override
    public MutableLiveData<Constants.Screens> getNavigateTo() {
        return this.navigateTo;
    }

    @Override
    public void refreshFavourites() {
        this.favouriteArticles.setValue(
                localStorageRepository.getFavouriteArticles()
        );
    }

    @Override
    public void showCocktailDetail(int childAdapterPosition) {

    }

    @Override
    public LiveData<List<CocktailVO>> getFavouriteCocktails() {
        return this.favouriteCocktails;
    }

    @Override
    public MutableLiveData<Constants.SubSections> getActiveSection() {
        return this.activeSection;
    }

}

