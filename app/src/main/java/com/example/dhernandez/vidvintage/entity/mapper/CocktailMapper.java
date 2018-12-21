package com.example.dhernandez.vidvintage.entity.mapper;

import com.example.dhernandez.vidvintage.entity.CocktailVO;
import com.example.dhernandez.vidvintage.entity.DAO.CocktailDAO;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;

/**
 * Created by dhernandez on 08/12/2018.
 */

public class CocktailMapper {

    public static CocktailDAO mapperVOtoDAO (CocktailVO cocktailVO){
        CocktailDAO cocktailDAO = new CocktailDAO();

        cocktailDAO.setAlcoholic(cocktailVO.getAlcoholic());
        cocktailDAO.setCocktailUrl(cocktailVO.getCocktailUrl());
        cocktailDAO.setTags(cocktailVO.getTags());
        cocktailDAO.setLikes(cocktailVO.getLikes());
        cocktailDAO.setReceipt(cocktailVO.getReceipt());
        cocktailDAO.setUrlPhoto(cocktailVO.getUrlPhoto());
        cocktailDAO.setUrlVideo(cocktailVO.getUrlVideo());
        cocktailDAO.setAuthor(com.example.dhernandez.vidvintage.entity.mapper.AuthorMapper.mapperVOtoDAO(cocktailVO.getAuthor()));
        cocktailDAO.setIngredients(com.example.dhernandez.vidvintage.entity.mapper.IngredientsMapper.mapperVOtoDAO(cocktailVO.getIngredients()));

        return cocktailDAO;

    }

    public static List<CocktailVO> mapperDAOtoVO(RealmResults<CocktailDAO> all) {
        List<CocktailVO> result = new ArrayList<>();

        for(CocktailDAO cocktailDAO : all){
            result.add(mapperDAOtoVO(cocktailDAO));
        }
        return result;
    }

    public static CocktailVO mapperDAOtoVO(CocktailDAO value){
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
