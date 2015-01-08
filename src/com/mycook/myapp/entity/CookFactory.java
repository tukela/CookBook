package com.mycook.myapp.entity;

import android.graphics.Bitmap;
import android.util.Log;
import com.lidroid.xutils.DbUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by honglei on 2014/7/11.
 */
public class CookFactory {

    public  static List<CookList> getCookList( List<Map<String, Object>> list){

        List<CookList> cookBookList=new ArrayList<CookList>(0);
        for (Map<String, Object> map : list)
            {
                CookList cookBook=getCookBook(map);
                cookBookList.add(cookBook);
            }
        return cookBookList;
    }
    public  static CookList getCookBook( Map<String, Object> map){
        String ImageURL="http://www.yi18.net/";
        CookList cookBook=new CookList();
        try {
            cookBook.setId(map.get("id").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            cookBook.setImg(ImageURL + map.get("img").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            cookBook.setName(map.get("name").toString());//
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            cookBook.setTag(map.get("tag").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            cookBook.setBar(map.get("bar").toString());//
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            cookBook.setMessage(map.get("message").toString());//
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            cookBook.setCount(map.get("count").toString());//
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            cookBook.setFood(map.get("food").toString());//
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cookBook;
    }

    public  static List<CookClass> getCookClass(List<Map<String, Object>> list){
        List<CookClass> listclass=new ArrayList<CookClass>(0);
        for (Map<String, Object> map : list) {
            CookClass cookClass=new CookClass();
            cookClass.setId(map.get("id").toString());
            cookClass.setName(map.get("name").toString());
            listclass.add(cookClass);
        }return listclass;
    }

}
