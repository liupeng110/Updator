package com.ukiy.module.updator.core;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.ukiy.module.updator.core.callback.DownloadCallback;
import com.ukiy.module.updator.core.callback.UpdateCallback;
import com.ukiy.module.updator.core.utils.MainPoster;
import com.ukiy.module.updator.core.utils.Notice;

import java.io.File;

public class UpdateService extends IntentService {

    private static UpdateInfo updateInfo;
    private static String updateFileName = "update.apk";
    final static int GET_UPDATE_INFO = 0;
    final static int DOWNLOAD_UPDATE_FILE = 1;
    final static String TYPE = "TYPE";
    final static String DOWNLOAD_CALLBACK = "DOWNLOAD_CALLBACK";
    final static String UPDATE_CALLBACK = "UPDATE_CALLBACK";

    public UpdateService() {
        super("UpdateService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int type = intent.getIntExtra(TYPE, -1);
        switch (type) {
            case GET_UPDATE_INFO:
                String json = P.getUpdataInfo(Updator.url);
                updateInfo = UpdateInfo.parse(json);
                if (updateInfo == null) {
                    Notice.showFailure(this, "json解析错误");
                    break;
                }
                UpdateCallback updateCallback = (UpdateCallback) intent.getSerializableExtra(UPDATE_CALLBACK);
                versionCheck(updateInfo, updateCallback);
                break;
            case DOWNLOAD_UPDATE_FILE:
                if (updateInfo == null) break;
                File updateFile = new File(getUpdatePath(), Updator.cur_version + updateFileName);
                DownloadCallback downloadCallback = (DownloadCallback) intent.getSerializableExtra(DOWNLOAD_CALLBACK);
                if (!P.downoadFile(false, updateInfo.url, updateFile, downloadCallback, this)) {
                    Notice.showFailure(this, "下载错误");
                    break;
                }
                if (updateFile.exists()) {
                    install();
                }
                break;
            default:
                break;
        }
    }

    //获取下载文件存放的临时文件夹
    private String getUpdatePath() {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = getExternalCacheDir().getPath();
        } else {
            cachePath = getCacheDir().getPath();
        }
        return cachePath;
    }

    //安装新版本
    private void install() {
        final File f = new File(getUpdatePath(), Updator.cur_version + updateFileName);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(f),
                "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    /*
    * 执行更新的动作
    * 0-----1(min)----2(new)
    * switch(cur){
    *   case [0,1):强制更新; break;
    *   case [1,2):选择更新; break;
    *   case 2:已经是最新; break;
    * }
    * */
    private void versionCheck(final UpdateInfo updateInfo, final UpdateCallback updateCallback) {
        if (updateInfo == null) return;
        final int r1 = compareVersion(Updator.cur_version, updateInfo.min_version);
        final int r2 = compareVersion(Updator.cur_version, updateInfo.new_version);
        MainPoster.async(new Runnable() {
            @Override
            public void run() {
                if (updateCallback == null) return;
                if (r1 < 0) {
                    updateCallback.onMustUpdate(UpdateService.this, updateInfo, Updator.cur_version);
                } else if (r2 < 0) {
                    updateCallback.onOptUpdate(UpdateService.this, updateInfo, Updator.cur_version);
                } else {
                    updateCallback.onAlreadyNewest(UpdateService.this, updateInfo, Updator.cur_version);
                }
            }
        });
    }

    //比较版本信息
    private static int compareVersion(String ver1, String ver2) {
        String[] ver1Array = ver1.split("\\.");
        String[] ver2Array = ver2.split("\\.");
        int shorter = ver1Array.length < ver2Array.length ? ver1Array.length : ver2Array.length;
        for (int i = 0; i < shorter; i++) {
            if (Integer.parseInt(ver1Array[i]) > Integer.parseInt(ver2Array[i])) {
                return 1;
            } else if (Integer.parseInt(ver1Array[i]) < Integer.parseInt(ver2Array[i])) {
                return -1;
            }
        }
        if (ver1Array.length < ver2Array.length) {
            return -1;
        } else if (ver1Array.length > ver2Array.length) {
            return 1;
        }
        return 0;
    }

}
