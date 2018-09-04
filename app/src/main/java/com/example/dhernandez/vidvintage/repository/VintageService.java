package com.example.dhernandez.vidvintage.repository;

import com.example.dhernandez.vidvintage.entity.CocktailDAO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by dhernandez on 03/09/2018.
 */

public interface VintageService {

    @GET("http://206.189.22.232:8080/cocktails")
    Call<List<CocktailDAO>> getCocktails();
}
