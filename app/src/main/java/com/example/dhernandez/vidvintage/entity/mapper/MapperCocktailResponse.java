package com.example.dhernandez.vidvintage.entity.mapper;

import com.example.dhernandez.vidvintage.entity.Author;
import com.example.dhernandez.vidvintage.entity.CocktailVO;
import com.example.dhernandez.vidvintage.entity.DAO.CocktailDTO;
import com.example.dhernandez.vidvintage.entity.Ingredient;
import com.example.dhernandez.vidvintage.entity.DAO.IngredientDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhernandez on 03/09/2018.
 */

public class MapperCocktailResponse {
    public static List<CocktailVO> CocktailDAOtoCocktail(List<CocktailDTO> value) {
        List<CocktailVO> aux = new ArrayList<>();
        if (value != null) {
            for (int i = 0; i < value.size(); i++) {
                aux.add(CocktailDAOtoCocktail(value.get(i)));
            }
        }
        return aux;
    }

    private static CocktailVO CocktailDAOtoCocktail(CocktailDTO value) {
        CocktailVO aux = new CocktailVO();

        if(value.getAlcoholic() != null)
            aux.setAlcoholic(value.getAlcoholic());
        if(value.getAuthor() != null){
            Author author = new Author();

            author.setName(value.getAuthor().getName());
            author.setEmail(value.getAuthor().getEmail());
            author.setLastName(value.getAuthor().getLastName());
            author.setUsername(value.getAuthor().getUsername());
            aux.setAuthor(author);
        }
        if(value.getCocktailUrl() != null)
            aux.setAlcoholic(value.getAlcoholic());
        if(value.getIngredients() != null) {
            List<Ingredient> ingredients = new ArrayList<>();
            for(IngredientDAO i : value.getIngredients())
                ingredients.add(new Ingredient(i.getName(), i.getType()));
            aux.setIngredients(ingredients);
        }
        if(value.getLikes() != null)
            aux.setLikes(value.getLikes());
        if(value.getTags() != null)
            aux.setTags(value.getTags());
        if(value.getUrlPhoto() != null)
            aux.setUrlPhoto(value.getUrlPhoto());
        if(value.getUrlVideo() != null)
            aux.setUrlVideo(value.getUrlVideo());

        return aux;
    }
}