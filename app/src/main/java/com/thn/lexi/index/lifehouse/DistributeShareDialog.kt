package com.thn.lexi.index.lifehouse

import android.graphics.Color
import android.support.v4.app.FragmentActivity
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import com.flyco.dialog.widget.base.BaseDialog
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.Util
import com.flyco.dialog.utils.CornerUtils
import com.thn.lexi.R
import com.thn.lexi.view.CenterShareView
import kotlinx.android.synthetic.main.dialog_edit_life_house.view.*


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