package com.thn.lexi.user.completeinfo

import android.view.View
import com.basemodule.tools.ToastUtil
import com.basemodule.ui.BaseActivity
import com.thn.lexi.R
import kotlinx.android.synthetic.main.activity_complete_info.*

class CompleteInfoActivity:BaseActivity(), View.OnClickListener {
    override val layout: Int = R.layout.activity_complete_info

    override fun initView() {

    }

    override fun installListener() {
        relativeLayoutOval.setOnClickListener(this)
        textViewBirth.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.relativeLayoutOval -> ToastUtil.showInfo("上传头像")

            R.id.textViewBirth -> ToastUtil.showInfo("上传头像")
        }
    }
}