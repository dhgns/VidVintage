package com.example.dhernandez.vidvintage.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.dhernandez.vidvintage.entity.Cocktail;
import com.example.dhernandez.vidvintage.entity.CocktailDAO;
import com.example.dhernandez.vidvintage.entity.CocktailsMenuResponse;
import com.example.dhernandez.vidvintage.entity.ErrorComm;
import com.example.dhernandez.vidvintage.entity.mapper.MapperCocktailResponse;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by dhernandez on 03/09/2018.
 */

public class VintageRepository implements IVintageRepository {

    private final Retrofit retrofit;
    private final IVintageService vintageService;
    private final String baseUrl = "http://206.189.22.232:8080";

    private interface IVintageService {
        @Headers({
                "Accept: application/json",
                "Content-Type: application/json"
        })
        @GET("/cocktails")
        Call<List<CocktailDAO>> getCocktails();
    }

    public VintageRepository() {

        OkHttpClient.Builder client = new OkHttpClient.Builder();

        client.connectTimeout(60, TimeUnit.SECONDS);
        client.readTimeout(60, TimeUnit.SECONDS);
        client.writeTimeout(60, TimeUnit.SECONDS);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.addInterceptor(logging);

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        vintageService = retrofit.create(IVintageService.class);

    }

    @Override
    public LiveData<CocktailsMenuResponse> getCocktailsMenu() {
        MutableLiveData<CocktailsMenuResponse> cocktailsMenuMLD = new MutableLiveData<>();

        this.getCocktails(cocktailsMenuMLD);

        return cocktailsMenuMLD;
    }

    private LiveData<CocktailsMenuResponse> getCocktails(MutableLiveData<CocktailsMenuResponse> cocktailsMenuResponseMLD) {
        Call<List<CocktailDAO>> call = vintageService.getCocktails();

        call.enqueue(new Callback<List<CocktailDAO>>() {
            CocktailsMenuResponse cocktailsMenuResponse;
            ErrorComm errorComm;

            @Override
            public void onResponse(Call<List<CocktailDAO>> call, Response<List<CocktailDAO>> response) {
                List<Cocktail> cocktailList = null;
                if (response.code() == 200) {
                    errorComm = new ErrorComm("Successfull response", response.code(), ErrorComm.STATUS.NO_ERROR);
                    cocktailList = MapperCocktailResponse.CocktailDAOtoCocktail(response.body());

                    cocktailsMenuResponse = new CocktailsMenuResponse(cocktailList, errorComm);
                } else {
                    errorComm = new ErrorComm("Error", response.code(), ErrorComm.STATUS.ERROR);
                }

                cocktailsMenuResponse = new CocktailsMenuResponse(cocktailList, errorComm);
                cocktailsMenuResponseMLD.setValue(cocktailsMenuResponse);

            }

            @Override
            public void onFailure(Call<List<CocktailDAO>> call, Throwable t) {
                errorComm = new ErrorComm("Error", 500, ErrorComm.STATUS.ERROR);
                cocktailsMenuResponseMLD.setValue(new CocktailsMenuResponse(null, errorComm));
            }

        });

        return null;
    }
}
