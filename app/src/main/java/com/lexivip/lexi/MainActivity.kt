package com.lexivip.lexi

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.widget.Toast
import com.basemodule.tools.AppManager
import com.basemodule.tools.LogUtil
import com.basemodule.ui.BaseActivity
import com.basemodule.ui.BaseFragment
import com.lexivip.lexi.coupon.UserCouponActivity
import com.lexivip.lexi.eventBusMessge.MessageChangePage
import com.lexivip.lexi.index.selection.applyForLifeHouse.ApplyForLifeHouseSuccessActivity
import com.lexivip.lexi.user.completeinfo.CompleteInfoActivity
import com.lexivip.lexi.user.login.LoginActivity
import com.lexivip.lexi.user.login.UserProfileUtil
import com.lexivip.lexi.user.register.RegisterActivity
import com.lexivip.lexi.user.setting.SettingActivity
import com.umeng.analytics.MobclickAgent
import com.umeng.message.PushAgent
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.util.*

class MainActivity : BaseActivity() {
    /**
     * 是否切换到生活馆界面
     */
    private var showLifeHouseTab: Boolean = false

    override fun onResume() {
        super.onResume()
        MobclickAgent.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        MobclickAgent.onPause(this)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
        LogUtil.e("activity的回电")
        if (fragment3 != null) {
            LogUtil.e("activity传到fragment")
            fragment3!!.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    override val layout: Int = R.layout.activity_main

    override fun onBackPressed() {
        if (!isFinishing) super.onBackPressed()
    }

    private var fragment0: BaseFragment? = null
    private var fragment1: BaseFragment? = null
    private var fragment2: BaseFragment? = null
    private var fragment3: BaseFragment? = null

    private var lastClickedId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            val mainFragment0 = supportFragmentManager.getFragment(savedInstanceState, MainFragment0::class.java.simpleName)
            if (mainFragment0 == null) {
                initIndexPage()
            } else {
                fragment0 = mainFragment0 as BaseFragment
            }

            val mainFragment2 = supportFragmentManager.getFragment(savedInstanceState, MainFragment2::class.java.simpleName)

            if (mainFragment2 == null) {
                initDiscoverPage()
            } else {
                fragment1 = mainFragment2 as BaseFragment
            }

            val mainFragment1 = supportFragmentManager.getFragment(savedInstanceState, MainFragment1::class.java.simpleName)

            if (mainFragment1 == null) {
                initShopCart()
            } else {
                fragment2 = mainFragment1 as BaseFragment
            }

            val mainFragmentUser = supportFragmentManager.getFragment(savedInstanceState, MainFragmentUser::class.java.simpleName)

            if (mainFragmentUser == null) {
                initUserCenter()
            } else {
                fragment3 = mainFragmentUser as BaseFragment
            }

        } else {
            initFragments()
        }
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        PushAgent.getInstance(this).onAppStart()
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
        fragment2 = MainFragment1.newInstance()
    }

    /**
     * 初始化发现
     */
    private fun initDiscoverPage() {
        fragment1 = MainFragment2.newInstance()
    }

    /**
     * 初始化首页
     */
    private fun initIndexPage() {
        fragment0 = MainFragment0.newInstance()
    }

