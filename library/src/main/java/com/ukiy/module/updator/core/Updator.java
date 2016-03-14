package com.ukiy.module.updator.core;

import android.content.Context;
import android.content.Intent;

import com.ukiy.module.updator.core.callback.DownloadCallback;
import com.ukiy.module.updator.core.callback.SimpleDownloadCallback;
import com.ukiy.module.updator.core.callback.SimpleUpdateCallback;
import com.ukiy.module.updator.core.callback.UpdateCallback;

/**
 * Created by ukiy on 15/12/24.
 * 用于检查版本更新情况
 */
public class Updator {

    static String url;
    static String cur_version;
    private static boolean isInit = false;

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

    //向service发送检查更新的任务
    public static void getUpdateInfo(Context context, UpdateCallback updateCallback) {
        if (!isInit) {
            throw new RuntimeException("Updator is not yet init!");
        }
        Intent intent = new Intent(context, UpdateService.class);
        intent.putExtra(UpdateService.TYPE, UpdateService.GET_UPDATE_INFO);
        intent.putExtra(UpdateService.UPDATE_CALLBACK, updateCallback);
        context.startService(intent);
    }

    //向service发送下载并安装更新包的任务
    public static void downloadUpdateInfo(Context context, DownloadCallback downloadCallback) {
        if (!isInit) {
            throw new RuntimeException("Updator is not yet init!");
        }
        Intent intent = new Intent(context, UpdateService.class);
        intent.putExtra(UpdateService.TYPE, UpdateService.DOWNLOAD_UPDATE_FILE);
        intent.putExtra(UpdateService.DOWNLOAD_CALLBACK, downloadCallback);
        context.startService(intent);
    }

    public static void start(Context context) {
        if (!isInit) {
            throw new RuntimeException("Updator is not yet init!");
        }
        getUpdateInfo(context, new SimpleUpdateCallback(new SimpleDownloadCallback(), false));
    }

}
