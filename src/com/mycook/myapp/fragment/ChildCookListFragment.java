package com.mycook.myapp.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.mycook.myapp.utils.JsonFormat;
import com.mycook.myapp.activity.HomeActivity;
import com.mycook.myapp.entity.CookClass;
import com.mycook.myapp.entity.CookFactory;
import com.mycook.myapp.entity.UrlUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by honglei on 2014/7/11.
 */
public class ChildCookListFragment extends ListFragment{
    private Context context;
    String TestURL="http://api.yi18.net/cook/cookclass?id=1";
    String URL="http://api.yi18.net/cook/cookclass?id=";
    List<CookClass> listclass =new ArrayList<CookClass>(0);
    private Handler mhandelr;
    private HomeActivity activity;

    public ChildCookListFragment(Context context){
        this.context=context;
    }
    public ChildCookListFragment(){
        super();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity= (HomeActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDatas(TestURL);

    }
    public void doResetDatas(String id){
        URL= UrlUtils.getCookClassURL(id);
        mhandelr=new Handler();
        mhandelr.post(new Runnable() {
        @Override
        public void run() {
            setDatas(URL);
        }
    });
}

    private void setDatas(final String Url) {
       // Toast.makeText(activity,Url,Toast.LENGTH_SHORT).show();

        final SharedPreferences sharedPreferences=context.getSharedPreferences("cook",Context.MODE_PRIVATE);
        String str=sharedPreferences.getString(Url,"");
        if (str.equals("")){
           listclass.clear();
        }else {
            listclass.clear();
            listclass = CookFactory.getCookClass(JsonFormat.getFormatByYi18(str));
        }
        if (listclass != null && listclass.size() > 0) {
            setListAdapter(MyAdapter);
        } else {
            HttpUtils client = new HttpUtils();
            client.send(HttpRequest.HttpMethod.GET, Url, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> objectResponseInfo) {
                    listclass = CookFactory.getCookClass(JsonFormat.getFormatByYi18(objectResponseInfo.result));
                    try {
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString(Url,objectResponseInfo.result).commit();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    setListAdapter(MyAdapter);
                }

                @Override
                public void onFailure(HttpException e, String s) {

                }
            });
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
            TextView textView= (TextView) LayoutInflater.from(getActivity())
                    .inflate(android.R.layout.simple_list_item_1, null);
            textView.setText(listclass.get(position).getName());
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.refreshFragment(listclass.get(position).getId(), UrlUtils.SHOWLIST);
                }
            });
            return textView;
        }
    };
}
