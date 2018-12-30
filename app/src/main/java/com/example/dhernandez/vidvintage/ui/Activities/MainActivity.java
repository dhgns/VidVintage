package com.example.dhernandez.vidvintage.ui.Activities;

import android.animation.ObjectAnimator;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.dhernandez.vidvintage.R;
import com.example.dhernandez.vidvintage.Utils.Constants;
import com.example.dhernandez.vidvintage.Utils.DialogFragments.PreferencesDialog;
import com.example.dhernandez.vidvintage.presenter.MainPresenter.IMainPresenter;
import com.example.dhernandez.vidvintage.presenter.MainPresenter.MainPresenter;
import com.example.dhernandez.vidvintage.presenter.PresenterFactory;
import com.example.dhernandez.vidvintage.ui.Fragments.ArticleDetailFragment;
import com.example.dhernandez.vidvintage.ui.Fragments.CocktailDetailFragment;
import com.example.dhernandez.vidvintage.ui.Fragments.CocktailsMenuFragment;
import com.example.dhernandez.vidvintage.ui.Fragments.FeedRssFragment;
import com.example.dhernandez.vidvintage.ui.Fragments.MenuListFragment;
import com.example.dhernandez.vidvintage.ui.Fragments.ProfileFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

/**
 * Created by dhernandez on 30/08/2018.
 */

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Inject
    PresenterFactory presenterFactory;
    IMainPresenter presenter;

    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    @BindView(R.id.main_toolbar)
    android.support.v7.widget.Toolbar mainToolBar;

    @BindView(R.id.main_toolbar_right_button)
    ImageButton preferencesButton;

    @BindView(R.id.main_toolbar_title)
    TextView sectionTitle;

    @BindView(R.id.refresh_button_feed)
    ImageButton refreshButton;

    private PreferencesDialog preferencesDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AndroidInjection.inject(this);

        ButterKnife.bind(this);

        presenter = ViewModelProviders.of(this, presenterFactory).get(MainPresenter.class);
        presenter.getNavigateTo().observe(this, screen -> {
            if (screen != null) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();

                Fragment fragment = null;
                Intent intent = null;

                switch (screen) {
                    case PROFILE:
                        fragment = new ProfileFragment();
                        break;
                    case RSS:
                        fragment = new FeedRssFragment();
                        break;
                    case COCKTAILS_MENU:
                        fragment = new CocktailsMenuFragment();
                        break;
                    case GIN_MENU:
                    case RUM_MENU:
                    case WHISKY_MENU:
                    case OTHER_MENU:
                        fragment = new MenuListFragment();
                        break;
                    case COCKTAIL_DETAIL:
                        fragment = new CocktailDetailFragment();
                        break;
                    case ARTICLE_DETAIL:
                        fragment = new ArticleDetailFragment();
                    case LOGIN:
                        intent = new Intent(this, LoginActivity.class);
                }

                updateToolbar(screen);

                if (fragment != null) {
                    transaction.replace(R.id.main_container, fragment).addToBackStack(null).commit();
                } else if (intent != null) {
                    startActivity(intent);
                }
                presenter.getNavigateTo().setValue(null);
            }
        });
        presenter.getPreferences().observe(this, preferences -> {
            if (preferences != null) {
                this.setFullScreen(preferences.getFullScreen());
                //this.setTheme(preferences.getTheme());
            }
        });

        setSupportActionBar(mainToolBar);
        setUpNavigationView();
    }

    private void updateToolbar(Constants.Screens screen) {
        switch (screen) {
            case COCKTAILS_MENU:
                refreshButton.setVisibility(View.INVISIBLE);
                preferencesButton.setVisibility(View.VISIBLE);
                sectionTitle.setText(getString(R.string.section_title_cocktails));
                break;
            case PROFILE:
                refreshButton.setVisibility(View.INVISIBLE);
                preferencesButton.setVisibility(View.VISIBLE);
                sectionTitle.setText(getString(R.string.section_title_profile));
                break;
            case RSS:
                refreshButton.setVisibility(View.VISIBLE);
                preferencesButton.setVisibility(View.VISIBLE);
                sectionTitle.setText(getString(R.string.section_title_rss));
                break;
            case ARTICLE_DETAIL:
            case COCKTAIL_DETAIL:
                refreshButton.setVisibility(View.INVISIBLE);
                preferencesButton.setVisibility(View.VISIBLE);
                sectionTitle.setText(getString(R.string.section_title_no_title));
                break;
            case GIN_MENU:
                refreshButton.setVisibility(View.INVISIBLE);
                preferencesButton.setVisibility(View.VISIBLE);
                sectionTitle.setText(getString(R.string.section_title_gin));
                break;
            case RUM_MENU:
                refreshButton.setVisibility(View.INVISIBLE);
                preferencesButton.setVisibility(View.VISIBLE);
                sectionTitle.setText(getString(R.string.section_title_gin));
                break;
            case WHISKY_MENU:
                refreshButton.setVisibility(View.INVISIBLE);
                preferencesButton.setVisibility(View.VISIBLE);
                sectionTitle.setText(getString(R.string.section_title_gin));
                break;
            case OTHER_MENU:
                refreshButton.setVisibility(View.INVISIBLE);
                preferencesButton.setVisibility(View.VISIBLE);
                sectionTitle.setText(getString(R.string.section_title_gin));
                break;
        }
    }

    @OnClick(R.id.main_toolbar_right_button)
    public void showPreferencesDialog() {
        ObjectAnimator animation = ObjectAnimator.ofFloat(preferencesButton, "rotationY", 0.0f, 360f);
        animation.setDuration(720);
        animation.setRepeatCount(ObjectAnimator.RESTART);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.start();

        //Handler handler = new Handler();
        //handler.postDelayed(() -> presenter.showPreferences(), 100);

        preferencesDialog = new PreferencesDialog(presenter, this);
        preferencesDialog.show(getSupportFragmentManager(), null);

    }

    private void setTheme(Constants.Themes theme) {
        Drawable bg;
        switch (theme) {

            case LIGHT:
                bg = getResources().getDrawable(R.drawable.light_theme);
                break;
            case DARK:
            default:
                bg = getResources().getDrawable(R.drawable.cocktail_menu_item_bg);
                break;
        }
        findViewById(R.id.main_container).setBackground(bg);
    }

    private void setUpNavigationView() {
        Drawable bg_nav_menu = getResources().getDrawable(R.drawable.tool_bar_bg);
        ColorStateList colorStateList = getResources().getColorStateList(R.color.nav_buttons);

        navigation.setBackground(bg_nav_menu);
        navigation.setItemIconTintList(colorStateList);
        navigation.setOnNavigationItemSelectedListener(this);
        navigation.setSelectedItemId(R.id.navigation_cocktails_menu);
    }

    private void setFullScreen(Boolean fullScreen) {
        if (fullScreen)
            this.hideSystemUI();
        else
            this.showSystemUI();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_rss:
                presenter.onRss();
                return true;
            case R.id.navigation_cocktails_menu:
                presenter.onCocktailsMenu();
                return true;
            case R.id.navigation_profile:
                //TODO: remove this alert
                //this.profileSection();
                presenter.onProfile();
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
            if (count == 1) {
            finish();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    // This snippet hides the system bars.
    private void hideSystemUI() {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        View decorView = this.getWindow().getDecorView();
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
        View decorView = this.getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }

}
