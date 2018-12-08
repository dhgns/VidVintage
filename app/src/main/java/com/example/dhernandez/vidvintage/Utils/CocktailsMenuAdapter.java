package com.example.dhernandez.vidvintage.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dhernandez.vidvintage.R;
import com.example.dhernandez.vidvintage.entity.Cocktail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhernandez on 03/09/2018.
 */

public class CocktailsMenuAdapter extends RecyclerView.Adapter<CocktailsMenuAdapter.CocktailHolder>
        implements RecyclerView.OnClickListener {

    private List<Cocktail> cocktails;
    private RecyclerView.OnClickListener listener;

    private Context context;


    public CocktailsMenuAdapter(Context context, List<Cocktail> cocktails){
        this.cocktails = cocktails;
        this.context = context;
    }

    @Override
    public CocktailsMenuAdapter.CocktailHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View cocktailView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cocktail_row_view, parent, false);

        CocktailsMenuAdapter.CocktailHolder cocktailHolder = new CocktailsMenuAdapter.CocktailHolder(cocktailView);

        return cocktailHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CocktailsMenuAdapter.CocktailHolder holder, int position) {
        Cocktail cocktail = cocktails.get(position);

        holder.bind(cocktail);
    }

    @Override
    public int getItemCount() {
        if(cocktails != null)
            return cocktails.size();
        return 0;
    }

    public void setListSource(List<Cocktail> cocktails) {
        this.cocktails = cocktails;
    }

    public class CocktailHolder extends RecyclerView.ViewHolder {

        private ImageView cocktailImage;
        private TextView articleTitle;
        private TextView articleDescription;
        private RecyclerView tagsRecyclerView;

        public CocktailHolder(View itemView) {
            super(itemView);

            cocktailImage = itemView.findViewById(R.id.cocktail_menu_item_photo);
            articleTitle = itemView.findViewById(R.id.cocktail_menu_item_title);
            articleDescription = itemView.findViewById(R.id.cocktail_menu_item_description);
            tagsRecyclerView = itemView.findViewById(R.id.cocktail_menu_item_tags_recycler_view);


            itemView.setOnClickListener(listener);

        }

        @SuppressLint("NewApi")
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public void bind (Cocktail cocktail){
            if(cocktail.getName() != null)
                articleTitle.setText(cocktail.getName());
            if(cocktail.getDescription() != null)
                articleDescription.setText(cocktail.getDescription());
            if(cocktail.getUrlPhoto() == null || cocktail.getUrlPhoto().equals(""))
                cocktailImage.setVisibility(View.GONE);
            else
                cocktailImage.setImageDrawable(context.getDrawable(R.drawable.sex_on_the_beach));
                //Picasso.get().load(context.getResources().getDrawable(R.drawable.sex_on_the_beach)).into(cocktailImage);

            List<String> tagsText = new ArrayList<>();
            tagsText.add("alcoholic");
            tagsText.add("sweet");
            tagsText.add("orange");
            tagsText.add("fruits");

            TagsAdapter tagsAdapter = new TagsAdapter();
            tagsAdapter.setList(tagsText);

            tagsRecyclerView.setAdapter(tagsAdapter);
            tagsRecyclerView.setLayoutManager(new GridLayoutManager(context, 3));

            /*
            if(!cocktail.getTags().isEmpty()){
                TagsAdapter tagsAdapter = new TagsAdapter();
                tagsAdapter.setList(cocktail.getTags());

                tagsRecyclerView.setAdapter(tagsAdapter);
                tagsRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

            }*/

        }
    }


    public void setOnClickListener(RecyclerView.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }
}
