package com.example.dhernandez.vidvintage.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.dhernandez.vidvintage.BuildConfig;
import com.example.dhernandez.vidvintage.R;
import com.example.dhernandez.vidvintage.Utils.Adapters.ArticlesAdapter;
import com.example.dhernandez.vidvintage.Utils.Adapters.CocktailsAdapter;
import com.example.dhernandez.vidvintage.Utils.Constants;
import com.example.dhernandez.vidvintage.Utils.PreferencesDialog;
import com.example.dhernandez.vidvintage.entity.ArticleVO;
import com.example.dhernandez.vidvintage.entity.CocktailVO;
import com.example.dhernandez.vidvintage.presenter.PresenterFactory;
import com.example.dhernandez.vidvintage.presenter.ProfilePresenter.IProfilePresenter;
import com.example.dhernandez.vidvintage.presenter.ProfilePresenter.ProfilePresenter;

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

    PreferencesDialog preferencesDialog;

    @BindView(R.id.profile_articles_recyclerview)
    RecyclerView profileArticlesRecyclerView;
    @BindView(R.id.profile_cocktails_recyclerview)
    RecyclerView profileCocktailRecyclerView;

    @BindView(R.id.profile_menu_users)
    LinearLayout usersMenu;
    @BindView(R.id.profile_menu_locals)
    LinearLayout localsMenu;

    private ArticlesAdapter articlesAdapter;
    private CocktailsAdapter cocktailsAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidSupportInjection.inject(this);

        presenter = ViewModelProviders.of(this, presenterFactory).get(ProfilePresenter.class);

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
                    } else {
                        profileArticlesRecyclerView.setVisibility(View.INVISIBLE);
                    }
                });

        presenter.getFavouriteCocktails().observe(this,
                cocktails -> {
                    if (cocktails != null) {
                        initCocktailsAdapter(cocktails);
                    } else {

                    }
                });

        presenter.getNavigateTo().observe(this, screen -> {
            if (screen != null) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                Fragment f = null;
                if (screen == Constants.Screens.ARTICLE_DETAIL) {
                    f = new ArticleDetailFragment();
                }
                if (f != null) {
                    transaction.replace(container.getId(), f).addToBackStack(null).commit();
                    presenter.getNavigateTo().setValue(null);
                }
            }
        });

        return view;
    }


    private void showNewCocktail() {
        this.profileCocktailRecyclerView.setVisibility(View.GONE);
        this.profileArticlesRecyclerView.setVisibility(View.GONE);
    }

    private void showCocktails() {
        this.profileCocktailRecyclerView.setVisibility(View.VISIBLE);
        this.profileArticlesRecyclerView.setVisibility(View.GONE);
    }

    private void showArticles() {
        this.profileCocktailRecyclerView.setVisibility(View.GONE);
        this.profileArticlesRecyclerView.setVisibility(View.VISIBLE);
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
    }

    private void initArticlesAdapter(List<ArticleVO> articles) {
        articlesAdapter = new ArticlesAdapter(articles);

        articlesAdapter.setOnClickListener(view -> onArticleClick(profileArticlesRecyclerView.getChildAdapterPosition(view)));

        profileArticlesRecyclerView.setAdapter(articlesAdapter);
        profileArticlesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    private void onArticleClick(int childAdapterPosition) {
        presenter.showArticleDetail(childAdapterPosition);
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
        presenter.getActiveSection().setValue(Constants.SubSections.NEW_COCKTAIL);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.refreshFavourites();
    }

}
