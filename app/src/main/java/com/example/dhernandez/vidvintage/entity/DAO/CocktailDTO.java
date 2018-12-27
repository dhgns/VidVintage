package com.example.dhernandez.vidvintage.entity.DAO;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmField;
import io.realm.annotations.Required;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by dhernandez on 03/09/2018.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CocktailDTO {

    @SerializedName("name")
    String name;
    @SerializedName("description")
    String description;
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

    @PrimaryKey
    String id;

}
