package com.thn.lexi.address;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.thn.lexi.R;
import com.thn.lexi.album.ImageCropActivity;
import com.thn.lexi.album.ImageUtils;
import com.thn.lexi.album.PicturePickerUtils;
import com.thn.lexi.user.areacode.CountryAreaCodeBean;
import com.thn.lexi.user.areacode.MessageAreaCode;
import com.thn.lexi.user.areacode.SelectCountryOrAreaActivity;
import com.thn.lexi.user.completeinfo.CompleteInfoActivity;
import com.thn.lexi.user.completeinfo.UploadTokenBean;
import com.thn.lexi.view.CustomHeadView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static com.thn.lexi.album.PicturePickerUtils.obtainResult;

/**
 * 收货地址详情页面
 */
public class AddressActivity extends BaseActivity implements View.OnClickListener,AddressContract.View,EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {
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
    private EditText et_detailed1;
    private ImageView iv_position;
    private ImageView iv_opposion;
    private Button bt_delete;
    private Switch swit;
    private boolean isNew;
    private boolean isForeign;
    private String rid="1";
    private TextView tv_city;
    private TextView tv_country;
    private List<String> countryList;//国家的数据
    private Map<String,ArrayList<CityBean.CityNameBean>> map;
    private UploadTokenBean bean;
    private boolean isPosition;
    private String id_card_front;
    private String id_card_back;

    @Override
    protected int getLayout() {
        return R.layout.activity_select_address;
    }

    @Override
    public void initView() {
        context=this;
        EventBus.getDefault().register(this);
       dialog =new WaitingDialog(AddressActivity.this);
        super.initView();
        presenter=new AddressPresenter(this);

        customHeadView = findViewById(R.id.customHeadView);
        if (isNew) {
            customHeadView.setHeadCenterTxtShow(true, R.string.title_new_address);
        }else{
            customHeadView.setHeadCenterTxtShow(true,R.string.title_edit_address);
        }
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

        RelativeLayout rl_photo=findViewById(R.id.rl_photo);
        LinearLayout ll_ID=findViewById(R.id.ll_ID);
        TextView tv_remind=findViewById(R.id.tv_remind);

        /*if (isForeign){
            LogUtil.e("为什么");
            ll_country.setVisibility(View.VISIBLE);
            ll_country.setEnabled(true);
            rl_photo.setVisibility(View.VISIBLE);
            ll_ID.setVisibility(View.VISIBLE);
            tv_remind.setVisibility(View.VISIBLE);
        }else{
            LogUtil.e("为什么啊");
            ll_country.setVisibility(View.GONE);
            ll_country.setEnabled(false);
            ll_country.setClickable(false);
            rl_photo.setVisibility(View.GONE);
            ll_ID.setVisibility(View.GONE);
            tv_remind.setVisibility(View.GONE);
        }*/

        bt_save.setOnClickListener(this);
        tv_mobile.setOnClickListener(this);
        bt_delete.setOnClickListener(this);
        ll_city.setOnClickListener(this);
        ll_country.setOnClickListener(this);
        iv_position.setOnClickListener(this);
        iv_opposion.setOnClickListener(this);
    }

