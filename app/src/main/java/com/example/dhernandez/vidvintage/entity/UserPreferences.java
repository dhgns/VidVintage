package com.example.dhernandez.vidvintage.entity;

import com.example.dhernandez.vidvintage.Utils.Constants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Created by dhernandez on 07/09/2018.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPreferences {
    private Boolean activeSession;
    private Boolean saveSession;
    private Constants.Themes theme;
    private Boolean fullScreen;
}
