package com.lexivip.lexi;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDexApplication;
import android.support.multidex.MultiDex;
//import com.qiniu.android.storage.UploadManager;
//import com.squareup.leakcanary.LeakCanary;
//import com.thn.erp.common.constant.THNZone;
//import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import com.basemodule.tools.AppManager;
import com.basemodule.tools.BaseModuleContext;
import com.basemodule.tools.LogUtil;

import com.example.myapp.MyEventBusIndex;
import com.qiniu.android.common.AutoZone;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UploadManager;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import com.squareup.leakcanary.LeakCanary;


import org.greenrobot.eventbus.EventBus;

public class AppApplication extends MultiDexApplication {


    private static Application instance;

    public static Application getContext() {
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(restartHandler);
        instance = this;
//        initPush();
        if (BuildConfig.LOG_DEBUG) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                return;
            }
            LeakCanary.install(this);
        }

        BaseModuleContext.init(this);
        //使用时才自动生成MyEventBusIndex
        EventBus.builder().addIndex(new MyEventBusIndex()).throwSubscriberException(BuildConfig.DEBUG).installDefaultEventBus();

        //umeng分享
        UMConfigure.init(this,"5a12384aa40fa3551f0001d1"
                ,"umeng",UMConfigure.DEVICE_TYPE_PHONE,"");
        PlatformConfig.setWeixin("wx777520ec6a61fff5", "a049e19a6f464e7d53ad28b4dbc905e2");
        //豆瓣RENREN平台目前只能在服务器端配置
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad","http://sns.whalecloud.com");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
    }


//    public void initPush() {
//        JPushInterface.setDebugMode(true);
//        JPushInterface.init(this);
//        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(this);
//        builder.statusBarDrawable = R.mipmap.icon_logo;
//        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL
//                | Notification.FLAG_SHOW_LIGHTS;  //设置为自动消失和呼吸灯闪烁
//        builder.notificationDefaults = Notification.DEFAULT_SOUND
//                | Notification.DEFAULT_VIBRATE
//                | Notification.DEFAULT_LIGHTS;  // 设置为铃声、震动、呼吸灯闪烁都要
//        JPushInterface.setPushNotificationBuilder(1, builder);
//        tempSharedPreference = getSharedPreferences(DataConstants.PUSH_STATUS, Context.MODE_PRIVATE);
//    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    private static class SingletonInstance {
        static Configuration config = new Configuration.Builder()
                .zone(AutoZone.autoZone)
                .build();

        private static final UploadManager INSTANCE = new UploadManager(config);
    }

    public static UploadManager getUploadManager() {
        return SingletonInstance.INSTANCE;
    }


    private Thread.UncaughtExceptionHandler restartHandler = new Thread.UncaughtExceptionHandler() {
        public void uncaughtException(Thread thread, Throwable ex) {
            restartApp();
        }
    };

    public void restartApp() {
        Intent intent = new Intent(getApplicationContext(), UserGuideActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        AppManager.getAppManager().appExit();
    }
}
