package com.example.ying.quicknews;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

/**
 * Created by ying on 2017/2/28.
 * Adapter for RecyclerView on main activity - Large
 */

public class HorizontalListAdapterLarge extends RecyclerView.Adapter<HorizontalListAdapterLarge.ViewHolder> {

    // A manager that holds many news
    private NewsManager newsManager;

    public class ViewHolder extends RecyclerView.ViewHolder {

        //Views on the item view
        public TextView title;
        public TextView author;
        public TextView publishedAt;
        public ImageView image;
        public View v;
        public Context context;

        /**
         * Constructor for finding all views
         * @param v - View
         * @param context - Context
         */
        public ViewHolder(View v,Context context) {
            super(v);
            this.context = context;
            title = (TextView) v.findViewById(R.id.title);
            author = (TextView) v.findViewById(R.id.author);
            publishedAt = (TextView) v.findViewById(R.id.publishedAt);
            image = (ImageView) v.findViewById(R.id.image);
            this.v = v;
        }
    }

    /**
     * Constructor
     * @param newsManager - news manager that holds many news
     */
    public HorizontalListAdapterLarge(NewsManager newsManager) {
        this.newsManager = newsManager;
    }

    @Override
    public HorizontalListAdapterLarge.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_horizontal_list_large, parent, false);
        return new ViewHolder(v,parent.getContext());
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(newsManager.getNewsBase(position).url);
                Intent newsDetail = new Intent(holder.context, NewsDetailActivity.class);
                newsDetail.putExtra("news", newsManager.getNewsBase(position));
                holder.context.startActivity(newsDetail);
            }
        });

        // Set values to views
        holder.title.setText(newsManager.getNewsBase(position).title);
        holder.author.setText(newsManager.getNewsBase(position).author);
        holder.publishedAt.setText(newsManager.getNewsBase(position).publishedAt);
        String url = newsManager.getNewsBase(position).image;

        holder.image.setTag(url);
        new ImageSetter(holder.image).execute(url);
    }

    @Override
    public int getItemCount() {
        return newsManager.getSize();
    }
}
