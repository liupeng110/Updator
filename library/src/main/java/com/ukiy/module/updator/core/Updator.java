package com.ukiy.module.updator.core;

import android.content.Context;
import android.content.Intent;

import com.ukiy.module.updator.core.callback.DownloadCallBack;
import com.ukiy.module.updator.core.callback.SimpleUpdateCallback;
import com.ukiy.module.updator.core.callback.UpdateCallback;

/**
 * Created by ukiy on 15/12/24.
 * 用于检查版本更新情况
 */
public class Updator {

    static String url;
    static String cur_version;
    static boolean isInit = false;

    private Updator() {
    }

    public static void init(String cur_version, String url) {
        Updator.url = url;
        Updator.cur_version = cur_version;
        isInit = true;
    }

    public static String getCur_version() {
        return Updator.cur_version;
    }

    public static void getUpdateInfo(Context context, UpdateCallback updateCallback) {
        Intent intent = new Intent(context, UpdateService.class);
        intent.putExtra(UpdateService.TYPE, UpdateService.GET_UPDATE_INFO);
        intent.putExtra(UpdateService.UPDATE_CALLBACK, updateCallback);
        context.startService(intent);
    }

    public static void downloadUpdateInfo(Context context, DownloadCallBack downloadCallBack) {
        Intent intent = new Intent(context, UpdateService.class);
        intent.putExtra(UpdateService.TYPE, UpdateService.DOWNLOAD_UPDATE_FILE);
        intent.putExtra(UpdateService.DOWNLOAD_CALLBACK, downloadCallBack);
        context.startService(intent);
    }

    public static void start(Context context) {
        if (!isInit) {
            throw new RuntimeException("Updator is not yet init!");
        } else {
            getUpdateInfo(context, new SimpleUpdateCallback());
        }
    }

}
