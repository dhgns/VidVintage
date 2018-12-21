package com.example.dhernandez.vidvintage.entity.mapper;

import com.example.dhernandez.vidvintage.entity.CocktailVO;
import com.example.dhernandez.vidvintage.entity.DAO.CocktailDAO;
import com.example.dhernandez.vidvintage.entity.DAO.CocktailDTO;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;

/**
 * Created by dhernandez on 08/12/2018.
 */

public class CocktailMapper {

    public static CocktailDTO mapperVOtoDAO(CocktailVO cocktailVO) {
        CocktailDTO cocktailDTO = new CocktailDTO();

        cocktailDTO.setAlcoholic(cocktailVO.getAlcoholic());
        cocktailDTO.setCocktailUrl(cocktailVO.getCocktailUrl());
        cocktailDTO.setTags(cocktailVO.getTags());
        cocktailDTO.setLikes(cocktailVO.getLikes());
        cocktailDTO.setReceipt(cocktailVO.getReceipt());
        cocktailDTO.setUrlPhoto(cocktailVO.getUrlPhoto());
        cocktailDTO.setUrlVideo(cocktailVO.getUrlVideo());
        cocktailDTO.setAuthor(com.example.dhernandez.vidvintage.entity.mapper.AuthorMapper.mapperVOtoDAO(cocktailVO.getAuthor()));
        cocktailDTO.setIngredients(com.example.dhernandez.vidvintage.entity.mapper.IngredientsMapper.mapperVOtoDAO(cocktailVO.getIngredients()));

        return cocktailDTO;

    }

    public static List<CocktailVO> mapperDAOtoVO(RealmResults<CocktailDAO> all) {
        List<CocktailVO> result = new ArrayList<>();

        for (CocktailDAO cocktailDAO : all) {
            result.add(mapperDAOtoVO(cocktailDAO));
        }
        return result;
    }

    public static CocktailVO mapperDAOtoVO(CocktailDAO value) {
        CocktailVO item = new CocktailVO();

        item.setAuthor(com.example.dhernandez.vidvintage.entity.mapper.AuthorMapper.mapperDAOtoVO(value.getAuthor()));
        item.setTags(value.getTags());
        item.setUrlPhoto(value.getUrlPhoto());
        item.setLikes(value.getLikes());
        item.setReceipt(value.getReceipt());
        item.setCocktailUrl(value.getCocktailUrl());
        item.setAlcoholic(value.getAlcoholic());
        item.setName(value.getName());
        item.setUrlVideo(value.getUrlVideo());
        item.setIngredients(com.example.dhernandez.vidvintage.entity.mapper.IngredientsMapper.mapperDAOtoVO(value.getIngredients()));

        return item;
    }
}
