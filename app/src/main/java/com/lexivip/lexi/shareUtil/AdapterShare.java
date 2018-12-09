package com.lexivip.lexi.shareUtil;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.basemodule.tools.DimenUtil;
import com.basemodule.tools.GlideUtil;
import com.basemodule.tools.LogUtil;
import com.basemodule.tools.Util;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lexivip.lexi.R;

import java.util.List;

public class AdapterShare extends BaseQuickAdapter<ShareTypeBean,BaseViewHolder> {
    public AdapterShare(int layoutResId, @Nullable List<ShareTypeBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShareTypeBean item) {
        LogUtil.e("                "+item.name);
        helper.setText(R.id.tv_name,item.name);
        ImageView imageView=helper.getView(R.id.iv_logo);
        GlideUtil.loadCircleImageWidthDimen(item.image,imageView,DimenUtil.dp2px(50.0));
    }
}
