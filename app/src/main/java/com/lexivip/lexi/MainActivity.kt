package com.lexivip.lexi

import android.content.Intent
import android.text.TextUtils
import android.view.KeyEvent
import android.widget.Toast
import com.basemodule.tools.AppManager
import com.basemodule.tools.LogUtil
import com.basemodule.tools.ScreenUtil
import com.basemodule.ui.BaseActivity
import com.basemodule.ui.BaseFragment
import com.lexivip.lexi.user.login.LoginActivity
import com.lexivip.lexi.user.login.UserProfileUtil
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

class MainActivity : BaseActivity() {

    override val layout: Int = R.layout.activity_main

    override fun onBackPressed() {
        if (!isFinishing) super.onBackPressed()
    }

    private var fragment0: BaseFragment? = null
    private var fragment1: BaseFragment? = null
    private var fragment2: BaseFragment? = null
    private var fragment3: BaseFragment? = null

    private var lastClickedId: Int = -1

    override fun initView() {
        LogUtil.e("screenW=${ScreenUtil.getScreenWidth()};;screenHeight=${ScreenUtil.getScreenHeight()};;density=${ScreenUtil.getDensity()}")
        initFragments()
        switchFragment(R.id.button0)
        EventBus.getDefault().register(this)
    }

    private fun initFragments() {
        initIndexPage()
        initDiscoverPage()
        initShopCart()
        initUserCenter()
    }

    /**
     * 初始化购物车
     */
    private fun initShopCart() {
        fragment2 =null
        fragment2 = MainFragment1.newInstance()
    }

    /**
     * 初始化发现
     */
    private fun initDiscoverPage() {
        fragment1 = null
        fragment1 = MainFragment2.newInstance()
    }

    /**
     * 初始化首页
     */
    private fun initIndexPage() {
        fragment0 = null
        fragment0 = MainFragment0.newInstance()
    }

    /**
     * 初始化个人中心
     */
    private fun initUserCenter() {
        fragment3 = null
        if (UserProfileUtil.isSmallB()) {
            fragment3 = MainFragmentUser.newInstance()
        } else {
            fragment3 = MainFragment3.newInstance()
        }
    }

    override fun installListener() {
        customBottomBar.setOnTabClickListener { v ->
            when (v.id) {
                R.id.button3 -> {
                    if (UserProfileUtil.isLogin()) {
                        switchFragment(v.id)
                        customBottomBar.setTabChecked(v)
                    } else {
                        startActivity(Intent(applicationContext, LoginActivity::class.java))
                    }
                }
                else -> {
                    switchFragment(v.id)
                    customBottomBar.setTabChecked(v)
                }
            }

        }
    }

    override fun onNewIntent(intent: Intent?) {

        hideFragments()

        initFragments()

        if (intent == null) return
        var str = ""
        if (intent.hasExtra(TAG)) {
            str = intent.getStringExtra(TAG)
        }

        if (TextUtils.isEmpty(str)) { //跳转首页
            lastClickedId = -1
            switchFragment(R.id.button0)
            customBottomBar.getButton(R.id.button0).performClick()
            return
        }

        when (str) {
            MainFragment1::class.java.simpleName -> { //购物车
                switchFragment(R.id.button2)
                customBottomBar.getButton(R.id.button2).performClick()
            }
            MainFragment0::class.java.simpleName -> { //其它界面跳转到首页
                switchFragment(R.id.button0)
                customBottomBar.getButton(R.id.button0).performClick()
            }
        }


    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun changeFragment(messageChangePage: MessageChangePage) {
        when (messageChangePage.page) {
            MainFragment0::class.java.simpleName -> {
                switchFragment(R.id.button0)
                customBottomBar.getButton(R.id.button0).performClick()
            }
        }
    }


    /**
     * add()方式提交可以hide后可以show，replace方式提交无法通过show来显示
     */
    private fun switchFragment(id: Int) {

        if (lastClickedId == id) return

        lastClickedId = id

        hideFragments()

        when (id) {
            R.id.button0 -> { //首页
                if (!fragment0!!.isAdded) {
                    supportFragmentManager.beginTransaction().add(R.id.frameLayout, fragment0).show(fragment0).commitAllowingStateLoss()
                } else {
                    supportFragmentManager.beginTransaction().show(fragment0).commitAllowingStateLoss()
                }
            }
            R.id.button1 -> { //发现
                if (!fragment1!!.isAdded) {
                    supportFragmentManager.beginTransaction().add(R.id.frameLayout, fragment1).show(fragment1).commitAllowingStateLoss()
                } else {
                    supportFragmentManager.beginTransaction().show(fragment1).commitAllowingStateLoss()
                }

            }
            R.id.button2 -> { //购物车
                if (!fragment2!!.isAdded) {
                    supportFragmentManager.beginTransaction().add(R.id.frameLayout, fragment2).show(fragment2).commitAllowingStateLoss()
                } else {
                    supportFragmentManager.beginTransaction().show(fragment2).commitAllowingStateLoss()
                    //购物车每次点击都加载
//                    EventBus.getDefault().post(MainFragment1::class.java.simpleName)
                }
            }
            R.id.button3 -> {
                if (!fragment3!!.isAdded) {
                    supportFragmentManager.beginTransaction().add(R.id.frameLayout, fragment3).show(fragment3).commitAllowingStateLoss()
                } else {
                    supportFragmentManager.beginTransaction().show(fragment3).commitAllowingStateLoss()
                }

            }
        }
    }

    /**
     * fragment当前事务只能提交一次，否则报错，解决方法是每次提交后重新获取事务
     */
    private fun hideFragments() {
        supportFragmentManager.beginTransaction().hide(fragment0).commitAllowingStateLoss()
        supportFragmentManager.beginTransaction().hide(fragment1).commitAllowingStateLoss()
        supportFragmentManager.beginTransaction().hide(fragment2).commitAllowingStateLoss()
        supportFragmentManager.beginTransaction().hide(fragment3).commitAllowingStateLoss()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        fragment0?.onActivityResult(requestCode, resultCode, data)
        LogUtil.e("${TAG};;;requestCode=$requestCode;resultCode=$resultCode")
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    /**
     * 菜单、返回键响应
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click() //调用双击退出函数
        }
        return false
    }

    private var isExit: Boolean = false

    private fun exitBy2Click() {
        val tExit: Timer
        if (!isExit) {
            isExit = true
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show()
            tExit = Timer()
            tExit.schedule(object : TimerTask() {
                override fun run() {
                    isExit = false
                }
            }, 2000)

        } else {
            AppManager.getAppManager().appExit()
        }
    }
}

