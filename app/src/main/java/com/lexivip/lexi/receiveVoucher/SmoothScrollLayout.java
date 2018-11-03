package com.lexivip.lexi.receiveVoucher;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.basemodule.tools.DimenUtil;
import com.basemodule.tools.GlideUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lexivip.lexi.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class SmoothScrollLayout extends FrameLayout {

    private ScrollHandler mHandler;
    private MyAdapter mAdapter;
    private RecyclerView recyclerView;

    public SmoothScrollLayout(@NonNull Context context) {
        this(context, null);
    }

    public SmoothScrollLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmoothScrollLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.fragment_smooth_scroll, this);
        mHandler = new ScrollHandler(this);
        mAdapter = new MyAdapter(R.layout.item_smooth_scroll,null);
        recyclerView = findViewById(R.id.rvNews);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return false;       //拦截事件
    }

    public void addData(List<VoucherNoticeBean.DataBean.CouponLinesBean> data){
        mAdapter.addData(data);
    }

    public void setData(List<VoucherNoticeBean.DataBean.CouponLinesBean> data) {
        mAdapter.setNewData(data);
        if (data != null && data.size() > 0) {
            mHandler.sendEmptyMessageDelayed(0, 100);
        }
    }

    public void smoothScroll() {
        recyclerView.smoothScrollBy(0, 5);
        mHandler.sendEmptyMessageDelayed(0, 100);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeCallbacksAndMessages(null);
    }

    /**
     * 弱引用防止内存泄露
     */
    private static class ScrollHandler extends Handler {
        private WeakReference<SmoothScrollLayout> view;

        public ScrollHandler(SmoothScrollLayout mView) {
            view = new WeakReference<>(mView);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (view.get() != null) {
                view.get().smoothScroll();
            }
        }
    }

    private class MyAdapter extends BaseQuickAdapter<VoucherNoticeBean.DataBean.CouponLinesBean,BaseViewHolder> {

        public MyAdapter(int layoutResId, @Nullable List<VoucherNoticeBean.DataBean.CouponLinesBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, VoucherNoticeBean.DataBean.CouponLinesBean item) {
            helper.setText(R.id.tv_name, item.user_info.user_name + "刚刚领取了");
            helper.setText(R.id.tv_amount, item.amount + "元");
            ImageView imageView = helper.getView(R.id.imageView);
            GlideUtil.loadCircleImageWidthDimen(item.user_info.user_logo, imageView, DimenUtil.dp2px(25.0));
            // 1: 红包; 2: 官方优惠券; 3: 店铺优惠券 4: 现金
            switch (item.activity_type) {
                case 1:
                    helper.setText(R.id.tv_type, "红包");
                    break;
                case 2:
                    helper.setText(R.id.tv_type, "官方优惠券");
                    break;
                case 3:
                    helper.setText(R.id.tv_type, "店铺优惠券");
                    break;
                case 4:
                    helper.setText(R.id.tv_type, "现金");
                    break;
            }
        }
    }

}
