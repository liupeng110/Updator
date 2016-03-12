package com.ukiy.module.updator.core;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by ukiy on 16/1/24.
 */
public class MainPoster {

    private static Handler handler = new Handler(Looper.getMainLooper());

    public static void async(Runnable r) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            r.run();
        } else {
            handler.post(r);
        }
    }
}
