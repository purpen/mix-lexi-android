package com.lexivip.lexi;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.multidex.MultiDexApplication;
import android.support.multidex.MultiDex;
import android.widget.RemoteViews;
import android.widget.Toast;
//import com.qiniu.android.storage.UploadManager;
//import com.squareup.leakcanary.LeakCanary;
//import com.thn.erp.common.constant.THNZone;
//import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import com.basemodule.tools.AppManager;
import com.basemodule.tools.BaseModuleContext;
import com.basemodule.tools.Constants;
import com.basemodule.tools.LogUtil;

import com.example.myapp.MyEventBusIndex;
import com.lexivip.lexi.user.login.LoginActivity;
import com.qiniu.android.common.AutoZone;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UploadManager;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;


import org.android.agoo.huawei.HuaWeiRegister;
import org.android.agoo.xiaomi.MiPushRegistar;
import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;

public class AppApplication extends MultiDexApplication {


    private static Application instance;
    public static IWXAPI msgApi;
    private static Activity mActivity;
    private Handler handler;
    public static final String UPDATE_STATUS_ACTION = "com.umeng.message.example.action.UPDATE_STATUS";

    public static Application getContext() {
        return instance;
    }

    public static Activity getActivity(){
        return mActivity;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        if (!BuildConfig.LOG_DEBUG){
            Thread.setDefaultUncaughtExceptionHandler(restartHandler);
        }
        instance = this;

        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                mActivity=activity;
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
//        initPush();
//        if (BuildConfig.LOG_DEBUG) {
//            if (LeakCanary.isInAnalyzerProcess(this)) {
//                return;
//            }
//            LeakCanary.install(this);
//        }

        BaseModuleContext.init(this);
        //使用时才自动生成MyEventBusIndex
        EventBus.builder().addIndex(new MyEventBusIndex()).throwSubscriberException(BuildConfig.DEBUG).installDefaultEventBus();
        //Config.DEBUG = true;
        //umeng
        initUmeng();
    }

    private void initUmeng(){
        //umeng初始化
        UMConfigure.init(this,Constants.UMENG_ID
                ,"umeng",UMConfigure.DEVICE_TYPE_PHONE,Constants.UMENG_PUSH);
        //TODO umeng log记得关闭
        UMConfigure.setLogEnabled(true);
        //umeng统计
        MobclickAgent.setScenarioType(this,MobclickAgent.EScenarioType.E_UM_NORMAL);
        //umeng推送
        PushAgent mPushAgent = PushAgent.getInstance(this);
        handler = new Handler(getMainLooper());
        PushAgent.getInstance(this).onAppStart();
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                LogUtil.e("当前手机端的："+deviceToken);
                sendBroadcast(new Intent(UPDATE_STATUS_ACTION));
            }
            @Override
            public void onFailure(String s, String s1) {
            }
        });
        //推送点击状态栏到指定页面
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                LogUtil.e("umeng推送："+msg.custom);
                Intent intent=new Intent(context,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        };
        mPushAgent.setNotificationClickHandler(notificationClickHandler);
        //控制通知是否展示
        UmengMessageHandler messageHandler = new UmengMessageHandler() {
            /**
             * 通知的回调方法
             * @param context
             * @param msg
             */
            @Override
            public void dealWithNotificationMessage(Context context, UMessage msg) {
                //调用super则会走通知展示流程，不调用super则不展示通知
                super.dealWithNotificationMessage(context, msg);
            }
            @Override
            public Notification getNotification(Context context, UMessage msg) {
                switch (msg.builder_id) {
                    case 1:
                        Notification.Builder builder = new Notification.Builder(context);
//                        RemoteViews myNotificationView = new RemoteViews(context.getPackageName(),
//                                R.layout.notification_view);
//                        myNotificationView.setTextViewText(R.id.notification_title, msg.title);
//                        myNotificationView.setTextViewText(R.id.notification_text, msg.text);
//                        myNotificationView.setImageViewBitmap(R.id.notification_large_icon,
//                                getLargeIcon(context, msg));
//                        myNotificationView.setImageViewResource(R.id.notification_small_icon,
//                                getSmallIconId(context, msg));
//                        builder.setContent(myNotificationView)
//                                .setSmallIcon(getSmallIconId(context, msg))
//                                .setTicker(msg.ticker)
//                                .setAutoCancel(true);
                        return builder.getNotification();
                    default:
                        //默认为0，若填写的builder_id并不存在，也使用默认。
                        return super.getNotification(context, msg);
                }
            }
        };
        mPushAgent.setMessageHandler(messageHandler);

        //小米通道
        MiPushRegistar.register(this, Constants.MI_ID, Constants.MI_KEY);
        //华为通道
        HuaWeiRegister.register(this);
        //魅族通道
        //MeizuRegister.register(this, MEIZU_APPID, MEIZU_APPKEY);

        PlatformConfig.setWeixin(Constants.WX_ID, Constants.WX_key);
        PlatformConfig.setQQZone(Constants.QQ_ID,Constants.QQ_KEY);

        msgApi = WXAPIFactory.createWXAPI(this, Constants.WX_ID);
        msgApi.registerApp(Constants.WX_ID);

        //豆瓣RENREN平台目前只能在服务器端配置
        //PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad","http://sns.whalecloud.com");
        //PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
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
            ex.printStackTrace();
            restartApp();
        }
    };

    public void restartApp() {
//        Intent intent = new Intent(getApplicationContext(), UserGuideActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
        AppManager.getAppManager().appExit();
    }
}
