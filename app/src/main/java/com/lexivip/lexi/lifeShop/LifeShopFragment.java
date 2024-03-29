package com.lexivip.lexi.lifeShop;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.basemodule.tools.Constants;
import com.basemodule.tools.DateUtil;
import com.basemodule.tools.DimenUtil;
import com.basemodule.tools.GlideUtil;
import com.basemodule.tools.LogUtil;
import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.Util;
import com.basemodule.tools.WaitingDialog;
import com.basemodule.ui.BaseFragment;
import com.lexivip.lexi.ImageSizeConfig;
import com.lexivip.lexi.R;
import com.lexivip.lexi.address.AddressActivity;
import com.lexivip.lexi.dialog.InquiryDialog;
import com.lexivip.lexi.index.lifehouse.LifeHouseBean;
import com.lexivip.lexi.index.selection.applyForLifeHouse.OpenLifeHouseActivity;
import com.lexivip.lexi.net.WebUrl;
import com.lexivip.lexi.shareUtil.ShareUtil;
import com.lexivip.lexi.user.login.UserProfileUtil;

import java.util.Date;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 生活馆管理
 */
public class LifeShopFragment extends BaseFragment implements View.OnClickListener, LifeShopContract.View, EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {

    private ImageView logo;
    private TextView name;
    private TextView tv_live_id;
    private TextView tv_day;
    private TextView tv_hour;
    private TextView tv_minute;
    private TextView tv_second;
    private TextView tv_gross_earnings;
    private ImageView iv_money_show;
    private ImageView iv_problem;
    private ImageView iv_show_put;
    private TextView tv_day_money;
    private TextView tv_loading_money;
    private TextView tv_order_num;
    private TextView tv_day_num;
    private TextView tv_put_money;
    private TextView tv_already_put;
    private LinearLayout ll_preserve_image;
    private LinearLayout button;
    private TextView tv_status;
    private WaitingDialog dialog;
    private LifeShopPresenter presenter;
    private String rid;
    private long endTime;
    private Date curDate;
    private boolean isShowSale = true;
    private boolean isShowCash = true;
    private String saleMoney;
    private String cashMoney;
    private String scene;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            curDate = new Date(System.currentTimeMillis());
            time = endTime - (curDate.getTime() / 1000);
            if (time > 0) {
                LogUtil.e("当前时间："+DateUtil.getDateByTimestamp(time,"dd-HH-mm-ss"));
                String[] times = (DateUtil.getDateByTimestamp(time,"dd-HH-mm-ss")).split("-");
                tv_day.setText(times[0]);
                tv_hour.setText(times[1]);
                tv_minute.setText(times[2]);
                tv_second.setText(times[3]);
            }
            handler.sendEmptyMessageDelayed(0,1000);
        }
    };
    private long time;
    private Intent intent;
    private LinearLayout ll_gross_earnings;
    private LifeShopCashBean cashBean;
    private LifeShopSaleBean saleBean;
    private LifeShopOrderBean orderBean;
    private LinearLayout linearLayout1;
    private TextView tv_sales_volume;
    private TextView tv_friend_num;
    private TextView tv_reward_money;
    private TextView tv_day_invitation;
    private TextView tv_reward_put;

    public static LifeShopFragment newInstance(){
        LifeShopFragment lifeShopFragment=new LifeShopFragment();
        return lifeShopFragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_live_shop;
    }

    @Override
    public void initView() {
        super.initView();
        dialog = new WaitingDialog(getActivity());
        presenter = new LifeShopPresenter(this);
        rid=UserProfileUtil.storeId();
        logo = getView().findViewById(R.id.iv_logo);
        name = getView().findViewById(R.id.tv_live_name);
        tv_live_id = getView().findViewById(R.id.tv_live_ID);
        tv_day = getView().findViewById(R.id.tv_day);
        tv_hour = getView().findViewById(R.id.tv_hour);
        tv_minute = getView().findViewById(R.id.tv_minute);
        tv_second = getView().findViewById(R.id.tv_second);
        tv_gross_earnings = getView().findViewById(R.id.tv_gross_earnings);
        iv_money_show = getView().findViewById(R.id.iv_money_show);
        iv_problem = getView().findViewById(R.id.iv_problem);
        iv_show_put = getView().findViewById(R.id.iv_show_put);
        tv_day_money = getView().findViewById(R.id.tv_day_money);
        tv_loading_money = getView().findViewById(R.id.tv_loading_money);
        tv_order_num = getView().findViewById(R.id.tv_order_num);
        tv_day_num = getView().findViewById(R.id.tv_day_num);
        tv_put_money = getView().findViewById(R.id.tv_put_money);
        tv_sales_volume = getView().findViewById(R.id.tv_sales_volume);
        tv_already_put = getView().findViewById(R.id.tv_already_put);
        ll_preserve_image = getView().findViewById(R.id.ll_preserve_image);
        ll_gross_earnings = getView().findViewById(R.id.ll_gross_earnings);
        button = getView().findViewById(R.id.button);
        tv_status = getView().findViewById(R.id.tv_status);
        linearLayout1 = getView().findViewById(R.id.linearLayout1);
        Button bt_invitation=getView().findViewById(R.id.bt_invitation);
        bt_invitation.setOnClickListener(this);
        tv_friend_num = getView().findViewById(R.id.tv_friend_num);
        tv_friend_num.setOnClickListener(this);
        tv_day_invitation = getView().findViewById(R.id.tv_day_invitation);
        tv_reward_money = getView().findViewById(R.id.tv_reward_money);
        tv_reward_money.setOnClickListener(this);
        tv_reward_put = getView().findViewById(R.id.tv_reward_put);
        ImageView iv_reward_problem=getView().findViewById(R.id.iv_reward_problem);
        iv_reward_problem.setOnClickListener(this);
        iv_money_show.setOnClickListener(this);
        iv_problem.setOnClickListener(this);
        iv_show_put.setOnClickListener(this);
        ll_preserve_image.setOnClickListener(this);
        tv_order_num.setOnClickListener(this);
        tv_put_money.setOnClickListener(this);
        button.setOnClickListener(this);
        ll_gross_earnings.setOnClickListener(this);
    }

    @Override
    public void loadData() {
        super.loadData();
        presenter.loadData(rid, 0);
        presenter.loadData(rid, 1);
        presenter.loadData(rid, 2);
        presenter.loadData(rid, 3);
        presenter.loadData(rid,4);
        presenter.loadData(rid,5);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_money_show:
                if (isShowSale) {
                    iv_money_show.setBackgroundResource(R.mipmap.icon_live_hidden_money);
                    isShowSale = false;
                    tv_gross_earnings.setText("***");
                    tv_sales_volume.setText("***");
                    tv_day_money.setText("***");
                    tv_loading_money.setText("***");
                } else {
                    iv_money_show.setBackgroundResource(R.mipmap.icon_live_show_money);
                    isShowSale = true;
                    if (saleBean==null) {
                        tv_gross_earnings.setText("0.00");
                        tv_sales_volume.setText("0.00");
                        tv_day_money.setText("0.00");
                        tv_loading_money.setText("0.00");
                    } else {
                        tv_gross_earnings.setText(saleBean.data.total_commission_price);
                        tv_sales_volume.setText(saleBean.data.total_payed_amount);
                        tv_day_money.setText(saleBean.data.today_commission_price);
                        tv_loading_money.setText(String.valueOf(saleBean.data.pending_commission_price));
                    }
                }
                break;
            case R.id.iv_problem:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage(Util.getString(R.string.text_pending_cash));
                builder.create().show();
                break;
            case R.id.iv_show_put:
                if (isShowCash) {
                    iv_show_put.setBackgroundResource(R.mipmap.icon_live_hidden_put);
                    isShowCash = false;
                    tv_put_money.setText("***");
                    tv_already_put.setText("***");
                } else {
                    iv_show_put.setBackgroundResource(R.mipmap.icon_live_show_put);
                    isShowCash = true;
                    if (cashBean==null) {
                        tv_put_money.setText("0.00");
                        tv_already_put.setText("0.00");
                    } else {
                        tv_put_money.setText(cashBean.data.cash_price);
                        tv_already_put.setText(cashBean.data.total_cash_price);
                    }
                }
                break;
            case R.id.ll_preserve_image:
                saveImage();
                break;
            case R.id.tv_order_num:
                intent = new Intent(getContext(), TransactionOrderActivity.class);
                intent.putExtra("rid",rid);
                getActivity().startActivity(intent);
                break;
            case R.id.tv_put_money:
                intent = new Intent(getContext(), PutForwardActivity.class);
                intent.putExtra("rid",rid);
                getActivity().startActivity(intent);
                break;
            case R.id.button:
                /*InquiryDialog inquiryDialog = new com.lexivip.lexi.dialog.InquiryDialog(getContext(),"确定拨打客服电话？","取消","拨打", new InquiryDialog.InquiryInterface() {
                    @Override
                    public void getCheck(boolean isCheck) {
                        if (!isCheck) {
                            //拨打电话
                            intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "400-2345-0000"));
                            getActivity().startActivity(intent);
                        }
                    }
                });
                inquiryDialog.show();*/
                ClipboardManager clip = (ClipboardManager)getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                //clip.getText(); // 粘贴
                //clip.setText("lexixiaoduo"); // 复制
                //创建ClipData对象
                ClipData clipData = ClipData.newPlainText("simple text copy", "lexixiaoduo");
                //添加ClipData对象到剪切板中
                clip.setPrimaryClip(clipData);
                InquiryDialog inquiryDialog1=new InquiryDialog(getContext(),"已复制到粘贴板，请添加管理员加群：\nlexixiaoduo");
                inquiryDialog1.show();
                break;
            case R.id.bt_invitation:
                intent=new Intent(getContext(),OpenLifeHouseActivity.class);
                intent.putExtra("title",R.string.text_invitation_friend_open);
                intent.putExtra("url",WebUrl.INVITATION_OPEN);
                startActivity(intent);
                break;
            case R.id.ll_gross_earnings:
                intent = new Intent(getContext(), TransactionRecordActivity.class);
                intent.putExtra("rid", rid);
                getActivity().startActivity(intent);
                break;
            case R.id.tv_friend_num:
                startActivity(new Intent(getContext(),MyFriendActivity.class));
                break;
            case R.id.tv_reward_money:
                startActivity(new Intent(getContext(),RewardActivity.class));
                break;
            case R.id.iv_reward_problem:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setMessage(Util.getString(R.string.text_pending_reward));
                builder1.create().show();
                break;
        }
    }

    /**
     * 邀请好友开馆
     */
    @AfterPermissionGranted(Constants.REQUEST_CODE_SHARE)
    private void share(){
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(getActivity(), perms)) {
            ShareUtil shareUtil=new ShareUtil(getActivity());
            shareUtil.shareInvitation(WebUrl.OPEN_SHOP,WebUrl.AUTH_GUIDE,R.mipmap.ic_launcher,"邀请你开馆","",scene);
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_photo), Constants.REQUEST_CODE_SHARE, perms);
        }
    }

    /**
     * 保存图片到图库
     */
    @AfterPermissionGranted(Constants.REQUEST_CODE_SAVE_IMAGE)
    private void saveImage() {
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(getActivity(), perms)) {
            PreserveImageDialog imageDialog = new PreserveImageDialog(getContext(), getActivity());
            imageDialog.show();
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_photo), Constants.REQUEST_CODE_SAVE_IMAGE, perms);
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
    public void showError(String error) {
        ToastUtil.showError(error);
    }

    @Override
    public void setShopData(LifeHouseBean bean) {
        GlideUtil.loadImageWithRadiusNotPlace(bean.data.logo+ ImageSizeConfig.SIZE_AVA, logo, DimenUtil.dp2px(4.0));
        name.setText(bean.data.name);
        if (1 != bean.data.phases) {
            tv_status.setText(Util.getString(R.string.text_formal_shop));
            linearLayout1.setVisibility(View.INVISIBLE);
        }else{
            tv_status.setText(Util.getString(R.string.text_internship_shop));
            linearLayout1.setVisibility(View.VISIBLE);
            endTime = bean.data.created_at + 2592000;
            handler.sendEmptyMessage(0);
        }
        scene=bean.data.ID;
        tv_live_id.setText("ID：" + bean.data.ID);

        //calendar = Calendar.getInstance();
        //simpleDateFormat = new SimpleDateFormat("dd-HH-mm-ss");
    }

    @Override
    public void setOrderData(LifeShopOrderBean bean) {
        orderBean = bean;
        tv_order_num.setText(String.valueOf(bean.data.all_count));
        tv_day_num.setText(String.valueOf(bean.data.today_count));
    }

    @Override
    public void setCashData(LifeShopCashBean bean) {
        cashBean = bean;
        if (isShowCash) {
            tv_put_money.setText(bean.data.cash_price);
            tv_already_put.setText(bean.data.total_cash_price);
        }
    }

    @Override
    public void setSaleData(LifeShopSaleBean bean) {
        saleBean = bean;
        saleMoney = bean.data.total_commission_price;
        if (isShowSale) {
            tv_gross_earnings.setText(bean.data.total_commission_price);
            tv_sales_volume.setText(bean.data.total_payed_amount);
            tv_day_money.setText(bean.data.today_commission_price);
            tv_loading_money.setText(String.valueOf(bean.data.pending_commission_price));
        }
    }

    @Override
    public void setFriendData(LifeShopFriendBean bean) {
        tv_friend_num.setText(bean.data.invite_count);
        tv_day_invitation.setText(bean.data.today_count);
    }

    @Override
    public void setRewardData(LifeShopRewardBean bean) {
        tv_reward_money.setText(bean.data.reward_price);
        tv_reward_put.setText(bean.data.pending_price);
    }

    @Override
    public void setPresenter(LifeShopContract.Presenter presenter) {
        setPresenter(presenter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onRationaleAccepted(int requestCode) {

    }

    @Override
    public void onRationaleDenied(int requestCode) {

    }
}
