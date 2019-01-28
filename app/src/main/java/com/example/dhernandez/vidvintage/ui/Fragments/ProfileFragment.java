package com.example.dhernandez.vidvintage.ui.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dhernandez.vidvintage.BuildConfig;
import com.example.dhernandez.vidvintage.R;
import com.example.dhernandez.vidvintage.Utils.Adapters.ArticlesAdapter;
import com.example.dhernandez.vidvintage.Utils.Adapters.CocktailsAdapter;
import com.example.dhernandez.vidvintage.Utils.Adapters.NewCocktailIngredientsAdapter;
import com.example.dhernandez.vidvintage.Utils.Constants;
import com.example.dhernandez.vidvintage.Utils.CustomImageView;
import com.example.dhernandez.vidvintage.Utils.DialogFragments.NewCocktailImageURLDialog;
import com.example.dhernandez.vidvintage.Utils.DialogFragments.NewProfilePictureDialog;
import com.example.dhernandez.vidvintage.entity.ArticleVO;
import com.example.dhernandez.vidvintage.entity.CocktailVO;
import com.example.dhernandez.vidvintage.presenter.MainPresenter.IMainPresenter;
import com.example.dhernandez.vidvintage.presenter.MainPresenter.MainPresenter;
import com.example.dhernandez.vidvintage.presenter.PresenterFactory;
import com.example.dhernandez.vidvintage.presenter.ProfilePresenter.IProfilePresenter;
import com.example.dhernandez.vidvintage.presenter.ProfilePresenter.ProfilePresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;

/**
 * Created by dhernandez on 10/12/2018.
 */

public class ProfileFragment extends Fragment {

    @Inject
    PresenterFactory presenterFactory;
    IProfilePresenter presenter;
    IMainPresenter mainPresenter;

    @BindView(R.id.profile_articles_recyclerview)
    RecyclerView profileArticlesRecyclerView;
    @BindView(R.id.profile_cocktails_recyclerview)
    RecyclerView profileCocktailRecyclerView;
    @BindView(R.id.new_cocktail_process)
    View newCocktailSection;

    @BindView(R.id.profile_menu_users)
    LinearLayout usersMenu;
    @BindView(R.id.profile_menu_locals)
    LinearLayout localsMenu;

    @BindView(R.id.profile_holder_cocktails_number)
    TextView cocktailsNumber;
    @BindView(R.id.profile_holder_articles_number)
    TextView articlesNumber;
    @BindView(R.id.profile_holder_picture)
    ImageView profilePicture;

    @BindView(R.id.profile_menu_articles)
    ImageButton articlesSection;
    @BindView(R.id.profile_menu_cocktails)
    ImageButton cocktailsSection;
    @BindView(R.id.profile_menu_new_cocktail)
    ImageButton addNewSection;
    @BindView(R.id.profile_menu_users_articles)
    ImageButton usersArticlesSection;
    @BindView(R.id.profile_menu_users_cocktails)
    ImageButton usersCocktailsSection;

    @BindView(R.id.no_cocktails_view)
    View noCocktails;
    @BindView(R.id.no_articles_view)
    View noArticles;

    @BindView(R.id.profile_holder_name)
    TextView profileName;

    @BindView(R.id.new_cocktail_next_button)
    CustomImageView nextButton;
    @BindView(R.id.new_cocktail_back_button)
    CustomImageView backButton;

    @BindView(R.id.new_cocktail_name)
    TextInputEditText newCocktailName;
    @BindView(R.id.new_cocktail_image)
    CustomImageView newCocktailPhotoUrl;
    @BindView(R.id.new_cocktail_description)
    TextInputEditText newCocktailDescription;
    @BindView(R.id.new_cocktail_receipt)
    TextInputEditText newCocktailReceipt;
    @BindView(R.id.new_cocktail_add_ingredient_name)
    EditText newIngredientName;

    @BindView(R.id.new_cocktail_step_1)
    View newCocktailStep1;
    @BindView(R.id.new_cocktail_step_2)
    View newCocktailStep2;
    @BindView(R.id.new_cocktail_step_3)
    View newCocktailStep3;
    @BindView(R.id.new_cocktail_ingredients_rv)
    RecyclerView newCocktailIngredientsRV;


