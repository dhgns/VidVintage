package com.example.dhernandez.vidvintage.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.dhernandez.vidvintage.R;
import com.example.dhernandez.vidvintage.entity.LoadedPreferences;
import com.example.dhernandez.vidvintage.presenter.CocktailsMenuPresenter.ICocktailsMenuPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dhernandez on 06/09/2018.
 */

@SuppressLint("ValidFragment")
public class PreferencesDialog extends DialogFragment implements View.OnSystemUiVisibilityChangeListener {

    private LoadedPreferences loadedPreferences;
    ICocktailsMenuPresenter presenter;

    @BindView(R.id.preferences_fullscreen_theme_light)
    Button lightThemeButton;
    @BindView(R.id.preferences_fullscreen_theme_dark)
    Button darkThemeButton;
    @BindView(R.id.preferences_fullscreen_checkbox)
    CheckBox fullScreenCheckbox;
    @BindView(R.id.preferences_save_session_checkbox)
    CheckBox saveSessionCheckBox;

    Drawable selectedButton;
    Drawable unselectedButton;

    Context context;
    FragmentActivity activity;
    private Boolean fullScreenActive;
    private Boolean saveSession;

    public PreferencesDialog(ICocktailsMenuPresenter presenter, Context context, FragmentActivity activity) {
        super();
        this.presenter = presenter;
        this.context = context;
        this.activity = activity;

        selectedButton = context.getResources().getDrawable(R.drawable.button_selected);
        unselectedButton = context.getResources().getDrawable(R.drawable.button_unselected);

        presenter.getActivePreferences().observeForever(loadedPreferences -> {
            this.loadedPreferences = loadedPreferences;
        });

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setStyle(DialogFragment.STYLE_NORMAL, R.style.MY_DIALOG);
    }

    @Override
    public void onResume() {
        super.onResume();
        new android.os.Handler().postDelayed(
                () -> {
                    if(loadedPreferences.getFullScreen())
                        setImmersiveMode();
                }, 10);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.preferences_dialog, container, false);

        ButterKnife.bind(this, view);

        setRetainInstance(true);

        setImmersiveMode();

        this.fullScreenCheckbox.setChecked(loadedPreferences.getFullScreen());
        this.saveSessionCheckBox.setChecked(loadedPreferences.getSaveSession());

        if (loadedPreferences.getTheme().equals(Constants.Themes.DARK)) {
            darkThemeButton.setBackground(selectedButton);
            lightThemeButton.setBackground(unselectedButton);
        } else {
            darkThemeButton.setBackground(unselectedButton);
            lightThemeButton.setBackground(selectedButton);
        }

        return view;
    }

    private void setImmersiveMode() {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        if (loadedPreferences.getFullScreen()) {
            View decorView = activity.getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }

    }

    @OnClick(R.id.preferences_fullscreen_theme_light)
    public void onLightThemeClick() {
        presenter.setDarkTheme(false);

        lightThemeButton.setBackground(selectedButton);
        darkThemeButton.setBackground(unselectedButton);
    }

    @OnClick(R.id.preferences_fullscreen_theme_dark)
    public void onDarkThemeClick() {
        presenter.setDarkTheme(true);

        lightThemeButton.setBackground(unselectedButton);
        darkThemeButton.setBackground(selectedButton);
    }

    @OnClick(R.id.preferences_fullscreen_checkbox)
    public void onFullScreenClick(CheckBox checkBox) {
        if (checkBox.isChecked()) {
            presenter.setAppFullScreen(true);
        } else {
            presenter.setAppFullScreen(false);
        }
    }

    @OnClick(R.id.preferences_save_session_checkbox)
    public void onStoreSessionClick(CheckBox checkBox) {
        if (checkBox.isChecked()) {
            presenter.setStoreSession(true);
        } else {
            presenter.setStoreSession(false);
        }
    }

    @OnClick(R.id.preferences_close_session_btn)
    public void onCloseSessionClick() {
        presenter.closeSession();
    }


    @Override
    public void onSystemUiVisibilityChange(int i) {
        setImmersiveMode();
    }
}
