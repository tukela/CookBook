package com.mycook.myapp.entity;

/**
 * Created by honglei on 2014/7/11.
 */
public class UrlUtils {

    public static int SUBCOOKCLASS=1;
    public static int SHOWLIST=2;
    public static int SHOWDetails=3;
    private static  String BaseURL="http://api.yi18.net/cook/";
    public static  String getCookClassURL(String  id){
        return BaseURL+"cookclass?id="+id;
    }


    public static String getCookListURL(int page,int limit,String type,String  id)
    {
        return BaseURL+"list?page="+page+"&limit="+limit+"&type="+type+"&id="+id;
    }
    public static String getCookBookURL(String  id){
        return BaseURL+"show?id="+id;
    }
    public static String getSearchURL(String keyword,int page,int limit){
        return BaseURL+"search?keyword="+keyword+"&limit="+limit+"&page="+page;
    }

}
