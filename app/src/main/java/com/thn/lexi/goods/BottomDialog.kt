package com.thn.lexi.goods
import android.app.DialogFragment
import android.os.Bundle
import com.thn.lexi.R
import android.graphics.drawable.ColorDrawable
import android.support.v4.content.ContextCompat
import android.view.*


class BottomDialog() : DialogFragment() {

    override fun onStart() {
        super.onStart()
        val window = dialog.window
        val params = window?.attributes
        params?.gravity = Gravity.BOTTOM // 显示在底部
        params?.width = WindowManager.LayoutParams.MATCH_PARENT // 宽度填充满屏
        window.attributes = params
// 这里用透明颜色替换掉系统自带背景
        val color = ContextCompat.getColor(activity, android.R.color.transparent)
        window.setBackgroundDrawable(ColorDrawable(color))
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window.setWindowAnimations(R.style.BottomDialogAnim)
        val view = LayoutInflater.from(activity).inflate(
                R.layout.dialog_purchase_goods, null)
        return view
    }

}