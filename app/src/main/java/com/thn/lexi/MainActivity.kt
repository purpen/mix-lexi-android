package com.thn.lexi

import android.content.Intent
import android.text.TextUtils
import com.basemodule.tools.LogUtil
import com.basemodule.ui.BaseActivity
import com.basemodule.ui.BaseFragment

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override val layout: Int = R.layout.activity_main

    override fun onBackPressed() {
        if (!isFinishing) super.onBackPressed()
    }

    private val fragment0: BaseFragment by lazy { MainFragment0.newInstance() }
    private val fragment1: BaseFragment by lazy { MainFragment2.newInstance() }
    private val fragment2: BaseFragment by lazy { MainFragment1.newInstance() }
    private val fragment3: BaseFragment by lazy { MainFragment3.newInstance() }

    private var lastClickedId: Int = -1

    override fun initView() {
        switchFragment(R.id.button0)
    }

    override fun installListener() {
        customBottomBar.setOnTabClickListener { id ->
            switchFragment(id)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        if (intent==null) return
        var str = ""
        if (intent.hasExtra(TAG)){
           str = intent.getStringExtra(TAG)
        }

        if (TextUtils.equals(MainFragment1::class.java.simpleName,str)){
            switchFragment(R.id.button2)
            customBottomBar.getButton(R.id.button2).performClick()
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
                if (!fragment0.isAdded) {
                    supportFragmentManager.beginTransaction().add(R.id.frameLayout, fragment0).show(fragment0).commitAllowingStateLoss()
                } else {
                    supportFragmentManager.beginTransaction().show(fragment0).commitAllowingStateLoss()
                }
            }
            R.id.button1 -> { //发现
                if (!fragment1.isAdded) {
                    supportFragmentManager.beginTransaction().add(R.id.frameLayout, fragment1).show(fragment1).commitAllowingStateLoss()
                } else {
                    supportFragmentManager.beginTransaction().show(fragment1).commitAllowingStateLoss()
                }

            }
            R.id.button2 -> { //购物车
                if (!fragment2.isAdded) {
                    supportFragmentManager.beginTransaction().add(R.id.frameLayout, fragment2).show(fragment2).commitAllowingStateLoss()
                } else {
                    supportFragmentManager.beginTransaction().show(fragment2).commitAllowingStateLoss()
                }
            }
            R.id.button3 -> {
                if (!fragment3.isAdded) {
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
        fragment0.onActivityResult(requestCode, resultCode, data)
        LogUtil.e("${TAG};;;requestCode=$requestCode;resultCode=$resultCode")
    }

}
