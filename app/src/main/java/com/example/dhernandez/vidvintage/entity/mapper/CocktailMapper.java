package com.example.dhernandez.vidvintage.entity.mapper;

import com.example.dhernandez.vidvintage.entity.CocktailVO;
import com.example.dhernandez.vidvintage.entity.DAO.CocktailDAO;
import com.example.dhernandez.vidvintage.entity.DAO.CocktailDTO;
import com.example.dhernandez.vidvintage.entity.DAO.IngredientDAO;
import com.example.dhernandez.vidvintage.entity.Ingredient;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by dhernandez on 08/12/2018.
 */

public class CocktailMapper {

    public static CocktailDTO mapperVOtoDTO(CocktailVO cocktailVO) {
        CocktailDTO cocktailDTO = new CocktailDTO();

        cocktailDTO.setName(cocktailVO.getName());
        cocktailDTO.setDescription(cocktailVO.getDescription());
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

    public static List<CocktailVO> mapperDTOtoVO(List<CocktailDTO> all) {
        List<CocktailVO> result = new ArrayList<>();

        for (CocktailDTO cocktailDTO : all) {
            result.add(mapperDTOtoVO(cocktailDTO));
        }
        return result;
    }

    public static CocktailVO mapperDTOtoVO(CocktailDTO value) {
        CocktailVO item = new CocktailVO();

        item.setAuthor(com.example.dhernandez.vidvintage.entity.mapper.AuthorMapper.mapperDAOtoVO(value.getAuthor()));
        item.setTags(value.getTags());
        item.setUrlPhoto(value.getUrlPhoto());
        item.setLikes(value.getLikes());
        item.setReceipt(value.getReceipt());
        item.setCocktailUrl(value.getCocktailUrl());
        item.setAlcoholic(value.getAlcoholic());
        item.setName(value.getName());
        item.setDescription(value.getDescription());
        item.setUrlVideo(value.getUrlVideo());
        item.setIngredients(com.example.dhernandez.vidvintage.entity.mapper.IngredientsMapper.mapperDAOtoVO(value.getIngredients()));

        return item;
    }

    public static CocktailDAO mapperVOtoDAO(CocktailVO value) {
        CocktailDAO item = new CocktailDAO();

        item.setAuthor(com.example.dhernandez.vidvintage.entity.mapper.AuthorMapper.mapperVOtoDAO(value.getAuthor()));
        RealmList<String> tags = new RealmList<>();
        for (String tag : value.getTags()) {
            tags.add(tag);
        }
        item.setTags(tags);
        item.setDescription(value.getDescription());
        item.setUrlPhoto(value.getUrlPhoto());
        item.setLikes(value.getLikes());
        item.setReceipt(value.getReceipt());
        item.setCocktailUrl(value.getCocktailUrl());
        item.setAlcoholic(value.getAlcoholic());
        item.setName(value.getName());
        item.setUrlVideo(value.getUrlVideo());
        RealmList<IngredientDAO> ingredients = new RealmList<>();
        for (Ingredient ingredient : value.getIngredients())
            ingredients.add(IngredientsMapper.mapperVOtoDAO(ingredient));
        item.setIngredients(ingredients);

        item.setId(value.getId());

        return item;
    }

    public static List<CocktailDAO> mapperVOtoDAO(List<CocktailVO> value) {
        List<CocktailDAO> result = new ArrayList<>();
        for (CocktailVO item : value) {
            result.add(mapperVOtoDAO(item));
        }
        return result;
    }

    public static CocktailVO mapperDAOtoVO(CocktailDAO value) {
        if(value == null)
            return null;

        CocktailVO item = new CocktailVO();

        if (value.getAuthor() != null)
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
        item.setDescription(value.getDescription());
        return item;
    }

    public static List<CocktailVO> mapperDAOtoVO(List<CocktailDAO> value) {
        List<CocktailVO> result = new ArrayList<>();

        for (CocktailDAO item : value) {
            result.add(mapperDAOtoVO(item));
        }
        return result;
    }
}
