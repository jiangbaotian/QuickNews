package com.example.ying.quicknews;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ying on 2017/2/27.
 * A tool for downloading image from provided url
 * and set to the view provided
 */

public class ImageSetter extends AsyncTask<String,Void,Bitmap> {

    //The image view need to set
    private ImageView imageView;
    // The url of the image
    private String url;

    public ImageSetter(ImageView imageView){
        this.imageView = imageView;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if(imageView.getTag().equals(url)) {
            imageView.setImageBitmap(bitmap);
        }
    }

    @Override
    protected Bitmap doInBackground(String[] params) {
        this.url = params[0];
        return download(url);
    }

    /**
     * Download the image
     * @param link - String url
     * @return - Bitmap - the downloaded image
     */
    private Bitmap download(String link){
        InputStream inputStream;
        Bitmap bitmap;
        try {
            URL url = new URL(link);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            bitmap = BitmapFactory.decodeStream(inputStream);
            httpURLConnection.disconnect();
            inputStream.close();
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
