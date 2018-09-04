package com.example.dhernandez.vidvintage.entity;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by dhernandez on 03/09/2018.
 */
@Data
public class IngredientDAO {
    @SerializedName("name")
    String name;
    @SerializedName("tpye")
    String type;
}
