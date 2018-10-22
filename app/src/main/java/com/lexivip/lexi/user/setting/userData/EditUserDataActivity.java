package com.lexivip.lexi.user.setting.userData;

import android.Manifest;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.basemodule.tools.Constants;
import com.basemodule.tools.DateUtil;
import com.basemodule.tools.GlideUtil;
import com.basemodule.tools.LogUtil;
import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.Util;
import com.basemodule.tools.WaitingDialog;
import com.basemodule.ui.BaseActivity;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.lexivip.lexi.R;
import com.lexivip.lexi.address.AddressActivity;
import com.lexivip.lexi.address.AddressDialog;
import com.lexivip.lexi.address.CityBean;
import com.lexivip.lexi.album.ImageCropActivity;
import com.lexivip.lexi.album.ImageUtils;
import com.lexivip.lexi.album.PicturePickerUtils;
import com.lexivip.lexi.user.completeinfo.UploadTokenBean;
import com.lexivip.lexi.user.login.UserProfileBean;
import com.lexivip.lexi.view.CustomHeadView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 编辑个人资料
 */
//TODO 上传图片dialog
public class EditUserDataActivity extends BaseActivity implements EditUserDataContract.View, View.OnClickListener,EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {

    private ImageView iv_logo;
    private TextView tv_time;
    private ImageView iv_change_logo;
    private Button button;
    private EditText et_name;
    private EditText et_slogan;
    private EditText et_email;
    private TextView et_position;
    private TextView tv_date;
    private TextView tv_sex;
    private WaitingDialog dialog;
    private EditUserDataPresenter presenter;
    private UploadTokenBean tokenBean;
    private byte[] data;
    private UserProfileBean userBean=new UserProfileBean();
    private int provinceId;
    private int cityId;
    private int areaId;
    private ArrayList<String> sexList=new ArrayList<>();

    @Override
    protected int getLayout() {
        return R.layout.activity_edit_user_data;
    }

    @Override
    public void initView() {
        super.initView();
        //注册eventBus
        EventBus.getDefault().register(this);
        dialog = new WaitingDialog(this);
        CustomHeadView customHeadView=findViewById(R.id.customHeadView);
        customHeadView.setHeadCenterTxtShow(true,Util.getString(R.string.text_edit_user_data));
        iv_logo = findViewById(R.id.iv_logo);
        tv_time = findViewById(R.id.tv_time);
        iv_change_logo = findViewById(R.id.iv_change_logo);
        button = findViewById(R.id.button);
        et_name = findViewById(R.id.et_name);
        et_slogan = findViewById(R.id.et_slogan);
        et_email = findViewById(R.id.et_email);
        et_position = findViewById(R.id.et_position);
        tv_date = findViewById(R.id.tv_date);
        tv_sex = findViewById(R.id.tv_sex);
        presenter = new EditUserDataPresenter(this);

        sexList.add(Util.getString(R.string.text_gender_woman));
        sexList.add(Util.getString(R.string.text_gender_man));
    }

    @Override
    public void installListener() {
        super.installListener();
        button.setOnClickListener(this);
        iv_change_logo.setOnClickListener(this);
        et_position.setOnClickListener(this);
        tv_date.setOnClickListener(this);
        tv_sex.setOnClickListener(this);
        presenter.loadData();
        presenter.loadToken();
    }

    //相机
    @AfterPermissionGranted(Constants.REQUEST_CODE_CAPTURE_CAMERA)
    private void cameraTask(){
        String[] perms = {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            LogUtil.e("有权限");
            openCamera();
        } else {
            EasyPermissions.requestPermissions(EditUserDataActivity.this, getString(R.string.rationale_camera), Constants.REQUEST_CODE_CAPTURE_CAMERA, perms);
        }
    }

    private File mCurrentPhotoFile;
    private void openCamera(){
        mCurrentPhotoFile = ImageUtils.getDefaultFile();
        ImageUtils.getImageFromCamera(EditUserDataActivity.this, ImageUtils.getUriForFile(getApplicationContext(), mCurrentPhotoFile));
    }

