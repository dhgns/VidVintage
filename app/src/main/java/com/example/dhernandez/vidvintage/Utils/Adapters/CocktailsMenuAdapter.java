package com.example.dhernandez.vidvintage.Utils.Adapters;

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
import com.example.dhernandez.vidvintage.entity.CocktailVO;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhernandez on 03/09/2018.
 */

public class CocktailsMenuAdapter extends RecyclerView.Adapter<CocktailsMenuAdapter.CocktailHolder>
        implements RecyclerView.OnClickListener {

    private List<CocktailVO> cocktailVOS;
    private RecyclerView.OnClickListener listener;

    private Context context;


    public CocktailsMenuAdapter(Context context, List<CocktailVO> cocktailVOS){
        this.cocktailVOS = cocktailVOS;
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
        CocktailVO cocktailVO = cocktailVOS.get(position);

        holder.bind(cocktailVO);
    }

    @Override
    public int getItemCount() {
        if(cocktailVOS != null)
            return cocktailVOS.size();
        return 0;
    }

    public void setListSource(List<CocktailVO> cocktailVOS) {
        this.cocktailVOS = cocktailVOS;
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
        public void bind (CocktailVO cocktailVO){
            if(cocktailVO.getName() != null)
                articleTitle.setText(cocktailVO.getName());
            if(cocktailVO.getDescription() != null)
                articleDescription.setText(cocktailVO.getDescription());
            if(cocktailVO.getUrlPhoto() == null || cocktailVO.getUrlPhoto().equals(""))
                cocktailImage.setVisibility(View.GONE);
            else
                //cocktailImage.setImageDrawable(context.getDrawable(R.drawable.sex_on_the_beach));
                Picasso.get().load(cocktailVO.getUrlPhoto()).into(cocktailImage);

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
            if(!cocktailVO.getTags().isEmpty()){
                TagsAdapter tagsAdapter = new TagsAdapter();
                tagsAdapter.setList(cocktailVO.getTags());

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
