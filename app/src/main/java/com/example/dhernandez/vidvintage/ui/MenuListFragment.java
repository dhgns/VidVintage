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

import com.example.dhernandez.vidvintage.R;
import com.example.dhernandez.vidvintage.Utils.Adapters.CocktailsMenuAdapter;
import com.example.dhernandez.vidvintage.entity.CocktailVO;
import com.example.dhernandez.vidvintage.presenter.MenuListPresenter.IMenuListPresenter;
import com.example.dhernandez.vidvintage.presenter.MenuListPresenter.MenuListPresenter;
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
    private List<CocktailVO> cocktailVOList;

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
                this.cocktailVOList = cocktails;
                setUpRecyclerView();
            }
            else
                presenter.getCocktails();
        });

        presenter.getNavigateTo().observe(this, screen -> {
            if(screen != null){
                Fragment fragment = null;
                switch (screen){
                    case COCKTAIL_DETAIL:
                        fragment = new CocktailDetailFragment();
                        break;
                }
                if(fragment != null){
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();

                    transaction.replace(container.getId(), fragment).addToBackStack(null).commit();
                }
            }
        });
        return view;
    }

    private void setUpRecyclerView() {
        cocktailListAdapter = new CocktailsMenuAdapter(getContext(), this.cocktailVOList);

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
