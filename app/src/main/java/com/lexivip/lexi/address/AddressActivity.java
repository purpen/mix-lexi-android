package com.lexivip.lexi.address;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.basemodule.tools.Constants;
import com.basemodule.tools.GlideUtil;
import com.basemodule.tools.LogUtil;
import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.Util;
import com.basemodule.tools.WaitingDialog;
import com.basemodule.ui.BaseActivity;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.lexivip.lexi.R;
import com.lexivip.lexi.album.ImageCropActivity;
import com.lexivip.lexi.album.ImageUtils;
import com.lexivip.lexi.album.PicturePickerUtils;
import com.lexivip.lexi.orderList.InquiryDialog;
import com.lexivip.lexi.user.areacode.CountryAreaCodeBean;
import com.lexivip.lexi.user.areacode.MessageAreaCode;
import com.lexivip.lexi.user.completeinfo.UploadTokenBean;
import com.lexivip.lexi.view.CustomHeadView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 收货地址详情页面
 */
public class AddressActivity extends BaseActivity implements View.OnClickListener, AddressContract.View, EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {
    private Context context;
    private WaitingDialog dialog;
    private AddressPresenter presenter;
    private Button bt_save;
    private CustomHeadView customHeadView;
    private EditText et_name;
    private EditText et_id;
    private EditText et_mobile;
    private EditText et_code;
    private TextView tv_mobile;
    private LinearLayout ll_country;
    private LinearLayout ll_city;
    private EditText et_detailed;
    private ImageView iv_position;
    private ImageView iv_opposion;
    private Button bt_delete;
    private Switch swit;
    private boolean isNew;
    private boolean isForeign;
    private TextView tv_city;
    private TextView tv_country;
    private List<String> countryList;//国家的数据
    private Map<String, ArrayList<CityBean.CityNameBean>> map;
    private UploadTokenBean bean;
    private boolean isPosition;
    private String id_card_front = null;
    private String id_card_back = null;
    private String addressId;
    private AddressBean.DataBean dataBean;
    private int provinceId;
    private int cityId;
    private int areaId;
    private boolean isdefault;
    private String id_card = null;
    private HashMap<String, ArrayList<CityBean.CityNameBean>> cityMap;
    private int countryID;
    private int newCountryID;

    @Override
    protected int getLayout() {
        return R.layout.activity_select_address;
    }

