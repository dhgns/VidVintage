package com.example.dhernandez.vidvintage.Utils;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by dhernandez on 20/08/2018.
 */

public class ScreenUtils {

    public static void hideSoftKeyboard(Activity activity){
        try{
            hideSoftKeyboard(activity.getCurrentFocus());
        }catch (Exception e){}
    }

    public static void hideSoftKeyboard(View view) {
        try {
            if (view != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }catch (Exception e){}
    }

}
