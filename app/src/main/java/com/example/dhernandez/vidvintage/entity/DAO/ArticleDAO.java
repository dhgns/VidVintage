package com.example.dhernandez.vidvintage.entity.DAO;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by dhernandez on 19/12/2018.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDAO extends RealmObject {

    @PrimaryKey
    String url;

    @Required
    String title;

    @Required
    String description;

    String author;

    @Required
    String articleImageURL;

}

