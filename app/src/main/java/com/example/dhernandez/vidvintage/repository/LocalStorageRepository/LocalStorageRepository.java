package com.example.dhernandez.vidvintage.repository.LocalStorageRepository;

import android.arch.lifecycle.MutableLiveData;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import com.example.dhernandez.vidvintage.Utils.PictureProfileMapper;
import com.example.dhernandez.vidvintage.application.MyApplication;
import com.example.dhernandez.vidvintage.entity.ArticleVO;
import com.example.dhernandez.vidvintage.entity.CocktailVO;
import com.example.dhernandez.vidvintage.entity.DAO.ArticleDAO;
import com.example.dhernandez.vidvintage.entity.DAO.CocktailDAO;
import com.example.dhernandez.vidvintage.entity.DAO.PictureProfileDAO;
import com.example.dhernandez.vidvintage.entity.mapper.ArticleMapper;
import com.example.dhernandez.vidvintage.entity.mapper.CocktailMapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by dhernandez on 06/09/2018.
 */

public class LocalStorageRepository implements ILocalStorageRepository {

    private final SharedPreferences sharedPreferences;
    protected Realm realm;

    @Inject
    public LocalStorageRepository(Realm realm, SharedPreferences sharedPreferences) {
        MyApplication.getApplicationComponent().inject(this);
        this.sharedPreferences = sharedPreferences;
        this.realm = realm;
    }

    @Override
    public void saveTheme(String key, String value) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    @Override
    public String loadTheme(String key) {
        return sharedPreferences.getString(key, "");
    }

    @Override
    public void saveString(String key, String value) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    @Override
    public String loadString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    @Override
    public void deleteKey(String key) {
        sharedPreferences.edit().remove(key).apply();
    }

    @Override
    public void saveBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    @Override
    public boolean loadBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    @Override
    public void saveCocktail(CocktailVO cocktailVO) {
        realm.beginTransaction();

        realm.insertOrUpdate(com.example.dhernandez.vidvintage.entity.mapper.
                CocktailMapper.mapperVOtoDAO(cocktailVO));

        realm.commitTransaction();
    }

    @Override
    public MutableLiveData<List<CocktailVO>> retrieveCocktails(String userID) {
        MutableLiveData<List<CocktailVO>> result = new MutableLiveData<>();

        List<CocktailVO> value = new ArrayList<>();
        /*
        List<CocktailVO> value = CocktailMapper.mapperDTOtoVO(
                realm.where(CocktailDTO.class).findAll());
                */

        List<CocktailVO> filteredValue = new ArrayList<>();

        for (CocktailVO cocktailVO : value) {
            if (cocktailVO.getAuthor().getUsername().equals(userID)) {
                filteredValue.add(cocktailVO);
            }
        }

        result.setValue(filteredValue);

        return result;

    }

    @Override
    public ArticleVO getFavouriteArticle(String url) {
        return ArticleMapper.mapperDAOtoVO(realm.where(ArticleDAO.class).equalTo("url", url).findFirst());
    }

    @Override
    public void removeFavouriteArticle(ArticleVO value) {
        RealmResults<ArticleDAO> results = realm.where(ArticleDAO.class)
                .equalTo("url", value.getUrl()).findAllAsync();

        results.addChangeListener(new OrderedRealmCollectionChangeListener<RealmResults<ArticleDAO>>() {
            @Override
            public void onChange(RealmResults<ArticleDAO> articleDAOS, OrderedCollectionChangeSet changeSet) {
                realm.beginTransaction();
                results.deleteAllFromRealm();
                realm.commitTransaction();
                results.removeChangeListener(this);
            }
        });
    }

    @Override
    public void addArticleFavourite(ArticleVO value) {
        realm = Realm.getDefaultInstance();
        realm.refresh();
        realm.executeTransactionAsync(
                realmAux -> realmAux.insertOrUpdate(ArticleMapper.mapperVOtoDAO(value)));
    }

    @Override
    public List<ArticleVO> getFavouriteArticles() {
        return ArticleMapper.mapperDAOtoVO(realm.where(ArticleDAO.class).findAll());
    }

    @Override
    public List<CocktailVO> getFavouriteCocktails() {
        return CocktailMapper.mapperDAOtoVO(realm.where(CocktailDAO.class).findAll());
    }

    @Override
    public CocktailVO getFavouriteCocktail(CocktailVO value) {
        return CocktailMapper.mapperDAOtoVO(realm.where(CocktailDAO.class).equalTo("id", value.getId()).findFirst());
    }

    @Override
    public void removeFavouriteCocktail(CocktailVO value) {
        RealmResults<CocktailDAO> results = realm.where(CocktailDAO.class)
                .equalTo("name", value.getName()).findAllAsync();

        results.addChangeListener(new OrderedRealmCollectionChangeListener<RealmResults<CocktailDAO>>() {
            @Override
            public void onChange(RealmResults<CocktailDAO> cocktailDAOS, OrderedCollectionChangeSet changeSet) {
                realm.beginTransaction();
                results.deleteAllFromRealm();
                realm.commitTransaction();
                results.removeChangeListener(this);
            }
        });
    }

    @Override
    public void saveImage(String email, Bitmap value) {
        realm = Realm.getDefaultInstance();
        realm.refresh();
        realm.executeTransactionAsync(
                realmAux -> realmAux.insertOrUpdate(PictureProfileMapper.mapperVOtoDAO(email, value)));
    }

    @Override
    public Bitmap loadImage(String email){
        return PictureProfileMapper.mapperDAOtoVO(realm.where(PictureProfileDAO.class).equalTo("id", email).findFirst());
    }

}
