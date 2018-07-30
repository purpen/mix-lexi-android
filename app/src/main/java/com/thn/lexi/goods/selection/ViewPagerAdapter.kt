package com.thn.lexi.goods.selection
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.basemodule.tools.GlideUtil
import com.thn.lexi.AppApplication
import com.thn.lexi.R

class ViewPagerAdapter(images: List<String>) : PagerAdapter() {
    private var mImages: List<String> = checkNotNull(images)

    override fun getCount(): Int {
        return mImages.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val context = AppApplication.getContext()
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_selection_viewpager, null)
        val imageView = view.findViewById<ImageView>(R.id.imageView)
        GlideUtil.loadImageWithRadius(mImages[position],imageView,context.resources.getDimensionPixelSize(R.dimen.dp5))
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}