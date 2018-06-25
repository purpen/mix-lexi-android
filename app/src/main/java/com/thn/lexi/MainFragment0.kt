package com.thn.lexi

import android.content.Intent
import com.basemodule.ui.BaseFragment
import com.basemodule.ui.CustomFragmentPagerAdapter
import com.thn.lexi.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_main0.*
import kotlinx.android.synthetic.main.view_main_head_nav.*

class MainFragment0 : BaseFragment() {

    companion object {
        fun newInstance(): MainFragment0 {
            return MainFragment0()
        }
    }

    override val layout: Int = R.layout.fragment_main0


    override fun initView() {
        setUpViewPager()
    }

    private fun setUpViewPager() {
        val fragments = ArrayList<BaseFragment>()

        fragments.add(FragmentCharacteristic.newInstance())
        fragments.add(FragmentClassify.newInstance())

        val titles = resources.getStringArray(R.array.strings_main_titles)
        val adapter = CustomFragmentPagerAdapter(childFragmentManager, fragments, titles)
        customViewPager.adapter = adapter
        customViewPager.offscreenPageLimit = fragments.size
        customViewPager.setPagingEnabled(true)
        slidingTabLayout.setViewPager(customViewPager)
    }

    override fun installListener() {
        textView.setOnClickListener {
            startActivity(Intent(activity, LoginActivity::class.java))
        }
    }

    override fun loadData() {

    }


}