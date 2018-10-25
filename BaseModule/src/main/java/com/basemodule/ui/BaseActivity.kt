package com.basemodule.ui

import android.app.AppOpsManager
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.basemodule.tools.AppManager
import com.basemodule.tools.GlideUtil
import com.basemodule.tools.LogUtil

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
        getIntentData()
        //        ShareSDK.initSDK(this);·
        LogUtil.e("id:"+layout.toLong())
        setContentView(layout)
        initView()
        installListener()
        requestNet()
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
