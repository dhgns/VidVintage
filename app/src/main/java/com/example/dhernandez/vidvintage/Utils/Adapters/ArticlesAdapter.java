package com.example.dhernandez.vidvintage.Utils.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dhernandez.vidvintage.R;
import com.example.dhernandez.vidvintage.entity.ArticleVO;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by dhernandez on 30/08/2018.
 */

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ArcticlesAdapterHolder>
        implements RecyclerView.OnClickListener {

    private List<ArticleVO> articleVOS;
    private RecyclerView.OnClickListener listener;

    public ArticlesAdapter(List<ArticleVO> articleVOS){
        this.articleVOS = articleVOS;
    }

    @Override
    public void onAttachedToRecyclerView( RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    @Override
    public ArcticlesAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View articleView = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_row_view, parent, false);

        ArcticlesAdapterHolder arcticlesAdapterHolder = new ArcticlesAdapterHolder(articleView);

        return arcticlesAdapterHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ArcticlesAdapterHolder holder, int position) {
        ArticleVO articleVO = articleVOS.get(position);

        //Add the context here if you need to includ som libraries from the app to rendder the articleVO
        holder.bind(articleVO);
    }

    @Override
    public int getItemCount() {
        if(articleVOS != null)
            return articleVOS.size();
        return 0;
    }

    public void setListSource(List<ArticleVO> articles) {
        this.articleVOS = articles;
    }

    public class ArcticlesAdapterHolder extends RecyclerView.ViewHolder {

        private ImageView articleImage;
        private TextView articleTitle;
        private TextView articleDescription;

        public ArcticlesAdapterHolder(View itemView) {
            super(itemView);

            articleImage = itemView.findViewById(R.id.article_image);
            articleTitle = itemView.findViewById(R.id.article_title);
            articleDescription = itemView.findViewById(R.id.article_description);

            itemView.setOnClickListener(listener);

        }

        public void bind (ArticleVO articleVO){
            articleDescription.setText(articleVO.getDescription());
            articleTitle.setText(articleVO.getTitle());
            if(articleVO.getArticleImageURL() == null || articleVO.getArticleImageURL().equals(""))
                articleImage.setVisibility(View.GONE);
            else
                Picasso.get().load(articleVO.getArticleImageURL()).into(articleImage);
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
