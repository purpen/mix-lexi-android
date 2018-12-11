package com.lexivip.lexi.lifeShop;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.Util;
import com.basemodule.tools.WaitingDialog;
import com.basemodule.ui.BaseActivity;
import com.lexivip.lexi.R;
import com.lexivip.lexi.dialog.InquiryDialog;
import com.lexivip.lexi.user.login.UserProfileUtil;
import com.lexivip.lexi.view.CustomHeadView;
import com.umeng.qq.handler.UmengQQShareContent;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * 提现页面
 */
public class PutForwardActivity extends BaseActivity implements View.OnClickListener,PutForwardContract.View {

    private String rid;
    private WaitingDialog dialog;
    private TextView tv_put_money;
    private TextView tv_put;
    private TextView tv_put_record;
    private PutForwardPresenter presenter;
    private Button button;

    @Override
    protected int getLayout() {
        return R.layout.activity_put_forward;
    }

    @Override
    public void initView() {
        super.initView();
        Intent intent=getIntent();
        rid = intent.getStringExtra("rid");
        dialog = new WaitingDialog(this);
        CustomHeadView customHeadView=findViewById(R.id.customHeadView);
        customHeadView.setHeadCenterTxtShow(true,Util.getString(R.string.text_put_money));
        tv_put_money = findViewById(R.id.tv_put_money);
        tv_put = findViewById(R.id.tv_put);
        button = findViewById(R.id.button);
        LinearLayout ll_statements=findViewById(R.id.ll_statements);
        tv_put_record = findViewById(R.id.tv_put_record);
        ll_statements.setOnClickListener(this);
        presenter = new PutForwardPresenter(this);
        presenter.loadData(rid);
        presenter.loadRecentData(rid);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button://todo 提现待完成
                if (UserProfileUtil.isBindWX()){

                }else {
                    InquiryDialog inquiryDialog=new InquiryDialog(this, "您还未绑定微信，绑定之后才能提现。", "取消", "立即绑定", new InquiryDialog.InquiryInterface() {
                        @Override
                        public void getCheck(boolean isCheck) {
                            if (isCheck){
                                UMShareAPI.get(PutForwardActivity.this).getPlatformInfo(PutForwardActivity.this, SHARE_MEDIA.WEIXIN,authListener);
                            }
                        }
                    });
                }
                break;
            case R.id.ll_statements:
                Intent intent=new Intent(this,AccountStatementActivity.class);
                intent.putExtra("rid",rid);
                startActivity(intent);
                break;
        }
    }

    UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }
        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            presenter.bindWX(data);
        }
        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            ToastUtil.showError("授权回调失败");
        }
        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            ToastUtil.showError("取消授权");
        }
    };

    @Override
    public void showLoadingView() {
        dialog.show();
    }

    @Override
    public void dismissLoadingView() {
        dialog.dismiss();
    }

    @Override
    public void showError(String error) {
        dialog.dismiss();
        ToastUtil.showInfo(error);
    }

    @Override
    public void setData(LifeShopCashBean bean) {
        tv_put_money.setText(bean.data.cash_price);
        tv_put.setText(bean.data.total_cash_price);
        if (10>=Double.valueOf(bean.data.cash_price)){
            button.setEnabled(true);
        }else {
            button.setEnabled(false);
        }
    }

    @Override
    public void setCashRecent(PutForwardBean bean) {
        if (0!=bean.data.actual_amount) {
            tv_put_record.setText(Util.getString(R.string.text_put_money_recent)+bean.data.actual_amount);
        }
    }

    @Override
    public void setPresenter(PutForwardContract.Presenter presenter) {
        setPresenter(presenter);
    }
}
