package com.example.dhernandez.vidvintage.entity.mapper;

import com.example.dhernandez.vidvintage.entity.DAO.IngredientDAO;
import com.example.dhernandez.vidvintage.entity.Ingredient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhernandez on 08/12/2018.
 */

public class IngredientsMapper {

    public static List<IngredientDAO> mapperVOtoDAO(List<Ingredient> ingredients) {
        List<IngredientDAO> result = new ArrayList<>();

        for (Ingredient aux : ingredients) {
            result.add(mapperVOtoDAO(aux));
        }

        return result;
    }

    public static IngredientDAO mapperVOtoDAO(Ingredient ingredient) {
        IngredientDAO ingredientDAO = new IngredientDAO();

        ingredientDAO.setName(ingredient.getName());
        ingredientDAO.setType(ingredient.getType());

        return ingredientDAO;
    }

    public static List<Ingredient> mapperDAOtoVO(List<IngredientDAO> ingredients) {
        List<Ingredient> result = new ArrayList<>();

        for (IngredientDAO aux : ingredients) {
            result.add(mapperDAOtoVO(aux));
        }

        return result;
    }

    public static Ingredient mapperDAOtoVO(IngredientDAO ingredientDAO) {
        Ingredient ingredient = new Ingredient();

        ingredient.setName(ingredientDAO.getName());
        ingredient.setType(ingredientDAO.getType());

        return ingredient;
    }

}


