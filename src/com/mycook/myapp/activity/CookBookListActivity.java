package com.mycook.myapp.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.mycook.myapp.utils.HttpClient;
import com.mycook.myapp.utils.JsonFormat;
import com.mycook.myapp.entity.CookClass;
import com.mycook.myapp.entity.CookFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by honglei on 2014/7/11.
 */
public class CookBookListActivity extends ListActivity {
    String URL="http://api.yi18.net/cook/cookclass";
    List<CookClass> listclass =new ArrayList<CookClass>(0);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new MyAsync().execute();

    }

    class  MyAsync extends AsyncTask<String, Integer, String> {
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
            listclass= CookFactory.getCookClass(JsonFormat.getFormatByYi18(s));
            setListAdapter(MyAdapter);

        }
    }
    BaseAdapter MyAdapter=new BaseAdapter() {
        @Override
        public int getCount() {
            return listclass.size();
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            TextView textView= (TextView) LayoutInflater.from(CookBookListActivity.this)
                    .inflate(android.R.layout.simple_list_item_1, null);
            textView.setText(listclass.get(position).getName());
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(CookBookListActivity.this,ChildCookListActivity.class);
                    intent.putExtra("id",listclass.get(position).getId());
                   startActivity(intent);
                }
            });
            return textView;
        }
    };

}
