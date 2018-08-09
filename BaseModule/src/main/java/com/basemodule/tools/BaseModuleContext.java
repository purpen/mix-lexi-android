package com.basemodule.tools;

import android.app.Application;
import android.content.Context;

/**
 * Created by Stephen on 2018/3/5 16:46
 * Email: 895745843@qq.com
 */

public class BaseModuleContext {
    private static Application mContext;

    private BaseModuleContext() {
    }

    public static void init(Application application) {
        mContext = application;
    }

    public static Context getContext(){
        return mContext;
    }
}
