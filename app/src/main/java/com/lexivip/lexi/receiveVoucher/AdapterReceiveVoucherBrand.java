package com.lexivip.lexi.receiveVoucher;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.basemodule.tools.DimenUtil;
import com.basemodule.tools.GlideUtil;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.lexivip.lexi.R;

import java.util.List;

public class AdapterReceiveVoucherBrand extends BaseMultiItemQuickAdapter<AdapterReceiveVoucherBrand.MultipleItem,BaseViewHolder> {

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
        ImageView iv_logo=helper.getView(R.id.iv_logo);
        GlideUtil.loadImageWithRadius(item.bean.store_logo,iv_logo,DimenUtil.dp2px(R.dimen.dp5));
        helper.setText(R.id.tv_name,item.bean.store_name);
        helper.setText(R.id.tv_full,"满"+item.bean.min_amount+"可用");
        switch (item.getItemType()){
            case 1:
                helper.setText(R.id.tv_price,item.bean.amount);
                RecyclerView recyclerView=helper.getView(R.id.recyclerView);
                recyclerView.setLayoutManager(new GridLayoutManager(activity,3));
                AdapterReceiveVoucherBrandTwo adapter=new AdapterReceiveVoucherBrandTwo(R.layout.adapter_voucher_brand_one_two, item.bean.product_sku);
                recyclerView.setAdapter(adapter);
                break;
            case 2:
                ImageView imageView=helper.getView(R.id.imageView);
                GlideUtil.loadImageWithTopRadius(item.bean.store_bgcover,imageView,DimenUtil.dp2px(R.dimen.dp4));
                helper.setText(R.id.tv_amount,item.bean.amount);
                break;
        }
    }

    static class MultipleItem implements MultiItemEntity{
        public static int ITEM_TYPE_SPAN1=1;
        public static int ITEM_TYPE_SPAN2=2;
        public static int itemType;
        public static VoucherBrandBean.DataBean.CouponsBean bean;

        public MultipleItem(int itemType,VoucherBrandBean.DataBean.CouponsBean bean) {
            this.itemType=itemType;
            this.bean=bean;
        }



        @Override
        public int getItemType() {
            return itemType;
        }
    }
}
