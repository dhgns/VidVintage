package com.example.dhernandez.vidvintage.entity;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by dhernandez on 03/09/2018.
 */
@Data
public class CocktailAuthorDAO {
    @SerializedName("username")
    String username;
    @SerializedName("firstName")
    String name;
    @SerializedName("lastName")
    String lastName;
    @SerializedName("email")
    String email;
}