    private NewCocktailIngredientsAdapter ingredientsAdapter;
    private ArticlesAdapter articlesAdapter;
    private CocktailsAdapter cocktailsAdapter;

    int activeColor;
    int inactiveColor;
    private NewProfilePictureDialog newProfilePictureDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidSupportInjection.inject(this);

        presenter = ViewModelProviders.of(this, presenterFactory).get(ProfilePresenter.class);
        mainPresenter = ViewModelProviders.of(getActivity(), presenterFactory).get(MainPresenter.class);

        activeColor = Color.parseColor(getString(R.string.color_subsection_active));
        inactiveColor = Color.parseColor(getString(R.string.color_inactive_subection));

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ButterKnife.bind(this, view);

        initMenu();
        loadUserName();

        loadPresentersListeners(container);
        loadNewCocktailListeners();
        loadNewCocktailRecyclerView();

        return view;
    }

    private void loadNewCocktailRecyclerView() {
        ingredientsAdapter = new NewCocktailIngredientsAdapter(presenter);

        newCocktailIngredientsRV.setAdapter(ingredientsAdapter);
        newCocktailIngredientsRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    private void loadNewCocktailListeners() {
        newCocktailName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                presenter.getNewCocktailName().setValue(editable.toString());
            }
        });
        newCocktailPhotoUrl.setOnClickListener((click) -> {
            NewCocktailImageURLDialog newCocktailURL = new NewCocktailImageURLDialog(presenter);
            newCocktailURL.setCancelable(false);
            newCocktailURL.show(getActivity().getSupportFragmentManager(), null);
        });
        newCocktailDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                presenter.getNewCocktailDescription().setValue(editable.toString());
            }
        });
        newCocktailReceipt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                presenter.getNewCocktailReceipt().setValue(editable.toString());
            }
        });
    }

    private void loadPresentersListeners(ViewGroup container) {
        presenter.getActiveSection().observe(this, subSection -> {
            switch (subSection) {
                case ARTICLES:
                    showArticles();
                    break;
                case COCKTAILS:
                    showCocktails();
                    break;
                case NEW_COCKTAIL:
                    showNewCocktailSection();
                    break;
                default:
                    showArticles();
                    break;
            }
        });
        presenter.getFavouriteArticles().observe(this,
                articles -> {
                    if (articles != null) {
                        initArticlesAdapter(articles);
                        articlesNumber.setText(String.valueOf((articles.size())));
                    } else {
                        profileArticlesRecyclerView.setVisibility(View.INVISIBLE);
                        articlesNumber.setText("-");
                    }
                });
        presenter.getFavouriteCocktails().observe(this,
                cocktails -> {
                    if (cocktails != null) {
                        initCocktailsAdapter(cocktails);
                        cocktailsNumber.setText(String.valueOf(cocktails.size()));
                    } else {
                        cocktailsNumber.setText("-");
                    }
                });
        presenter.getNewCocktailStep().observe(this, step -> {
            switch (step) {
                case ONE:
                    newCocktailStepOne();
                    break;
                case TWO:
                    newCocktailStepTwo();
                    break;
                case THREE:
                    newCocktailStepThree();
                    break;
                default:
                    newCocktailStepOne();
                    break;
            }
        });
        mainPresenter.getNavigateTo().observe(this, screen -> {
            if (screen != null) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                Fragment f = null;
                if (screen == Constants.Screens.ARTICLE_DETAIL) {
                    f = new ArticleDetailFragment();
                } else if (screen == Constants.Screens.RSS) {
                    f = new FeedRssFragment();
                }
                if (f != null) {
                    transaction.replace(container.getId(), f).addToBackStack(null).commit();
                    presenter.getNavigateTo().setValue(null);
                }
            }
        });
        presenter.getProfilePicture().observe(this, pictureProfile -> {
            if (pictureProfile != null) {
                this.profilePicture.setImageBitmap(pictureProfile);
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                presenter.saveProfilePicture(mAuth.getCurrentUser().getEmail());
            }
        });
        presenter.getNewCocktailURL().observe(this, url -> {
            if (url != null && !url.isEmpty())
                Picasso.get().load(url.trim()).into(newCocktailPhotoUrl);
        });
        presenter.getUsername().observe(this, firebaseUser -> profileName.setText(firebaseUser.getEmail()));

        presenter.getShowError().observe(this, errorInfo -> {
            if (errorInfo != null) {
                Toast.makeText(getContext(), getString(errorInfo),
                        Toast.LENGTH_SHORT).show();
            }
        });

        presenter.getCleanFields().observe(this, cleanFields -> {
            if (cleanFields != null) {
                resetNewCocktailFields();
            }
        });

    }

    private void resetNewCocktailFields() {
        this.newCocktailName.setText(null);
        this.newCocktailName.setHint(R.string.new_cocktail_name);

        this.newCocktailDescription.setText(null);
        this.newCocktailDescription.setHint(R.string.new_cocktail_description);

        this.newCocktailReceipt.setText(null);
        this.newCocktailReceipt.setHint(R.string.new_cocktail_receipt);
    }

    private void loadUserName() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        presenter.getUsername().setValue(mAuth.getCurrentUser());
    }

    private void newCocktailStepOne() {
        backButton.setVisibility(View.INVISIBLE);
        nextButton.setVisibility(View.VISIBLE);
        newCocktailStep1.setVisibility(View.VISIBLE);
        newCocktailStep2.setVisibility(View.GONE);
        newCocktailStep3.setVisibility(View.GONE);
    }

    private void newCocktailStepTwo() {
        backButton.setVisibility(View.VISIBLE);
        nextButton.setVisibility(View.VISIBLE);
        newCocktailStep1.setVisibility(View.GONE);
        newCocktailStep2.setVisibility(View.VISIBLE);
        newCocktailStep3.setVisibility(View.GONE);
    }

    private void newCocktailStepThree() {
        backButton.setVisibility(View.VISIBLE);
        nextButton.setVisibility(View.INVISIBLE);
        newCocktailStep1.setVisibility(View.GONE);
        newCocktailStep2.setVisibility(View.GONE);
        newCocktailStep3.setVisibility(View.VISIBLE);
    }

    private void showNewCocktailSection() {
        //Subsections views
        this.profileCocktailRecyclerView.setVisibility(View.GONE);
        this.profileArticlesRecyclerView.setVisibility(View.GONE);
        this.newCocktailSection.setVisibility(View.VISIBLE);

        //Icons of the subsection menu
        this.addNewSection.setColorFilter(activeColor);
        this.articlesSection.setColorFilter(inactiveColor);
        this.cocktailsSection.setColorFilter(inactiveColor);

        //Default images when there is no articles or cocktails in memory
        this.noArticles.setVisibility(View.GONE);
        this.noCocktails.setVisibility(View.GONE);

    }

    private void showCocktails() {
        this.profileCocktailRecyclerView.setVisibility(View.VISIBLE);
        this.profileArticlesRecyclerView.setVisibility(View.GONE);
        this.newCocktailSection.setVisibility(View.GONE);

        this.cocktailsSection.setColorFilter(activeColor);
        this.articlesSection.setColorFilter(inactiveColor);
        this.addNewSection.setColorFilter(inactiveColor);
        this.usersArticlesSection.setColorFilter(inactiveColor);
        this.usersCocktailsSection.setColorFilter(activeColor);

        this.noArticles.setVisibility(View.GONE);

        if (presenter.getFavouriteCocktails().getValue() != null
                && presenter.getFavouriteCocktails().getValue().isEmpty()) {
            this.profileCocktailRecyclerView.setVisibility(View.GONE);
            noCocktails.setVisibility(View.VISIBLE);
        } else {
            noCocktails.setVisibility(View.GONE);
        }
    }

    private void showArticles() {
        this.profileCocktailRecyclerView.setVisibility(View.GONE);
        this.profileArticlesRecyclerView.setVisibility(View.VISIBLE);
        this.newCocktailSection.setVisibility(View.GONE);

        this.articlesSection.setColorFilter(activeColor);

        this.cocktailsSection.setColorFilter(inactiveColor);
        this.addNewSection.setColorFilter(inactiveColor);
        this.usersArticlesSection.setColorFilter(activeColor);
        this.usersCocktailsSection.setColorFilter(inactiveColor);

        this.noCocktails.setVisibility(View.GONE);

        if (presenter.getFavouriteArticles().getValue() != null
                && presenter.getFavouriteArticles().getValue().isEmpty()) {
            this.profileArticlesRecyclerView.setVisibility(View.GONE);
            noArticles.setVisibility(View.VISIBLE);
        } else {
            noArticles.setVisibility(View.GONE);
        }
    }

    private void initMenu() {
        if (BuildConfig.FLAVOR.equals(BuildConfig.LOCAL)) {
            localsMenu.setVisibility(View.VISIBLE);
            usersMenu.setVisibility(View.GONE);
        } else {
            localsMenu.setVisibility(View.GONE);
            usersMenu.setVisibility(View.VISIBLE);
        }
    }

    private void initCocktailsAdapter(List<CocktailVO> cocktails) {
        cocktailsAdapter = new CocktailsAdapter(cocktails);

        cocktailsAdapter.setOnClickListener(view -> onCocktailClick(profileCocktailRecyclerView.getChildAdapterPosition(view)));

        profileCocktailRecyclerView.setAdapter(cocktailsAdapter);
        profileCocktailRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    private void onCocktailClick(int childAdapterPosition) {
        presenter.showCocktailDetail(childAdapterPosition);
        mainPresenter.getNavigateTo().setValue(Constants.Screens.COCKTAIL_DETAIL);
    }

    private void initArticlesAdapter(List<ArticleVO> articles) {
        articlesAdapter = new ArticlesAdapter(articles);

        articlesAdapter.setOnClickListener(view -> onArticleClick(profileArticlesRecyclerView.getChildAdapterPosition(view)));

        profileArticlesRecyclerView.setAdapter(articlesAdapter);
        profileArticlesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    private void onArticleClick(int childAdapterPosition) {
        presenter.showArticleDetail(childAdapterPosition);
        mainPresenter.getNavigateTo().setValue(Constants.Screens.ARTICLE_DETAIL);
    }

    @OnClick(R.id.profile_holder_picture)
    public void onProfilePictureClick() {
        newProfilePictureDialog = new NewProfilePictureDialog(presenter);
        newProfilePictureDialog.setCancelable(true);
        newProfilePictureDialog.show(getActivity().getFragmentManager(), null);
    }

    @OnClick({R.id.profile_menu_articles, R.id.profile_menu_users_articles})
    public void onArticlesClick() {
        presenter.getActiveSection().setValue(Constants.SubSections.ARTICLES);
    }

    @OnClick({R.id.profile_menu_users_cocktails, R.id.profile_menu_cocktails})
    public void onCocktailsClick() {
        presenter.getActiveSection().setValue(Constants.SubSections.COCKTAILS);
    }

    @OnClick(R.id.profile_menu_new_cocktail)
    public void onNewCocktailClick() {
        //presenter.addNewCocktail();
        presenter.getActiveSection().setValue(Constants.SubSections.NEW_COCKTAIL);
    }

    @OnClick(R.id.no_articles_view)
    public void onFeedRssClick() {
        mainPresenter.getNavigateTo().setValue(Constants.Screens.RSS);
    }

    @OnClick(R.id.no_cocktails_view)
    public void onCocktailsMenu() {
        mainPresenter.getNavigateTo().setValue(Constants.Screens.COCKTAILS_MENU);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.refreshFavourites();
        showArticles();
    }

    @OnClick(R.id.new_cocktail_back_button)
    public void onNewCocktailBackClick() {
        presenter.onNewCocktailBackClick();
    }

    @OnClick(R.id.new_cocktail_next_button)
    public void onNewCocktailNextClick() {
        presenter.onNewCocktailNextClick();
    }

    @OnClick(R.id.new_cocktail_add_ingredient_button)
    public void onNewIngredientClick() {
        presenter.addNewIngredient(this.newIngredientName.getText().toString());
        this.newIngredientName.setText(null);
        this.newIngredientName.setHint(R.string.new_cocktail_add_ingredient);
        this.ingredientsAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.new_cocktail_finish)
    public void onAddNewCocktail() {
        presenter.addNewCocktail();
    }

}

