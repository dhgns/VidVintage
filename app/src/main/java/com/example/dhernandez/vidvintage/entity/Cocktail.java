package com.example.dhernandez.vidvintage.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

/**
 * Created by dhernandez on 03/09/2018.
 */

@Data
public class Cocktail {

    String name;
    Author author;
    Boolean alcoholic;
    List<String> tags;
    List<Ingredient> ingredients;
    String receipt;
    String urlVideo;
    String urlPhoto;
    Integer likes;
    String cocktailUrl;
    String description;

}
