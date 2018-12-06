package com.lexivip.lexi.index.lifehouse

import android.graphics.Color
import android.support.v4.app.FragmentActivity
import android.view.View
import com.basemodule.tools.DimenUtil
import com.lexivip.lexi.R
import com.lexivip.lexi.view.CenterShareView
import com.smart.dialog.utils.CornerUtils
import com.smart.dialog.widget.base.BaseDialog


class DistributeShareDialog(context: FragmentActivity?) : BaseDialog<DistributeShareDialog>(context) {
//    private var titleLifeHouse: CharSequence = title
//    private var desc: CharSequence = description
//    private val present: LifeHousePresenter = presenter
    private lateinit var view: View
    override fun onCreateView(): View {
        widthScale(0.85f)
        view = CenterShareView(context)

        view.background = CornerUtils.cornerDrawable(Color.parseColor("#ffffff"), DimenUtil.getDimensionPixelSize(R.dimen.dp4).toFloat())

        return view
    }

    override fun setUiBeforShow() {

    }
}