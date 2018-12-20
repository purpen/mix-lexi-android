package com.lexivip.lexi.album

import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import android.widget.ImageView
import com.basemodule.tools.GlideUtil
import com.basemodule.tools.LogUtil
import com.basemodule.ui.BaseActivity
import com.lexivip.lexi.R
import com.lexivip.lexi.address.AddressActivity
import com.lexivip.lexi.cashMoney.NameAuthenticationActivity
import com.lexivip.lexi.index.lifehouse.FragmentLifeHouse
import com.lexivip.lexi.orderList.EvaluateActivity
import com.lexivip.lexi.user.completeinfo.CompleteInfoActivity
import com.lexivip.lexi.user.setting.userData.EditUserDataActivity
import kotlinx.android.synthetic.main.activity_image_crop.*
import org.greenrobot.eventbus.EventBus

class ImageCropActivity : BaseActivity(), View.OnClickListener {
    /**
     * 消息
     */
    class MessageCropComplete(val bitmap: Bitmap, val simpleName: String)

    private var uri: Uri? = null
    private var key: String = ""

    override val layout: Int = R.layout.activity_image_crop

    override fun getIntentData() {
        if (intent.hasExtra(CompleteInfoActivity::class.java.simpleName)) {
            uri = intent.getParcelableExtra(CompleteInfoActivity::class.java.simpleName)
            key = CompleteInfoActivity::class.java.simpleName
        } else if (intent.hasExtra(FragmentLifeHouse::class.java.simpleName)) {
            uri = intent.getParcelableExtra(FragmentLifeHouse::class.java.simpleName)
            key = FragmentLifeHouse::class.java.simpleName
        } else if (intent.hasExtra(AddressActivity::class.java.simpleName)) {
            uri = intent.getParcelableExtra(AddressActivity::class.java.simpleName)
            key = AddressActivity::class.java.simpleName
        } else if (intent.hasExtra(EvaluateActivity::class.java.simpleName)) {
            uri = intent.getParcelableExtra(EvaluateActivity::class.java.simpleName)
            key = EvaluateActivity::class.java.simpleName
        } else if (intent.hasExtra(EditUserDataActivity::class.java.simpleName)) {
            uri = intent.getParcelableExtra(EditUserDataActivity::class.java.simpleName)
            key = EditUserDataActivity::class.java.simpleName
        } else if (intent.hasExtra(NameAuthenticationActivity::class.java.simpleName)){
            uri = intent.getParcelableExtra(NameAuthenticationActivity::class.java.simpleName)
            key = NameAuthenticationActivity::class.java.simpleName
        }
    }

    override fun initView() {
        GlideUtil.loadImageUpBitmap(uri, csiv as ImageView)
    }

    override fun installListener() {
        buttonCancel.setOnClickListener(this)
        buttonComfirm.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.buttonCancel -> finish()

            R.id.buttonComfirm -> {
                val bitmap = csiv.clip()
                EventBus.getDefault().post(MessageCropComplete(bitmap, key))
                finish()
            }
        }
    }
}