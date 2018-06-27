package com.thn.lexi

import com.basemodule.ui.BaseActivity
import com.basemodule.ui.BaseFragment

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override val layout: Int = R.layout.activity_main

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private var fragment0: BaseFragment? = null
    private var fragment1: BaseFragment? = null
    private var fragment2: BaseFragment? = null
    private var fragment3: BaseFragment? = null

    private var lastClickedId: Int = -1

    override fun initView() {
        switchFragment(R.id.button0)
    }

    override fun installListener() {
        customBottomBar.setOnTabClickListener { id ->
            switchFragment(id)
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
            R.id.button0 -> {
                if (fragment0 == null) {
                    fragment0 = MainFragment0.newInstance()
                    supportFragmentManager.beginTransaction().add(R.id.frameLayout, fragment0).commit()
                } else {
                    supportFragmentManager.beginTransaction().show(fragment0).commit()
                }
            }
            R.id.button1 -> {
                if (fragment1 == null) {
                    fragment1 = MainFragment1.newInstance()
                    supportFragmentManager.beginTransaction().add(R.id.frameLayout, fragment1).commit()
                } else {
                    supportFragmentManager.beginTransaction().show(fragment1).commit()
                }

            }
            R.id.button2 -> {
                if (fragment2 == null) {
                    fragment2 = MainFragment2.newInstance()
                    supportFragmentManager.beginTransaction().add(R.id.frameLayout, fragment2).commit()
                } else {
                    supportFragmentManager.beginTransaction().show(fragment2).commit()
                }
            }
            R.id.button3 -> {
                if (fragment3 == null) {
                    fragment3 = MainFragment3.newInstance()
                    supportFragmentManager.beginTransaction().add(R.id.frameLayout, fragment3).commit()
                } else {
                    supportFragmentManager.beginTransaction().show(fragment3).commit()
                }
            }
        }
    }

    /**
     * fragment当前事务只能提交一次，否则报错，解决方法是每次提交后重新获取事务
     */
    private fun hideFragments() {
        if (fragment0 != null) supportFragmentManager.beginTransaction().hide(fragment0).commit()
        if (fragment1 != null) supportFragmentManager.beginTransaction().hide(fragment1).commit()
        if (fragment2 != null) supportFragmentManager.beginTransaction().hide(fragment2).commit()
        if (fragment3 != null) supportFragmentManager.beginTransaction().hide(fragment3).commit()
    }

}
