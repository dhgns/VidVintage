package com.example.dhernandez.vidvintage.entity.DAO;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Data;

/**
 * Created by dhernandez on 03/09/2018.
 */
@Data
public class CocktailAuthorDAO extends RealmObject {

    @PrimaryKey
    @SerializedName("username")
    String username;
    @SerializedName("firstName")
    String name;
    @SerializedName("lastName")
    String lastName;
    @SerializedName("email")
    String email;
}
