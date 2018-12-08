package com.example.dhernandez.vidvintage.repository.LocalStorageRepository;

import android.arch.lifecycle.MutableLiveData;
import android.content.SharedPreferences;

import com.example.dhernandez.vidvintage.application.MyApplication;
import com.example.dhernandez.vidvintage.entity.Cocktail;
import com.example.dhernandez.vidvintage.entity.DAO.CocktailDAO;
import com.example.dhernandez.vidvintage.entity.mapper.CocktailMapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;

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
    public void saveTheme(String key,String value) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putString(key,value);
        editor.apply();
    }

    @Override
    public String loadTheme(String key) {
        return sharedPreferences.getString(key, "");
    }

    @Override
    public void saveString(String key, String value) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putString(key,value);
        editor.apply();
    }

    @Override
    public String loadString(String key, String defaultValue) {
        return sharedPreferences.getString(key,defaultValue);
    }

    @Override
    public void deleteKey(String key) {
        sharedPreferences.edit().remove(key).apply();
    }

    @Override
    public void saveBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }

    @Override
    public boolean loadBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    @Override
    public void saveCocktail(Cocktail cocktail) {
        realm.beginTransaction();
        /*
        realm.insertOrUpdate(com.example.dhernandez.vidvintage.entity.mapper.
                CocktailMapper.mapperVOtoDAO(cocktail));
                */
        realm.commitTransaction();
    }

    @Override
    public MutableLiveData<List<Cocktail>> retrieveCocktails(String userID) {
        MutableLiveData<List<Cocktail>> result = new MutableLiveData<>();

        List<Cocktail> value = new ArrayList<>();
        /*
        List<Cocktail> value = CocktailMapper.mapperDAOtoVO(
                realm.where(CocktailDAO.class).findAll());
                */

        List<Cocktail> filteredValue = new ArrayList<>();

        for (Cocktail cocktail : value){
            if(cocktail.getAuthor().getUsername().equals(userID)){
                filteredValue.add(cocktail);
            }
        }

        result.setValue(filteredValue);

        return result;

    }

}
