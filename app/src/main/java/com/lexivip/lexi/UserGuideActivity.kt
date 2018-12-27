package com.lexivip.lexi

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.View
import com.basemodule.tools.*

import com.basemodule.ui.BaseActivity
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.net.HttpRequest
import com.lexivip.lexi.net.URL
import com.lexivip.lexi.user.login.UserProfileUtil
import com.lexivip.lexi.view.autoScrollViewpager.ViewPagerAdapter
import com.lexivip.lexi.welcome.WelcomeActivity
import kotlinx.android.synthetic.main.activity_user_guide.*
import java.io.IOException

/**
 * @author lilin
 * created at 2016/4/18 16:10
 */
class UserGuideActivity : BaseActivity() {

    private var empty: Boolean = false
    override val layout: Int = R.layout.activity_user_guide


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        GlideUtil.loadImageAsDrawable("",this.window.decorView)
        if (!isTaskRoot) {
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && TextUtils.equals(Intent.ACTION_MAIN, intent.action)) {
                finish()
            }
        }

    }

    override fun getIntentData() {
        super.getIntentData()
//        if (intent!!.hasExtra(MineFragment::class.java!!.getSimpleName())) {
//            fromPage = intent!!.getStringExtra(MineFragment::class.java!!.getSimpleName())
//        }
        empty = TextUtils.isEmpty(SPUtil.read(Constants.GUIDE_TAG))
    }

    override fun initView() {
//        if (TextUtils.isEmpty(fromPage)) {
//        val options = BitmapFactory.Options()
//        val bitmap = BitmapFactory.decodeResource(resources,R.mipmap.welcome, options)
//        val scaleImage = ImageUtil.scaleImage(bitmap, ScreenUtil.getScreenWidth(), ScreenUtil.getScreenHeight())
//        imageView.setImageBitmap(scaleImage)
//        bitmap.recycle()
//        imageView.visibility = View.VISIBLE
        Handler().postDelayed({
            //                imageView.visibility = View.GONE
//                scaleImage.recycle()
            if (empty) {
                initGuide()
            } else {
                if (isTaskRoot) {
                    goMainPage()
                }
            }
        }, Constants.GUIDE_INTERVAL)
//        } else {
//            initGuide()
//        }
    }

//    override fun requestNet() {
//        val url = URL.BASE_URL + "market/guide/android"
//        HttpRequest.sendRequest(HttpRequest.GET, url, object : IDataSource.HttpRequestCallBack {
//            override fun onSuccess(json: String) {
//                val welcomeBean = JsonUtil.fromJson(json, WelcomeBean::class.java)
//                if (welcomeBean.success) {
//                    if (TextUtils.isEmpty(welcomeBean.data.small)) return
//                    imageView.visibility = View.VISIBLE
//                    GlideUtil.loadImageAsDrawable("",.window.decorView)
//                    GlideUtil.loadImageWithDimen(welcomeBean.data.small, imageView, ScreenUtil.getScreenWidth(), ScreenUtil.getScreenHeight(), R.mipmap.welcome, ImageSizeConfig.DEFAULT)
//                }
//            }
//
//            override fun onFailure(e: IOException) {
//                LogUtil.e("请求远程欢迎页失败")
//            }
//        })
//    }

    private fun initGuide() {
        scrollableView.visibility = View.VISIBLE
        val list: MutableList<Int> = ArrayList()
        list.add(R.mipmap.guide)
        list.add(R.mipmap.guide1)
        list.add(R.mipmap.guide2)
        list.add(R.mipmap.guide3)
        scrollableView.setAdapter(ViewPagerAdapter(this, list), list.size)
        SPUtil.write(Constants.GUIDE_TAG, Constants.GUIDE_TAG)
    }

    private fun goMainPage() {
        if (UserProfileUtil.isLogin()) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            startActivity(Intent(this, WelcomeActivity::class.java))
        }
        finish()
    }

}
