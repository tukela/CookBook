package com.mycook.myapp.utils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by ShinChven on 2014/9/29.
 */
public class IMEUtil {
    public static void hideIME(Activity window) {
        try {
            InputMethodManager imm = (InputMethodManager) window.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(window.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
    }


}
