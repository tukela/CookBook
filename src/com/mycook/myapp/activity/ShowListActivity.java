package com.mycook.myapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.mycook.myapp.utils.DownLoadImage;
import com.mycook.myapp.utils.HttpClient;
import com.mycook.myapp.utils.JsonFormat;
import com.mycook.myapp.R;
import com.mycook.myapp.entity.CookFactory;
import com.mycook.myapp.entity.CookList;
import com.mycook.myapp.entity.UrlUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ShowListActivity extends Activity {
    private  String URL="http://api.yi18.net/cook/list?page=1&limit=20";
    private Map<String,Object> map=new HashMap<String, Object>(0);
    private List<CookList> lists= new ArrayList<CookList>(0);
    private GridView gridview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String  id= getIntent().getExtras().getString("id");
        URL= UrlUtils.getCookListURL(1, 20, "id", id);
        gridview =(GridView)findViewById(R.id.gridview);
        MyAsync myAsync=new MyAsync();
        myAsync.execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(ShowListActivity.this, CookBookListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    class  MyAsync extends AsyncTask <String, Integer, String>{
        @Override
        protected String doInBackground(String... params) {
            String string = HttpClient.getDateByGet(URL);
            Log.i("","MyAsyncGet########"+string);
            return string;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("", "dddddddddd" + s);
            map= JsonFormat.getFormatFromJsonObject(s);
            String ss=map.get("yi18").toString();
            lists= CookFactory.getCookList(JsonFormat.getFormatFromJsonArray(ss));
            gridview.setAdapter(new MyBaseAdapter(lists));

        }
    }




    class MyBaseAdapter extends BaseAdapter{
        List<CookList> lists=null;
        public MyBaseAdapter(List<CookList> lists){
            this.lists=lists;
        }
        @Override
        public int getCount() {
            return lists.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final CookList cookList=lists.get(position);
            LinearLayout linearLayout= (LinearLayout) LayoutInflater.from(ShowListActivity.this).inflate(R.layout.show_item, null);
            ImageView imageView= (ImageView) linearLayout.findViewById(R.id.image);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(ShowListActivity.this,ShowDetailsActivity.class);
                    intent.putExtra("id", cookList.getId());
                    startActivity(intent);
                }
            });
            TextView textView= (TextView) linearLayout.findViewById(R.id.tv);
//            imageView.setImageBitmap(cookList.getBitmap());
            new DownLoadImage(imageView).execute(cookList.getImg());
            textView.setText(cookList.getName());
            return linearLayout;
        }
    }
}
