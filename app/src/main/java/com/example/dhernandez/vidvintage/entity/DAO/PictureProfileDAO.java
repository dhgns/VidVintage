package com.example.dhernandez.vidvintage.entity.DAO;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by dhernandez on 26/12/2018.
 */

@Data
@Getter
@Setter
public class PictureProfileDAO extends RealmObject {
    @PrimaryKey
    String id;

    @Required
    String data;
}
