package com.mycook.myapp.entity;

import android.graphics.Bitmap;

/**
 * Created by honglei on 2014/7/11.
 */
public class CookList {

    String  id="";//  long 食谱ID
    String img=null;//  图片
    String Name=""; // 食谱名称
    String  tag=""; // 食谱疗效主要功能
    String  bar=""; //  食品 主要的功能
    String  message="";// 食谱的主要做法
    String count="";   // int 浏览次数
    String food="";

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getBar() {
        return bar;
    }

    public void setBar(String bar) {
        this.bar = bar;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

}
