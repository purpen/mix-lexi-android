package com.thn.lexi

import android.content.Context
import android.widget.ImageView
import com.basemodule.tools.GlideUtil
import com.youth.banner.loader.ImageLoader

class GlideImageLoader: ImageLoader() {
    override fun displayImage(context: Context, path: Any?, imageView: ImageView) {
        GlideUtil.loadImageWithRadius(path,imageView,context.resources.getDimensionPixelSize(R.dimen.dp5))
    }
}