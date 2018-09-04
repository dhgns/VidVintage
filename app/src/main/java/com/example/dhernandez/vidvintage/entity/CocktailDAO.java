package com.example.dhernandez.vidvintage.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

/**
 * Created by dhernandez on 03/09/2018.
 */

@Data
public class CocktailDAO {
    @SerializedName("name")
    String name;
    @SerializedName("author")
    CocktailAuthorDAO author;
    @SerializedName("alcoholic")
    Boolean alcoholic;
    @SerializedName("tags")
    List<String> tags;
    @SerializedName("ingredients")
    List<IngredientDAO> ingredients;
    @SerializedName("receipt")
    String receipt;
    @SerializedName("video")
    String urlVideo;
    @SerializedName("photo")
    String urlPhoto;
    @SerializedName("likes")
    Integer likes;
    @SerializedName("url")
    String cocktailUrl;

}
