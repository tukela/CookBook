package com.mycook.myapp.utils;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

/**
 * Created by honglei on 2014/7/10.
 */
public class JsonFormat {
    public static List<Map<String, Object>>  getFormatFromJsonArray(String JsonData) {
        List<Map<String, Object>> list =new ArrayList<Map<String, Object>>();
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(JsonData);
            for(int i = 0 ; i < jsonArray.length() ; i ++){
                JSONObject item = jsonArray.getJSONObject(i);
                Iterator<String> keyIter= item.keys();
                String key;
                Object value ;
                Map<String, Object> valueMap = new HashMap<String, Object>();
                while (keyIter.hasNext()) {
                    key = keyIter.next();
                    value =item.get(key);
                    valueMap.put(key, value);
                }
                list.add(valueMap);
            }return  list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, Object>  getFormatFromJsonObject(String JsonData) {
        Map<String, Object> valueMap = new HashMap<String, Object>(0);
        JSONObject obj=null;
        try {
            obj=new JSONObject(JsonData);
            Iterator<String> keyIter= obj.keys();
            String key;
            Object value ;
            while (keyIter.hasNext()) {
                key = keyIter.next();
                value = obj.get(key);
                if (key.toString().equals("yi18")){
                    value=(Object)obj.getJSONArray(key);
                }
                valueMap.put(key, value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            return valueMap;

        }
    }
    public static List<Map<String, Object>>  getFormatByYi18(String JsonData) {
        List<Map<String, Object>> list =new ArrayList<Map<String, Object>>();
        JSONObject obj=null;
        try {
            obj=new JSONObject(JsonData);
            Iterator<String> keyIter= obj.keys();
            String key;
            Object value ;
            while (keyIter.hasNext()) {
                key = keyIter.next();
                value = obj.get(key);
                if (key.toString().equals("yi18")){
                    String str=obj.getJSONArray(key).toString();
                    list=getFormatFromJsonArray(str);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            return list;

        }
    }
    public static Map<String, Object>  getFormatMapByYi18(String JsonData) {
        JsonData=JsonData.replace("{\"success\": true, \"yi18\":","");
        JsonData=JsonData.replace("}}","}");
        Map<String, Object> valueMap = new HashMap<String, Object>(0);
        JSONObject obj=null;
        try {
            obj=new JSONObject(JsonData);
            Iterator<String> keyIter= obj.keys();
            String key;
            Object value ;
            while (keyIter.hasNext()) {
                key = keyIter.next();
                value = obj.get(key);
                valueMap.put(key, value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            return valueMap;
        }
    }
}
