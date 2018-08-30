package com.thn.lexi

import android.content.Context
import android.support.annotation.DimenRes
import android.widget.ImageView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.youth.banner.loader.ImageLoader

class GlideImageLoader(@DimenRes radius:Int): ImageLoader() {
    private val radiusSize:Int = radius
    override fun displayImage(context: Context, path: Any?, imageView: ImageView) {
        GlideUtil.loadImageWithRadius(path,imageView,DimenUtil.getDimensionPixelSize(radiusSize))
    }
}