package com.ukiy.module.updator.core.callback;

import android.app.Service;
import android.content.Context;

import java.io.Serializable;

/**
 * Created by ukiy on 16/3/12.
 */
public interface DownloadCallback extends Serializable{
    void onProgress(Service service,int progress);
    void onDone(Service service);
    //占位，暂无实现
    void onFail(Service service);
}
