package com.lexivip.lexi.brandHouse;

import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.basemodule.tools.GlideUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lexivip.lexi.R;

import java.util.List;

public class AdapterBrandHouseAbout extends BaseQuickAdapter<AboutBrandHouseDetailBean.DataBean.SplitContentBean,BaseViewHolder> {
    public AdapterBrandHouseAbout(int layoutResId, @Nullable List<AboutBrandHouseDetailBean.DataBean.SplitContentBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AboutBrandHouseDetailBean.DataBean.SplitContentBean item) {
        ImageView imageView=helper.getView(R.id.imageView);
        TextView textView=helper.getView(R.id.tv_context);
        if ("text".equals(item.type)){
            textView.setVisibility(View.VISIBLE);
            textView.setText(Html.fromHtml(item.content));
            imageView.setVisibility(View.GONE);
        }else {
            imageView.setVisibility(View.VISIBLE);
            GlideUtil.loadImageAsBitmap(item.content,imageView);
            textView.setVisibility(View.GONE);
        }
    }
}
