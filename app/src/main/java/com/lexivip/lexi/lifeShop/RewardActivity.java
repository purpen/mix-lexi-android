package com.lexivip.lexi.lifeShop;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.basemodule.tools.LogUtil;
import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.Util;
import com.basemodule.tools.WaitingDialog;
import com.basemodule.ui.BaseActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lexivip.lexi.R;
import com.lexivip.lexi.cashMoney.CashAlipayActivity;
import com.lexivip.lexi.cashMoney.CashMoneyActivity;
import com.lexivip.lexi.cashMoney.CashMoneyBean;
import com.lexivip.lexi.cashMoney.CashTimeActivity;
import com.lexivip.lexi.dialog.InquiryDialog;
import com.lexivip.lexi.user.login.UserProfileBean;
import com.lexivip.lexi.user.login.UserProfileUtil;
import com.lexivip.lexi.user.setting.SettingActivity;
import com.lexivip.lexi.view.CustomHeadView;

public class RewardActivity extends BaseActivity implements View.OnClickListener , RewardContract.View{

    private TextView tv_reward;
    private TextView tv_already_cash;
    private TextView tv_loading_cash;
    private TextView tv_cash;
    private AdapterReward adapterReward;
    private WaitingDialog dialog;
    private int page=1;
    private boolean isShow=true;
    private String reward_price;
    private String cumulative_cash_amount;
    private String pending_price;
    private RewardPresentre presentre;
    private RecyclerView recyclerView;

    @Override
    protected int getLayout() {
        return R.layout.activity_reward;
    }

    @Override
    public void initView() {
        super.initView();
        dialog = new WaitingDialog(this);
        presentre = new RewardPresentre(this);
        CustomHeadView customHeadView=findViewById(R.id.customHeadView);
        customHeadView.setHeadCenterTxtShow(true,R.string.text_my_reward);
        customHeadView.setRightTxt(getString(R.string.text_cash_record),Util.getColor(R.color.color_666));
        TextView right=customHeadView.getHeadRightTV();
        right.setOnClickListener(this);
        ImageView iv_money_show=findViewById(R.id.iv_money_show);
        iv_money_show.setOnClickListener(this);
        tv_reward = findViewById(R.id.tv_reward);
        tv_already_cash = findViewById(R.id.tv_already_cash);
        tv_loading_cash = findViewById(R.id.tv_loading_cash);
        ImageView iv_problem=findViewById(R.id.iv_problem);
        iv_problem.setOnClickListener(this);
        tv_cash = findViewById(R.id.tv_cash);
        Button button=findViewById(R.id.button);
        button.setOnClickListener(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterReward = new AdapterReward(R.layout.item_reward);
        recyclerView.setAdapter(adapterReward);
        presentre.loadData(page);
        presentre.loadReward();
    }

    @Override
    public void installListener() {
        super.installListener();
        adapterReward.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                presentre.loadData(page);
            }
        },recyclerView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                if (10>Double.valueOf(tv_cash.getText().toString())){
                    ToastUtil.showError("可提现金额超过10元才可提现");
                }else {
                    presentre.loadCashCount();
                }
                break;
            case R.id.iv_money_show:
                isShow=!isShow;
                if (isShow){
                    tv_reward.setText("***");
                    tv_already_cash.setText("***");
                    tv_loading_cash.setText("***");
                }else {
                    tv_reward.setText(reward_price);
                    tv_already_cash.setText(cumulative_cash_amount);
                    tv_loading_cash.setText(pending_price);
                }
                break;
            case R.id.iv_problem:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setMessage(Util.getString(R.string.text_pending_reward));
                builder1.create().show();
                break;
                //TODO 接口待添加
            case R.id.tv_head_right:
                break;
        }
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
    public void loadMoreEnd() {
        adapterReward.loadMoreEnd();
    }

    @Override
    public void loadMoreComplete() {
        adapterReward.loadMoreComplete();
    }

    @Override
    public void loadMoreFail() {
        adapterReward.loadMoreFail();
    }

    @Override
    public void showError(String error) {
        if (dialog!=null){
            dismissLoadingView();
        }
        ToastUtil.showError(error);
    }

    @Override
    public void setData(RewardBean bean) {
        if (page==1){
            adapterReward.setNewData(bean.data.rewards);
        }else {
            adapterReward.addData(bean.data.rewards);
        }
        page++;
    }

    @Override
    public void setReward(LifeShopRewardBean bean) {
        reward_price = bean.data.reward_price;
        tv_reward.setText(bean.data.reward_price);
        cumulative_cash_amount = bean.data.cumulative_cash_amount;
        tv_already_cash.setText(bean.data.cumulative_cash_amount);
        pending_price = bean.data.pending_price;
        tv_loading_cash.setText(bean.data.pending_price);
        tv_cash.setText(bean.data.cash_amount);
    }

    @Override
    public void setCashCount(int count) {
        LogUtil.e("次数"+count);
            if (count>3){
                InquiryDialog inquiryDialog1=new InquiryDialog(this,"你今日提现已达三次\n明日再来吧！");
                inquiryDialog1.show();
            }else {
                RewardDialog rewardDialog=new RewardDialog(this, new RewardDialog.InquiryInterface() {
                    @Override
                    public void getType(int type) {
                        if (type==1){
                            final UserProfileBean.DataBean.ProfileBean bean=UserProfileUtil.getUserData().data.profile;
                            if (!UserProfileUtil.isBindWX()){
                                InquiryDialog inquiryDialog=new InquiryDialog(RewardActivity.this, "您还未绑定微信", "取消", "去绑定", new InquiryDialog.InquiryInterface() {
                                    @Override
                                    public void getCheck(boolean isCheck) {
                                        if (!isCheck){
                                            startActivity(new Intent(RewardActivity.this,SettingActivity.class));
                                        }
                                    }
                                });
                                inquiryDialog.show();
                            }else {
                                InquiryDialog inquiryDialog=new InquiryDialog(RewardActivity.this, bean.nick_name, bean.wx_avatar, "取消", "确定", new InquiryDialog.InquiryInterface() {
                                    @Override
                                    public void getCheck(boolean isCheck) {
                                        if (!isCheck){
                                            presentre.cashMoney(bean.openid);
                                        }
                                    }
                                },1);
                                inquiryDialog.show();
                            }
                        }else {
                            Intent intent=new Intent(RewardActivity.this,CashAlipayActivity.class);
                            intent.putExtra("amount",tv_cash.getText().toString());
                            intent.putExtra("type",2);
                            startActivity(intent);
                        }
                    }
                });
                rewardDialog.show();
            }
    }

    @Override
    public void setCash(CashMoneyBean bean) {
        Intent intent=new Intent(this,CashTimeActivity.class);
        intent.putExtra("data",bean);
        startActivity(intent);
    }

    @Override
    public void setPresenter(RewardContract.Presenter presenter) {
        setPresenter(presenter);
    }
}
