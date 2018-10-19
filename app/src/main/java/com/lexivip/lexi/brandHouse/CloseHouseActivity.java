package com.lexivip.lexi.brandHouse;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.basemodule.tools.DateUtil;
import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.Util;
import com.basemodule.tools.WaitingDialog;
import com.basemodule.ui.BaseActivity;
import com.lexivip.lexi.R;

public class CloseHouseActivity extends BaseActivity implements CloseHouseContract.View {

    private WaitingDialog dialog;
    private String rid;
    private ImageView imageView;
    private TextView tv_close_time;
    private TextView tv_recovery;
    private TextView tv_context;
    private ImageView iv_close;

    @Override
    protected int getLayout() {
        return R.layout.activity_close_house;
    }

    @Override
    public void initView() {
        super.initView();
        dialog = new WaitingDialog(this);
        Intent intent = getIntent();
        String rid = intent.getStringExtra("rid");
        CloseHousePresenter presenter = new CloseHousePresenter(this);
        imageView = findViewById(R.id.image);
        tv_close_time = findViewById(R.id.tv_close_time);
        tv_recovery = findViewById(R.id.tv_recovery);
        tv_context = findViewById(R.id.tv_context);
        ImageView iv_close = findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        presenter.loadData(rid);
    }

    @Override
    public void showLoadingView() {
        dialog.show();
    }

    @Override
    public void dismissLoadingView() {
        dialog.dismiss();
    }

    @Override
    public void showError(@NonNull String error) {
        dialog.dismiss();
        ToastUtil.showError(error);
    }

    @Override
    public void setData(BrandHouseNoticeBean bean) {
        if (bean.data.is_closed) {
            tv_close_time.setText(Util.getString(R.string.text_brand_close_time)
                    + DateUtil.getDateByTimestamp(Long.valueOf(bean.data.begin_date), DateUtil.PATTERN_DOT)
                    + "—" + DateUtil.getDateByTimestamp(Long.valueOf(bean.data.end_date), DateUtil.PATTERN_DOT));
            tv_recovery.setText(Util.getString(R.string.text_recovery_time)
                    + DateUtil.getDateByTimestamp(Long.valueOf(bean.data.delivery_date), DateUtil.PATTERN_DOT));
        } else {
            imageView.setVisibility(View.GONE);
            tv_close_time.setVisibility(View.GONE);
            tv_recovery.setVisibility(View.GONE);
        }
        tv_context.setText(Html.fromHtml(bean.data.announcement));
    }

    @Override
    public void setPresenter(CloseHouseContract.Presenter presenter) {
        setPresenter(presenter);
    }
}
