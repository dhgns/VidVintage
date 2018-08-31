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

import com.example.dhernandez.vidvintage.R;
import com.example.dhernandez.vidvintage.presenter.IMainPresenter;
import com.example.dhernandez.vidvintage.presenter.MainPresenter;
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

        Drawable bg_item = getResources().getDrawable(R.drawable.tool_bar_bg);

        navigation.setSelectedItemId(R.id.navigation_cocktails);
        navigation.setBackground(bg_item);

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
                        break;
                    case RSS:
                        fragment = new FeedRssFragment();
                        break;
                    case COCKTAILS:
                        fragment = new CocktailsFragment();
                        break;
                }
                if(fragment != null){
                    transaction.replace(R.id.container, fragment).commit();
                    transaction.addToBackStack(null);
                }else if(intent != null){
                    startActivity(intent);
                }
                presenter.getNavigateTo().setValue(null);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_rss:
                presenter.onRss();
                //transaction.replace(R.id.container, new RssFragment()).commit();
                return true;
            case R.id.navigation_cocktails:
                presenter.onCocktails();
                //transaction.replace(R.id.container, new CocktailsFragment()).commit();
                return true;
            case R.id.navigation_profile:
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
        }else {
            getSupportFragmentManager().popBackStack();
        }
    }
}
