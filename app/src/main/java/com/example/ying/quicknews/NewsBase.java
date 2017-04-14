package com.example.ying.quicknews;

import java.io.Serializable;

/**
 * Created by ying on 2017/2/26.
 * A class for holding a single news information
 */

public class NewsBase  implements Serializable {

    // news title
    public String title = "";

    // news detail url
    public String url = "";

    // news image url
    public String image = "";

    //author
    public String author = "";

    // release date
    public String publishedAt = "";

    // if title, url, image is not empty, it is true
    // If it is not completed,this news will not be displayed
    public boolean completed = true;

    /**
     * Constructor
     * @param title - news title
     * @param url - detail url
     * @param image - image url
     * @param author - author
     * @param publishedAt - release date
     */
    NewsBase(String title, String url, String image, String author, String publishedAt){
        if(!title.isEmpty() && !url.isEmpty() && !image.isEmpty()){
            this.title = title;
            this.url = url;
            this.image = image;
            this.author = convertAuthor(author);
            this.publishedAt = converPublishAt(publishedAt);
        }else{
            completed = false;
        }
    }

    /**
     * Convert to shorter author
     * @param s - String - Author
     * @return - String - Converted author
     */
    String convertAuthor(String s){
        String b[] = s.split("/");
        s = b[b.length-1];
        if(s.equals("null"))
            s="Unknown";
        return s;
    }

    /**
     * Convert to shorter release date
     * @param s - String - release date
     * @return - String - converted release date
     */
    String converPublishAt(String s){
        if(s.equals("null"))
            return "Unknown";

        if (s.length() > 10)
            s = s.substring(0, 10);
        try{
            if(Integer.valueOf(s.split("-")[0]) < 2016)
                s = "Unknown";
        }catch (Exception e){
            s = "Unknown";
        }
        return s;
    }

}
