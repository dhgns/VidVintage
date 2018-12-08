package com.example.dhernandez.vidvintage.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dhernandez.vidvintage.R;
import com.example.dhernandez.vidvintage.entity.ArticleVO;
import com.example.dhernandez.vidvintage.presenter.FeedRssPresenter.FeedRssPresenter;
import com.example.dhernandez.vidvintage.presenter.FeedRssPresenter.IFeedRssPresenter;
import com.example.dhernandez.vidvintage.presenter.PresenterFactory;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

/**
 * Created by dhernandez on 30/08/2018.
 */

public class ArticleDetailFragment extends Fragment {

    @Inject
    PresenterFactory presenterFactory;
    IFeedRssPresenter presenter;

    @BindView(R.id.article_detail_author)
    TextView articleAuthor;
    @BindView(R.id.article_detail_url)
    TextView articleUrl;
    @BindView(R.id.article_detail_title)
    TextView articleTitle;
    @BindView(R.id.article_detail_description)
    TextView articleDescription;
    @BindView(R.id.article_detail_image)
    ImageView articleImage;

    @BindView(R.id.article_detail_author_view)
    LinearLayout articleAuthorView;
    @BindView(R.id.article_detail_url_view)
    LinearLayout articleUrlView;

    private ArticleVO articleDetail;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidSupportInjection.inject(this);

        // Generate the presenter with activity context to share it with the article detail fragment
        presenter = ViewModelProviders.of(getActivity(),presenterFactory).get(FeedRssPresenter.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_article_detail, container, false);

        ButterKnife.bind(this, view);

        presenter.getArticleClicked().observe(this, articleVO -> {
            if(articleVO != null) {
                this.articleDetail = articleVO;
                drawArticle();
            }
        });
        return view;
    }

    private void drawArticle() {

        if(articleDetail.getArticleImageURL() != null && !articleDetail.getArticleImageURL().equals(""))
            Picasso.get().load(articleDetail.getArticleImageURL()).into(articleImage);
        else
            articleImage.setVisibility(View.GONE);

        if(articleDetail.getTitle() != null && !articleDetail.getTitle().equals(""))
            articleTitle.setText(articleDetail.getTitle());
        else
            articleTitle.setVisibility(View.GONE);

        if(articleDetail.getAuthor() != null && !articleDetail.getAuthor().equals(""))
            articleAuthor.setText(articleDetail.getAuthor());
        else {
            articleAuthor.setVisibility(View.GONE);
            articleAuthorView.setVisibility(View.GONE);
        }

        if(articleDetail.getUrl() != null && !articleDetail.getUrl().equals(""))
            articleUrl.setText(articleDetail.getUrl());
        else {
            articleUrl.setVisibility(View.GONE);
            articleUrlView.setVisibility(View.GONE);
        }

        if(articleDetail.getDescription() != null && !articleDetail.getDescription().equals(""))
            articleDescription.setText(articleDetail.getDescription());
        else
            articleDescription.setVisibility(View.GONE);
    }


}
