package com.example.dhernandez.vidvintage.ui.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dhernandez.vidvintage.R;
import com.example.dhernandez.vidvintage.Utils.PreferencesDialog;
import com.example.dhernandez.vidvintage.entity.CocktailVO;
import com.example.dhernandez.vidvintage.presenter.CocktailsMenuPresenter.CocktailsMenuPresenter;
import com.example.dhernandez.vidvintage.presenter.CocktailsMenuPresenter.ICocktailsMenuPresenter;
import com.example.dhernandez.vidvintage.presenter.MainPresenter.IMainPresenter;
import com.example.dhernandez.vidvintage.presenter.MainPresenter.MainPresenter;
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
    IMainPresenter mainPresenter;

    private List<CocktailVO> cocktailVOS;
    PreferencesDialog preferencesDialog;
    private Boolean fullScreenActive;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidSupportInjection.inject(this);

        // Generate the presenter with activity context to share it with the article detail fragment
        presenter = ViewModelProviders.of(getActivity(), presenterFactory).get(CocktailsMenuPresenter.class);
        mainPresenter = ViewModelProviders.of(getActivity(), presenterFactory).get(MainPresenter.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cocktails_menu, container, false);

        ButterKnife.bind(this, view);

        presenter.getCocktailsList().observe(this, cocktails -> {
            this.cocktailVOS = cocktails;
        });

        return view;
    }

    @OnClick(R.id.cocktail_menu_gin_section)
    public void onGinSectionClick() {
        mainPresenter.onGinSectionClick();
    }

    @OnClick(R.id.cocktail_menu_rum_section)
    public void onRumSectionClick() {
        mainPresenter.onRumSectionClick();
    }

    @OnClick(R.id.cocktail_menu_whisky_section)
    public void onWhiskySectionClick() {
        mainPresenter.onWhiskySectionClick();
    }

    @OnClick(R.id.cocktail_menu_other_section)
    public void onOtherSectionClick() {
        mainPresenter.onOtherSectionClick();
    }

}