    //相册
    @AfterPermissionGranted(Constants.REQUEST_CODE_PICK_IMAGE)
    private void albumTask() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            ImageUtils.getImageFromAlbum(EditUserDataActivity.this, 1);
        } else {
            // 申请权限。
            EasyPermissions.requestPermissions(EditUserDataActivity.this, getString(R.string.rationale_photo),
                    Constants.REQUEST_CODE_PICK_IMAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.e("这里有没有调用"+requestCode+"这个code："+RESULT_OK+"resultCode:"+resultCode);
        if (resultCode!= RESULT_OK)
            return;
        switch (requestCode){
            case Constants.REQUEST_CODE_CAPTURE_CAMERA:
                LogUtil.e("有没有调用成功");
                if (null == mCurrentPhotoFile)
                    return;
                LogUtil.e("调用成功了");
                toCropActivity(ImageUtils.getUriForFile(getApplicationContext(), mCurrentPhotoFile));
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
        Intent intent= new Intent(this,ImageCropActivity.class);
        intent.putExtra(EditUserDataActivity.class.getSimpleName(), uri);
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onClipComplete(ImageCropActivity.MessageCropComplete cropComplete){
        if (EditUserDataActivity.class.getSimpleName().equals(cropComplete.getSimpleName())) {
            data = ImageUtils.bitmap2ByteArray(cropComplete.getBitmap());
            if (tokenBean.success) {
                presenter.loadPhoto(tokenBean, data);
            }else {
                presenter.loadToken();
            }
        }
    }

    @Override
    protected void onDestroy() {
        //反注册eventBus
        EventBus.getDefault().unregister(this);
        super.onDestroy();
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
        dialog.dismiss();
        ToastUtil.showError(error);
    }

    @Override
    public void getData(UserProfileBean bean) {
        userBean = bean;
        GlideUtil.loadImageWithFading(bean.data.profile.avatar,iv_logo);
        tv_time.setText(Util.getString(R.string.text_register_time)+DateUtil.getDateByTimestamp(bean.data.profile.created_at,"yyyy年MM月dd日"));
        et_name.setText(bean.data.profile.username);
        if (bean.data.profile.about_me!=null&&!bean.data.profile.about_me.isEmpty()){
            et_slogan.setText(bean.data.profile.about_me);
        }
        if (bean.data.profile.mail!=null&&!bean.data.profile.mail.isEmpty()){
            et_email.setText(bean.data.profile.mail);
        }
        if (bean.data.profile.city!=null&&!bean.data.profile.city.isEmpty()){
            et_position.setText(bean.data.profile.province+bean.data.profile.city+bean.data.profile.area);
        }
        tv_date.setText(bean.data.profile.date);
        if (0==bean.data.profile.gender) {
            tv_sex.setText(Util.getString(R.string.text_gender_woman));
        }else {
            tv_sex.setText(Util.getString(R.string.text_gender_man));
        }
    }

    @Override
    public void getUpdate() {
        Intent intent=new Intent();
        intent.putExtra("isRefresh",true);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void getImage(JSONArray ids) {
        GlideUtil.loadImageWithFading(data,iv_logo);
        try {
            userBean.data.profile.avatar_id=ids.getInt(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setToken(UploadTokenBean bean) {
        tokenBean = bean;
        if (data!=null){
            presenter.loadPhoto(tokenBean, data);
        }
    }

    @Override
    public void getCity(HashMap<String, ArrayList<CityBean.CityNameBean>> map) {
        //地址选择器
        AddressDialog addressDialog=new AddressDialog(this,map);
        addressDialog.setDialogCallback(new AddressDialog.DialogCallback() {

            @Override
            public void callBack(String addressName, int pId, int cId, int aId) {
                provinceId = pId;
                cityId = cId;
                areaId = aId;
                et_position.setText(addressName);
            }
        });
        addressDialog.show();
    }

    @Override
    public void setPresenter(EditUserDataContract.Presenter presenter) {
        setPresenter(presenter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                if (!et_name.getText().toString().isEmpty()) {
                    presenter.updateData(et_name.getText().toString(), String.valueOf(userBean.data.profile.avatar_id)
                            , String.valueOf(userBean.data.profile.about_me), String.valueOf(userBean.data.profile.gender), String.valueOf(userBean.data.profile.area_id)
                            , String.valueOf(userBean.data.profile.province_id), String.valueOf(userBean.data.profile.city_id), et_email.getText().toString()
                            , userBean.data.profile.date);
                }else {
                    ToastUtil.showError("请输入昵称");
                }
                break;
            case R.id.iv_change_logo:
                String[] result= Util.getStringArray(R.array.strings_photo_titles);
                final ActionSheetDialog dialog0=new ActionSheetDialog(EditUserDataActivity.this,result,null);
                dialog0.itemTextColor(Util.getColor(R.color.color_333));
                TranslateAnimation animation=new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                        0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
                animation.setInterpolator(new DecelerateInterpolator());
                dialog0.layoutAnimation(new LayoutAnimationController(animation));
                dialog0.isTitleShow(false).show();
                dialog0.setOnOperItemClickL(new OnOperItemClickL() {
                    @Override
                    public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                        LogUtil.e("点击了第几个："+position);
                        switch (position){
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
            case R.id.et_position:
                if (userBean.data.profile.country_id!=0) {
                    presenter.loadCity(String.valueOf(userBean.data.profile.country_id));
                }else {
                    presenter.loadCity("1");
                }
                break;
            case R.id.tv_date:
                Calendar startDate = Calendar.getInstance();
                Calendar endDate = Calendar.getInstance();
                startDate.set(1900, 1, 1);
                Calendar selectData = Calendar.getInstance();
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                if (userBean.data.profile.date != null&&!userBean.data.profile.date.isEmpty()) {
                    try {
                        selectData.setTime(format.parse(userBean.data.profile.date));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                endDate.set(endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH), endDate.get(Calendar.DAY_OF_MONTH));
                //时间选择器
                TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        userBean.data.profile.date=DateUtil.getDateString(date);

                        tv_date.setText(DateUtil.getDateString(date));
                    }
                })
                        .setRangDate(startDate, endDate)
                        .setDate(selectData)
                        .build();
                pvTime.show();
                break;
            case R.id.tv_sex:
                OptionsPickerView pvOptions1 = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        userBean.data.profile.gender=options1;
                        tv_sex.setText(sexList.get(options1));
                    }
                }).build();
                pvOptions1.setPicker(sexList);
                pvOptions1.show();
                break;
        }
    }

    /**
     * 授权结束后会继续执行
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
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
