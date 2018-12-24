package com.lexivip.lexi.orderList;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.basemodule.tools.GlideUtil;
import com.basemodule.tools.LogUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lexivip.lexi.ImageSizeConfig;
import com.lexivip.lexi.R;
import com.lexivip.lexi.view.MyRatingBar;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class AdapterEvaluate extends BaseQuickAdapter<MyOrderListBean.DataBean.OrdersBean.ItemsBean,BaseViewHolder> {
    private String shopName;
    private Context context;
    private int positions;
    public AdapterEvaluate(int layoutResId, @Nullable List<MyOrderListBean.DataBean.OrdersBean.ItemsBean> data, String shopName,  Context context) {
        super(layoutResId, data);
        this.shopName=shopName;
        this.context=context;
    }

    @Override

    protected void convert(final BaseViewHolder helper, final MyOrderListBean.DataBean.OrdersBean.ItemsBean item) {
        GlideUtil.loadImageWithFading(item.getCover()+ImageSizeConfig.SIZE_P30X2,(ImageView) helper.getView(R.id.iv_goods));
        helper.setText(R.id.tv_sale_price,"¥"+item.getDeal_price());
        TextView price=helper.getView(R.id.tv_goods_price);
        price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        helper.setText(R.id.tv_goods_price,"¥"+item.getPrice());
        helper.setText(R.id.tv_goods_name,item.getStore_name());
        StringBuilder stringBuilder=new StringBuilder();
        if(item.getS_color()!=null&&!item.getS_color().isEmpty()) {
            LogUtil.e("颜色：" + item.getS_color());
            stringBuilder.append(item.getS_color()+"/");
            LogUtil.e(stringBuilder.toString());
        }
        if (item.getS_model()!=null&&!item.getS_model().isEmpty()) {
            LogUtil.e("样式：" + item.getS_model());
            stringBuilder.append(item.getS_model()+ "/" );
            LogUtil.e(stringBuilder.toString());
        }
        if (0!=item.getS_weight()) {
            LogUtil.e("尺码：" + item.getS_weight());
            stringBuilder.append(String.valueOf(item.getS_weight()));
            LogUtil.e(stringBuilder.toString());
        }
        helper.setText(R.id.tv_goods_parm,stringBuilder.toString());
        helper.setText(R.id.tv_shop_name,shopName);
        MyRatingBar ratingBar=helper.getView(R.id.ratingBar);
        ratingBar.setStar(item.score);
        ratingBar.setOnRatingChangeListener(new MyRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float ratingCount) {
                item.score= (int) ratingCount;
            }
        });
        positions=helper.getAdapterPosition();
        EditText editText=helper.getView(R.id.et_evaluate);
        editText.setText(item.content);
        editText.addTextChangedListener(textWatcher);
        RecyclerView recyclerView=helper.getView(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(context,4));
        if (item.asset_image.size()==0){
            item.asset_image.add(null);
        }
        final AdapterEvaluateImage adapterEvaluateImage=new AdapterEvaluateImage(R.layout.item_evaluate_image,item.asset_image);
        recyclerView.setAdapter(adapterEvaluateImage);
        LogUtil.e("图片的数量："+item.asset_image.size());
        adapterEvaluateImage.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.iv_delete:
                        LogUtil.e("剩余几个"+adapterEvaluateImage.getData().size());
                        adapterEvaluateImage.remove(position);
                        //item.asset_image.remove(position);
                        adapter.notifyDataSetChanged();
                        notifyDataSetChanged();
                        break;
                    case R.id.iv_evaluate:
                        if (position==item.asset_image.size()){
                            EventBus.getDefault().post(new ClickImageItem(positions,true,position));
                        }else{
                            EventBus.getDefault().post(new ClickImageItem(positions,false,position));
                        }
                        break;
                }
            }
        });

    }

    @Override
    public void notifyLoadMoreToLoading() {
        super.notifyLoadMoreToLoading();
        LogUtil.e("itmeimage:"+this.getData().get(0).asset_image.size());
    }

    private TextWatcher textWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            getData().get(positions).content=s.toString();
        }
    };

    class ClickImageItem{
        public int position;
        public boolean isLast;
        public int itemPostion;

        public ClickImageItem(int position, boolean isLast, int itemPostion) {
            this.position = position;
            this.isLast = isLast;
            this.itemPostion = itemPostion;
        }
    }
}
