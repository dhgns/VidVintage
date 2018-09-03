package com.example.dhernandez.vidvintage.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dhernandez.vidvintage.R;
import com.example.dhernandez.vidvintage.presenter.ICocktailsMenuPresenter;
import com.example.dhernandez.vidvintage.presenter.PresenterFactory;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

/**
 * Created by dhernandez on 30/08/2018.
 */

public class CocktailsMenuFragment extends Fragment {

    @Inject
    PresenterFactory presenterFactory;
    ICocktailsMenuPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidSupportInjection.inject(this);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cocktails_menu, container, false);


        ButterKnife.bind(this, view);

        return view;
    }
}
