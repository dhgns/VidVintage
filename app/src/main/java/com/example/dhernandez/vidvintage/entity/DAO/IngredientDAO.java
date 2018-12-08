package com.example.dhernandez.vidvintage.entity.DAO;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import lombok.Data;

/**
 * Created by dhernandez on 03/09/2018.
 */
@Data
public class IngredientDAO extends RealmObject {
    @SerializedName("name")
    String name;
    @SerializedName("tpye")
    String type;
}
