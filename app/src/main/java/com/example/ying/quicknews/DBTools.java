package com.example.ying.quicknews;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by ying on 2017/3/15.
 * DBTools contains all the tools to access SQLite including initial, drop, get, add, delete
 */

public class DBTools extends SQLiteOpenHelper {
    SQLiteDatabase db;


    /**
     * Column information
     */
    private static class Entity implements BaseColumns {
        public static final String TABLE_NAME = "news";
        public static final String TITLE = "title";
        public static final String URL = "url";
        public static final String IMAGE_URL = "imageurl";
        public static final String AUTHOR = "author";
        public static final String RELEASE_DATE = "releasedate";
    }

    //Table name
    private static final String DB_NAME = "SavedNews.db";
    //Table version
    private static final int DB_VERSION = 3;

    /**
     * Command for creating table
     */
    private static final String SQL_CREATE_TABLE_QUERY =
            "CREATE TABLE " + Entity.TABLE_NAME + " ("
                    + Entity._ID + " INTEGER PRIMARY KEY,"
                    + Entity.TITLE + " TEXT,"
                    + Entity.URL + " TEXT,"
                    + Entity.IMAGE_URL + " TEXT,"
                    + Entity.AUTHOR + " TEXT,"
                    + Entity.RELEASE_DATE + " TEXT )";

    /**
     * Command for dropping table
     */
    private static final String SQL_DELETE_QUERY = "DROP TABLE IF EXISTS " + Entity.TABLE_NAME;

    /**
     * constructor
     *
     * @param context
     */
    public DBTools(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DB creating", SQL_CREATE_TABLE_QUERY);
        db.execSQL(SQL_CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_QUERY);
        onCreate(db);
    }

    /**
     * Add new row to SQLite
     *
     * @param news - NewsBase
     * @return true if success
     */
    public boolean add(NewsBase news) {
        ContentValues newRow = new ContentValues();

        newRow.put(Entity.TITLE, news.title);
        newRow.put(Entity.URL, news.url);
        newRow.put(Entity.IMAGE_URL, news.image);
        newRow.put(Entity.AUTHOR, news.author);
        newRow.put(Entity.RELEASE_DATE, news.publishedAt);

        long newRowID = db.insert(Entity.TABLE_NAME, null, newRow);
        Log.d("DB insert", "" + newRowID);
        return newRowID > 0;
    }

    /**
     * Check if the news is saved in SQLite
     * @param news - NewsBase - single news
     * @return true - if it is saved in SQLite
     */
    public boolean isSaved(NewsBase news) {
        String[] urls = {news.url};
        Cursor cursor = db.query(Entity.TABLE_NAME, null, Entity.URL + "=?",urls, null, null, null);

        return cursor.getCount() > 0;
    }

    /**
     * Get all the news that saved in SQLite
     * @return NewsManager - list of news
     */
    public NewsManager getAll(){
        NewsManager newsManager = new NewsManager();

        Cursor cursor = db.query(Entity.TABLE_NAME, null, null, null, null, null, null);
        boolean hasMoreData = cursor.moveToFirst();
        while (hasMoreData) {
            String title = cursor.getString(cursor.getColumnIndex(Entity.TITLE));
            String url = cursor.getString(cursor.getColumnIndex(Entity.URL));
            String image = cursor.getString(cursor.getColumnIndex(Entity.IMAGE_URL));
            String author = cursor.getString(cursor.getColumnIndex(Entity.AUTHOR));
            String rd = cursor.getString(cursor.getColumnIndex(Entity.RELEASE_DATE));
            newsManager.add(new NewsBase(title, url, image, author, rd));

            hasMoreData = cursor.moveToNext();
        }

        return newsManager;
    }

    /**
     * Delete a row
     * @param news - NewsBase - single news
     * @return - true - if the news is deleted
     */
    public boolean delete(NewsBase news) {
        String whereClause = Entity.URL + "=?";
        String[] whereArgs = {news.url};
        db.delete(Entity.TABLE_NAME,whereClause,whereArgs);

        return !isSaved(news);
    }
}