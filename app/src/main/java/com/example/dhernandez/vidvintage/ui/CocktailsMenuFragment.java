package com.example.dhernandez.vidvintage.ui;

import android.animation.ObjectAnimator;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;

import com.example.dhernandez.vidvintage.R;
import com.example.dhernandez.vidvintage.Utils.PreferencesDialog;
import com.example.dhernandez.vidvintage.entity.CocktailVO;
import com.example.dhernandez.vidvintage.presenter.CocktailsMenuPresenter.CocktailsMenuPresenter;
import com.example.dhernandez.vidvintage.presenter.CocktailsMenuPresenter.ICocktailsMenuPresenter;
import com.example.dhernandez.vidvintage.presenter.PresenterFactory;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;

/**
 * Created by dhernandez on 30/08/2018.
 */

public class CocktailsMenuFragment extends Fragment implements View.OnSystemUiVisibilityChangeListener {

    @Inject
    PresenterFactory presenterFactory;
    ICocktailsMenuPresenter presenter;

    private List<CocktailVO> cocktailVOS;
    PreferencesDialog preferencesDialog;
    private Boolean fullScreenActive;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidSupportInjection.inject(this);

        // Generate the presenter with activity context to share it with the article detail fragment
        presenter = ViewModelProviders.of(getActivity(), presenterFactory).get(CocktailsMenuPresenter.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cocktails_menu, container, false);

        ButterKnife.bind(this, view);

        presenter.getCocktailsList().observe(this, cocktails -> {
            this.cocktailVOS = cocktails;
        });

        presenter.getNavigateTo().observe(this, screen -> {
            if (screen != null) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                Fragment fragment = null;
                Intent intent = null;

                switch (screen) {
                    case GIN_MENU:
                    case RUM_MENU:
                    case WHISKY_MENU:
                    case OTHER_MENU:
                        fragment = new MenuListFragment();
                        break;
                    case LOGIN:
                        intent = new Intent(getActivity(), LoginActivity.class);
                        break;
                }
                if (fragment != null) {
                    transaction.replace(container.getId(), fragment).addToBackStack(null).commit();
                    presenter.getNavigateTo().setValue(null);
                }
                if (intent != null) {
                    startActivity(intent);
                }
            }

        });
        presenter.getShowPreferences().observe(this, this::showPreferencesDialog);
        presenter.getDarkThemeActive().observe(this, this::showDarkTheme);
        presenter.getFullScreen().observe(this, fullScreen -> {
            if (fullScreen != null && fullScreen != this.fullScreenActive) {
                this.fullScreenActive = fullScreen;
                if (fullScreen)
                    this.hideSystemUI();
                else
                    this.showSystemUI();
            }
        });

        return view;
    }

    private void showDarkTheme(Boolean darkThemeActive) {
        if (darkThemeActive != null) {
            Drawable bg;
            if (darkThemeActive) {
                bg = getResources().getDrawable(R.drawable.cocktail_menu_item_bg);
                getView().setBackground(bg);
            } else {
                bg = getResources().getDrawable(R.drawable.light_theme);
                getView().setBackground(bg);
            }
        }
    }

    private void showPreferencesDialog(Boolean showDialog) {
        if (showDialog != null && showDialog) {
            presenter.updateActivePreferences();
            preferencesDialog = new PreferencesDialog(presenter, getContext(), getActivity());
            preferencesDialog.show(getChildFragmentManager(), null);
            presenter.getShowPreferences().setValue(null);
        }
    }

    @OnClick(R.id.cocktail_menu_gin_section)
    public void onGinSectionClick() {
        presenter.onGinSectionClick();
    }

    @OnClick(R.id.cocktail_menu_rum_section)
    public void onRumSectionClick() {
        presenter.onRumSectionClick();

    }

    @OnClick(R.id.cocktail_menu_whisky_section)
    public void onWhiskySectionClick() {
        presenter.onWhiskySectionClick();
    }

    @OnClick(R.id.cocktail_menu_other_section)
    public void onOtherSectionClick() {
        presenter.onOtherSectionClick();
    }

    @OnClick(R.id.cocktail_menu_config_button)
    public void onPreferencesClick(ImageButton imageButton) {
        ObjectAnimator animation = ObjectAnimator.ofFloat(imageButton, "rotationY", 0.0f, 360f);
        animation.setDuration(720);
        animation.setRepeatCount(ObjectAnimator.RESTART);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.start();

        Handler handler = new Handler();
        handler.postDelayed(() -> presenter.showPreferences(), 100);
    }

    // This snippet hides the system bars.
    private void hideSystemUI() {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        View decorView = getActivity().getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    // This snippet shows the system bars. It does this by removing all the flags
    // except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getActivity().getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }

    @Override
    public void onSystemUiVisibilityChange(int i) {
        if (this.fullScreenActive)
            hideSystemUI();
    }
}
