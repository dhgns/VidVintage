package com.example.dhernandez.vidvintage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by dhernandez on 30/08/2018.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleVO {

    private String articleImageURL;
    private String title;
    private String description;
    private String author;
    private String url;

}