package com.example.dhernandez.vidvintage.ui.Fragments;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dhernandez.vidvintage.BuildConfig;
import com.example.dhernandez.vidvintage.R;
import com.example.dhernandez.vidvintage.Utils.Adapters.ArticlesAdapter;
import com.example.dhernandez.vidvintage.Utils.Adapters.CocktailsAdapter;
import com.example.dhernandez.vidvintage.Utils.Constants;
import com.example.dhernandez.vidvintage.Utils.NewProfilePicture;
import com.example.dhernandez.vidvintage.entity.ArticleVO;
import com.example.dhernandez.vidvintage.entity.CocktailVO;
import com.example.dhernandez.vidvintage.presenter.MainPresenter.IMainPresenter;
import com.example.dhernandez.vidvintage.presenter.MainPresenter.MainPresenter;
import com.example.dhernandez.vidvintage.presenter.PresenterFactory;
import com.example.dhernandez.vidvintage.presenter.ProfilePresenter.IProfilePresenter;
import com.example.dhernandez.vidvintage.presenter.ProfilePresenter.ProfilePresenter;
import com.google.firebase.auth.FirebaseAuth;

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
    @BindView(R.id.new_cocktail_section)
    View newSection;
    @BindView(R.id.profile_holder_name)
    TextView profileName;

    private ArticlesAdapter articlesAdapter;
    private CocktailsAdapter cocktailsAdapter;

    int activeColor;
    int inactiveColor;
    private NewProfilePicture newProfilePicture;

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

        presenter.getActiveSection().observe(this, subSection -> {
            switch (subSection) {
                case ARTICLES:
                    showArticles();
                    break;
                case COCKTAILS:
                    showCocktails();
                    break;
                case NEW_COCKTAIL:
                    showNewCocktail();
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

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        profileName.setText(mAuth.getCurrentUser().getEmail());

        return view;
    }

    private void showNewCocktail() {

        this.profileCocktailRecyclerView.setVisibility(View.GONE);
        this.profileArticlesRecyclerView.setVisibility(View.GONE);
        this.newSection.setVisibility(View.VISIBLE);
        this.addNewSection.setColorFilter(activeColor);
        this.articlesSection.setColorFilter(inactiveColor);
        this.cocktailsSection.setColorFilter(inactiveColor);

        this.noArticles.setVisibility(View.GONE);
        this.noCocktails.setVisibility(View.GONE);

    }

    private void showCocktails() {
        this.profileCocktailRecyclerView.setVisibility(View.VISIBLE);
        this.profileArticlesRecyclerView.setVisibility(View.GONE);
        this.cocktailsSection.setColorFilter(activeColor);
        this.articlesSection.setColorFilter(inactiveColor);
        this.addNewSection.setColorFilter(inactiveColor);
        this.usersArticlesSection.setColorFilter(inactiveColor);
        this.usersCocktailsSection.setColorFilter(activeColor);

        this.newSection.setVisibility(View.GONE);
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
        this.articlesSection.setColorFilter(activeColor);

        this.cocktailsSection.setColorFilter(inactiveColor);
        this.addNewSection.setColorFilter(inactiveColor);
        this.usersArticlesSection.setColorFilter(activeColor);
        this.usersCocktailsSection.setColorFilter(inactiveColor);

        this.noCocktails.setVisibility(View.GONE);
        this.newSection.setVisibility(View.GONE);

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
        newProfilePicture = new NewProfilePicture(presenter);
        newProfilePicture.setCancelable(false);
        newProfilePicture.show(getActivity().getFragmentManager(), null);
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
        presenter.addNewCocktail();
        //presenter.getActiveSection().setValue(Constants.SubSections.NEW_COCKTAIL);
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
}
