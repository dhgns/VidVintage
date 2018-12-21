package com.example.dhernandez.vidvintage.repository.VintageRepository;

import com.example.dhernandez.vidvintage.entity.DAO.CocktailDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by dhernandez on 03/09/2018.
 */

public interface IVintageAPI {

    //@GET("http://206.189.22.232:8080/cocktails")
    //Call<List<CocktailDTO>> getCocktailVOS();

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("/cocktails")
    Call<List<CocktailDTO>> getCocktails();
}
