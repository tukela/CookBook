package com.mycook.myapp.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.mycook.myapp.utils.DownLoadImage;
import com.mycook.myapp.utils.HttpClient;
import com.mycook.myapp.utils.JsonFormat;
import com.mycook.myapp.R;
import com.mycook.myapp.entity.UrlUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by honglei on 2014/7/11.
 */
public class ShowDetailsActivity extends Activity {
    String URL="http://api.yi18.net/cook/show?id=2";
    private  String ImageURL="http://www.yi18.net/";
    Map<String,Object> map=new HashMap<String, Object>(0);
    private ImageView imgeview;
    private TextView textview1;
    private TextView textview2;
    private TextView textview3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String  id= getIntent().getExtras().getString("id");
        URL= UrlUtils.getCookBookURL(id);
        setContentView(R.layout.showcb);
        imgeview=(ImageView)findViewById(R.id.image);
        textview1=(TextView)findViewById(R.id.textview1);
        textview2=(TextView)findViewById(R.id.textview2);
        textview3= (TextView)findViewById(R.id.textview3);
        new MyAsync().execute();
    }
    class  MyAsync extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            String string = HttpClient.getDateByGet(URL);
            Log.i("", "MyAsyncGet########" + string);
            return string;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("", "1234567890-==--=-=-=-=" + s);
            map= JsonFormat.getFormatMapByYi18(s);
            String Pd=ImageURL+map.get("img").toString();
            new DownLoadImage(imgeview).execute(Pd);
            textview1.setText(map.get("tag").toString());
            textview2.setText(map.get("food").toString());
            textview3.setText(map.get("message").toString());
        }
    }
}
