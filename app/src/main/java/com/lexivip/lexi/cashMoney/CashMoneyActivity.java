package com.lexivip.lexi.cashMoney;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.Util;
import com.basemodule.tools.WaitingDialog;
import com.basemodule.ui.BaseActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lexivip.lexi.R;
import com.lexivip.lexi.dialog.InquiryDialog;
import com.lexivip.lexi.user.login.UserProfileBean;
import com.lexivip.lexi.user.login.UserProfileUtil;

import java.util.ArrayList;
import java.util.List;

public class CashMoneyActivity extends BaseActivity implements View.OnClickListener ,CashMoneyContract.View{
    private List<CashItemBean> list;
    private AdapterCashMoney adapterCashMoney;
    private TextView tv_money;
    private RecyclerView recyclerView;
    private RelativeLayout rl_select_wx;
    private ImageView tv_select_wx;
    private RelativeLayout rl_select_alipay;
    private ImageView tv_select_alipay;
    private Button button;
    private int cashType=1;
    private int cashMoney;
    private CashMoneyPresenter presenter;
    private WaitingDialog dialog;

    @Override
    protected int getLayout() {
        return R.layout.activity_cash_money;
    }

    @Override
    public void initView() {
        super.initView();
        presenter=new CashMoneyPresenter(this);
        ImageView head_goback=findViewById(R.id.head_goback);
        dialog=new WaitingDialog(this);
        head_goback.setOnClickListener(this);
        TextView head_center_tv=findViewById(R.id.head_center_tv);
        head_center_tv.setText(Util.getString(R.string.text_put_money));
        TextView tv_cash=findViewById(R.id.tv_cash);
        tv_cash.setOnClickListener(this);
        tv_money = findViewById(R.id.tv_money);
        recyclerView = findViewById(R.id.recyclerView);
        rl_select_wx = findViewById(R.id.rl_select_wx);
        rl_select_wx.setOnClickListener(this);
        tv_select_wx = findViewById(R.id.tv_select_wx);
        rl_select_alipay = findViewById(R.id.rl_select_alipay);
        rl_select_alipay.setOnClickListener(this);
        tv_select_alipay = findViewById(R.id.tv_select_alipay);
        button = findViewById(R.id.bt_put);
        button.setOnClickListener(this);
        TextView textView=findViewById(R.id.textView);
        SpannableStringBuilder builder=new SpannableStringBuilder(Util.getString(R.string.text_need_one));
        builder.setSpan(new ForegroundColorSpan(Util.getColor(R.color.color_ff6666)),6,9,Spanned.SPAN_COMPOSING);
        textView.setText(builder);
        recyclerView.setLayoutManager(new GridLayoutManager(this,4));
        setList();
        adapterCashMoney = new AdapterCashMoney(R.layout.item_cash_money,list);
    }

    @Override
    public void installListener() {
        super.installListener();
        adapterCashMoney.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                selectCash(position);
                switch (position){
                    case 0:
                        cashMoney=1;
                        break;
                    case 1:
                        cashMoney=2;
                        break;
                    case 2:
                        cashMoney=3;
                        break;
                    case 3:
                        cashMoney=4;
                        break;
                    case 4:
                        cashMoney=10;
                        break;
                    case 5:
                        cashMoney=20;
                        break;
                }
                adapterCashMoney.notifyDataSetChanged();
            }
        });
    }

    private void setList(){
        list=new ArrayList<>();
        CashItemBean cashItemBean=new CashItemBean();
        cashItemBean.isSelect=true;
        cashItemBean.name="1元";
        list.add(cashItemBean);
        CashItemBean cashItemBean1=new CashItemBean();
        cashItemBean1.name="2元";
        list.add(cashItemBean1);
        CashItemBean cashItemBean2=new CashItemBean();
        cashItemBean2.name="3元";
        list.add(cashItemBean2);
        CashItemBean cashItemBean3=new CashItemBean();
        cashItemBean3.name="4元";
        list.add(cashItemBean3);
        CashItemBean cashItemBean4=new CashItemBean();
        cashItemBean4.name="10元";
        list.add(cashItemBean4);
        CashItemBean cashItemBean5=new CashItemBean();
        cashItemBean5.name="20元";
        list.add(cashItemBean5);
    }

    private void selectCash(int position){
        for (int i=0;i<list.size();i++){
            if (i==position){
                list.get(i).isSelect = true;
            }else {
                list.get(i).isSelect = false;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.head_goback:
                finish();
                break;
            case R.id.tv_cash:
                break;
            case R.id.rl_select_wx:
                cashType=1;
                selectCashType();
                rl_select_wx.setBackgroundResource(R.drawable.bg_color5fe4b1_line_radiu4);
                tv_select_wx.setVisibility(View.VISIBLE);
                break;
            case R.id.rl_select_alipay:
                cashType=2;
                selectCashType();
                rl_select_alipay.setBackgroundResource(R.drawable.bg_color5fe4b1_line_radiu4);
                tv_select_alipay.setVisibility(View.VISIBLE);
                break;
            case R.id.bt_put:
                if (cashType==1){
                    final UserProfileBean.DataBean.ProfileBean bean=UserProfileUtil.getUserData().data.profile;
                    InquiryDialog inquiryDialog=new InquiryDialog(this, bean.nick_name, bean.wx_avatar, "确定", "取消", new InquiryDialog.InquiryInterface() {
                        @Override
                        public void getCheck(boolean isCheck) {
                            if (!isCheck){
                                presenter.loadCash("1",bean.openid,null,null,cashMoney);
                            }
                        }
                    });
                    inquiryDialog.show();
                }else {

                }
                break;
        }
    }

    private void selectCashType(){
        rl_select_wx.setBackgroundResource(R.drawable.bg_colorccc_line_radiu4);
        tv_select_wx.setVisibility(View.INVISIBLE);
        rl_select_alipay.setBackgroundResource(R.drawable.bg_colorccc_line_radiu4);
        tv_select_alipay.setVisibility(View.INVISIBLE);
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
    public void showError(String error) {
        if (dialog!=null){
            dialog.dismiss();
        }
        ToastUtil.showError(error);
    }

    @Override
    public void setCashResult(CashMoneyBean bean) {
        Intent intent=new Intent(this,CashTimeActivity.class);
        intent.putExtra("data",bean);
        startActivity(intent);
    }

    @Override
    public void setPresenter(CashMoneyContract.Presenter presenter) {
        setPresenter(presenter);
    }

}
