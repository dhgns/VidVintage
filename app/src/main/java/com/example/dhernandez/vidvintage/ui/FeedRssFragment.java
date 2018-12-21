package com.example.dhernandez.vidvintage.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dhernandez.vidvintage.R;
import com.example.dhernandez.vidvintage.Utils.Adapters.ArticlesAdapter;
import com.example.dhernandez.vidvintage.Utils.Constants;
import com.example.dhernandez.vidvintage.entity.ArticleVO;
import com.example.dhernandez.vidvintage.presenter.FeedRssPresenter.FeedRssPresenter;
import com.example.dhernandez.vidvintage.presenter.FeedRssPresenter.IFeedRssPresenter;
import com.example.dhernandez.vidvintage.presenter.PresenterFactory;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;

/**
 * Created by dhernandez on 30/08/2018.
 */

public class FeedRssFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @Inject
    PresenterFactory presenterFactory;
    IFeedRssPresenter presenter;

    @BindView(R.id.recycle_view_rss)
    RecyclerView rss_recycler_view;

    private List<ArticleVO> articles;
    private ArticlesAdapter articlesAdapter;

    @BindView(R.id.feed_rss_view_progress_bar)
    ProgressBar progressView;

    @BindView(R.id.feed_rss_view_swipelayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidSupportInjection.inject(this);

        // Generate the presenter with activity context to share it with the article detail fragment
        presenter = ViewModelProviders.of(getActivity(),presenterFactory).get(FeedRssPresenter.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feed_rss, container, false);

        ButterKnife.bind(this, view);

        this.articles = presenter.getFeedArticles().getValue();

        swipeRefreshLayout.setOnRefreshListener(this);

        setUpRecyclerView();

        presenter.getFeedArticles().observe(this, articleVOS -> {
            if(articleVOS != null && articleVOS != articles) {
                this.articles = articleVOS;
                articlesAdapter.setListSource(articles);
                articlesAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        presenter.getNavigateTo().observe(this, screen -> {
            if (screen != null) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                Fragment f = null;
                if (screen == Constants.Screens.ARTICLE_DETAIL) {
                    f = new ArticleDetailFragment();
                }
                if (f != null) {
                    transaction.replace(container.getId(), f).addToBackStack(null).commit();
                    presenter.getNavigateTo().setValue(null);
                }
            }
        });
        presenter.showReadingProgress().observe(this, showProgress->{
            if(showProgress){
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        presenter.showFeedReadError().observe(this, this::showFeedError);

        return view;
    }

    private void showFeedError(Boolean show) {
        if(show){
            Toast.makeText(getContext(), "Error reading Feed",
                    Toast.LENGTH_SHORT).show();
            presenter.showFeedReadError().setValue(false);
        }
    }

    private void setUpRecyclerView() {
        articlesAdapter = new ArticlesAdapter(articles);

        articlesAdapter.setOnClickListener(view -> onArticleClick(rss_recycler_view.getChildAdapterPosition(view)));

        rss_recycler_view.setAdapter(articlesAdapter);
        rss_recycler_view.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @OnClick(R.id.refresh_button_feed)
    public void forceFeedRefresh(){
        swipeRefreshLayout.setRefreshing(true);
        this.onRefresh();
    }

    private void onArticleClick(int childAdapterPosition) {
        if(articles != null && !articles.isEmpty())
            presenter.showArticleDetail(articles.get(childAdapterPosition));
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            progressView.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressView.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
        }
        //This is important in order to avoid performing multiple call for the same UC before
        //repetitive button touches
        if (show) {
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        } else {
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }

    }

    @Override
    public void onRefresh() {
        presenter.refreshFeed();
    }
}