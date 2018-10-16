package com.thn.lexi.orderList;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;

import com.basemodule.tools.Constants;
import com.basemodule.tools.LogUtil;
import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.Util;
import com.basemodule.tools.WaitingDialog;
import com.basemodule.ui.BaseActivity;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.thn.lexi.R;
import com.thn.lexi.address.AddressActivity;
import com.thn.lexi.address.AddressContract;
import com.thn.lexi.album.ImageCropActivity;
import com.thn.lexi.album.ImageUtils;
import com.thn.lexi.album.PicturePickerUtils;
import com.thn.lexi.user.completeinfo.UploadTokenBean;
import com.thn.lexi.view.CustomHeadView;
import com.thn.lexi.view.MyRatingBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 评价页面
 */
public class EvaluateActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks,EvaluateContract.View{

    private MyOrderListBean.DataBean.OrdersBean ordersBean;
    private int position;
    private int itemPosition;
    private boolean isLast;
    private AdapterEvaluate adapterEvaluate;
    private UploadTokenBean tokenBean;
    private EvaluatePresenter presenter;
    private WaitingDialog dialog;
    private boolean isToken;
    private byte[] imageData;
    private Button button;
    private String order_rid;
    private List<EvaluateBean> items=new ArrayList<EvaluateBean>();

    @Override
    protected int getLayout() {
        return R.layout.activity_evaluate;
    }

    @Override
    public void initView() {
        super.initView();
        EventBus.getDefault().register(this);
        Intent intent=getIntent();
        if (intent.hasExtra(EvaluateActivity.class.getSimpleName())) {
            LogUtil.e("数据为空");
            ordersBean = intent.getParcelableExtra(EvaluateActivity.class.getSimpleName());
            order_rid=ordersBean.getRid();
        }
        presenter=new EvaluatePresenter(this);
        dialog = new WaitingDialog(this);
        CustomHeadView customHeadView=findViewById(R.id.customHeadView);
        customHeadView.setHeadCenterTxtShow(true,R.string.title_evaluate);
        button = findViewById(R.id.button);
        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LogUtil.e("数据长度："+ordersBean.getItems().size());
        adapterEvaluate = new AdapterEvaluate(R.layout.item_evaluate,ordersBean.getItems(),ordersBean.getStore().getStore_name(),this);
        recyclerView.setAdapter(adapterEvaluate);
    }

    @Override
    public void installListener() {
        super.installListener();
        presenter.getToken();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.clear();
                for (int i=0;i<adapterEvaluate.getData().size();i++){
                    EvaluateBean bean=new EvaluateBean();
                    bean.asset_ids=adapterEvaluate.getData().get(i).asset_ids;
                    bean.content=adapterEvaluate.getData().get(i).content;
                    bean.score=adapterEvaluate.getData().get(i).score;
                    bean.sku_rid=adapterEvaluate.getData().get(i).getRid();
                    items.add(bean);
                    if (0==adapterEvaluate.getData().get(i).score){
                        ToastUtil.showInfo("请对所有商品进行评价");
                        return;
                    }
                }
                presenter.setEvaluate(order_rid, (ArrayList<EvaluateBean>) items);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onClickItem(AdapterEvaluate.ClickImageItem clickImageItem){
        position=clickImageItem.position;
        itemPosition=clickImageItem.itemPostion;
        isLast=clickImageItem.isLast;
        getImage();
    }

    private void getImage(){
        String[] result= Util.getStringArray(R.array.strings_photo_titles);
        final ActionSheetDialog dialog=new ActionSheetDialog(this,result,null);
        dialog.itemTextColor(Util.getColor(R.color.color_333));
        TranslateAnimation animation=new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
        animation.setInterpolator(new DecelerateInterpolator());
        dialog.layoutAnimation(new LayoutAnimationController(animation));
        dialog.isTitleShow(false).show();
        dialog.setOnOperItemClickL(new OnOperItemClickL() {
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
                dialog.dismiss();
            }
        });
    }

    //相机
    @AfterPermissionGranted(Constants.REQUEST_CODE_CAPTURE_CAMERA)
    private void cameraTask(){
        String[] perms = {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            LogUtil.e("有权限");
            openCamera();
        } else {
            EasyPermissions.requestPermissions(EvaluateActivity.this, getString(R.string.rationale_camera), Constants.REQUEST_CODE_CAPTURE_CAMERA, perms);
        }
    }

    private File mCurrentPhotoFile;
    private void openCamera(){
        mCurrentPhotoFile = ImageUtils.getDefaultFile();
        ImageUtils.getImageFromCamera(EvaluateActivity.this, ImageUtils.getUriForFile(getApplicationContext(), mCurrentPhotoFile));
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
        LogUtil.e("调用图片返回值："+requestCode);
        if (resultCode!= Activity.RESULT_OK)
            return;
        switch (requestCode){
            case Constants.REQUEST_CODE_CAPTURE_CAMERA:
                LogUtil.e("调用相机成功");
                if (null == mCurrentPhotoFile)
                    return;
                toCropActivity(ImageUtils.getUriForFile(getApplicationContext(), mCurrentPhotoFile));
                break;
            case Constants.REQUEST_CODE_PICK_IMAGE:
                List<Uri> iamgeList= PicturePickerUtils.obtainResult(data);
                if (iamgeList == null || iamgeList.isEmpty()) {
                    return;
                }
                toCropActivity(iamgeList.get(0));
                break;
        }
    }

    private void toCropActivity(Uri uri){
        Intent intent= new Intent(this,ImageCropActivity.class);
        intent.putExtra(EvaluateActivity.class.getSimpleName(), uri);
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onClipComplete(ImageCropActivity.MessageCropComplete cropComplete){
        LogUtil.e("订阅者");
        if (EvaluateActivity.class.getSimpleName().equals(cropComplete.getSimpleName())) {
            LogUtil.e("返回成功");
            imageData = ImageUtils.bitmap2ByteArray(cropComplete.getBitmap());
            if (!isToken) {
                presenter.getToken();
            } else {
                presenter.loadImage(tokenBean, imageData);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
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
        ToastUtil.showInfo(error);
    }

    @Override
    public void setToken(UploadTokenBean bean) {
        isToken=true;
        tokenBean=bean;
    }

    @Override
    public void setImageId(JSONArray ids) throws JSONException {
        if (isLast) {
            LogUtil.e("是否是最后一个");
            adapterEvaluate.getData().get(position).asset_image.add(itemPosition-1, imageData);
            adapterEvaluate.getData().get(position).asset_ids.add(itemPosition-1,ids.getString(0));
            adapterEvaluate.notifyDataSetChanged();
        }else{
            if(itemPosition!=0) {
                LogUtil.e("是否是中间的一个");
                adapterEvaluate.getData().get(position).asset_image.remove(itemPosition - 1);
                adapterEvaluate.getData().get(position).asset_image.add(itemPosition - 1, imageData);
                adapterEvaluate.getData().get(position).asset_ids.remove(itemPosition - 1);
                adapterEvaluate.getData().get(position).asset_ids.add(itemPosition - 1, ids.getString(0));
                adapterEvaluate.notifyDataSetChanged();
            }else {
                LogUtil.e("是否是第一个");
                adapterEvaluate.getData().get(position).asset_image=new ArrayList<>();
                adapterEvaluate.getData().get(position).asset_image.add(imageData);
                adapterEvaluate.getData().get(position).asset_ids=new ArrayList<>();
                adapterEvaluate.getData().get(position).asset_ids.add(ids.getString(0));
                adapterEvaluate.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void setPresenter(EvaluateContract.Presenter presenter) {
        setPresenter(presenter);
    }
}
