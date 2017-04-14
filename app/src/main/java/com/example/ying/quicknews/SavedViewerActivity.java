package com.example.ying.quicknews;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Activity for showing a list of saved news
 */
public class SavedViewerActivity extends AppCompatActivity {
    NewsManager newsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_viewer);

        setNewsManager();
        setView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setNewsManager();
        setView();
    }

    private void setNewsManager(){
        DBTools dbTools = new DBTools(this);
        newsManager = dbTools.getAll();
    }

    private void setView(){
        //listView
        ListView listView = (ListView) findViewById(R.id.listView);
        NewsAdapter newsAdapter = new NewsAdapter(this ,newsManager);
        listView.setAdapter(newsAdapter);
        listView.setOnItemClickListener(itemClickHandler);
    }

    private AdapterView.OnItemClickListener itemClickHandler = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            NewsBase news = newsManager.getNewsBase(position);
            Intent newsDetail = new Intent(SavedViewerActivity.this, NewsDetailActivity.class);
            newsDetail.putExtra("news",news);
            startActivity(newsDetail);
        }
    };

}
