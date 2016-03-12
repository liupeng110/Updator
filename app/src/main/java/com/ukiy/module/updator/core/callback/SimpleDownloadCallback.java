package com.ukiy.module.updator.core.callback;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.support.v4.app.NotificationManagerCompat;

import com.ukiy.module.updator.R;
import com.ukiy.module.updator.core.Notice;

/**
 * Created by ukiy on 16/3/12.
 */
public class SimpleDownloadCallback implements DownloadCallBack {

    @Override
    public void onProgress(Service service, int progress) {
        Notice.d(progress + "");
        notificationInit(service, progress);
    }

    @Override
    public void onDone(Service service) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(service);
        notificationManager.cancel(0);
    }

    private void notificationInit(Context context, int progress) {
        //通知栏内显示下载进度条
//        Intent intent = new Intent(context, test.class);//点击进度条，进入程序
//        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
//        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.notification_item);//通知栏中进度布局
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        Notification notification =
                new Notification.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("正在下载更新包")
                        .setProgress(100, progress, false)
                        .setContentText("进度：" + progress + "%")
                        .build();
        notificationManager.notify(0, notification);
    }
}
