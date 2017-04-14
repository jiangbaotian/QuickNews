package com.example.ying.quicknews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ying on 2017/2/26.
 * Saved news adapter
 */

public class NewsAdapter extends BaseAdapter {

    // News manager that holds many news
    private NewsManager newsManager;
    private LayoutInflater inflater;

    public NewsAdapter(Context context, NewsManager newsManager){
        this.newsManager = newsManager;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return newsManager.getSize();
    }

    @Override
    public Object getItem(int position) {
        return newsManager.getNewsBase(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NewsView newsView = new NewsView();
        if(convertView == null){
            convertView = inflater.inflate(R.layout.news,null);
            newsView.title = (TextView) convertView.findViewById(R.id.title);
            newsView.author = (TextView) convertView.findViewById(R.id.author);
            newsView.publishedAt = (TextView) convertView.findViewById(R.id.publishedAt);
            newsView.image = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(newsView);
        }else {
            newsView = (NewsView) convertView.getTag();
        }
        String url = newsManager.getNewsBase(position).image;
        newsView.image.setTag(url);

        new ImageSetter(newsView.image).execute(url);
        newsView.title.setText(newsManager.getNewsBase(position).title);
        newsView.author.setText(newsManager.getNewsBase(position).author);
        newsView.publishedAt.setText(newsManager.getNewsBase(position).publishedAt);

        return convertView;
    }

    private class NewsView{
        public TextView title;
        public TextView author;
        public TextView publishedAt;
        public ImageView image;
    }

}
