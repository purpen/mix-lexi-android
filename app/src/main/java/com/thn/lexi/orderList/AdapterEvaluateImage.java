package com.thn.lexi.orderList;

import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.basemodule.tools.GlideUtil;
import com.basemodule.tools.LogUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.thn.lexi.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterEvaluateImage extends BaseQuickAdapter<byte[],BaseViewHolder> {
    public AdapterEvaluateImage(int layoutResId, @Nullable List<byte[]> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, byte[] item) {
        LogUtil.e("有么有执行");
        if (item==null){
            LogUtil.e("没有的图片的时间");
            GlideUtil.loadImageWithFading(R.mipmap.icon_evaluate_image,(ImageView) helper.getView(R.id.iv_evaluate));
            helper.setGone(R.id.iv_delete,false);
        }else{
            LogUtil.e("有的图片的时候");
            GlideUtil.loadImageWithFading(item,(ImageView) helper.getView(R.id.iv_evaluate));
            helper.setGone(R.id.iv_delete,true);
        }
        helper.addOnClickListener(R.id.iv_delete)
                .addOnClickListener(R.id.iv_evaluate);
    }
}
