package com.example.dhernandez.vidvintage.ui;

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
import android.widget.Toast;

import com.example.dhernandez.vidvintage.R;
import com.example.dhernandez.vidvintage.presenter.MainPresenter.IMainPresenter;
import com.example.dhernandez.vidvintage.presenter.MainPresenter.MainPresenter;
import com.example.dhernandez.vidvintage.presenter.PresenterFactory;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AndroidInjection.inject(this);

        ButterKnife.bind(this);

        presenter = ViewModelProviders.of(this, presenterFactory).get(MainPresenter.class);

        navigation.setOnNavigationItemSelectedListener(this);
        navigation.setSelectedItemId(R.id.navigation_cocktails_menu);

        Drawable bg_nav_menu = getResources().getDrawable(R.drawable.tool_bar_bg);
        navigation.setBackground(bg_nav_menu);

        ColorStateList colorStateList = getResources().getColorStateList(R.color.nav_buttons);
        navigation.setItemIconTintList(colorStateList);

        presenter.getNavigateTo().observe(this, screen ->{
            if(screen != null) {
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
                }
                if(fragment != null){
                    transaction.replace(R.id.main_container, fragment).addToBackStack(null).commit();
                }else if(intent != null){
                    startActivity(intent);
                }
                presenter.getNavigateTo().setValue(null);
            }
        });

        presenter.getFullScreen().observe(this, this::setFullScreen);

    }

    private void setFullScreen(Boolean fullScreen) {
        if(fullScreen)
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

    private void profileSection() {
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.not_supported),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 1) {
            finish();
        }else {
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
