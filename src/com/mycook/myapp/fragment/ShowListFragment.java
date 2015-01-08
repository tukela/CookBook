package com.mycook.myapp.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.mycook.myapp.*;
import com.mycook.myapp.activity.HomeActivity;
import com.mycook.myapp.app.MyApplication;
import com.mycook.myapp.entity.CookFactory;
import com.mycook.myapp.entity.CookList;
import com.mycook.myapp.entity.UrlUtils;
import com.mycook.myapp.utils.JsonFormat;
import com.mycook.myapp.utils.ViewHolder;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.security.AlgorithmParameterGenerator;
import java.util.*;


public class ShowListFragment extends Fragment implements AdapterView.OnItemClickListener{
    private final Context context;
    private  String URL="http://api.yi18.net/cook/list?page=1&limit=20";
    private Map<String,Object> map=new HashMap<String, Object>(0);
    private GridView gridview;
    private List<CookList> listclass=new ArrayList<CookList>(0);
    private HomeActivity activity;
    private Handler mhandelr;
    private String Id="";
    private int page=1;
    private int limit=30;
    private String type="id";
    private DisplayImageOptions options;
    private MyApplication app;

    public ShowListFragment(Context context){
       this.context=context;
   }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity= (HomeActivity) activity;
        app= (MyApplication) getActivity().getApplication();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_main,null);
        gridview =(GridView)view.findViewById(R.id.gridview);
        gridview.setAdapter(MyBaseAdapter);
        gridview.setOnItemClickListener(this);
        if (listclass.size()>0)
        {
            MyBaseAdapter.notifyDataSetChanged();
        }
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        mhandelr=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what==0)
                {
                    MyBaseAdapter.notifyDataSetChanged();
                }

            }
        };
    }

    public void doResetDatas(String id){
        URL= UrlUtils.getCookListURL(page,limit,type,id);
//        mhandelr=new Handler();
//        mhandelr.post(new Runnable() {
//            @Override
//            public void run() {
//                setDatas(URL);
//            }
//        });
        new Thread(){
            @Override
            public void run() {
                super.run();
                setDatas(URL);
            }
        }.start();
    }
    private void setDatas(final String Url) {
        final SharedPreferences sharedPreferences=context.getSharedPreferences("cook", Context.MODE_PRIVATE);
        String str=sharedPreferences.getString(Url,"");
        if (str.equals("")){
            HttpUtils client = new HttpUtils();
            client.send(HttpRequest.HttpMethod.GET, Url, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> objectResponseInfo) {
                    String str=objectResponseInfo.result;
                    Log.i("Log",str);
                    listclass.clear();
                    listclass = CookFactory.getCookList(JsonFormat.getFormatByYi18(str));
                    try {
                        if (listclass.size()>0){
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString(Url,objectResponseInfo.result).commit();
                        }else {
                            Toast.makeText(activity,"暂无此功效食谱",Toast.LENGTH_SHORT).show();
                            return;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mhandelr.sendEmptyMessage(0);
                    // MyBaseAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    Log.i("Log",s);
                    Toast.makeText(activity,"获取数据失败"+s,Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            listclass.clear();
            listclass = CookFactory.getCookList(JsonFormat.getFormatByYi18(str));
            //MyBaseAdapter.notifyDataSetChanged();
            mhandelr.sendEmptyMessage(0);
        }
    }




    BaseAdapter MyBaseAdapter =new  BaseAdapter(){

        @Override
        public int getCount() {
            return listclass.size()>0?listclass.size():0;
        }

        @Override
        public Object getItem(int position) {
            return listclass.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final CookList cookList=listclass.get(position);
            if (convertView==null){
                convertView=LayoutInflater.from(context).inflate(R.layout.show_item, null);
            }
            ImageView imageView= ViewHolder.get(convertView, R.id.image);
            TextView textView= ViewHolder.get(convertView,R.id.tv);
            ImageLoader.getInstance().displayImage(cookList.getImg(), imageView);
        //    new DownLoadImage(imageView).execute(cookList.getImg());
            textView.setText(cookList.getName());
            return convertView;
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CookList cookList=listclass.get(position);
        activity.refreshFragment(cookList.getId(),UrlUtils.SHOWDetails);
    }
}
