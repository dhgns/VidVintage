package com.example.dhernandez.vidvintage.Utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dhernandez.vidvintage.R;
import com.example.dhernandez.vidvintage.entity.Cocktail;

import java.util.List;

/**
 * Created by dhernandez on 03/09/2018.
 */

class TagsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> tags;

    public void setList(List<String> tags) {
        this.tags = tags;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View articleView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_item_list, parent, false);

        RecyclerView.ViewHolder tagHolder = new TagViewHolder(articleView);

        return tagHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String tag = tags.get(position);

        ((TagViewHolder)holder).tag.setText(tag);
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

    private class TagViewHolder extends RecyclerView.ViewHolder {
        public TextView tag;

        public TagViewHolder(View tagView) {
            super(tagView);

            tag = tagView.findViewById(R.id.tag_item_list_text);
        }
    }

}
