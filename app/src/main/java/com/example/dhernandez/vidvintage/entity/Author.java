package com.example.dhernandez.vidvintage.entity;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by dhernandez on 03/09/2018.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    String username;
    String name;
    String lastName;
    String email;
}
