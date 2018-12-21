package com.example.dhernandez.vidvintage.Utils.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dhernandez.vidvintage.R;
import com.example.dhernandez.vidvintage.entity.CocktailVO;

import java.util.List;

/**
 * Created by dhernandez on 21/12/2018.
 */

public class CocktailsAdapter extends RecyclerView.Adapter<CocktailsAdapter.CocktailsHolder>
        implements RecyclerView.OnClickListener {

    private List<CocktailVO> cocktailVOS;
    private RecyclerView.OnClickListener listener;

    public CocktailsAdapter(List<CocktailVO> cocktailVOS) {
        this.cocktailVOS = cocktailVOS;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    @Override
    public CocktailsAdapter.CocktailsHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View cocktailView = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_row_view, parent, false);

        CocktailsAdapter.CocktailsHolder cocktailsHolder = new CocktailsAdapter.CocktailsHolder(cocktailView);

        return cocktailsHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CocktailsAdapter.CocktailsHolder holder, int position) {
        CocktailVO cocktailVO = cocktailVOS.get(position);

        //Add the context here if you need to includ som libraries from the app to rendder the articleVO
        holder.bind(cocktailVO);
    }

    @Override
    public int getItemCount() {
        if (cocktailVOS != null)
            return cocktailVOS.size();
        return 0;
    }

    public void setListSource(List<CocktailVO> cocktailVOS) {
        this.cocktailVOS = cocktailVOS;
    }

    public class CocktailsHolder extends RecyclerView.ViewHolder {

        private ImageView articleImage;
        private TextView articleTitle;
        private TextView articleDescription;

        public CocktailsHolder(View itemView) {
            super(itemView);

            articleImage = itemView.findViewById(R.id.article_image);
            articleTitle = itemView.findViewById(R.id.article_title);
            articleDescription = itemView.findViewById(R.id.article_description);

            itemView.setOnClickListener(listener);

        }

        public void bind(CocktailVO cocktailVO) {

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
