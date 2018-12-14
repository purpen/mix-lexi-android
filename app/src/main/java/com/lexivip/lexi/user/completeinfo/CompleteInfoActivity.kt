package com.lexivip.lexi.user.completeinfo

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.View
import com.basemodule.ui.BaseActivity
import com.lexivip.lexi.R
import kotlinx.android.synthetic.main.activity_complete_info.*
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.lexivip.lexi.MainActivity
import com.lexivip.lexi.eventBusMessge.MessageClose
import org.greenrobot.eventbus.EventBus
import java.util.*
import android.view.animation.*
import com.basemodule.tools.*
import com.lexivip.lexi.ImageSizeConfig
import com.lexivip.lexi.album.ImageCropActivity
import com.lexivip.lexi.album.ImageUtils
import com.lexivip.lexi.album.PicturePickerUtils
import com.smart.dialog.widget.ActionSheetDialog
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONArray
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import java.io.File
import pub.devrel.easypermissions.AppSettingsDialog

class CompleteInfoActivity : BaseActivity(), CompleteInfoContract.View, View.OnClickListener, EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {
    private var gender: String = "0" //默认性别女
    private var avatarId: String = "" //图像id
    private var birth: String = "" // 出生日期
    private var uploadTokenBean: UploadTokenBean? = null
    private val dialog: WaitingDialog? by lazy { WaitingDialog(this) }

    override val layout: Int = R.layout.activity_complete_info

    private lateinit var presenter: CompleteInfoPresenter

    override fun initView() {
        presenter = CompleteInfoPresenter(this)
        EventBus.getDefault().register(this)
    }

    override fun installListener() {
        relativeLayoutOval.setOnClickListener(this)
        textViewBirth.setOnClickListener(this)
        button.setOnClickListener(this)
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioButton0 -> gender = "0"
                R.id.radioButton1 -> gender = "1"
            }
        }
    }

    override fun setPresenter(presenter: CompleteInfoContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.relativeLayoutOval -> {
                val stringItems = Util.getStringArray(R.array.strings_photo_titles)
                val dialog = ActionSheetDialog(this, stringItems, null)
                dialog.itemTextColor(Util.getColor(R.color.color_333))
                val animation = TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                        0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f)
                animation.interpolator = DecelerateInterpolator()
                dialog.layoutAnimation(LayoutAnimationController(animation, 0.12f))
                dialog.isTitleShow(false).show()

                dialog.setOnOperItemClickL { _, _, position, _ ->
                    when (position) {
                        0 -> cameraTask()

                        1 -> albumTask()
                    }
                    dialog.dismiss()
                }
            }

            R.id.textViewBirth -> {
                //默认选今天
                val selectedDate = Calendar.getInstance()
                val startDate = Calendar.getInstance()
                //开始日期
                startDate.set(1900, 0, 1)
                //截止日期
                val endDate = Calendar.getInstance()
                endDate.set(endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH), endDate.get(Calendar.DAY_OF_MONTH))
                val pvTime = TimePickerBuilder(this, OnTimeSelectListener { date, v ->
                    textViewBirth.setTextColor(resources.getColor(R.color.color_333))
                    val dateString = DateUtil.getDateString(date)
                    birth = dateString
                    textViewBirth.text = dateString
                }).setRangDate(startDate, endDate)
                        .setDate(selectedDate)
                        .build()
                pvTime.show()
            }

            R.id.button -> {
                presenter.uploadUserInfo(avatarId, etName.text.trim().toString(), birth, gender)
            }
        }
    }

    @AfterPermissionGranted(Constants.REQUEST_CODE_PICK_IMAGE)
    private fun albumTask() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            ImageUtils.getImageFromAlbum(this, 1)
        } else {
            // 申请权限。
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_photo),
                    Constants.REQUEST_CODE_PICK_IMAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    @AfterPermissionGranted(Constants.REQUEST_CODE_CAPTURE_CAMERA)
    private fun cameraTask() {
        val perms= arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (EasyPermissions.hasPermissions(this, *perms)) {
            openCamera()
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_camera),
                    Constants.REQUEST_CODE_CAPTURE_CAMERA, *perms)
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }


    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        LogUtil.e("onPermissionsGranted:" + requestCode + ":" + perms.size)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        LogUtil.e("onPermissionsDenied:" + requestCode + ":" + perms.size)
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onRationaleDenied(requestCode: Int) {
        LogUtil.e("onRationaleDenied:$requestCode")
    }

    override fun onRationaleAccepted(requestCode: Int) {
        LogUtil.e("onRationaleAccepted:$requestCode")
    }

    private var mCurrentPhotoFile: File? = null

    private fun openCamera() {
        mCurrentPhotoFile = ImageUtils.getDefaultFile()
        ImageUtils.getImageFromCamera(this, ImageUtils.getUriForFile(applicationContext, mCurrentPhotoFile))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        
        if (resultCode != Activity.RESULT_OK) return

        when (requestCode) {
            Constants.REQUEST_CODE_CAPTURE_CAMERA -> {
                if (null == mCurrentPhotoFile) return
                toCropActivity(ImageUtils.getUriForFile(applicationContext, mCurrentPhotoFile))
            }
            Constants.REQUEST_CODE_PICK_IMAGE -> {
                var mSelected = PicturePickerUtils.obtainResult(data)
                if (mSelected == null || mSelected.isEmpty()) {
                    return
                }
                toCropActivity(mSelected[0])
            }
        }
    }

    private fun toCropActivity(uri: Uri?) {
        val intent = Intent(this, ImageCropActivity::class.java)
        intent.putExtra(CompleteInfoActivity::class.java.simpleName, uri)
        startActivity(intent)

    }

    override fun requestNet() {
        presenter.getUploadToken()
    }


    override fun setUploadTokenData(uploadTokenBean: UploadTokenBean?) {
        this.uploadTokenBean = uploadTokenBean
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onClipComplete(message: ImageCropActivity.MessageCropComplete) {
        if(CompleteInfoActivity::class.java.simpleName.equals(message.simpleName)) {
            val byteArray = ImageUtils.bitmap2ByteArray(message.bitmap)
            setUserAvatar(byteArray)
            presenter.uploadAvatar(uploadTokenBean, byteArray)
        }
    }

    /**
     * 显示头像
     */
    private fun setUserAvatar(byteArray: ByteArray) {
        GlideUtil.loadCircleImageWidthDimen(byteArray, imageView, resources.getDimensionPixelSize(R.dimen.dp90),ImageSizeConfig.DEFAULT)
        imageViewBottom.visibility = View.VISIBLE
    }

    override fun setUploadAvatarData(ids: JSONArray) {
        avatarId = "${ids[0]}"
    }

    override fun goPage() {//上传完关闭比界面，跳转主页
        EventBus.getDefault().post(MessageClose())
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(MainActivity::class.java.simpleName,TAG)
        startActivity(intent)
        finish()
    }

    override fun showLoadingView() {
        dialog?.show()
    }

    override fun showInfo(string: String) {

    }

    override fun showError(s: String) {
        ToastUtil.showError(s)
    }

    override fun dismissLoadingView() {
        dialog?.dismiss()
    }


    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }
}