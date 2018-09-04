package com.example.dhernandez.vidvintage.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dhernandez.vidvintage.R;
import com.example.dhernandez.vidvintage.Utils.ArticlesAdapter;
import com.example.dhernandez.vidvintage.Utils.CocktailsMenuAdapter;
import com.example.dhernandez.vidvintage.entity.Cocktail;
import com.example.dhernandez.vidvintage.presenter.FeedRssPresenter;
import com.example.dhernandez.vidvintage.presenter.IMenuListPresenter;
import com.example.dhernandez.vidvintage.presenter.MenuListPresenter;
import com.example.dhernandez.vidvintage.presenter.PresenterFactory;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

/**
 * Created by dhernandez on 03/09/2018.
 */

public class MenuListFragment extends Fragment {

    @Inject
    PresenterFactory presenterFactory;
    IMenuListPresenter presenter;

    @BindView(R.id.menu_list_recycler_view)
    RecyclerView recyclerView;

    private CocktailsMenuAdapter cocktailListAdapter;
    private List<Cocktail> cocktailList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidSupportInjection.inject(this);

        presenter = ViewModelProviders.of(getActivity(),presenterFactory).get(MenuListPresenter.class);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_menu, container, false);

        ButterKnife.bind(this, view);

        presenter.getCocktails().observe(this, cocktails -> {
            if(cocktails != null) {
                this.cocktailList = cocktails;
                setUpRecyclerView();
            }
            else
                presenter.getCocktails();
        });

        return view;
    }

    private void setUpRecyclerView() {
        cocktailListAdapter = new CocktailsMenuAdapter(getContext(), this.cocktailList);

        cocktailListAdapter.setOnClickListener(new RecyclerView.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCocktailClick(recyclerView.getChildAdapterPosition(view));
            }
        });

        recyclerView.setAdapter(cocktailListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    private void onCocktailClick(int cocktailIndex){
        presenter.showCocktailDetail(cocktailIndex);
    }
}
