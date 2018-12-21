package com.example.dhernandez.vidvintage.presenter.FeedRssPresenter;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.dhernandez.vidvintage.Utils.Constants;
import com.example.dhernandez.vidvintage.application.MyApplication;
import com.example.dhernandez.vidvintage.entity.ArticleVO;
import com.example.dhernandez.vidvintage.repository.LocalStorageRepository.ILocalStorageRepository;
import com.prof.rssparser.Article;
import com.prof.rssparser.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.OnClick;

/**
 * Created by dhernandez on 30/08/2018.
 */

public class FeedRssPresenter extends ViewModel implements IFeedRssPresenter {

    private final ILocalStorageRepository localStorageRepository;
    private MutableLiveData<List<ArticleVO>> feedArticles;

    private final MutableLiveData<ArticleVO> articleClicked;
    private MutableLiveData<Constants.Screens> navigateTo;
    private MutableLiveData<Boolean> showProgress;
    private MutableLiveData<Boolean> showFeedReadError;
    private MutableLiveData<Boolean> isFavourite;
    private String urlString;

    public FeedRssPresenter(MutableLiveData<List<ArticleVO>> feedArticles,
                            ILocalStorageRepository localStorageRepository,
                            MutableLiveData<ArticleVO> articleClicked) {
        //Import the presenter in the application component to make The job of Dagger
        //a little bit easier by the time it will have to resolve dependencies
        MyApplication.getApplicationComponent().inject(this);

        this.feedArticles = feedArticles;
        this.localStorageRepository = localStorageRepository;
        this.articleClicked = articleClicked;
        this.navigateTo = new MutableLiveData<>();
        this.showProgress = new MutableLiveData<>();
        this.showFeedReadError = new MutableLiveData<>();
        this.isFavourite = new MutableLiveData<>();
        this.showFeedReadError.setValue(false);

        //urlString = "https://www.reddit.com/r/cocktails/.rss";
        //urlString = "http://www.europapress.es/rss/rss.aspx?ch=00564";
        //urlString = "http://rss.cnn.com/rss/edition_sport.rss";
        //urlString = "https://www.cocacolaespana.es/Feeds/standard-rss-feed.xml";
        //urlString = "http://rss.cnn.com/rss/edition.rss";
        //urlString = "https://www.nasa.gov/rss/dyn/educationnews.rss";
        urlString = "http://newsrss.bbc.co.uk/rss/newsonline_uk_edition/front_page/rss.xml";

        readFeed();
    }

    @Override
    public void showArticleDetail(ArticleVO articleVO) {
        this.isFavourite.setValue(this.checkFavouriteArticle(articleVO.getUrl()));
        this.articleClicked.setValue(articleVO);
        this.navigateTo.setValue(Constants.Screens.ARTICLE_DETAIL);
    }

    @Override
    public MutableLiveData<Boolean> getIsFavourite() {
        return this.isFavourite;
    }

    @Override
    public MutableLiveData<List<ArticleVO>> getFeedArticles() {
        return this.feedArticles;
    }

    @Override
    public MutableLiveData<ArticleVO> getArticleClicked() {
        return this.articleClicked;
    }

    @Override
    public MutableLiveData<Constants.Screens> getNavigateTo() {
        return this.navigateTo;
    }

    @Override
    public MutableLiveData<Boolean> showReadingProgress() {
        return this.showProgress;
    }

    @Override
    public MutableLiveData<Boolean> showFeedReadError() {
        return this.showFeedReadError;
    }

    @Override
    public void readFeed() {
        Parser parser = new Parser();

        showProgress.setValue(true);

        parser.execute(urlString);
        parser.onFinish(new Parser.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(ArrayList<Article> list) {
                showProgress.setValue(false);

                ArrayList<ArticleVO> readResult = new ArrayList<>();
                for (Article a : list) {
                    ArticleVO articleVO = new ArticleVO();

                    String urlImage = a.getImage();
                    if (urlImage != null)
                        urlImage = urlImage.replace("\n", "");
                    articleVO.setArticleImageURL(urlImage);

                    String authorName = a.getAuthor();
                    if (authorName != null)
                        authorName = authorName.replace("\n", "");
                    articleVO.setAuthor(authorName);

                    String articleTitle = a.getTitle();
                    if (articleTitle != null)
                        articleTitle = articleTitle.replace("\n", "");
                    articleVO.setTitle(articleTitle);

                    String articleDescription = a.getDescription();
                    if (articleDescription != null)
                        articleDescription = formatArticleBody(articleDescription);
                    articleVO.setDescription(articleDescription);


                    String articleUrl = a.getLink();
                    if (articleUrl != null)
                        articleUrl = articleUrl.replace("\n", "");
                    articleVO.setUrl(articleUrl);

                    readResult.add(articleVO);
                }

                if (!readResult.isEmpty()) {
                    feedArticles.postValue(readResult);
                }
            }

            @Override
            public void onError() {
                showProgress.postValue(false);
                showFeedReadError.postValue(true);

            }
        });

    }

    @Override
    public void refreshFeed() {
        this.feedArticles.setValue(null);
        this.readFeed();
    }

    @Override
    public boolean checkFavouriteArticle(String url) {
        return localStorageRepository.getFavouriteArticle(url) != null;
    }

    @Override
    public void removeFavouriteArticle() {
        localStorageRepository.removeFavourite(this.articleClicked.getValue());
    }

    @Override
    public void addFavouriteArticle() {
        localStorageRepository.addArticleFavourite(articleClicked.getValue());
    }

    @Override
    public void toogleFavourite() {
        if(this.isFavourite.getValue()){
            this.removeFavouriteArticle();
        }else{
            this.addFavouriteArticle();
        }
        this.isFavourite.setValue(!this.isFavourite.getValue());
    }

    /**
     * This function will call an recursive function tha will remove the repeated "\n" in the article body
     *
     * @param articleDescription
     * @return
     */
    private String formatArticleBody(String articleDescription) {
        String ret;

        Pattern p = Pattern.compile("[\n]{2,}");
        ret = replaceRepeatedBreakLine(articleDescription, p);

        return ret;
    }

    private String replaceRepeatedBreakLine(String s, Pattern p) {
        String ret = s;

        Matcher m = p.matcher(s);

        if (m.find()) {
            String toReplace = ret.substring(m.start(), m.end());
            ret = replaceRepeatedBreakLine(ret.replace(toReplace, "\n\n"), p);
        }

        return ret;

    }

}
