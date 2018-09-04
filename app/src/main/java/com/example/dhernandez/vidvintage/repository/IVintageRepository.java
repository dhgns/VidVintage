package com.example.dhernandez.vidvintage.repository;

import android.arch.lifecycle.LiveData;

import com.example.dhernandez.vidvintage.entity.CocktailDAO;
import com.example.dhernandez.vidvintage.entity.CocktailsMenuResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by dhernandez on 03/09/2018.
 */

public interface IVintageRepository {

    LiveData<CocktailsMenuResponse> getCocktailsMenu();

}
