package com.example.dhernandez.vidvintage.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by dhernandez on 03/09/2018.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CocktailsMenuResponse {

    private List<CocktailVO> cocktailVOS;
    private ErrorComm errorComm;
}
