package com.thn.lexi.orderList;

import com.basemodule.tools.LogUtil;
import com.basemodule.ui.BaseActivity;
import com.thn.lexi.R;
import com.thn.lexi.view.MyRatingBar;

/**
 * 评价页面
 */
public class EvaluateActivity extends BaseActivity{
    @Override
    protected int getLayout() {
        return R.layout.item_evaluate;
    }

    @Override
    public void initView() {
        super.initView();
        MyRatingBar myRatingBar=findViewById(R.id.ratingBar);
        myRatingBar.setOnRatingChangeListener(new MyRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float ratingCount) {
                LogUtil.e("点击了第几个："+ratingCount);
            }
        });
    }
}
