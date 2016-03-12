package com.ukiy.module.updator.core;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by ukiy on 15/12/24.
 * 程序中出现的信息展示
 */
public class Notice {
    public static void showSuccess(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showFailure(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showInfo(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    //debug
    public static void d( String message) {
        if (true) {
//            Toast.makeText(App.get(), message, Toast.LENGTH_SHORT).show();
            Log.d("debugg", message);
        }
    }

}
