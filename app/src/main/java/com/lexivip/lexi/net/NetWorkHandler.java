package com.lexivip.lexi.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.basemodule.ui.IDataSource;

import java.io.IOException;

/**
 * Created by Stephen on 2017/1/9 15:00
 * Email: 895745843@qq.com
 */

public class NetWorkHandler extends Handler {
    public static final int CALLBACK_SUCCESS = 200;
    public static final int CALLBACK_FAILURE = 400;
    public static final int AUTH_ERROR = 401;
    private IDataSource.HttpRequestCallBack callback;

    public NetWorkHandler(IDataSource.HttpRequestCallBack callback) {
        super(Looper.getMainLooper());
        this.callback = callback;
    }

    public NetWorkHandler(Context context, IDataSource.HttpRequestCallBack callback) {
        super(Looper.getMainLooper());
        this.callback = callback;
    }

    public void handleMessage(Message msg) {
        switch (msg.what) {
            case CALLBACK_SUCCESS:
                if (msg.obj instanceof String){
                    callback.onSuccess((String) msg.obj);
                }else if (msg.obj instanceof Bitmap){
                    callback.onSuccess((Bitmap) msg.obj);
                }

                break;
            case CALLBACK_FAILURE:
                callback.onFailure((IOException) msg.obj);
                break;
        }
    }
}
