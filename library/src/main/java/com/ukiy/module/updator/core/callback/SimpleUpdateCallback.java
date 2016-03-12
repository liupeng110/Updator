package com.ukiy.module.updator.core.callback;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.WindowManager;

import com.ukiy.module.updator.core.Notice;
import com.ukiy.module.updator.core.UpdateInfo;
import com.ukiy.module.updator.core.Updator;

/**
 * Created by UKIY on 2016/1/8.
 */
public class SimpleUpdateCallback implements UpdateCallback {

    @Override
    public void onOptUpdate(final Context context, UpdateInfo versionInfo, String cur_version) {
        Notice.showInfo(context, "发现新版本" + versionInfo.new_version);
        AlertDialog dialog = new AlertDialog.Builder(context).setTitle("发现新版本")
                .setMessage("当前版本：" + cur_version + "\n" +
                        "最新版本：" + versionInfo.new_version + "\n" +
                        "更新内容：" + versionInfo.change_log)
                .setNegativeButton("暂不升级", null)
                .setPositiveButton("下载更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Notice.showInfo(context,"后台开启下载");
                        Updator.downloadUpdateInfo(context, new SimpleDownloadCallback());
                    }
                }).create();
        dialog.getWindow().setType((WindowManager.LayoutParams.TYPE_SYSTEM_ALERT));
        dialog.show();
    }

    @Override
    public void onMustUpdate(Context context, UpdateInfo versionInfo, String cur_version) {
        Notice.showInfo(context, "当前版本" + cur_version + "低于最低版本" + versionInfo.min_version);
    }

    @Override
    public void onAlreadyNewest(Context context, UpdateInfo versionInfo, String cur_version) {
//        if (!silent) {
        Notice.showInfo(context, "已经是最新版本 ,当前版本号：" + cur_version);
//        }
    }

}
