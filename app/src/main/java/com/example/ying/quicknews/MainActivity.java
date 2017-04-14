package com.example.ying.quicknews;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    /**
     * urls for get news data from internet
     * category / url   ex. sport/http://.....
     */
    private final String urlSet[] = {
            "all/https://newsapi.org/v1/articles?source=google-news&sortBy=top&apiKey=264d87b281b44b47b8c44b6c0a06a40c",
            "economic/https://newsapi.org/v1/articles?source=the-economist&sortBy=top&apiKey=264d87b281b44b47b8c44b6c0a06a40c",
            "entertainment/https://newsapi.org/v1/articles?source=entertainment-weekly&sortBy=top&apiKey=264d87b281b44b47b8c44b6c0a06a40c",
            "sport/https://newsapi.org/v1/articles?source=fox-sports&sortBy=top&apiKey=264d87b281b44b47b8c44b6c0a06a40c",
            "tech/https://newsapi.org/v1/articles?source=ars-technica&sortBy=top&apiKey=264d87b281b44b47b8c44b6c0a06a40c"
    };

    // A list of news managers
    private HashMap<String, NewsManager> newsManagerSet = new HashMap<String, NewsManager>();

    // View UI on main activity
    ListView listView;
    private RecyclerView recyclerViewAll;
    private RecyclerView recyclerViewEconomic;
    private RecyclerView recyclerViewEntertainment;
    private RecyclerView recyclerViewSport;
    private RecyclerView recyclerViewTech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start();
    }

    void start(){
        if(HttpTools.hasInternet(this)){
            findViews();
            new downloadAsyncTask().execute(urlSet);
        }else{
            Toast.makeText(getApplicationContext(),
                    "Sorry, you don't have internet access.", Toast.LENGTH_LONG)
                    .show();
        }
    }

    class downloadAsyncTask extends AsyncTask<String, Void, HashMap<String, NewsManager>> {
        @Override
        protected HashMap<String, NewsManager> doInBackground(String... params) {
            for(String ss:params){
                String s[] = ss.split("/",2);
                String category = s[0];
                String url = s[1];
                String jasonString = HttpTools.getNewsJasonString(url);
                NewsManager newsManager = new NewsManager(jasonString);
                newsManagerSet.put(category,newsManager);
            }
            return newsManagerSet;
        }

        @Override
        protected void onPostExecute(HashMap<String, NewsManager> newsManagerSet) {
            super.onPostExecute(newsManagerSet);
            setViews(newsManagerSet);
        }
    }

    /**
     * Finds all views on main activity and set to private variable
     */
    private void findViews(){
        listView = (ListView) findViewById(R.id.listView);

        recyclerViewAll = (RecyclerView) findViewById(R.id.horizontalRecyclerViewAll);
        recyclerViewAll.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewAll.setLayoutManager(layoutManager);

        //Economic
        recyclerViewEconomic = (RecyclerView) findViewById(R.id.horizontalRecyclerViewEconomic);
        recyclerViewEconomic.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewEconomic.setLayoutManager(layoutManager);

        //Entertainment
        recyclerViewEntertainment = (RecyclerView) findViewById(R.id.horizontalRecyclerViewEntertainment);
        recyclerViewEntertainment.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewEntertainment.setLayoutManager(layoutManager);

        //Sport
        recyclerViewSport = (RecyclerView) findViewById(R.id.horizontalRecyclerViewSport);
        recyclerViewSport.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewSport.setLayoutManager(layoutManager);

        //Tech
        recyclerViewTech = (RecyclerView) findViewById(R.id.horizontalRecyclerViewTech);
        recyclerViewTech.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewTech.setLayoutManager(layoutManager);
    }

    /**
     * Set adapters for private views
     * @param newsManagerSet - Data for adapter
     */
    private void setViews(HashMap<String, NewsManager> newsManagerSet){
        //All
        TextView feature = (TextView)findViewById(R.id.feature);
        feature.setText("Feature");
        NewsManager newsManager = newsManagerSet.get("all");
        RecyclerView.Adapter adapter = new HorizontalListAdapterLarge(newsManager);
        recyclerViewAll.setAdapter(adapter);

        //Sport
        TextView sport = (TextView)findViewById(R.id.sport);
        sport.setText("Sport");
        newsManager = newsManagerSet.get("sport");
        adapter = new HorizontalListAdapterSmall(newsManager);
        recyclerViewSport.setAdapter(adapter);

        //Tech
        TextView tech = (TextView)findViewById(R.id.tech);
        tech.setText("Technology");
        newsManager = newsManagerSet.get("tech");
        adapter = new HorizontalListAdapterSmall(newsManager);
        recyclerViewTech.setAdapter(adapter);

        //Entertament
        TextView entertainment = (TextView)findViewById(R.id.entertainment);
        entertainment.setText("Entertainment");
        newsManager = newsManagerSet.get("entertainment");
        adapter = new HorizontalListAdapterSmall(newsManager);
        recyclerViewEntertainment.setAdapter(adapter);

        //Economic
        TextView economic = (TextView)findViewById(R.id.economic);
        economic.setText("Economic");
        newsManager = newsManagerSet.get("economic");
        adapter = new HorizontalListAdapterSmall(newsManager);
        recyclerViewEconomic.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_list, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                start();
                return true;
            case R.id.savedList:
                startActivity(new Intent(this,SavedViewerActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
