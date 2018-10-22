package com.lexivip.lexi.index.detail

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.basemodule.tools.GlideUtil
import com.github.chrisbanes.photoview.PhotoView
import com.lexivip.lexi.AppApplication

class GoodsImagePagerAdapter(data: GoodsAllDetailBean.DataBean,presenter: GoodsDetailPresenter):PagerAdapter() {

    private val assets:List<GoodsAllDetailBean.DataBean.AssetsBean> by lazy { data.assets }
    private val present:GoodsDetailPresenter by lazy { presenter }

    override fun getCount(): Int {
        return assets.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val photoView = PhotoView(AppApplication.getContext())
        photoView.scaleType = ImageView.ScaleType.FIT_START
        GlideUtil.loadImageWithFading(assets[position].view_url,photoView)
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