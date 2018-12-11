package com.lexivip.lexi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.basemodule.tools.GlideUtil;
import com.basemodule.tools.Util;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.util.DensityUtil;

public class CustomRefreshHeader extends LinearLayout implements RefreshHeader {

    public CustomRefreshHeader(Context context) {
        this(context, null);
    }

    public CustomRefreshHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRefreshHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        setGravity(Gravity.CENTER);
        setBackgroundColor(Util.getColor(R.color.color_f5f7f9));
        ImageView imageView = new ImageView(context);
        GlideUtil.loadingImage(R.drawable.loading_image, imageView);
        addView(imageView, DensityUtil.dp2px(60), DensityUtil.dp2px(14));
        setMinimumHeight(DensityUtil.dp2px(48));
    }

    @Override
    public void onPulling(float percent, int offset, int height, int extendHeight) {

    }

    @Override
    public void onReleasing(float percent, int offset, int height, int extendHeight) {

    }

    @Override
    public void onReleased(RefreshLayout refreshLayout, int height, int extendHeight) {

    }


    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        return 500;
    }

    @NonNull
    public View getView() {
        return this;//真实的视图就是自己，不能返回null
    }

    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;//指定为平移，不能null
    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int extendHeight) {

    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {

    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onInitialized(RefreshKernel kernel, int height, int maxDragHeight) {
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {
    }
}
