package com.example.dhernandez.vidvintage.presenter.ProfilePresenter;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.graphics.Bitmap;

import com.example.dhernandez.vidvintage.Utils.Constants;
import com.example.dhernandez.vidvintage.entity.ArticleVO;
import com.example.dhernandez.vidvintage.entity.CocktailVO;
import com.google.firebase.auth.FirebaseUser;

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

    void setProfilePicture(Bitmap bitmap);

    MutableLiveData<Bitmap> getProfilePicture();

    void saveProfilePicture(String email);

    MutableLiveData<Constants.Steps> getNewCocktailStep();

    void addNewCocktail();

    void onNewCocktailBackClick();

    void onNewCocktailNextClick();

    MutableLiveData<String> getNewCocktailName();

    MutableLiveData<String> getNewCocktailURL();

    MutableLiveData<String> getNewCocktailDescription();

    MutableLiveData<String> getNewCocktailReceipt();

    MutableLiveData<FirebaseUser> getUsername();

    void addNewIngredient(String s);

    MutableLiveData<List<String>> getNewCocktailIngredients();

    void removeIngredient(int position);

    MutableLiveData<Integer> getShowError();

    MutableLiveData<Boolean> getCleanFields();
}
