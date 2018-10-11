package com.thn.lexi

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.text.TextUtils
import android.view.View
import com.basemodule.tools.*
import com.basemodule.ui.BaseFragment
import com.basemodule.ui.CustomFragmentPagerAdapter
import com.thn.lexi.mine.*
import com.thn.lexi.mine.designPavilion.FavoriteShopFragment
import com.thn.lexi.mine.dynamic.DynamicActivity
import com.thn.lexi.mine.enshrine.EnshrineFragment
import com.thn.lexi.mine.like.FavoriteFragment
import com.thn.lexi.orderList.OrderListActivity
import com.thn.lexi.user.setting.SettingActivity
import kotlinx.android.synthetic.main.fragment_main3.*
import kotlinx.android.synthetic.main.view_mine_head.*

class MainFragment3 : BaseFragment(),  View.OnClickListener {
    private lateinit var adapter0: MineFavoritesAdapter
    private lateinit var fragments: ArrayList<BaseFragment>

    companion object {
        fun newInstance(bean:UserCenterBean.DataBean): MainFragment3 {
            val mainFragment3 =MainFragment3()
            val bundle = Bundle()
            bundle.putParcelable("key",bean)
            mainFragment3.arguments=bundle
            return mainFragment3
        }
        fun newInstance(): MainFragment3 {
            val mainFragment3 =MainFragment3()
            return mainFragment3
        }
    }

    override val layout: Int = R.layout.fragment_main3


    private fun setUpViewPager() {
        fragments = ArrayList()
        fragments.add(FavoriteFragment.newInstance())
        fragments.add(EnshrineFragment.newInstance())
        fragments.add(FavoriteShopFragment.newInstance())

        val titles = resources.getStringArray(R.array.strings_mine_titles)

        val adapter = CustomFragmentPagerAdapter(childFragmentManager, fragments, titles.asList())
        customViewPager.setPagingEnabled(false)
        customViewPager.adapter = adapter
        customViewPager.offscreenPageLimit = fragments.size
    }


    override fun initView() {
        setUpViewPager()
        adapter0 = MineFavoritesAdapter(R.layout.adapter_goods_layout)
    }


    /**
     * 重置文字颜色
     */
    private fun resetTextColor(){
        val color = Util.getColor(R.color.color_999)
        textViewLikeNum.setTextColor(color)
        textViewLike.setTextColor(color)
        textViewEnshrineNum.setTextColor(color)
        textViewEnshrine.setTextColor(color)
        textViewDesignNum.setTextColor(color)
        textViewDesign.setTextColor(color)
    }

    override fun installListener() {
        customViewPager.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageSelected(position: Int) {
                when(position){
                    0 -> {
                        val color = Util.getColor(R.color.color_6ed7af)
                        resetTextColor()
                        textViewLikeNum.setTextColor(color)
                        textViewLike.setTextColor(color)
                    }

                    1 -> {
                        val color = Util.getColor(R.color.color_6ed7af)
                        resetTextColor()
                        textViewEnshrineNum.setTextColor(color)
                        textViewEnshrine.setTextColor(color)
                    }

                    2 -> {
                        val color = Util.getColor(R.color.color_6ed7af)
                        resetTextColor()
                        textViewDesignNum.setTextColor(color)
                        textViewDesign.setTextColor(color)
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }
        })

        imageViewShare.setOnClickListener(this)
        imageViewSetting.setOnClickListener(this)
        buttonOrder.setOnClickListener(this)

        linearLayoutLike.setOnClickListener{
            customViewPager.setCurrentItem(0,true)
        }

        linearLayoutEnshrine.setOnClickListener {
            customViewPager.setCurrentItem(1,true)
        }

        linearLayoutDesign.setOnClickListener {
            customViewPager.setCurrentItem(2,true)
        }

        buttonActivity.setOnClickListener {
            startActivity(Intent(activity, DynamicActivity::class.java))
        }
    }

    override fun onClick(v: View) {
        val id = v.id
        when (id) {
            R.id.imageViewShare -> ToastUtil.showInfo("分享")
            R.id.imageViewSetting -> startActivity(Intent(activity, SettingActivity::class.java))
            R.id.buttonOrder->startActivity(Intent(activity,OrderListActivity::class.java))
        }
    }

    override fun loadData() {
        val bundle = arguments
        setUserData(bundle?.getParcelable("key"))
    }

    /**
     * 设置用户数据
     */
    fun setUserData(data: UserCenterBean.DataBean?) {
        if (data==null){
            textViewLikeNum.text = "0"
            textViewEnshrineNum.text = "0"
            textViewDesignNum.text = "0"
            textViewFocusNum.text = "0"
            textViewFansNum.text = "0"
            textViewName.text = ""
            GlideUtil.loadCircleImageWidthDimen("", imageView, DimenUtil.getDimensionPixelSize(R.dimen.dp70))
        }else{
            textViewLikeNum.text = data.user_like_counts
            textViewEnshrineNum.text = data.wish_list_counts
            textViewDesignNum.text = data.followed_stores_counts
            textViewFocusNum.text = data.followed_users_counts
            textViewFansNum.text = data.fans_counts
            textViewName.text = data.username
            if (TextUtils.isEmpty(data.about_me)) {
                textViewSignature.visibility = View.GONE
            } else {
                textViewSignature.visibility = View.VISIBLE
                textViewSignature.text = data.about_me
            }
            GlideUtil.loadCircleImageWidthDimen(data.avatar, imageView, DimenUtil.getDimensionPixelSize(R.dimen.dp70))
        }

    }
}