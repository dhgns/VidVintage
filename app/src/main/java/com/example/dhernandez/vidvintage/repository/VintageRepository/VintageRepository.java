package com.example.dhernandez.vidvintage.repository.VintageRepository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.dhernandez.vidvintage.application.MyApplication;
import com.example.dhernandez.vidvintage.entity.CocktailVO;
import com.example.dhernandez.vidvintage.entity.DAO.CocktailDTO;
import com.example.dhernandez.vidvintage.entity.CocktailsMenuResponse;
import com.example.dhernandez.vidvintage.entity.ErrorComm;
import com.example.dhernandez.vidvintage.entity.mapper.CocktailMapper;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by dhernandez on 03/09/2018.
 */

public class VintageRepository implements IVintageRepository {

    protected Retrofit retrofit;

    @Inject
    public VintageRepository(Retrofit retrofit) {
        this.retrofit = retrofit;
        MyApplication.getApplicationComponent().inject(this);
    }

    @Override
    public LiveData<CocktailsMenuResponse> getCocktailsMenu() {
        MutableLiveData<CocktailsMenuResponse> cocktailsMenuMLD = new MutableLiveData<>();

        this.getCocktails(cocktailsMenuMLD);

        return cocktailsMenuMLD;
    }

    @Override
    public MutableLiveData<CocktailVO> addNewCocktail(CocktailVO cocktailVO) {
        MutableLiveData<CocktailVO> response = new MutableLiveData<>();

        this.addCocktail(response, cocktailVO);

        return response;
    }

    private void addCocktail(MutableLiveData<CocktailVO> responseMLD, CocktailVO cocktailVO) {

        Call<CocktailDTO> call = retrofit.create(IVintageAPI.class)
                .addCocktail(CocktailMapper.mapperVOtoDTO(cocktailVO));

        call.enqueue(new Callback<CocktailDTO>() {
            @Override
            public void onResponse(Call<CocktailDTO> call, Response<CocktailDTO> response) {
                if(response.code() == 200) {
                    CocktailVO cocktail = CocktailMapper.mapperDTOtoVO(response.body());

                    responseMLD.setValue(cocktail);
                }
            }

            @Override
            public void onFailure(Call<CocktailDTO> call, Throwable t) {
                if(call.isCanceled()){

                }

            }
        });


    }

    private LiveData<CocktailsMenuResponse> getCocktails(MutableLiveData<CocktailsMenuResponse> cocktailsMenuResponseMLD) {
        Call<List<CocktailDTO>> call = retrofit.create(IVintageAPI.class).getCocktails();

        call.enqueue(new Callback<List<CocktailDTO>>() {
            CocktailsMenuResponse cocktailsMenuResponse;
            ErrorComm errorComm;

            @Override
            public void onResponse(Call<List<CocktailDTO>> call, Response<List<CocktailDTO>> response) {
                List<CocktailVO> cocktailVOList = null;
                if (response.code() == 200) {
                    errorComm = new ErrorComm("Successfull response", response.code(), ErrorComm.STATUS.NO_ERROR);
                    cocktailVOList = CocktailMapper.mapperDTOtoVO(response.body());

                    cocktailsMenuResponse = new CocktailsMenuResponse(cocktailVOList, errorComm);
                } else {
                    errorComm = new ErrorComm("Error", response.code(), ErrorComm.STATUS.ERROR);
                }

                cocktailsMenuResponse = new CocktailsMenuResponse(cocktailVOList, errorComm);
                cocktailsMenuResponseMLD.setValue(cocktailsMenuResponse);

            }

            @Override
            public void onFailure(Call<List<CocktailDTO>> call, Throwable t) {
                errorComm = new ErrorComm("Error", 500, ErrorComm.STATUS.ERROR);
                cocktailsMenuResponseMLD.setValue(new CocktailsMenuResponse(null, errorComm));
            }

        });

        return null;
    }
}
