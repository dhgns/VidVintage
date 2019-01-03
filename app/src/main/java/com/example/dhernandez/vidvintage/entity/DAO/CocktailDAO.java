package com.example.dhernandez.vidvintage.entity.DAO;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by dhernandez on 21/12/2018.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CocktailDAO extends RealmObject{
    @Required
    @SerializedName("name")
    String name;
    @SerializedName("author")
    CocktailAuthorDAO author;
    @SerializedName("alcoholic")
    Boolean alcoholic;
    @SerializedName("tags")
    RealmList<String> tags;
    @SerializedName("ingredients")
    RealmList<IngredientDAO> ingredients;
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
    @SerializedName("description")
    String description;

    @PrimaryKey
    String id;
}