    @Override
    public void installListener() {
        super.installListener();
        //如果是编辑地址，先请求网络获取地址
        if (!isNew)
            presenter.loadData(rid);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_save:

                break;
            case R.id.bt_delete:
                break;
            case R.id.ll_region:
                LogUtil.e("你有么有被触发");
                presenter.loadCityData("1");
                break;
            case R.id.ll_country:
                presenter.loadCountry();
                break;
            case R.id.textView_mobile:
                startActivity(new Intent(context,SelectCountryOrAreaActivity.class));
                break;
            case R.id.iv_photo_position:

                isPosition=true;
                String[] result= Util.getStringArray(R.array.strings_photo_titles);
                final ActionSheetDialog dialog=new ActionSheetDialog(context,result,null);
                dialog.itemTextColor(Util.getColor(R.color.color_333));
                TranslateAnimation animation=new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                        0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
                animation.setInterpolator(new DecelerateInterpolator());
                dialog.layoutAnimation(new LayoutAnimationController(animation));
                dialog.isTitleShow(false).show();
                dialog.setOnOperItemClickL(new OnOperItemClickL() {
                    @Override
                    public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position){
                            case 0:
                                cameraTask();
                                break;
                            case 1:
                                albumTask();
                                break;
                        }
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.iv_photo_opposite:
                isPosition=false;
                String[] results= Util.getStringArray(R.array.strings_photo_titles);
                final ActionSheetDialog dialogs=new ActionSheetDialog(context,results,null);
                dialogs.itemTextColor(Util.getColor(R.color.color_333));
                TranslateAnimation animations=new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                        0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
                animations.setInterpolator(new DecelerateInterpolator());
                dialogs.layoutAnimation(new LayoutAnimationController(animations));
                dialogs.isTitleShow(false).show();
                dialogs.setOnOperItemClickL(new OnOperItemClickL() {
                    @Override
                    public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position){
                            case 0:
                                cameraTask();
                                break;
                            case 1:
                                albumTask();
                                break;
                        }
                        dialogs.dismiss();
                    }
                });
                break;
        }
    }

    @Override
    public void getIntentData() {
        super.getIntentData();
        Intent intent=getIntent();
        isNew = intent.getBooleanExtra("isNew",true);
        isForeign = intent.getBooleanExtra("idForeign",false);
        rid = intent.getStringExtra("rid");
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
        et_name.setText(data.getFirst_name());
        swit.setChecked(data.isIs_default());
        et_code.setText((Integer) data.getZipcode());
        tv_city.setText(data.getFull_address());
        tv_country.setText(data.getCountry_name());

    }

    @Override
    public void setCityData(HashMap<String, ArrayList<CityBean.CityNameBean>> map) {
        //地址选择器
        AddressDialog addressDialog=new AddressDialog(context,map);
        addressDialog.setDialogCallback(new AddressDialog.DialogCallback() {
            @Override
            public void callBack(String addressName, int pId, int cId, int aId) {
            }
        });
        addressDialog.show();
    }

    @Override
    public void setToken(UploadTokenBean bean) {
        this.bean=bean;
    }

    @Override
    public void setImageId(JSONArray ids) throws JSONException {
        if (isPosition){
            id_card_front=ids.getString(0);
        }else{
            id_card_back=ids.getString(0);
        }
    }

    @Override
    public void setCountry(CountryAreaCodeBean bean) {
        countryList=new ArrayList<>();
        for (int i=0;i<bean.data.area_codes.size();i++){
            countryList.add(bean.data.area_codes.get(i).name);
        }

            //国家选择器
            OptionsPickerView pvOptions1 = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3, View v) {
                    //返回的分别是三个级别的选中位置
                        /*String tx = options1Items.get(options1).getPickerViewText()
                            +options2Items.get(options1).get(option2)
                                    + options3Items.get(options1).get(option2).get(options3).getPickerViewText();*/
                    //tvOptions.setText(tx);
                }
            }).build();
            pvOptions1.setPicker(countryList);
            pvOptions1.show();
    }

    @Override
    public void finishActivity() {
        finish();
    }

    //相机
    @AfterPermissionGranted(Constants.REQUEST_CODE_CAPTURE_CAMERA)
    private void cameraTask(){
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            openCamera();
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_camera),
                    Constants.REQUEST_CODE_CAPTURE_CAMERA, Manifest.permission.CAMERA);
        }
    }

    private File mCurrentPhotoFile;
    private void openCamera(){
        mCurrentPhotoFile = ImageUtils.getDefaultFile();
        ImageUtils.getImageFromCamera(this, ImageUtils.getUriForFile(getApplicationContext(), mCurrentPhotoFile));
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
        LogUtil.e("这里有没有调用"+requestCode);
        if (resultCode!= Activity.RESULT_OK)
            return;
        switch (requestCode){
            case Constants.REQUEST_CODE_CAPTURE_CAMERA:
                break;
            case Constants.REQUEST_CODE_PICK_IMAGE:
                LogUtil.e("调用成功");
                List<Uri> iamgeList=PicturePickerUtils.obtainResult(data);
                if (iamgeList == null || iamgeList.isEmpty()) {
                    LogUtil.e("启动失败");
                    return;
                }
                toCropActivity(iamgeList.get(0));
                break;
        }
    }

    private void toCropActivity(Uri uri){
        Intent intent= new Intent(context,ImageCropActivity.class);
        intent.putExtra(AddressActivity.class.getSimpleName(), uri);
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onClipComplete(ImageCropActivity.MessageCropComplete cropComplete){
        byte[] data=ImageUtils.bitmap2ByteArray(cropComplete.getBitmap());
        presenter.loadPhoto(bean,data);
        setImageUri(data);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent( MessageAreaCode code){
        tv_mobile.setText(code.areaCode);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void setImageUri(byte[] bytes){
        GlideUtil.loadImageAsBitmap(bytes,iv_position);
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
