package com.thn.lexi.index.detail

import android.support.annotation.LayoutRes
import android.widget.Button
import android.widget.ImageView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.basemodule.tools.Util

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.lexi.R
import com.thn.lexi.beans.UserBean

class AdapterFavoriteUserList(@LayoutRes res: Int) : BaseQuickAdapter<UserBean, BaseViewHolder>(res) {

    override fun convert(helper: BaseViewHolder, item: UserBean) {

        val imageView = helper.getView<ImageView>(R.id.imageView)
        GlideUtil.loadCircleImageWidthDimen(item.avatar,imageView,DimenUtil.getDimensionPixelSize(R.dimen.dp40))

        helper.setText(R.id.textViewName,item.username)

        val buttonFocus = helper.getView<Button>(R.id.buttonFocus)

        //关注
        helper.addOnClickListener(R.id.buttonFocus)

        when(item.followed_status){

            0 ->{ //未关注
                buttonFocus.text = Util.getString(R.string.text_focus)
                buttonFocus.setTextColor(Util.getColor(android.R.color.white))
                buttonFocus.setBackgroundResource(R.drawable.corner_bg_6ed7af)
                buttonFocus.setCompoundDrawables(Util.getDrawableWidthDimen(R.mipmap.icon_add_white, R.dimen.dp10, R.dimen.dp10), null, null, null)
            }

            1->{ //已关注
                buttonFocus.text = Util.getString(R.string.text_focused)
                buttonFocus.setTextColor(Util.getColor(R.color.color_949ea6))
                buttonFocus.setBackgroundResource(R.drawable.bg_coloreff3f2_radius4)
                buttonFocus.setPadding(0,0,0,0)
                buttonFocus.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            }

            2->{ //相互关注
                buttonFocus.text = Util.getString(R.string.text_focused_each_other)
                buttonFocus.setTextColor(Util.getColor(R.color.color_949ea6))
                buttonFocus.setBackgroundResource(R.drawable.bg_coloreff3f2_radius4)
                buttonFocus.setPadding(0,0,0,0)
                buttonFocus.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            }

        }
    }
}