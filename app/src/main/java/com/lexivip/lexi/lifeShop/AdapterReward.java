package com.lexivip.lexi.lifeShop;

import android.widget.ImageView;
import android.widget.TextView;

import com.basemodule.tools.DateUtil;
import com.basemodule.tools.DimenUtil;
import com.basemodule.tools.GlideUtil;
import com.basemodule.tools.Util;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lexivip.lexi.R;

public class AdapterReward extends BaseQuickAdapter<RewardBean.DataBean.RewardsBean,BaseViewHolder> {
    public AdapterReward(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, RewardBean.DataBean.RewardsBean item) {
        helper.setText(R.id.tv_type,item.title);
        helper.setText(R.id.tv_time,DateUtil.getDateByTimestamp(item.created_at,"yyyy-MM-dd  HH:mm:ss"));
        ImageView imageView=helper.getView(R.id.image);
        GlideUtil.loadCircleImageWidthDimen(item.user_logo,imageView,DimenUtil.dp2px(35.0));
        TextView tv_state=helper.getView(R.id.tv_state);
        if (item.status==1){
            helper.setText(R.id.tv_money,"+"+item.amount);
            tv_state.setText(Util.getString(R.string.text_success));
            tv_state.setTextColor(Util.getColor(R.color.color_6ED7AF));
        }else if (item.status==2){
            helper.setText(R.id.tv_money,"+"+item.amount);
            tv_state.setText(Util.getString(R.string.text_loading_settlement));
            tv_state.setTextColor(Util.getColor(R.color.color_FFA22A));
        }else {
            helper.setText(R.id.tv_money,"-"+item.amount);
            tv_state.setText(Util.getString(R.string.text_return));
            tv_state.setTextColor(Util.getColor(R.color.color_b2b2b2));
        }
    }
}