    @Override
    public void initView() {
        context = this;
        //注册eventBus
        EventBus.getDefault().register(this);
        dialog = new WaitingDialog(AddressActivity.this);
        dataBean = new AddressBean.DataBean();
        super.initView();
        presenter = new AddressPresenter(this);

        customHeadView = findViewById(R.id.customHeadView);
        bt_save = findViewById(R.id.bt_save);
        et_name = findViewById(R.id.editText_name);
        et_id = findViewById(R.id.et_ID);
        et_mobile = findViewById(R.id.editText_mobile);
        et_code = findViewById(R.id.editText_code);
        tv_mobile = findViewById(R.id.textView_mobile);
        ll_country = findViewById(R.id.ll_country);
        ll_city = findViewById(R.id.ll_region);
        et_detailed = findViewById(R.id.editText_detailed);
        iv_position = findViewById(R.id.iv_photo_position);
        iv_opposion = findViewById(R.id.iv_photo_opposite);
        bt_delete = findViewById(R.id.bt_delete);
        swit = findViewById(R.id.Switch);
        tv_city = findViewById(R.id.tv_region);
        tv_country = findViewById(R.id.tv_country);
        if (isNew) {
            customHeadView.setHeadCenterTxtShow(true, R.string.title_new_address);
            bt_delete.setVisibility(View.GONE);
        } else {
            customHeadView.setHeadCenterTxtShow(true, R.string.title_edit_address);
            bt_delete.setVisibility(View.VISIBLE);
        }

        RelativeLayout rl_photo = findViewById(R.id.rl_photo);
        LinearLayout ll_ID = findViewById(R.id.ll_ID);
        TextView tv_remind = findViewById(R.id.tv_remind);

        if (isForeign) {
            rl_photo.setVisibility(View.VISIBLE);
            ll_ID.setVisibility(View.VISIBLE);
            tv_remind.setVisibility(View.VISIBLE);
        } else {
            rl_photo.setVisibility(View.GONE);
            ll_ID.setVisibility(View.GONE);
            tv_remind.setVisibility(View.GONE);
        }

        if (isNew) {
            bt_delete.setVisibility(View.GONE);
        } else {
            bt_delete.setVisibility(View.VISIBLE);
        }

        bt_save.setOnClickListener(this);
        tv_mobile.setOnClickListener(this);
        bt_delete.setOnClickListener(this);
        ll_city.setOnClickListener(this);
        ll_country.setOnClickListener(this);
        iv_position.setOnClickListener(this);
        iv_opposion.setOnClickListener(this);
        swit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isdefault = isChecked;
            }
        });
    }

    @Override
    public void installListener() {
        super.installListener();
        //如果是编辑地址，先请求网络获取地址
        if (!isNew || isForeign)
            presenter.loadData(addressId);
    }

    @Override
    public void requestNet() {
        super.requestNet();
        presenter.getToken();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_save:
                if (setDataBean())
                    presenter.saveAddress(dataBean, isForeign, id_card, id_card_front, id_card_back);
                break;
            case R.id.bt_delete:
                InquiryDialog inquiryDialog = new InquiryDialog("确定删除地址？", this, new InquiryDialog.ImagePopwindowInterface() {
                    @Override
                    public void getCheck(boolean isCheck) {
                        if (isCheck)
                            presenter.deleteAddress(addressId);
                    }
                });
                break;
            case R.id.ll_region:
                if (countryID==0){
                    if (newCountryID==0) {
                        ToastUtil.showError("请先选择国家");
                    }else {
                        countryID = newCountryID;
                        presenter.loadCityData(String.valueOf(countryID));
                    }
                }else {
                    if (cityMap != null&&countryID==newCountryID) {
                        setCityData(cityMap);
                    } else {
                        countryID=newCountryID;
                        presenter.loadCityData(String.valueOf(countryID));
                    }
                }
                break;
            case R.id.ll_country:
                presenter.loadCountry();
                break;
            case R.id.textView_mobile:
                //startActivity(new Intent(context,SelectCountryOrAreaActivity.class));
                break;
            case R.id.iv_photo_position:

                isPosition = true;
                String[] result = Util.getStringArray(R.array.strings_photo_titles);
                final ActionSheetDialog dialog0 = new ActionSheetDialog(context, result, null);
                dialog0.itemTextColor(Util.getColor(R.color.color_333));
                TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                        0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
                animation.setInterpolator(new DecelerateInterpolator());
                dialog0.layoutAnimation(new LayoutAnimationController(animation));
                dialog0.isTitleShow(false).show();
                dialog0.setOnOperItemClickL(new OnOperItemClickL() {
                    @Override
                    public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                        LogUtil.e("点击了第几个：" + position);
                        switch (position) {
                            case 0:
                                cameraTask();
                                break;
                            case 1:
                                albumTask();
                                break;
                        }
                        dialog0.dismiss();
                    }
                });
                break;
            case R.id.iv_photo_opposite:
                isPosition = false;
                String[] results = Util.getStringArray(R.array.strings_photo_titles);
                final ActionSheetDialog dialog1 = new ActionSheetDialog(context, results, null);
                dialog1.itemTextColor(Util.getColor(R.color.color_333));
                TranslateAnimation animations = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                        0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
                animations.setInterpolator(new DecelerateInterpolator());
                dialog1.layoutAnimation(new LayoutAnimationController(animations));
                dialog1.isTitleShow(false).show();
                dialog1.setOnOperItemClickL(new OnOperItemClickL() {
                    @Override
                    public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0:
                                cameraTask();
                                break;
                            case 1:
                                albumTask();
                                break;
                        }
                        dialog1.dismiss();
                    }
                });
                break;
        }
    }

    @Override
    public void getIntentData() {
        super.getIntentData();
        Intent intent = getIntent();
        isNew = intent.getBooleanExtra("isNew", true);
        isForeign = intent.getBooleanExtra("isForeign", false);
        addressId = intent.getStringExtra(AddressActivity.class.getSimpleName());

    }

    private boolean setDataBean() {
        if (et_name.getText().toString().isEmpty()) {
            ToastUtil.showInfo("请输入姓名");
        } else {
            dataBean.setFirst_name(et_name.getText().toString());
            if (et_mobile.getText().toString().isEmpty())
                ToastUtil.showInfo("请输入手机号");
            else {
                dataBean.setMobile(et_mobile.getText().toString());
                et_mobile.getText().toString();
                if (areaId == 0) {
                    ToastUtil.showInfo("请选择地址");
                } else {
                    dataBean.setProvince_id(provinceId);
                    dataBean.setCity_id(cityId);
                    dataBean.setTown_id(areaId);
                    if (et_detailed.getText().toString().isEmpty()) {
                        ToastUtil.showInfo("请输入详细地址");
                    } else {
                        if (!isForeign) {
                            dataBean.setStreet_address(et_detailed.getText().toString());
                            //dataBean.setCountry_id(Integer.valueOf(rid));
                            dataBean.setZipcode(et_code.getText().toString());
                            dataBean.setIs_default(isdefault);
                            return true;
                        } else {
                            if (id_card.isEmpty()) {
                                ToastUtil.showInfo("请输入身份证号");
                            } else {
                                id_card = et_id.getText().toString();
                                if (!id_card_back.isEmpty() && !id_card_front.isEmpty()) {
                                    return true;
                                } else {
                                    ToastUtil.showInfo("请上传身份证照片");
                                }
                            }
                        }
                    }
                }
            }

        }

        return false;
    }

    @Override
    public void setPresenter(AddressContract.Presenter presenter) {
        setPresenter(presenter);
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
    public void setAddressData(AddressBean.DataBean data) {
        tv_country.setText(data.getCountry_name());
        countryID=data.getCountry_id();
        newCountryID=data.getCountry_id();
        et_name.setText(data.getFull_name());
        et_mobile.setText(data.getMobile());
        swit.setChecked(data.isIs_default());
        if (!data.getZipcode().isEmpty())
            et_code.setText(data.getZipcode());
        et_detailed.setText(data.getStreet_address());
        tv_city.setText(data.getProvince() + data.getCity() + data.getTown());
        dataBean = data;
    }

    @Override
    public void setCityData(HashMap<String, ArrayList<CityBean.CityNameBean>> map) {
        cityMap = map;
        //地址选择器
        AddressDialog addressDialog = new AddressDialog(context, cityMap);
        addressDialog.setDialogCallback(new AddressDialog.DialogCallback() {

            @Override
            public void callBack(String addressName, int pId, int cId, int aId) {
                provinceId = pId;
                cityId = cId;
                areaId = aId;
                tv_city.setText(addressName);
            }
        });
        addressDialog.show();
    }

    @Override
    public void setToken(UploadTokenBean bean) {
        this.bean = bean;
    }

    @Override
    public void setImageId(JSONArray ids) throws JSONException {
        if (isPosition) {
            id_card_front = ids.getString(0);
        } else {
            id_card_back = ids.getString(0);
        }
    }

    @Override
    public void setCountry(final CountryAreaCodeBean bean) {
        countryList = new ArrayList<>();
        for (int i = 0; i < bean.data.area_codes.size(); i++) {
            countryList.add(bean.data.area_codes.get(i).name);
        }

        //国家选择器
        final OptionsPickerView pvOptions1 = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                tv_country.setText(countryList.get(options1));
                newCountryID=bean.data.area_codes.get(options1).id;
            }
        }).build();
        pvOptions1.setPicker(countryList);
        pvOptions1.show();
    }

    @Override
    public void finishActivity() {
        Intent intent = new Intent();
        intent.putExtra("isRefresh", true);
        setResult(RESULT_OK, intent);
        finish();
    }

    //相机
    @AfterPermissionGranted(Constants.REQUEST_CODE_CAPTURE_CAMERA)
    private void cameraTask() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            LogUtil.e("有权限");
            openCamera();
        } else {
            EasyPermissions.requestPermissions(AddressActivity.this, getString(R.string.rationale_camera), Constants.REQUEST_CODE_CAPTURE_CAMERA, perms);
        }
    }

    private File mCurrentPhotoFile;

    private void openCamera() {
        mCurrentPhotoFile = ImageUtils.getDefaultFile();
        ImageUtils.getImageFromCamera(AddressActivity.this, ImageUtils.getUriForFile(getApplicationContext(), mCurrentPhotoFile));
    }

    //相册

    @AfterPermissionGranted(Constants.REQUEST_CODE_PICK_IMAGE)
    private void albumTask() {
        if (EasyPermissions.hasPermissions(context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            ImageUtils.getImageFromAlbum(AddressActivity.this, 1);
        } else {
            // 申请权限。
            EasyPermissions.requestPermissions(AddressActivity.this, getString(R.string.rationale_photo),
                    Constants.REQUEST_CODE_PICK_IMAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.e("这里有没有调用" + requestCode + "这个code：" + RESULT_OK + "resultCode:" + resultCode);
        if (resultCode != RESULT_OK)
            return;
        switch (requestCode) {
            case Constants.REQUEST_CODE_CAPTURE_CAMERA:
                LogUtil.e("有没有调用成功");
                if (null == mCurrentPhotoFile)
                    return;
                LogUtil.e("调用成功了");
                toCropActivity(ImageUtils.getUriForFile(getApplicationContext(), mCurrentPhotoFile));
                break;
            case Constants.REQUEST_CODE_PICK_IMAGE:
                LogUtil.e("调用成功");
                List<Uri> iamgeList = PicturePickerUtils.obtainResult(data);
                if (iamgeList == null || iamgeList.isEmpty()) {
                    LogUtil.e("启动失败");
                    return;
                }
                toCropActivity(iamgeList.get(0));
                break;
        }
    }

    private void toCropActivity(Uri uri) {
        Intent intent = new Intent(context, ImageCropActivity.class);
        intent.putExtra(AddressActivity.class.getSimpleName(), uri);
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onClipComplete(ImageCropActivity.MessageCropComplete cropComplete) {
        if (AddressActivity.class.getSimpleName().equals(cropComplete.getSimpleName())) {
            byte[] data = ImageUtils.bitmap2ByteArray(cropComplete.getBitmap());
            presenter.loadPhoto(bean, data);
            setImageUri(data);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageAreaCode code) {
        tv_mobile.setText(code.areaCode);
    }

    @Override
    protected void onDestroy() {
        //反注册eventBus
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void setImageUri(byte[] bytes) {
        if (isPosition) {
            GlideUtil.loadImageAsBitmap(bytes, iv_position);
        } else {
            GlideUtil.loadImageAsBitmap(bytes, iv_opposion);
        }
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
            new AppSettingsDialog.Builder(AddressActivity.this).build().show();
        }
    }

    @Override
    public void onRationaleAccepted(int requestCode) {

    }

    @Override
    public void onRationaleDenied(int requestCode) {

    }


}
