package com.lexivip.lexi.index.detail

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import com.basemodule.tools.GlideUtil
import com.github.chrisbanes.photoview.PhotoView
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.ImageSizeConfig

class GoodsImagePagerAdapter(data: GoodsAllDetailBean.DataBean):PagerAdapter() {

    private val assets:List<GoodsAllDetailBean.DataBean.AssetsBean> by lazy { data.assets }

    override fun getCount(): Int {
        return assets.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val photoView = PhotoView(AppApplication.getContext())
        GlideUtil.loadImage(assets[position].view_url,photoView,ImageSizeConfig.SIZE_P50)
        container.addView(photoView)
        return photoView
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }
}