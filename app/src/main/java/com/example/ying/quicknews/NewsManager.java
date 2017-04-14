package com.example.ying.quicknews;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;
import java.util.concurrent.Exchanger;

/**
 * Created by ying on 2017/2/26.
 */

public class NewsManager{

    private Vector<NewsBase> newsList;

    NewsManager(){
        newsList = new Vector<NewsBase>();
    }

    NewsManager(String JasonString){
        newsList = new Vector<NewsBase>();
        add(JasonString);
    }


    public void add(String JasonString){
        try {
            JSONObject jsonObject = new JSONObject(JasonString);
            JSONArray jsonArray = jsonObject.getJSONArray("articles");
            for(int i = 0; i < jsonArray.length(); i++){
                String title = "";
                String url = "";
                String image = "";
                String author = "";
                String publishedAt = "";

                try{
                    jsonObject = jsonArray.getJSONObject(i);
                    title = jsonObject.getString("title");
                    url = jsonObject.getString("url");
                    image = jsonObject.getString("urlToImage");
                    author = jsonObject.getString("author");
                    publishedAt = jsonObject.getString("publishedAt");
                }catch (Exception e){ }
                NewsBase newsBase = new NewsBase(title,url,image,author,publishedAt);

                newsList.add(newsBase);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            Log.d("News list size","" + newsList.size());
        }
    }

    public void add(NewsBase newsBase){
        if(newsBase.completed)
            newsList.add(newsBase);
    }


    public int getSize(){
        return newsList.size();
    }

    public NewsBase getNewsBase(int idx){
        return newsList.get(idx);
    }

}
