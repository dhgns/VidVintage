package com.example.dhernandez.vidvintage.presenter.ProfilePresenter;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.dhernandez.vidvintage.Utils.Constants;
import com.example.dhernandez.vidvintage.entity.ArticleVO;
import com.example.dhernandez.vidvintage.entity.CocktailVO;

import java.util.List;

/**
 * Created by dhernandez on 19/12/2018.
 */

public interface IProfilePresenter {

    MutableLiveData<List<ArticleVO>> getFavouriteArticles();

    void showArticleDetail(int childAdapterPosition);

    MutableLiveData<Constants.Screens> getNavigateTo();

    void refreshFavourites();

    void showCocktailDetail(int childAdapterPosition);

    LiveData<List<CocktailVO>> getFavouriteCocktails();

    MutableLiveData<Constants.SubSections> getActiveSection();
}
