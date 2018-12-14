package com.lexivip.lexi.index.selection.applyForLifeHouse

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.RelativeLayout
import com.basemodule.tools.LogUtil
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseActivity
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.R
import com.lexivip.lexi.eventBusMessge.MessageClose
import kotlinx.android.synthetic.main.acticity_open_life_house.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class OpenLifeHouseActivity : BaseActivity() {
    private val dialog: WaitingDialog by lazy { WaitingDialog(this) }
    override val layout: Int = R.layout.acticity_open_life_house
    private lateinit var webView: WebView
    private lateinit var url: String
    private var titleId: Int = 0
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
        if (titleId == R.string.title_open_life_house) {
            relativeLayoutBottom.visibility = View.VISIBLE
            layoutParams.addRule(RelativeLayout.ABOVE, R.id.relativeLayoutBottom)
        }
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

    override fun installListener() {
        button.setOnClickListener {
            startActivity(Intent(this, ApplyForLifeHouseActivity::class.java))
        }
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        val parent = webView.parent as ViewGroup
        parent.removeView(webView)
        webView.removeView(webView)
        webView.destroy()
        super.onDestroy()
    }

    public class JsInterface(context: Context){
        @JavascriptInterface
        fun share(string: String){
            LogUtil.e("js交互传值："+string)
        }
    }

    /*private class JsInterface {
        private Context mContext;

        public JsInterface(Context context) {
            this.mContext = context;
        }

        //在js中调用window.AndroidWebView.showInfoFromJs(name)，便会触发此方法。
        @JavascriptInterface
        public void showInfoFromJs(String name) {
            //Toast.makeText(mContext, name, Toast.LENGTH_SHORT).show();
        }
    }*/
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageClose) {
        LogUtil.e("$TAG be closed")
        finish()
    }
}