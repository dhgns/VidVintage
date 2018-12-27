package com.example.dhernandez.vidvintage.ui.Fragments;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dhernandez.vidvintage.R;
import com.example.dhernandez.vidvintage.Utils.Adapters.TagsAdapter;
import com.example.dhernandez.vidvintage.entity.CocktailVO;
import com.example.dhernandez.vidvintage.presenter.CocktailDetailPresenter.CocktailDetailPresenter;
import com.example.dhernandez.vidvintage.presenter.CocktailDetailPresenter.ICocktailDetailPresenter;
import com.example.dhernandez.vidvintage.presenter.PresenterFactory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;

/**
 * Created by dhernandez on 21/12/2018.
 */

@SuppressLint("ValidFragment")
public class CocktailDetailFragment extends Fragment {

    @Inject
    PresenterFactory presenterFactory;
    ICocktailDetailPresenter presenter;

    @BindView(R.id.cocktail_menu_item_photo)
    ImageView cocktailImage;
    @BindView(R.id.cocktail_menu_item_title)
    TextView articleTitle;
    @BindView(R.id.cocktail_menu_item_description)
    TextView articleDescription;
    @BindView(R.id.cocktail_menu_item_tags_recycler_view)
    RecyclerView tagsRecyclerView;

    @BindView(R.id.cocktail_detail_fav)
    ImageButton cocktailFav;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidSupportInjection.inject(this);

        // Generate the presenter with activity context to share it with the article detail fragment
        presenter = ViewModelProviders.of(getActivity(), presenterFactory).get(CocktailDetailPresenter.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cocktail_detail, container, false);

        ButterKnife.bind(this, view);

        presenter.getCocktailDetail().observe(this, cocktailVO -> {
            if (cocktailVO != null) {
                drawCocktail(cocktailVO);
            }
        });

        presenter.getIsFavourite().observe(this, isFavourite -> {
            Drawable favIcon;
            if (isFavourite) {
                favIcon = getResources().getDrawable(R.drawable.fav_icon_active);
            } else {
                favIcon = getResources().getDrawable(R.drawable.fav_icon_inactive);
            }
            cocktailFav.setImageDrawable(favIcon);
        });

        return view;
    }

    private void drawCocktail(CocktailVO cocktailVO) {
        if (cocktailVO.getName() != null)
            articleTitle.setText(cocktailVO.getName());
        if (cocktailVO.getDescription() != null)
            articleDescription.setText(cocktailVO.getDescription());
        if (cocktailVO.getUrlPhoto() == null || cocktailVO.getUrlPhoto().equals(""))
            cocktailImage.setVisibility(View.GONE);
        else
            //cocktailImage.setImageDrawable(getContext().getResources().getDrawable(R.drawable.sex_on_the_beach));
            Picasso.get().load(cocktailVO.getUrlPhoto()).into(cocktailImage);

        List<String> tagsText = new ArrayList<>();
        tagsText.add("alcoholic");
        tagsText.add("sweet");
        tagsText.add("orange");
        tagsText.add("fruits");

        TagsAdapter tagsAdapter = new TagsAdapter();
        tagsAdapter.setList(tagsText);

        tagsRecyclerView.setAdapter(tagsAdapter);
        tagsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

            /*
            if(!cocktailVO.getTags().isEmpty()){
                TagsAdapter tagsAdapter = new TagsAdapter();
                tagsAdapter.setList(cocktailVO.getTags());

                tagsRecyclerView.setAdapter(tagsAdapter);
                tagsRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

            }*/

    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.checkFavourite();
    }

    @OnClick(R.id.cocktail_detail_fav)
    public void onFavClick() {
        if(presenter.getIsFavourite().getValue()){
            Toast.makeText(getContext(), getResources().getString(R.string.article_removed),
                    Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(), getResources().getString(R.string.article_added),
                    Toast.LENGTH_SHORT).show();
        }
        presenter.onClickFav();
    }

}
