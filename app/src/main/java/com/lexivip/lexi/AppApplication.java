package com.lexivip.lexi;

import android.app.Application;
import android.content.Context;
//import com.qiniu.android.storage.UploadManager;
//import com.squareup.leakcanary.LeakCanary;
//import com.thn.erp.common.constant.THNZone;
//import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import com.basemodule.tools.BaseModuleContext;
import com.basemodule.tools.LogUtil;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.example.myapp.MyEventBusIndex;
import com.qiniu.android.common.AutoZone;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UploadManager;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.squareup.leakcanary.LeakCanary;


import org.greenrobot.eventbus.EventBus;

public class AppApplication extends Application {
    @GlideModule
    public final class MyAppGlideModule extends AppGlideModule {
        @Override
        public void applyOptions(Context context, GlideBuilder builder) {
            MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context)
                    .setMemoryCacheScreens(2)
                    .build();
            builder.setMemoryCache(new LruResourceCache(calculator.getMemoryCacheSize()));

            int bitmapPoolSizeBytes = 1024 * 1024 * 30; // 30mb
            builder.setBitmapPool(new LruBitmapPool(bitmapPoolSizeBytes));

            int diskCacheSizeBytes = 1024*1024*100; // 100 MB

            builder.setDiskCache(new InternalCacheDiskCacheFactory(context, diskCacheSizeBytes));
        }
    }

    private static Application instance;

    public static Application getContext() {
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
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
//        MultiDex.install(base);
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

}
