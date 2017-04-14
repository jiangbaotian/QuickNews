package com.example.ying.quicknews;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity for displaying a single news detail
 */
public class NewsDetailActivity extends AppCompatActivity {
    Menu menu;
    NewsBase news;
    DBTools dbTools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        dbTools = new DBTools(this);
        Intent intent = getIntent();
        news = (NewsBase) intent.getSerializableExtra("news");

        // if there is internet
        if(HttpTools.hasInternet(this)){
            TextView title = (TextView) findViewById(R.id.title);
            WebView webView = (WebView)findViewById(R.id.webView);
            WebSettings settings = webView.getSettings();
            webView.setWebChromeClient(new WebChromeClient());
            webView.setWebViewClient(new WebViewClient());
            //webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl(news.url);
        }else{
            Toast.makeText(getApplicationContext(),
                    "Sorry, you don't have internet access.", Toast.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail, menu);
        refreshMenu();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         switch (item.getItemId()) {
            case R.id.save:
                if(addToDB()){
                    Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,"Fail to save",Toast.LENGTH_SHORT).show();
                }
                refreshMenu();
                return true;
            case R.id.remove:
                if (removeFromDB()){
                    Toast.makeText(this,"Removed",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,"Fail to remove",Toast.LENGTH_SHORT).show();
                }

                refreshMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * refresh option menu when clicking
     */
    private void refreshMenu(){
        menu.clear();
        if(!dbTools.isSaved(news)){
            menu.add(0, R.id.save, 0, "save").setIcon(R.drawable.menu_save).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }else{
            menu.add(0, R.id.remove, 0, "remove").setIcon(R.drawable.menu_saved).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
    }

    /**
     * Add this news to SQLite
     * @return - true if success
     */
    private boolean addToDB(){
        return dbTools.add(news);
    }

    /**
     *  Remove this news from SQLite
     */
    private boolean removeFromDB(){
        return dbTools.delete(news);
    }
}