    /**
     * 初始化个人中心
     */
    private fun initUserCenter() {
        fragment3 = MainFragmentUser.newInstance()
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


    /**
     * 是否展示生活馆
     */
    fun switch2LifeHouseTab(): Boolean {
        return showLifeHouseTab
    }

    override fun onNewIntent(intent: Intent?) {
        if (intent == null) return
        var str = ""

        if (intent.hasExtra(TAG)) {
            str = intent.getStringExtra(TAG)
        }

        if (TextUtils.isEmpty(str)) { //跳转首页
            showIndexPage()
            return
        }

        if (TextUtils.equals(str, UserCouponActivity::class.java.simpleName)) { //官方优惠券跳转精选
            showIndexPage()
            if (fragment0 is MainFragment0) {
                (fragment0 as MainFragment0).getViewPager().currentItem = 1
            }
        }

//        LogUtil.e("TextUtils.equals(str,ApplyForLifeHouseSuccessActivity::class.java.simpleName)=="+TextUtils.equals(str,ApplyForLifeHouseSuccessActivity::class.java.simpleName))
        if (TextUtils.equals(str, ApplyForLifeHouseSuccessActivity::class.java.simpleName)) showLifeHouseTab = true

        when (str) { //MainActivity存在时刷新fragment
            LoginActivity::class.java.simpleName, CompleteInfoActivity::class.java.simpleName,
            RegisterActivity::class.java.simpleName, SettingActivity::class.java.simpleName,
            ApplyForLifeHouseSuccessActivity::class.java.simpleName -> {
                hideFragments()
                initFragments()
                showIndexPage()
            }
        }


        when (str) {
            MainFragment1::class.java.simpleName -> { //购物车
                switchFragment(R.id.button2)
                customBottomBar.getButton(R.id.button2).performClick()
            }
            MainFragment0::class.java.simpleName -> { //其它界面跳转到首页
                showIndexPage()
            }
        }


    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun changeFragment(messageChangePage: MessageChangePage) {
        when (messageChangePage.page) {
            MainFragment0::class.java.simpleName -> {
                showIndexPage()
            }
        }
    }

    /**
     * 切换到首页
     */
    private fun showIndexPage() {
        lastClickedId = -1
        switchFragment(R.id.button0)
        customBottomBar.getButton(R.id.button0).performClick()
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
                    supportFragmentManager.beginTransaction().add(R.id.frameLayout, fragment0!!).show(fragment0!!).commitAllowingStateLoss()
                } else {
                    supportFragmentManager.beginTransaction().show(fragment0!!).commitAllowingStateLoss()
                }
            }
            R.id.button1 -> { //发现
                if (!fragment1!!.isAdded) {
                    supportFragmentManager.beginTransaction().add(R.id.frameLayout, fragment1!!).show(fragment1!!).commitAllowingStateLoss()
                } else {
                    supportFragmentManager.beginTransaction().show(fragment1!!).commitAllowingStateLoss()
                }

            }
            R.id.button2 -> { //购物车
                if (!fragment2!!.isAdded) {
                    supportFragmentManager.beginTransaction().add(R.id.frameLayout, fragment2!!).show(fragment2!!).commitAllowingStateLoss()
                } else {
                    supportFragmentManager.beginTransaction().show(fragment2!!).commitAllowingStateLoss()
                    //购物车每次点击都加载
//                    EventBus.getDefault().post(MainFragment1::class.java.simpleName)
                }
            }
            R.id.button3 -> {
                if (!fragment3!!.isAdded) {
                    supportFragmentManager.beginTransaction().add(R.id.frameLayout, fragment3!!).show(fragment3!!).commitAllowingStateLoss()
                } else {
                    supportFragmentManager.beginTransaction().show(fragment3!!).commitAllowingStateLoss()
                }

            }
        }
    }

    /**
     * fragment当前事务只能提交一次，否则报错，解决方法是每次提交后重新获取事务
     */
    private fun hideFragments() {
        if (fragment0 != null) supportFragmentManager.beginTransaction().hide(fragment0!!).commitAllowingStateLoss()
        if (fragment1 != null) supportFragmentManager.beginTransaction().hide(fragment1!!).commitAllowingStateLoss()
        if (fragment2 != null) supportFragmentManager.beginTransaction().hide(fragment2!!).commitAllowingStateLoss()
        if (fragment3 != null) supportFragmentManager.beginTransaction().hide(fragment3!!).commitAllowingStateLoss()
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

    override fun onSaveInstanceState(outState: Bundle) {
        val fragments = supportFragmentManager.fragments

        if (fragment0 != null && fragments.contains(fragment0)) {
            supportFragmentManager.putFragment(outState, MainFragment0::class.java.simpleName, fragment0!!)
        }
        if (fragment1 != null && fragments.contains(fragment1)) {
            supportFragmentManager.putFragment(outState, MainFragment2::class.java.simpleName, fragment1!!)
        }

        if (fragment2 != null && fragments.contains(fragment2)) {
            supportFragmentManager.putFragment(outState, MainFragment1::class.java.simpleName, fragment2!!)
        }

        if (fragment3 != null && fragments.contains(fragment3)) {
            supportFragmentManager.putFragment(outState, MainFragmentUser::class.java.simpleName, fragment3!!)
        }
        super.onSaveInstanceState(outState)
    }
}

