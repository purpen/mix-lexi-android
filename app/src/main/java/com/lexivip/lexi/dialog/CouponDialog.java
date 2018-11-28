package com.lexivip.lexi.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.basemodule.tools.DateUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flyco.dialog.widget.base.BaseDialog;
import com.lexivip.lexi.R;
import com.lexivip.lexi.receiveVoucher.VoucherOfficialBean;
import com.lexivip.lexi.user.login.LoginActivity;
import com.lexivip.lexi.user.login.UserProfileUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CouponDialog extends BaseDialog<CouponDialog> implements CouponContract.View{

    private ImageView iv_cancel;
    private RecyclerView recyclerView;
    private Button button;
    private CouponPresenter presenter;
    private String time;
    private List<VoucherOfficialBean.DataBean.OfficialCouponsBean> list=new ArrayList<>();
    private CouponInterface couponInterface;

    public CouponDialog(Context context, CouponInterface couponInterface) {
        super(context);
        this.couponInterface=couponInterface;
    }

    @Override
    public View onCreateView() {
        View view= View.inflate(getContext(),R.layout.dialog_coupon,null);
        iv_cancel = view.findViewById(R.id.iv_cancel);
        button = view.findViewById(R.id.button);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        presenter=new CouponPresenter(this);
        return view;
    }

    @Override
    public void setUiBeforShow() {
        Date date = new Date(System.currentTimeMillis());
        time = DateUtil.getDateString(date,DateUtil.PATTERN_DOT)+"至"+DateUtil.getSpecifiedDayBefore(date,-7,DateUtil.PATTERN_DOT);
        VoucherOfficialBean.DataBean.OfficialCouponsBean bean=new VoucherOfficialBean.DataBean.OfficialCouponsBean();
        bean.amount="10";
        bean.type_text="满10使用";
        bean.code="全场通用";
        VoucherOfficialBean.DataBean.OfficialCouponsBean bean1=new VoucherOfficialBean.DataBean.OfficialCouponsBean();
        bean1.amount="15";
        bean1.type_text="满159使用";
        bean1.code="生活良品";
        VoucherOfficialBean.DataBean.OfficialCouponsBean bean2=new VoucherOfficialBean.DataBean.OfficialCouponsBean();
        bean2.amount="25";
        bean2.type_text="满259使用";
        bean2.code="好感衣装";
        VoucherOfficialBean.DataBean.OfficialCouponsBean bean3=new VoucherOfficialBean.DataBean.OfficialCouponsBean();
        bean3.amount="35";
        bean3.type_text="满359使用";
        bean3.code="箱包&包装";
        VoucherOfficialBean.DataBean.OfficialCouponsBean bean4=new VoucherOfficialBean.DataBean.OfficialCouponsBean();
        bean4.amount="15";
        bean4.type_text="满159使用";
        bean4.code="全场通用";
        VoucherOfficialBean.DataBean.OfficialCouponsBean bean5=new VoucherOfficialBean.DataBean.OfficialCouponsBean();
        bean5.amount="5";
        bean5.type_text="满59使用";
        bean5.code="全场通用";
        list.add(bean);
        list.add(bean1);
        list.add(bean2);
        list.add(bean3);
        list.add(bean4);
        list.add(bean5);
        AdapterCoupon adapterCoupon=new AdapterCoupon(R.layout.adapter_coupon,list);
        recyclerView.setAdapter(adapterCoupon);
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserProfileUtil.isLogin()){
                    presenter.loadData();
                }else {
                    getContext().startActivity(new Intent(getContext(), LoginActivity.class));
                }
            }
        });
    }

    @Override
    public void loadData() {
        couponInterface.getReceive(true);
        dismiss();
    }

    @Override
    public void setPresenter(CouponContract.Presenter presenter) {
        setPresenter(presenter);
    }

    class AdapterCoupon extends BaseQuickAdapter<VoucherOfficialBean.DataBean.OfficialCouponsBean,BaseViewHolder> {
        public AdapterCoupon(int layoutResId, @Nullable List<VoucherOfficialBean.DataBean.OfficialCouponsBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, VoucherOfficialBean.DataBean.OfficialCouponsBean item) {
            helper.setText(R.id.tv_money,item.amount);
            helper.setText(R.id.tv_content,item.type_text);
            helper.setText(R.id.tv_range,item.code);
            helper.setText(R.id.tv_time,time);
        }
    }

    public interface CouponInterface {
        void getReceive(boolean isReceive);
    }
}
