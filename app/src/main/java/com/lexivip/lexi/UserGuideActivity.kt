package com.lexivip.lexi

import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.View
import com.basemodule.tools.*

import com.basemodule.ui.BaseActivity
import com.lexivip.lexi.user.login.UserProfileUtil
import com.lexivip.lexi.view.autoScrollViewpager.ViewPagerAdapter
import com.lexivip.lexi.welcome.WelcomeActivity
import kotlinx.android.synthetic.main.activity_user_guide.*

/**
 * @author lilin
 * created at 2016/4/18 16:10
 */
class UserGuideActivity : BaseActivity() {

    private val flag = false
    private val currentPosition: Int = 0
    private val mediaPlayer: MediaPlayer? = null
    private var empty: Boolean = false
    private val readBool: Boolean = false
    private val fromPage:String = ""
    override val layout: Int = R.layout.activity_user_guide



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    private fun initGuide() {
        scrollableView.visibility = View.VISIBLE
        val list: MutableList<Int> = ArrayList()
        list.add(R.mipmap.guide)
        list.add(R.mipmap.guide1)
        list.add(R.mipmap.guide2)
        list.add(R.mipmap.guide3)
        scrollableView.setAdapter(ViewPagerAdapter(this, list))
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
