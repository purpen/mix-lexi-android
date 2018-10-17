package com.lexivip.lexi.brandHouse;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.basemodule.tools.DimenUtil;
import com.basemodule.tools.GlideUtil;
import com.basemodule.tools.Util;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lexivip.lexi.R;

import java.util.List;

public class AdapterBrandHouseArticle extends BaseQuickAdapter<BrandHouseArticelBean.DataBean.LifeRecordsBean, BaseViewHolder> {
    public AdapterBrandHouseArticle(int layoutResId, @Nullable List<BrandHouseArticelBean.DataBean.LifeRecordsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BrandHouseArticelBean.DataBean.LifeRecordsBean item) {
        ImageView imageView = helper.getView(R.id.imageView);
        GlideUtil.loadImageWithTopRadius(item.cover, imageView, DimenUtil.getDimensionPixelSize(R.dimen.dp4));
        ImageView iv_logo = helper.getView(R.id.imageViewAvatar);
        GlideUtil.loadCircleImageWidthDimen(item.user_avator, iv_logo, DimenUtil.getDimensionPixelSize(R.dimen.dp20));
        helper.setText(R.id.textViewTitle0, item.title);
        helper.setText(R.id.textViewName, item.user_name);
        if (item.channel_name.isEmpty()) {
            helper.setText(R.id.tv_channel_name, "种草笔记");
            helper.setTextColor(R.id.tv_channel_name, Util.getColor(R.color.color_75AB9A));
        } else {
            switch (item.channel_name) {
                case "手作教学":
                    helper.setTextColor(R.id.tv_channel_name, Util.getColor(R.color.color_E3B395));
                    break;
                case "创作人故事":
                    helper.setTextColor(R.id.tv_channel_name, Util.getColor(R.color.color_829D7A));
                    break;
                case "生活记事":
                    helper.setTextColor(R.id.tv_channel_name, Util.getColor(R.color.color_8C7A6E));
                    break;
            }
            helper.setText(R.id.tv_channel_name, item.channel_name);
        }
    }
}
