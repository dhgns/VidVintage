package com.example.dhernandez.vidvintage.Utils;

/**
 * Created by dhernandez on 30/08/2018.
 */


import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.example.dhernandez.vidvintage.R;

/**
 * Created by dhernandez on 29/08/2018.
 */

public class CustomImageView extends AppCompatImageView {
    public CustomImageView(Context context) {
        super(context);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setBackgroundResource(int resId) {
        super.setBackgroundResource(R.color.transparent);
    }

    @Override
    public void setBackgroundColor(int color) {
        super.setBackgroundColor(getResources().getColor(R.color.transparent));
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}