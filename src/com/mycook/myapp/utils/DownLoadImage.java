package com.mycook.myapp.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by honglei on 2014/7/11.
 */
public  class DownLoadImage extends AsyncTask<String, Void, Bitmap> {
    ImageView imageView;

    public DownLoadImage(ImageView imageView) {
        // TODO Auto-generated constructor stub
        this.imageView = imageView;
    }

        @Override
        protected Bitmap doInBackground(String... urls) {
            // TODO Auto-generated method stub
            String url = urls[0];
            Bitmap bitmap=null;
            try {

                InputStream is = new java.net.URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(is);
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("test", e.getMessage());
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // TODO Auto-generated method stub
            imageView.setImageBitmap(result);
        }

}
