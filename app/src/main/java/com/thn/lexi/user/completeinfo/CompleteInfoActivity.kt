package com.thn.lexi.user.completeinfo

import android.view.View
import com.basemodule.tools.ToastUtil
import com.basemodule.ui.BaseActivity
import com.thn.lexi.R
import kotlinx.android.synthetic.main.activity_complete_info.*
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.builder.TimePickerBuilder
import java.text.SimpleDateFormat
import java.util.*


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

            R.id.textViewBirth -> {
                //当期日期
                val selectedDate = Calendar.getInstance()
                val startDate = Calendar.getInstance()
                startDate.set(1900, 0, 1)
                val endDate = Calendar.getInstance()
                endDate.set(endDate.get(Calendar.YEAR),endDate.get(Calendar.MONTH), endDate.get(Calendar.DAY_OF_MONTH))
                val pvTime = TimePickerBuilder(this, OnTimeSelectListener { date, v ->
                    textViewBirth.setTextColor(resources.getColor(R.color.color_333))
                    textViewBirth.text = getTime(date)
                }).setRangDate(startDate,endDate)
                        .setDate(selectedDate)
                        .build()
                pvTime.show()
            }
        }
    }

   private fun getTime(date:Date):String{
       val format = SimpleDateFormat("yyyy-MM-dd", Locale.SIMPLIFIED_CHINESE)
      return format.format(date)
   }
}