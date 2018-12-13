package com.basemodule.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.Pools;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.basemodule.tools.DimenUtil;
import com.basemodule.tools.GlideUtil;
import com.basemodule.tools.LogUtil;
import com.lexivip.basemodule.R;

import java.lang.ref.WeakReference;
import java.util.List;

public class ContentScrollView extends LinearLayout {

    private static final int DEFAULT_SIZE = 3;

    private MyHandler handler;

    public ContentScrollView(Context context) {
        this(context, null);
    }

    public ContentScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ContentScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    public void setData(List<ItemBean> list) {
        if (list == null || list.size() == 0) LogUtil.e("list is null or size is zero");
        handler = new MyHandler(this, list);
    }

    /**
     * 开始滚动
     */
    public void start() {
        if (handler!=null) handler.sendEmptyMessage(0);
    }

    /**
     * 停止滚动
     */
    public void stop() {
        if (handler!=null) handler.removeMessages(0);
    }

    /**
     * 移除所有消息
     */
    public void destroy() {
        if (handler!=null) handler.removeCallbacksAndMessages(null);
    }

    private void initViews() {
        setTransition();
    }

    private void setTransition() {
        LayoutTransition transition = new LayoutTransition();
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 0, 1);
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 0, 1);

        ObjectAnimator valueAnimator = ObjectAnimator.ofPropertyValuesHolder(null, new PropertyValuesHolder[]{scaleX, scaleY})
                .setDuration(transition.getDuration(LayoutTransition.APPEARING));
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                ObjectAnimator objectAnimator = (ObjectAnimator) animation;
                View view = (View) objectAnimator.getTarget();
                view.setPivotX(0f);
                view.setPivotY(view.getMeasuredHeight());
            }
        });
        transition.setAnimator(LayoutTransition.APPEARING, valueAnimator);

        PropertyValuesHolder alphaOut = PropertyValuesHolder.ofFloat("alpha", 1f, 0f);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder((View) null, alphaOut).setDuration(300);

        transition.setAnimator(LayoutTransition.DISAPPEARING, objectAnimator);
        transition.setStartDelay(LayoutTransition.APPEARING, 300);
        transition.setStartDelay(LayoutTransition.CHANGE_DISAPPEARING, 300);
        setLayoutTransition(transition);
    }


    private static class MyHandler extends Handler {
        private int index = 0;
        private WeakReference<LinearLayout> reference;
        private List<ItemBean> list;
        private int size;
        private LayoutParams layoutParams;

        MyHandler(LinearLayout linearLayout, List<ItemBean> list) {
            reference = new WeakReference<>(linearLayout);
            this.list = list;
            this.size = list.size();
            layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, DimenUtil.dp2px(20.0));
            layoutParams.topMargin = DimenUtil.dp2px(5.0);
            layoutParams.gravity = Gravity.BOTTOM | Gravity.END;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LinearLayout linearLayout = reference.get();
            int childCount = linearLayout.getChildCount();
            if (msg.what == 0 && childCount == DEFAULT_SIZE) {
                linearLayout.removeViewAt(0);
            }
            if (index % size == 0) {
                index = 0;
            }

            LinearLayoutItem view = obtainView();
            GlideUtil.loadCircleImageWidthDimen(list.get(index).avatar, view.getImageView(), DimenUtil.dp2px(16.0));
            view.getTextView().setText(list.get(index).content);
            view.setBackgroundResource(R.drawable.bg_colorccff5f9ce6_round);
            linearLayout.addView(view);
            for (int i=0;i< linearLayout.getChildCount()-1 ;i++){
                linearLayout.getChildAt(i).setBackgroundResource(R.drawable.bg_colorcc000000_round);
            }
            sendEmptyMessageDelayed(0, 2000);
            index++;
        }

        /**
         * 返回一条数据
         *
         * @return
         */
        private LinearLayoutItem obtainView() {
            LinearLayoutItem view = new LinearLayoutItem(reference.get().getContext());
            view.setLayoutParams(layoutParams);
            return view;
        }
    }

}
