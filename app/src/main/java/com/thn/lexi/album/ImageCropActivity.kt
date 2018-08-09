package com.thn.lexi.album

import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import android.widget.ImageView
import com.basemodule.tools.GlideUtil
import com.basemodule.ui.BaseActivity
import com.thn.lexi.R
import com.thn.lexi.goods.lifehouse.FragmentLifeHouse
import com.thn.lexi.user.completeinfo.CompleteInfoActivity
import kotlinx.android.synthetic.main.activity_image_crop.*
import org.greenrobot.eventbus.EventBus

class ImageCropActivity:BaseActivity(), View.OnClickListener {
    /**
     * 消息
     */
    class MessageCropComplete(val bitmap: Bitmap)

    private var uri: Uri? = null

    override val layout: Int = R.layout.activity_image_crop

    override fun getIntentData() {
        if (intent.hasExtra(CompleteInfoActivity::class.java.simpleName)){
            uri = intent.getParcelableExtra(CompleteInfoActivity::class.java.simpleName)
        }else if (intent.hasExtra(FragmentLifeHouse::class.java.simpleName)){
            uri = intent.getParcelableExtra(FragmentLifeHouse::class.java.simpleName)
        }
    }

    override fun initView() {
        GlideUtil.loadImageAsBitmap(uri, csiv as ImageView)
    }

    override fun installListener() {
        buttonCancel.setOnClickListener(this)
        buttonComfirm.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.buttonCancel-> finish()

            R.id.buttonComfirm ->{
                val bitmap = csiv.clip()
                EventBus.getDefault().post(MessageCropComplete(bitmap))
                finish()
            }
        }
    }
}