package com.lexivip.lexi.cashMoney;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.basemodule.tools.Constants;
import com.basemodule.tools.GlideUtil;
import com.basemodule.tools.LogUtil;
import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.Util;
import com.basemodule.tools.WaitingDialog;
import com.basemodule.ui.BaseActivity;
import com.lexivip.lexi.R;
import com.lexivip.lexi.album.ImageCropActivity;
import com.lexivip.lexi.album.ImageUtils;
import com.lexivip.lexi.album.PicturePickerUtils;
import com.lexivip.lexi.user.completeinfo.UploadTokenBean;
import com.lexivip.lexi.view.CustomHeadView;
import com.smart.dialog.listener.OnOperItemClickL;
import com.smart.dialog.widget.ActionSheetDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class NameAuthenticationActivity extends BaseActivity implements NameAuthenticationContract.View, EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks{

    private WaitingDialog dialog;
    private NameAuthenticationPresenter presenter;
    private EditText et_name;
    private EditText et_id;
    private ImageView iv_photo_position;
    private ImageView iv_photo_opposite;
    private Button button;
    private boolean isPositive;
    private byte[] data;
    private UploadTokenBean bean;
    private int id_card_front;
    private int id_card_back;

    @Override
    protected int getLayout() {
        return R.layout.activity_name_authentication;
    }

    @Override
    public void initView() {
        super.initView();
        //注册eventBus
        EventBus.getDefault().register(this);
        dialog = new WaitingDialog(this);
        presenter = new NameAuthenticationPresenter(this);
        CustomHeadView customHeadView=findViewById(R.id.customHeadView);
        customHeadView.setHeadCenterTxtShow(true,R.string.text_name_authentication);
        et_name = findViewById(R.id.et_name);
        et_id = findViewById(R.id.et_ID);
        iv_photo_position = findViewById(R.id.iv_photo_position);
        iv_photo_opposite = findViewById(R.id.iv_photo_opposite);
        button = findViewById(R.id.button);
        presenter.getToken();
    }

    @Override
    public void installListener() {
        super.installListener();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(et_name.getText().toString())){
                    if (!TextUtils.isEmpty(et_id.getText().toString())){
                        ToastUtil.showError("请输入身份证号");
                    }else {
                        if (id_card_back!=0 && id_card_front!=0) {
                            presenter.loadData(et_name.getText().toString(),et_id.getText().toString(),id_card_front,id_card_back);
                        } else {
                            ToastUtil.showInfo("请上传身份证照片");
                        }
                    }
                }else {
                    ToastUtil.showError("请输入姓名");
                }
            }
        });

        iv_photo_opposite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPositive=false;
                selectImage();
            }
        });

        iv_photo_position.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPositive=true;
                selectImage();
            }
        });
    }

    private void selectImage(){
        String[] result = Util.getStringArray(R.array.strings_photo_titles);
        final ActionSheetDialog dialog0 = new ActionSheetDialog(this, result, null);
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
    }

    //相机
    @AfterPermissionGranted(Constants.REQUEST_CODE_CAPTURE_CAMERA)
    private void cameraTask() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            LogUtil.e("有权限");
            openCamera();
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_camera), Constants.REQUEST_CODE_CAPTURE_CAMERA, perms);
        }
    }

    private File mCurrentPhotoFile;

    private void openCamera() {
        mCurrentPhotoFile = ImageUtils.getDefaultFile();
        ImageUtils.getImageFromCamera(this, ImageUtils.getUriForFile(getApplicationContext(), mCurrentPhotoFile));
    }

    //相册

    @AfterPermissionGranted(Constants.REQUEST_CODE_PICK_IMAGE)
    private void albumTask() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            ImageUtils.getImageFromAlbum(this, 1);
        } else {
            // 申请权限。
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_photo),
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
        Intent intent = new Intent(this, ImageCropActivity.class);
        intent.putExtra(NameAuthenticationActivity.class.getSimpleName(), uri);
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onClipComplete(ImageCropActivity.MessageCropComplete cropComplete) {
        if (NameAuthenticationActivity.class.getSimpleName().equals(cropComplete.getSimpleName())) {
            data = ImageUtils.bitmap2ByteArray(cropComplete.getBitmap());
            presenter.loadPhoto(bean, data);
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
        if (dialog!=null){
            dialog.dismiss();
        }
        ToastUtil.showError(error);
    }

    @Override
    public void setData() {
        startActivity(new Intent(this,AuthenticationSuccessActivity.class));
        finish();
    }

    @Override
    public void setImageId(JSONArray ids) throws JSONException {
        if (isPositive){
            id_card_front=ids.getInt(0);
            GlideUtil.loadImageWithFading(data,iv_photo_position);
        }else {
            id_card_back=ids.getInt(0);
            GlideUtil.loadImageWithFading(data,iv_photo_opposite);
        }
    }

    @Override
    public void setToken(UploadTokenBean bean) {
        this.bean = bean;
    }

    @Override
    public void setPresenter(CashAlipayConract.Presenter presenter) {
        setPresenter(presenter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //反注册eventBus
        EventBus.getDefault().unregister(this);
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
