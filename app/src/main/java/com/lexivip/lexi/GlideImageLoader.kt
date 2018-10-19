package com.lexivip.lexi

import android.content.Context
import android.support.annotation.DimenRes
import android.widget.ImageView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.youth.banner.loader.ImageLoader

class GlideImageLoader(@DimenRes radius:Int,width:Int,height:Int): ImageLoader() {
    private val radiusSize:Int = DimenUtil.getDimensionPixelSize(radius)
    private val w:Int = width
    private val h:Int = height
    override fun displayImage(context: Context, path: Any?, imageView: ImageView) {
        GlideUtil.loadImageWithDimenAndRadius(path,imageView,radiusSize,w,h)
    }
}