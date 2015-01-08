package com.mycook.myapp.activity;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.*;
import android.widget.*;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.mycook.myapp.utils.JsonFormat;
import com.mycook.myapp.R;
import com.mycook.myapp.entity.CookClass;
import com.mycook.myapp.entity.CookFactory;
import com.mycook.myapp.fragment.ChildCookListFragment;
import com.mycook.myapp.fragment.ShowDetailsFragment;
import com.mycook.myapp.fragment.ShowListFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by tukela on 2014/11/6.
 */
public class HomeActivity extends FragmentActivity implements AdapterView.OnItemClickListener {
    String URL="http://api.yi18.net/cook/cookclass";
    List<CookClass> listclass =new ArrayList<CookClass>(0);
    private ListView listView;
    private FragmentTransaction ft;
    public int SUBCOOKCLASS=1;
    public int SHOWLIST=2;
    public int SHOWDetails=3;
    ChildCookListFragment childCookListFragment=new ChildCookListFragment(this);
    ShowListFragment showListFragment=new ShowListFragment(this);
    ShowDetailsFragment showDetailsFragment=new ShowDetailsFragment(this);
    private DrawerLayout mDrawer;
    private int showType=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        ActionBar actionBar=getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        listView= (ListView) findViewById(R.id.left_drawer);
        mDrawer = ((DrawerLayout) findViewById(R.id.drawer_layout));
        listView.setOnItemClickListener(this);
        ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame,childCookListFragment).commit();
        setdatas();
        check();
    }

    private void check(){
       Calendar calendar=Calendar.getInstance();
        if (calendar.get(calendar.YEAR)>2015){
            this.finish();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawer.openDrawer(listView);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void  refreshFragment(String id,int type){
//        public int SUBCOOKCLASS=1;
            switch (type){
                case 0:
                    if (childCookListFragment.isAdded())
                    {
                        childCookListFragment.doResetDatas(id);
                        break;
                    }
                    ft=getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame,childCookListFragment).commit();
                    childCookListFragment.doResetDatas(id);
                    showType=0;
                    break;
                case 2://        public int SHOWLIST=2;
                    ft=getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame,showListFragment).addToBackStack(null).commit();
                    showListFragment.doResetDatas(id);
                    showType=2;
                    break;
                case 3://        public int SHOWDetails=3;
                    ft=getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame,showDetailsFragment).addToBackStack(null).commit();
                    showDetailsFragment.doResetDatas(id);
                    showType=3;
                    break;
                case 4:

                    break;
            }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CookClass cookClass=listclass.get(position);
        refreshFragment(cookClass.getId(),0);
     //   childCookListFragment.doResetDatas(cookClass.getId());
        mDrawer.closeDrawer(listView);

    }
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    private void setdatas(){
//        new Handler().post(new Runnable() {
//            @Override
//            public void run() {
//                getdataFromDB();
//            }
//        });
        new Thread(){
            @Override
            public void run() {
                super.run();
                getdataFromDB();
            }
        }.start();
    }
    private void getdataFromDB(){
        final DbUtils db=DbUtils.create(HomeActivity.this,"cookclass");
        try {
            listclass= db.findAll(Selector.from(CookClass.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (listclass!=null&&listclass.size()>0)
        {
            listView.setAdapter(MyAdapter);
          //  Toast.makeText(this,"ss",Toast.LENGTH_SHORT).show();

        }else {
            HttpUtils client=new HttpUtils();
            client.send(HttpRequest.HttpMethod.GET, URL,new RequestCallBack<String >() {
                @Override
                public void onSuccess(ResponseInfo<String > objectResponseInfo) {
                    listclass= CookFactory.getCookClass(JsonFormat.getFormatByYi18(objectResponseInfo.result));
                    try {
                        db.saveAll(listclass);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    listView.setAdapter(MyAdapter);
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
            return listclass.size()>0?listclass.size():0;
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
            TextView textView= (TextView) LayoutInflater.from(HomeActivity.this)
                    .inflate(android.R.layout.simple_list_item_1, null);
            textView.setText(listclass.get(position).getName());
            return textView;
        }
    };
    interface  IresetDatas{
        public  void  doResetDatas(String id);
    }


    @Override
    public void onBackPressed() {
        if (showType==0){
            new AlertDialog.Builder(this)
                    .setMessage("您确定要退出订单系统吗？")
                    .setTitle("正在退出")
                    .setPositiveButton("确认",
                            new android.content.DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                    //System.exit(1);
//                        android.os.Process.killProcess(android.os.Process.myPid());
                                }
                            })
                    .setNegativeButton("取消",null)
                    .create()
                    .show();
        }else {
            super.onBackPressed();
        }

    }

}
