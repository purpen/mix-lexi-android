package com.lexivip.lexi.receiveVoucher;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.basemodule.tools.DimenUtil;
import com.basemodule.tools.GlideUtil;
import com.basemodule.tools.LogUtil;
import com.basemodule.tools.ScreenUtil;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lexivip.lexi.R;

import java.util.List;

public class AdapterReceiveVoucherBrand extends BaseMultiItemQuickAdapter<MultipleItem,BaseViewHolder> {

    private Activity activity;
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public AdapterReceiveVoucherBrand(List<MultipleItem> data, Activity activity) {
        super(data);
        addItemType(MultipleItem.ITEM_TYPE_SPAN1, R.layout.adapter_voucher_brand_one);
        addItemType(MultipleItem.ITEM_TYPE_SPAN2,R.layout.adapter_voucher_brand_two);
        this.activity=activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItem item) {
        LogUtil.e("几个："+item.getItemType()+"item:"+helper.getLayoutPosition());
        ImageView iv_logo=helper.getView(R.id.iv_logo);
        GlideUtil.loadImageWithRadius(item.getBean().store_logo,iv_logo,DimenUtil.getDimensionPixelSize(R.dimen.dp5));
        helper.setText(R.id.tv_name,item.getBean().store_name);
        LogUtil.e("名字："+item.getBean().store_name);
        helper.setText(R.id.tv_full,"满"+item.getBean().min_amount+"可用");
        RelativeLayout relativeLayout=helper.getView(R.id.context);
        int sizeScreen= ScreenUtil.getScreenWidth()-DimenUtil.dp2px(30.0);
        int sizeSmall=(int)((ScreenUtil.getScreenWidth()-DimenUtil.getDimensionPixelSize(R.dimen.dp40))*0.5);
        ViewGroup.LayoutParams layoutParams;
        switch (item.getItemType()){
            case MultipleItem.ITEM_TYPE_SPAN1:
                helper.setText(R.id.tv_price,item.getBean().amount);
                RecyclerView recyclerView=helper.getView(R.id.recyclerView);
                recyclerView.setLayoutManager(new GridLayoutManager(activity,3));
                AdapterReceiveVoucherBrandTwo adapter=new AdapterReceiveVoucherBrandTwo(R.layout.adapter_voucher_brand_one_two, item.getBean().product_sku);
                recyclerView.setAdapter(adapter);
                layoutParams=new LinearLayout.LayoutParams(sizeScreen,ViewGroup.LayoutParams.WRAP_CONTENT);
                relativeLayout.setLayoutParams(layoutParams);
                break;
            case MultipleItem.ITEM_TYPE_SPAN2:
                ImageView imageView=helper.getView(R.id.imageView);
                GlideUtil.loadImageWithRadius(item.getBean().store_bgcover,imageView,DimenUtil.getDimensionPixelSize(R.dimen.dp4));
                LogUtil.e("上图："+item.getBean().store_bgcover);
                //GlideUtil.loadImageWithTopRadius(item.getBean().store_bgcover,imageView,mContext.getResources().getDimensionPixelSize(R.dimen.dp4));
                helper.setText(R.id.tv_amount,item.getBean().amount);
                layoutParams=new LinearLayout.LayoutParams(sizeSmall,ViewGroup.LayoutParams.WRAP_CONTENT);
                relativeLayout.setLayoutParams(layoutParams);
                break;
        }
    }
}
