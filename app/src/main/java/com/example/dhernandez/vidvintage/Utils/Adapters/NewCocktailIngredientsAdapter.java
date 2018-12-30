package com.example.dhernandez.vidvintage.Utils.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dhernandez.vidvintage.R;
import com.example.dhernandez.vidvintage.Utils.CustomImageView;
import com.example.dhernandez.vidvintage.presenter.ProfilePresenter.IProfilePresenter;

/**
 * Created by dhernandez on 28/12/2018.
 */

public class NewCocktailIngredientsAdapter extends RecyclerView.Adapter<NewCocktailIngredientsAdapter.IngredientViewHolder> {

    IProfilePresenter presenter;

    public NewCocktailIngredientsAdapter(IProfilePresenter presenter){
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ingredientView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_row_view, parent, false);

        IngredientViewHolder ingredientHolder = new IngredientViewHolder(ingredientView);

        return ingredientHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {

        holder.ingredientName.setText(presenter.getNewCocktailIngredients().getValue().get(position));

        holder.removeButton.setOnClickListener(click->{
            presenter.removeIngredient(position);
            this.notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        return presenter.getNewCocktailIngredients().getValue().size();
    }

    protected class IngredientViewHolder extends RecyclerView.ViewHolder {

        TextView ingredientName;
        CustomImageView removeButton;

        public IngredientViewHolder(View view) {
            super(view);

            ingredientName = view.findViewById(R.id.ingredient_name);
            removeButton = view.findViewById(R.id.remove_ingredient);
        }
    }
}
