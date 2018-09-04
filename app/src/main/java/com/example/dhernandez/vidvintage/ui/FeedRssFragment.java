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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dhernandez.vidvintage.R;
import com.example.dhernandez.vidvintage.Utils.ArticlesAdapter;
import com.example.dhernandez.vidvintage.Utils.Constants;
import com.example.dhernandez.vidvintage.entity.ArticleVO;
import com.example.dhernandez.vidvintage.presenter.FeedRssPresenter;
import com.example.dhernandez.vidvintage.presenter.IFeedRssPresenter;
import com.example.dhernandez.vidvintage.presenter.PresenterFactory;
import com.prof.rssparser.Article;
import com.prof.rssparser.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

/**
 * Created by dhernandez on 30/08/2018.
 */

public class FeedRssFragment extends Fragment {

    @Inject
    PresenterFactory presenterFactory;
    IFeedRssPresenter presenter;

    private String urlString;

    @BindView(R.id.recycle_view_rss)
    RecyclerView rss_recycler_view;

    private List<ArticleVO> articles;

    private ArticlesAdapter articlesAdapter;

    @BindView(R.id.feed_rss_view_progress_bar)
    ProgressBar progressView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidSupportInjection.inject(this);

        // Generate the presenter with activity context to share it with the article detail fragment
        presenter = ViewModelProviders.of(getActivity(),presenterFactory).get(FeedRssPresenter.class);

        urlString =  getResources().getString(R.string.feedUrl);
        urlString = "https://www.reddit.com/r/cocktails/.rss";
        urlString = "http://www.europapress.es/rss/rss.aspx?ch=00564";
        urlString = "http://rss.cnn.com/rss/edition_sport.rss";
        urlString = "https://www.cocacolaespana.es/Feeds/standard-rss-feed.xml";
        urlString = "http://rss.cnn.com/rss/edition.rss";
        urlString = "https://www.nasa.gov/rss/dyn/educationnews.rss";
        urlString = "http://newsrss.bbc.co.uk/rss/newsonline_uk_edition/front_page/rss.xml";

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feed_rss, container, false);

        ButterKnife.bind(this, view);

        this.articles = presenter.getFeedArticles().getValue();

        if(articles == null || articles.isEmpty())
            readFeed();

        setUpRecyclerView();

        presenter.getFeedArticles().observe(this, articleVOS -> {
            if(articleVOS != null && articleVOS != articles) {
                this.articles = articleVOS;
                articlesAdapter.setListSource(articles);
                articlesAdapter.notifyDataSetChanged();
            }
        });
        presenter.getNavigateTo().observe(this, screen ->{
            if(screen != null){
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                Fragment f = null;
                if(screen == Constants.Screens.ARTICLE_DETAIL){
                    f = new ArticleDetailFragment();
                }
                if(f != null) {
                    transaction.replace(container.getId(), f).addToBackStack(null).commit();
                    presenter.getNavigateTo().setValue(null);
                }
            }
        });
        presenter.showReadingProgress().observe(this, this::showProgress);
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

        articlesAdapter.setOnClickListener(new RecyclerView.OnClickListener() {
            @Override
            public void onClick(View view) {
                onArticleClick(rss_recycler_view.getChildAdapterPosition(view));
            }
        });

        rss_recycler_view.setAdapter(articlesAdapter);
        rss_recycler_view.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

    }

    private void readFeed() {
        Parser parser = new Parser();

        presenter.showReadingProgress().setValue(true);

        parser.execute(urlString);
        parser.onFinish(new Parser.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(ArrayList<Article> list) {
                presenter.showReadingProgress().setValue(false);

                ArrayList<ArticleVO> readResult = new ArrayList<>();
                for(Article a : list){
                    ArticleVO articleVO = new ArticleVO();

                    String urlImage = a.getImage();
                    if(urlImage != null)
                        urlImage = urlImage.replace("\n", "");
                    articleVO.setArticleImageURL(urlImage);

                    String authorName = a.getAuthor();
                    if(authorName != null)
                        authorName = authorName.replace("\n", "");
                    articleVO.setAuthor(authorName);

                    String articleTitle = a.getTitle();
                    if(articleTitle != null)
                        articleTitle = articleTitle.replace("\n", "");
                    articleVO.setTitle(articleTitle);

                    String articleDescription = a.getDescription();
                    if(articleDescription != null)
                        articleDescription = formatArticleBody(articleDescription);
                    articleVO.setDescription(articleDescription);


                    String articleUrl = a.getLink();
                    if(articleUrl != null)
                        articleUrl = articleUrl.replace("\n", "");
                    articleVO.setUrl(articleUrl);

                    readResult.add(articleVO);
                }

                if(!readResult.isEmpty()) {
                    presenter.getFeedArticles().setValue(readResult);
                }
            }

            @Override
            public void onError() {
                presenter.showReadingProgress().postValue(false);
                presenter.showFeedReadError().postValue(true);

            }
        });

    }

    /**
     * This function will call an recursive function tha will remove the repeated "\n" in the article body
     * @param articleDescription
     * @return
     */
    private String formatArticleBody(String articleDescription) {
        String ret;

        Pattern p = Pattern.compile("[\n]{2,}");
        ret = replaceRepeatedBreakLine(articleDescription, p);

        return ret;
    }

    private String replaceRepeatedBreakLine(String s, Pattern p){
        String ret = s;

        Matcher m = p.matcher(s);

        if(m.find()){
            String toReplace = ret.substring(m.start(), m.end());
            ret = replaceRepeatedBreakLine(ret.replace(toReplace, "\n\n"), p);
        }

        return ret;

    }

    @Override
    public void onStart() {
        super.onStart();
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
}