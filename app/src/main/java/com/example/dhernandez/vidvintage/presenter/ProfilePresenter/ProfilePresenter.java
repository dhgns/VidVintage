package com.example.dhernandez.vidvintage.presenter.ProfilePresenter;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;

import com.example.dhernandez.vidvintage.R;
import com.example.dhernandez.vidvintage.Utils.Constants;
import com.example.dhernandez.vidvintage.entity.ArticleVO;
import com.example.dhernandez.vidvintage.entity.Author;
import com.example.dhernandez.vidvintage.entity.CocktailVO;
import com.example.dhernandez.vidvintage.entity.Ingredient;
import com.example.dhernandez.vidvintage.repository.LocalStorageRepository.ILocalStorageRepository;
import com.example.dhernandez.vidvintage.repository.VintageRepository.IVintageRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import static com.example.dhernandez.vidvintage.Utils.Constants.Steps.ONE;
import static com.example.dhernandez.vidvintage.Utils.Constants.Steps.THREE;
import static com.example.dhernandez.vidvintage.Utils.Constants.Steps.TWO;

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
    private MutableLiveData<Constants.Steps> newCocktailStep;
    private MutableLiveData<Bitmap> profilePicture;
    private MutableLiveData<String> newCocktailName;
    private MutableLiveData<String> newCocktailURL;
    private MutableLiveData<String> newCocktailReceipt;
    private MutableLiveData<String> newCocktailDescription;
    private MutableLiveData<List<String>> newCocktailIngredients;
    private MutableLiveData<FirebaseUser> userMLD;
    private MutableLiveData<Integer> showError;
    private MutableLiveData<Boolean> cleanFields;

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
        this.newCocktailStep = new MutableLiveData<>();
        this.newCocktailName = new MutableLiveData<>();
        this.newCocktailURL = new MutableLiveData<>();
        this.newCocktailDescription = new MutableLiveData<>();
        this.newCocktailReceipt = new MutableLiveData<>();
        this.userMLD = new MutableLiveData<>();
        this.newCocktailIngredients = new MutableLiveData<>();
        this.showError = new MutableLiveData<>();
        this.cleanFields = new MutableLiveData<>();

        newCocktailIngredients.setValue(new ArrayList<>());
        newCocktailStep.setValue(ONE);
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
    public MutableLiveData<Constants.Steps> getNewCocktailStep() {
        return this.newCocktailStep;
    }

    @Override
    public void addNewCocktail() {
        if(!validateFields())
            return;

        CocktailVO cocktailVO = new CocktailVO();
        cocktailVO.setName(newCocktailName.getValue());
        cocktailVO.setDescription(newCocktailDescription.getValue());
        cocktailVO.setAlcoholic(true);
        cocktailVO.setAuthor(new Author(userMLD.getValue().getEmail(), userMLD.getValue().getProviderId(), userMLD.getValue().getUid() , userMLD.getValue().getEmail()));
        cocktailVO.setCocktailUrl("");
        cocktailVO.setLikes(0);
        cocktailVO.setReceipt(newCocktailReceipt.getValue());
        cocktailVO.setTags(new ArrayList<>());
        List<Ingredient> ingredients = new ArrayList<>();
        for(String name : newCocktailIngredients.getValue())
            ingredients.add(new Ingredient(name, ""));
        cocktailVO.setIngredients(ingredients);
        cocktailVO.setUrlPhoto(getNewCocktailURL().getValue());

        vintageRepository.addNewCocktail(cocktailVO);

        cleanFields();
    }

    private void cleanFields() {
        this.newCocktailIngredients.setValue(new ArrayList<>());
        this.newCocktailURL.setValue(null);
        this.cleanFields.setValue(true);
        this.activeSection.setValue(Constants.SubSections.ARTICLES);
        this.newCocktailStep.setValue(ONE);
        this.showError.setValue(R.string.new_cocktail_added);
        this.showError.setValue(null);
    }

    private boolean validateFields() {
        if(newCocktailName.getValue() == null || newCocktailName.getValue().trim() == "") {
            this.showError.setValue(R.string.new_cocktail_name_required);
            return false;
        }
        if(newCocktailURL.getValue() == null || newCocktailURL.getValue().trim() == "") {
            this.showError.setValue(R.string.new_cocktail_url_required);
            return false;
        }
        if(newCocktailDescription.getValue() == null || newCocktailDescription.getValue().trim() == "") {
            this.showError.setValue(R.string.new_cocktail_description_required);
            return false;
        }
        if(newCocktailReceipt.getValue() == null || newCocktailReceipt.getValue().trim() == "") {
            this.showError.setValue(R.string.new_cocktail_receipt_required);
            return false;
        }

        this.showError.setValue(null);
        return true;
    }

    @Override
    public void onNewCocktailBackClick() {
        switch (this.newCocktailStep.getValue()) {
            case ONE:
                break;
            case TWO:
                this.newCocktailStep.setValue(ONE);
                break;
            case THREE:
                this.newCocktailStep.setValue(TWO);
                break;
        }
    }

    @Override
    public void onNewCocktailNextClick() {
        switch (this.newCocktailStep.getValue()) {
            case ONE:
                this.newCocktailStep.setValue(TWO);
                break;
            case TWO:
                this.newCocktailStep.setValue(THREE);
                break;
            case THREE:
                break;
        }
    }

    @Override
    public MutableLiveData<String> getNewCocktailName() {
        return this.newCocktailName;
    }

    @Override
    public MutableLiveData<String> getNewCocktailURL() {
        return this.newCocktailURL;
    }

    @Override
    public MutableLiveData<String> getNewCocktailDescription() {
        return this.newCocktailDescription;
    }

    @Override
    public MutableLiveData<String> getNewCocktailReceipt() {
        return this.newCocktailReceipt;
    }

    @Override
    public MutableLiveData<FirebaseUser> getUsername() {
        return this.userMLD;
    }

    @Override
    public void addNewIngredient(String s) {
        if(s != null && s.trim() != ""){
            this.newCocktailIngredients.getValue().add(s);
        }
    }

    @Override
    public MutableLiveData<List<String>> getNewCocktailIngredients() {
        return this.newCocktailIngredients;
    }

    @Override
    public void removeIngredient(int position) {
        this.newCocktailIngredients.getValue().remove(position);
    }

    @Override
    public MutableLiveData<Integer> getShowError(){
        return this.showError;
    }

    @Override
    public  MutableLiveData<Boolean> getCleanFields() {
        return this.cleanFields;
    }
}

