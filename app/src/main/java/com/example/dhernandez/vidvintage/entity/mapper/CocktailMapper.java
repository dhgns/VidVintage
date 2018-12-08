package com.example.dhernandez.vidvintage.entity.mapper;

import com.example.dhernandez.vidvintage.entity.Cocktail;
import com.example.dhernandez.vidvintage.entity.DAO.CocktailDAO;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;

/**
 * Created by dhernandez on 08/12/2018.
 */

public class CocktailMapper {

    public static CocktailDAO mapperVOtoDAO (Cocktail cocktail){
        CocktailDAO cocktailDAO = new CocktailDAO();

        cocktailDAO.setAlcoholic(cocktail.getAlcoholic());
        cocktailDAO.setCocktailUrl(cocktail.getCocktailUrl());
        cocktailDAO.setTags(cocktail.getTags());
        cocktailDAO.setLikes(cocktail.getLikes());
        cocktailDAO.setReceipt(cocktail.getReceipt());
        cocktailDAO.setUrlPhoto(cocktail.getUrlPhoto());
        cocktailDAO.setUrlVideo(cocktail.getUrlVideo());
        cocktailDAO.setAuthor(com.example.dhernandez.vidvintage.entity.mapper.AuthorMapper.mapperVOtoDAO(cocktail.getAuthor()));
        cocktailDAO.setIngredients(com.example.dhernandez.vidvintage.entity.mapper.IngredientsMapper.mapperVOtoDAO(cocktail.getIngredients()));

        return cocktailDAO;

    }

    public static List<Cocktail> mapperDAOtoVO(RealmResults<CocktailDAO> all) {
        List<Cocktail> result = new ArrayList<>();

        for(CocktailDAO cocktailDAO : all){
            result.add(mapperDAOtoVO(cocktailDAO));
        }
        return result;
    }

    public static Cocktail mapperDAOtoVO(CocktailDAO value){
        Cocktail item = new Cocktail();

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
