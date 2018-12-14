package com.lexivip.lexi.lifeShop;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.basemodule.tools.DimenUtil;
import com.basemodule.tools.GlideUtil;
import com.basemodule.tools.Util;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lexivip.lexi.R;

import java.util.List;

public class AdapterMyFriend extends BaseQuickAdapter<MyFriendBean.DataBean.FriendsBean,BaseViewHolder> {

    public AdapterMyFriend(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyFriendBean.DataBean.FriendsBean item) {
        ImageView imageView=helper.getView(R.id.image);
        GlideUtil.loadCircleImageWidthDimen(item.user_logo,imageView,DimenUtil.dp2px(35.0));
        helper.setText(R.id.tv_name,item.user_name);
        TextView tv_type=helper.getView(R.id.tv_type);
        if (item.phases==1){
            tv_type.setText(Util.getString(R.string.text_internship));
            tv_type.setBackgroundResource(R.drawable.bg_color2785fa_rectangle);
        }else {
            tv_type.setText(Util.getString(R.string.text_daren));
            tv_type.setBackgroundResource(R.drawable.bg_colorffa22a_radius20);
        }
        helper.setText(R.id.tv_money,"￥"+item.amount);
    }
}
