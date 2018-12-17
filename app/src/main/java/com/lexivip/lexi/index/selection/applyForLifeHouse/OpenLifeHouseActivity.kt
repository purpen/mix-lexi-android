package com.lexivip.lexi.index.selection.applyForLifeHouse

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.RelativeLayout
import com.basemodule.tools.Constants
import com.basemodule.tools.LogUtil
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseActivity
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.R
import com.lexivip.lexi.cashMoney.CashMoneyActivity
import com.lexivip.lexi.eventBusMessge.MessageClose
import com.lexivip.lexi.net.WebUrl
import com.lexivip.lexi.shareUtil.ShareModel
import com.lexivip.lexi.shareUtil.ShareUtil
import com.lexivip.lexi.user.login.UserProfileUtil
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMImage
import com.umeng.socialize.media.UMWeb
import kotlinx.android.synthetic.main.acticity_open_life_house.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.io.IOException
import java.util.*

class OpenLifeHouseActivity : BaseActivity() , EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks{
    private val dialog: WaitingDialog by lazy { WaitingDialog(this) }
    override val layout: Int = R.layout.acticity_open_life_house
    private lateinit var webView: WebView
    private lateinit var url: String
    private var titleId: Int = 0
    var types:Int?=0
    private val shareModel=ShareModel()
    override fun getIntentData() {
        url = intent.getStringExtra("url")
        titleId = intent.getIntExtra("title", R.string.title_open_life_house)
    }

    override fun initView() {
        EventBus.getDefault().register(this)
        customHeadView.setHeadCenterTxtShow(true, titleId)
        webView = WebView(AppApplication.getContext())
        webView.overScrollMode = WebView.OVER_SCROLL_NEVER
        val settings = webView.settings
        settings.setAppCacheEnabled(true)
        settings.setJavaScriptEnabled(true);
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        settings.builtInZoomControls = false
        settings.javaScriptEnabled = true
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING
        settings.domStorageEnabled = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        val layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)
        layoutParams.addRule(RelativeLayout.BELOW, R.id.customHeadView)
        webView.layoutParams = layoutParams
        relativeLayout.addView(webView)
        webView.webViewClient = object : WebViewClient() {

            override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                LogUtil.e("onReceivedSslError=================")
                handler?.proceed()
                super.onReceivedSslError(view, handler, error)
            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                dialog.show()
                LogUtil.e("onPageStarted=================$url")
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                dialog.dismiss()
                super.onPageFinished(view, url)
            }
        }
        webView.loadUrl(url)

        //js调用本地方法
        webView.addJavascriptInterface(JsInterface(this), "android")
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        val parent = webView.parent as ViewGroup
        parent.removeView(webView)
        webView.removeView(webView)
        webView.destroy()
        super.onDestroy()
    }

    internal inner class JsInterface(context: Activity) {
        private val context=context
        private var title:String?=null
        private var cotent:String?=null
        private var image:Int?=0
        private val shareModel=ShareModel()
        @JavascriptInterface
        fun share() {//35元分享
            val rand = Random()
            val i = rand.nextInt(4)
            if (i==1){
                title="你还没有参加吗？"
                cotent="安装乐喜app，挑全球原创手作好物，每天都可以赚现金。"
                image=R.drawable.icon_share_invitation1
            }else if (i==2){
                title="做颗喜糖"
                cotent="拿35元现金，边玩边赚钱，逛全球优质设计师手作人社群。"
                image=R.drawable.icon_share_invitation
            }else{
                title="安利个我很喜欢的应用"
                cotent="报道先拿35元，汇聚了全球原创手作的暖心好设计。"
                image=R.drawable.icon_share_invation2
            }
            val shareUtil=ShareUtil(context)
            shareUtil.shareFriendInvitation(WebUrl.SHARE_INVITATION+UserProfileUtil.getUserId(), image!!,title,cotent)
        }

        @JavascriptInterface
        fun cashMoney() {//35元提现
            context.startActivity(Intent(context, CashMoneyActivity::class.java))
        }

        @JavascriptInterface
        fun handleShareFriend(type: Int) {//35元分享
            types=type
            shareInvation()
        }

        @JavascriptInterface
        fun applyLifeStore() {//开馆
            context.startActivity(Intent(context, ApplyForLifeHouseActivity::class.java))
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageClose) {
        LogUtil.e("$TAG be closed")
        finish()
    }

    @AfterPermissionGranted(Constants.REQUEST_CODE_SHARE)
    public fun shareInvation() {
        val perms = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (EasyPermissions.hasPermissions(this, *perms)) {
            var title:String?
            var cotent:String?
            var images:Int?
            val rand = Random()
            val i = rand.nextInt(4)
            if (i==1){
                title="你还没有参加吗？"
                cotent="安装乐喜app，挑全球原创手作好物，每天都可以赚现金。"
                images=R.drawable.icon_share_invitation1
            }else if (i==2){
                title="做颗喜糖"
                cotent="拿35元现金，边玩边赚钱，逛全球优质设计师手作人社群。"
                images=R.drawable.icon_share_invitation
            }else{
                title="安利个我很喜欢的应用"
                cotent="报道先拿35元，汇聚了全球原创手作的暖心好设计。"
                images=R.drawable.icon_share_invation2
            }
            val image = UMImage(this, images!!)
            val web = UMWeb(WebUrl.SHARE_INVITATION+UserProfileUtil.getUserId())
            web.title = title//标题
            web.setThumb(image)  //缩略图
            web.description = cotent//描述
            when(types){
                0-> {
                    ShareAction(this)
                            .withMedia(web)
                            .setPlatform(SHARE_MEDIA.WEIXIN)
                            .setCallback(shareListener)
                            .share()
                }
                1-> {
                    ShareAction(this)
                            .withMedia(web)
                            .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                            .setCallback(shareListener)
                            .share()
                }
                2-> {
                    ShareAction(this)
                            .withMedia(web)
                            .setPlatform(SHARE_MEDIA.SINA)
                            .setCallback(shareListener)
                            .share()
                }
                3-> {
                    ShareAction(this)
                            .withMedia(web)
                            .setPlatform(SHARE_MEDIA.QQ)
                            .setCallback(shareListener)
                            .share()
                }
                4-> {
                    ShareAction(this)
                            .withMedia(web)
                            .setPlatform(SHARE_MEDIA.QZONE)
                            .setCallback(shareListener)
                            .share()
                }
            }
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_photo), Constants.REQUEST_CODE_SHARE, *perms)
        }
    }

    private val shareListener = object : UMShareListener {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        override fun onStart(platform: SHARE_MEDIA) {
            LogUtil.e("回调开始了")
            shareModel.loadFriend(object :IDataSource.HttpRequestCallBack{
                override fun onSuccess(json: String) {

                }

                override fun onFailure(e: IOException) {

                }

            })
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        override fun onResult(platform: SHARE_MEDIA) {
            LogUtil.e("回调成功了" + platform.toString())
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        override fun onError(platform: SHARE_MEDIA, t: Throwable) {
            LogUtil.e("回调失败:" + t.toString())
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        override fun onCancel(platform: SHARE_MEDIA) {
            LogUtil.e("回调取消了")
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onRationaleDenied(requestCode: Int) {

    }

    override fun onRationaleAccepted(requestCode: Int) {

    }
}