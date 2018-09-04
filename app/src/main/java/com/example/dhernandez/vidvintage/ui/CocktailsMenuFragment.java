package com.example.dhernandez.vidvintage.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dhernandez.vidvintage.R;
import com.example.dhernandez.vidvintage.entity.Cocktail;
import com.example.dhernandez.vidvintage.presenter.CocktailsMenuPresenter;
import com.example.dhernandez.vidvintage.presenter.ICocktailsMenuPresenter;
import com.example.dhernandez.vidvintage.presenter.PresenterFactory;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;

/**
 * Created by dhernandez on 30/08/2018.
 */

public class CocktailsMenuFragment extends Fragment {

    @Inject
    PresenterFactory presenterFactory;
    ICocktailsMenuPresenter presenter;
    private List<Cocktail> cocktails;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidSupportInjection.inject(this);

        // Generate the presenter with activity context to share it with the article detail fragment
        presenter = ViewModelProviders.of(getActivity(),presenterFactory).get(CocktailsMenuPresenter.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cocktails_menu, container, false);

        ButterKnife.bind(this, view);

        presenter.getCocktailsList().observe(this, cocktails ->{
            this.cocktails = cocktails;
        });

        presenter.getNavigateTo().observe(this, screen->{
            if(screen != null){
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                Fragment fragment = null;
                switch (screen) {
                    case GIN_MENU:
                    case RUM_MENU:
                    case WHISKY_MENU:
                    case OTHER_MENU:
                        fragment = new MenuListFragment();
                }
                if(fragment != null){
                    transaction.replace(container.getId(), fragment).addToBackStack(null).commit();
                    presenter.getNavigateTo().setValue(null);
                }
            }

        });

        return view;
    }

    @OnClick(R.id.cocktail_menu_gin_section)
    public void onGinSectionClick(){
        presenter.onGinSectionClick();
    }
    @OnClick(R.id.cocktail_menu_rum_section)
    public void onRumSectionClick(){
        presenter.onRumSectionClick();

    }
    @OnClick(R.id.cocktail_menu_whisky_section)
    public void onWhiskySectionClick(){
        presenter.onWhiskySectionClick();

    }
    @OnClick(R.id.cocktail_menu_other_section)
    public void onOtherSectionClick(){
        presenter.onOtherSectionClick();

    }

}
