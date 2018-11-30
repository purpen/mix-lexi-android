package com.basemodule.ui

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.basemodule.tools.AppManager
import com.basemodule.tools.LogUtil
import com.umeng.message.PushAgent

/**
 * Created by lilin on 2017/6/16.
 */

abstract class BaseActivity : AppCompatActivity() {
    protected val TAG = javaClass.simpleName

    protected abstract val layout: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.getAppManager().addActivity(this)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        LogUtil.e("onCreate()$TAG")
        PushAgent.getInstance(this).onAppStart()
        getIntentData()
        //        ShareSDK.initSDK(this);·
        setContentView(layout)
        initView()
        requestNet()
        installListener()
    }



    open fun getIntentData() {}


    open fun initView() {

    }


    open fun installListener() {}

    open fun requestNet() {

    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onDestroy() {
        AppManager.getAppManager().stackRemoveActivity(this)
        super.onDestroy()
    }


}
