package com.mycook.myapp.utils;

import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by honglei on 2014/7/10.
 */
public class HttpClient {
    public static String  getDateByGet(String URL){
        String result="-1";
        // HttpGet连接对象
        HttpGet httpRequest=new HttpGet(URL);

        try {
            //取得HttpClient对象
            DefaultHttpClient httpclient = new DefaultHttpClient();
            //请求HttpCLient，取得HttpResponse
            HttpResponse httpResponse=httpclient.execute(httpRequest);
//请求成功
            if(httpResponse.getStatusLine().getStatusCode()== HttpStatus.SC_OK)
            {
//取得返回的字符串

                result=EntityUtils.toString(httpResponse.getEntity());
            }
        } catch (ClientProtocolException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            return result;
        }

    }
}