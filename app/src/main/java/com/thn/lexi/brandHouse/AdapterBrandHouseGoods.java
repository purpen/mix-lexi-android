package com.thn.lexi.brandHouse;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.basemodule.tools.DimenUtil;
import com.basemodule.tools.GlideUtil;
import com.basemodule.tools.LogUtil;
import com.basemodule.tools.ScreenUtil;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.thn.lexi.R;
import com.thn.lexi.beans.ProductBean;
import com.thn.lexi.search.AdapterSearchGoods;

import java.util.List;

public class AdapterBrandHouseGoods extends BaseMultiItemQuickAdapter<AdapterSearchGoods.MultipleItem,BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public AdapterBrandHouseGoods(List<AdapterSearchGoods.MultipleItem> data) {
        super(data);
        addItemType(AdapterSearchGoods.MultipleItem.ITEM_TYPE_SPAN2, R.layout.adapter_editor_recommend);
        addItemType(AdapterSearchGoods.MultipleItem.ITEM_TYPE_SPAN1, R.layout.adapter_editor_recommend);
    }

    @Override
    protected void convert(BaseViewHolder helper, AdapterSearchGoods.MultipleItem item) {
        LogUtil.e("item的"+helper.getLayoutPosition());
        ProductBean bean=item.getProduct();
        RelativeLayout relativeLayout=helper.getView(R.id.relativeLayout);
        ImageView imageView=helper.getView(R.id.imageView);
        ViewGroup.LayoutParams layoutParams;
        int sizeScreen= ScreenUtil.getScreenWidth()-DimenUtil.dp2px(30.0);
        int sizeSmall=(int)((ScreenUtil.getScreenWidth()-DimenUtil.getDimensionPixelSize(R.dimen.dp40))*0.5);
        if (item.getItemType() == AdapterSearchGoods.MultipleItem.ITEM_TYPE_SPAN2) {
            layoutParams = new RelativeLayout.LayoutParams(sizeScreen,sizeScreen);
            relativeLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        }else {
            layoutParams = new RelativeLayout.LayoutParams(sizeSmall, sizeSmall);
            relativeLayout.setLayoutParams(new ViewGroup.LayoutParams(sizeSmall,ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        imageView.setLayoutParams(layoutParams);
        GlideUtil.loadImageWithRadius(bean.cover, imageView,DimenUtil.dp2px(4.0));
        helper.setText(R.id.textViewTitle,bean.name);
        helper.setText(R.id.textViewPrice, String.valueOf(bean.min_price));
        helper.setText(R.id.textViewLike,"喜欢 +"+bean.like_count);
    }
}
