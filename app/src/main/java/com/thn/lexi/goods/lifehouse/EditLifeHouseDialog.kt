package com.thn.lexi.goods.lifehouse
import android.graphics.Color
import android.support.v4.app.FragmentActivity
import android.view.View
import com.flyco.dialog.widget.base.BaseDialog
import com.basemodule.tools.DimenUtil
import com.flyco.dialog.utils.CornerUtils
import com.thn.lexi.R


class EditLifeHouseDialog(context: FragmentActivity?) : BaseDialog<EditLifeHouseDialog>(context) {

    override fun onCreateView(): View {
        widthScale(0.85f)
        val inflate = View.inflate(context, R.layout.dialog_edit_life_house, null)
//        tv_cancel = ViewFindUtils.find(inflate, R.id.tv_cancel)
//        tv_exit = ViewFindUtils.find(inflate, R.id.tv_exit)
        inflate.background = CornerUtils.cornerDrawable(Color.parseColor("#ffffff"),DimenUtil.getDimensionPixelSize(R.dimen.dp4).toFloat())

        return inflate
    }

    override fun setUiBeforShow() {

    }
}