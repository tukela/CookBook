package com.mycook.myapp.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.domob.android.ads.AdEventListener;
import cn.domob.android.ads.AdManager;
import cn.domob.android.ads.AdView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.mycook.myapp.utils.JsonFormat;
import com.mycook.myapp.R;
import com.mycook.myapp.app.MyApplication;
import com.mycook.myapp.entity.CookFactory;
import com.mycook.myapp.entity.CookList;
import com.mycook.myapp.entity.UrlUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by happy_000 on 2014/11/14.
 */
public class ShowDetailsFragment extends Fragment {
    private final Context context;
    private ImageView imgeview;
    private TextView textview1;
    private TextView textview2;
    private TextView textview3;
    private String  id;
    String URL="";
    private CookList listbook;
    private Handler mhandelr;
    private MyApplication app;
    private RelativeLayout mAdContainer;
    private AdView mAdview;

    public ShowDetailsFragment(Context context){
        this.context=context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
       app= (MyApplication) activity.getApplication();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.showcb,null);
        imgeview=(ImageView)view.findViewById(R.id.image);
        textview1=(TextView)view.findViewById(R.id.textview1);
        textview2=(TextView)view.findViewById(R.id.textview2);
        textview3= (TextView)view.findViewById(R.id.textview3);
      /*  多盟*/
                mAdContainer = (RelativeLayout)view. findViewById(R.id.adcontainer);
        // Create ad view
        mAdview = new AdView(getActivity(), MyApplication.PUBLISHER_ID,MyApplication.InlinePPID);
        mAdview.setKeyword("game");
        mAdview.setUserGender("male");
        mAdview.setUserBirthdayStr("2000-08-08");
        mAdview.setUserPostcode("123456");
        mAdview.setAdEventListener(new AdEventListener() {
            @Override
            public void onAdOverlayPresented(AdView adView) {
                Log.i("DomobSDKDemo", "overlayPresented");
            }
            @Override
            public void onAdOverlayDismissed(AdView adView) {
                Log.i("DomobSDKDemo", "Overrided be dismissed");
            }
            @Override
            public void onAdClicked(AdView arg0) {
                Log.i("DomobSDKDemo", "onDomobAdClicked");
            }
            @Override
            public void onLeaveApplication(AdView arg0) {
                Log.i("DomobSDKDemo", "onDomobLeaveApplication");
            }
            @Override
            public Context onAdRequiresCurrentContext() {
                return getActivity();
            }
            @Override
            public void onAdFailed(AdView arg0, AdManager.ErrorCode arg1) {
                Log.i("DomobSDKDemo", "onDomobAdFailed");
            }
            @Override
            public void onEventAdReturned(AdView arg0) {
                Log.i("DomobSDKDemo", "onDomobAdReturned");
            }
        });
        RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.addRule(RelativeLayout.CENTER_HORIZONTAL);
        mAdview.setLayoutParams(layout);
        mAdContainer.addView(mAdview);
        /*  多盟-  end--*/
        return view;
    }
    private void setViewDatas(CookList listbook){
        textview1.setText(listbook.getTag());
        textview2.setText(listbook.getFood());
        textview3.setText(Html.fromHtml(listbook.getMessage()));
        ImageLoader.getInstance().displayImage(listbook.getImg(),imgeview);
    }
    public void doResetDatas(String id){
        URL = UrlUtils.getCookBookURL(id);
        mhandelr=new Handler();
        mhandelr.post(new Runnable() {
            @Override
            public void run() {
                getDataFormNet(URL);
            }
        });
    }
    public void getDataFormNet(final String Url) {
        final SharedPreferences sharedPreferences = context.getSharedPreferences("cook", Context.MODE_PRIVATE);
        String str = sharedPreferences.getString(Url, "");
        if (!str.equals("")) {
            Log.i("Log", str);
            listbook = CookFactory.getCookBook(JsonFormat.getFormatMapByYi18(str));
            setViewDatas(listbook);
        } else {
            HttpUtils client = new HttpUtils();
            client.send(HttpRequest.HttpMethod.GET, Url, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> objectResponseInfo) {
                    String str = objectResponseInfo.result;
                    Log.i("Log", str);
                    listbook = CookFactory.getCookBook(JsonFormat.getFormatMapByYi18(str));
                    setViewDatas(listbook);
                    try {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(Url, objectResponseInfo.result).commit();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(HttpException e, String s) {

                }
            });

        }
    }

}