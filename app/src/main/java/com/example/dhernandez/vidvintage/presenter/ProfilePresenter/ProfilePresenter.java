package com.example.dhernandez.vidvintage.presenter.ProfilePresenter;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;

import com.example.dhernandez.vidvintage.Utils.Constants;
import com.example.dhernandez.vidvintage.entity.ArticleVO;
import com.example.dhernandez.vidvintage.entity.Author;
import com.example.dhernandez.vidvintage.entity.CocktailVO;
import com.example.dhernandez.vidvintage.repository.LocalStorageRepository.ILocalStorageRepository;
import com.example.dhernandez.vidvintage.repository.VintageRepository.IVintageRepository;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhernandez on 19/12/2018.
 */

public class ProfilePresenter extends ViewModel implements IProfilePresenter {

    private final ILocalStorageRepository localStorageRepository;

    private final MutableLiveData<ArticleVO> articleDetail;
    private final MutableLiveData<CocktailVO> cocktailDetail;
    private final IVintageRepository vintageRepository;

    private MutableLiveData<List<CocktailVO>> favouriteCocktails;
    private MutableLiveData<List<ArticleVO>> favouriteArticles;

    private MutableLiveData<Constants.SubSections> activeSection;
    private MutableLiveData<Constants.Screens> navigateTo;
    private MutableLiveData<Bitmap> profilePicture;

    public ProfilePresenter(MutableLiveData<List<ArticleVO>> favouriteArticles,
                            MutableLiveData<List<CocktailVO>> favouriteCocktails,
                            MutableLiveData<ArticleVO> articleDetail,
                            MutableLiveData<CocktailVO> cocktailDetail,
                            ILocalStorageRepository localStorageRepository,
                            IVintageRepository vintageRepository) {

        this.localStorageRepository = localStorageRepository;
        this.vintageRepository = vintageRepository;
        this.favouriteArticles = favouriteArticles;
        this.favouriteCocktails = favouriteCocktails;
        this.articleDetail = articleDetail;
        this.cocktailDetail = cocktailDetail;

        this.navigateTo = new MutableLiveData<>();
        this.activeSection = new MutableLiveData<>();
        this.profilePicture = new MutableLiveData<>();

        activeSection.setValue(Constants.SubSections.ARTICLES);

        loadFavouriteArticles();
        loadFavouriteCocktails();
        loadPictureProfile();
    }

    private void loadPictureProfile() {
        FirebaseAuth mAtuh = FirebaseAuth.getInstance();
        this.profilePicture.setValue(localStorageRepository.loadImage(mAtuh.getCurrentUser().getEmail()));
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
        this.favouriteCocktails.setValue(
                localStorageRepository.getFavouriteCocktails()
        );
    }

    @Override
    public void showCocktailDetail(int childAdapterPosition) {
        this.cocktailDetail.setValue(
                this.favouriteCocktails.getValue().get(childAdapterPosition));
    }

    @Override
    public LiveData<List<CocktailVO>> getFavouriteCocktails() {
        return this.favouriteCocktails;
    }

    @Override
    public MutableLiveData<Constants.SubSections> getActiveSection() {
        return this.activeSection;
    }

    @Override
    public void setProfilePicture(Bitmap bitmap) {
        this.profilePicture.setValue(bitmap);
    }

    @Override
    public MutableLiveData<Bitmap> getProfilePicture() {
        return this.profilePicture;
    }

    @Override
    public void saveProfilePicture(String email) {
        this.localStorageRepository.saveImage(email, this.profilePicture.getValue());
    }

    @Override
    public void addNewCocktail() {
        CocktailVO cocktailVO = new CocktailVO();
        cocktailVO.setName("Test Cocktail");
        cocktailVO.setDescription("Este cocktel es de prueba");
        cocktailVO.setAlcoholic(false);
        cocktailVO.setAuthor(new Author("prueba", "prueba", "prueba@prueba.com", "eee"));
        cocktailVO.setCocktailUrl("cocktail url");
        cocktailVO.setLikes(0);
        cocktailVO.setReceipt("Reeceta magica");
        cocktailVO.setTags(new ArrayList<>());
        cocktailVO.setIngredients(new ArrayList<>());
        cocktailVO.setUrlPhoto("https://www.saveur.com/sites/saveur.com/files/styles/250_1x_/public/copper-king-6_2000x1500.jpg?itok=o1pbRcWe&fc=50,50");

        vintageRepository.addNewCocktail(cocktailVO);
    }
}

