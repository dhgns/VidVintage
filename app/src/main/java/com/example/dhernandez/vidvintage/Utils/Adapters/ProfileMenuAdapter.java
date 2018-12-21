package com.example.dhernandez.vidvintage.Utils.Adapters;

import android.app.Application;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.dhernandez.vidvintage.BuildConfig;
import com.example.dhernandez.vidvintage.R;

import butterknife.Unbinder;

/**
 * Created by dhernandez on 18/12/2018.
 */

public class ProfileMenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int USER = 0;
    private static final int LOCAL = 1;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder;
        switch (viewType) {
            case LOCAL:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.profile_local_menu, parent, false);
                viewHolder = new UserMenuHolder(view);
                break;
            case USER:
            default:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.profile_user_menu, parent, false);
                viewHolder = new UserMenuHolder(view);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if(BuildConfig.FLAVOR.equals(BuildConfig.LOCAL))
            return 3;
        else
            return 2;
    }

    public class UserMenuHolder extends RecyclerView.ViewHolder {
        private ImageButton articlesSection;
        private ImageButton cocktailsSection;

        public UserMenuHolder(View itemView) {
            super(itemView);
        }
    }

    public class LocalMenuHolder extends RecyclerView.ViewHolder {
        private ImageButton articlesSection;
        private ImageButton cocktailsSection;
        private ImageButton newCocktailSection;

        public LocalMenuHolder(View itemView) {
            super(itemView);

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (BuildConfig.FLAVOR.equals(BuildConfig.LOCAL))
            return LOCAL;
        else
            return USER;

    }

}
