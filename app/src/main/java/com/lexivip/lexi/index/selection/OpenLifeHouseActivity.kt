package com.lexivip.lexi.index.selection
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.view.ViewGroup
import android.webkit.*
import android.widget.RelativeLayout
import com.basemodule.tools.LogUtil
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseActivity
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.R
import kotlinx.android.synthetic.main.acticity_open_life_house.*

class OpenLifeHouseActivity : BaseActivity() {
    private val dialog: WaitingDialog by lazy { WaitingDialog(this) }
    override val layout: Int = R.layout.acticity_open_life_house
    private var webView: WebView? = null
    private lateinit var url:String
    private var title:Int? = 0
    override fun getIntentData() {
        url= intent.getStringExtra("url")
        title=intent.getIntExtra("title",R.string.title_open_life_house)
    }
    override fun initView() {
        //customHeadView.setHeadCenterTxtShow(true,R.string.title_open_life_house)
        customHeadView.setHeadCenterTxtShow(true,title!!)
        //val url = "https://h5.lexivip.com/shop/guide"
        webView = WebView(AppApplication.getContext())
        webView?.overScrollMode = WebView.OVER_SCROLL_NEVER
        val settings = webView?.settings
        settings?.setAppCacheEnabled(true)
        settings?.javaScriptCanOpenWindowsAutomatically = true
        settings?.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        settings?.builtInZoomControls = false
        settings?.javaScriptEnabled = true
        settings?.useWideViewPort = true
        settings?.loadWithOverviewMode = true
        settings?.layoutAlgorithm = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING
        settings?.domStorageEnabled = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings?.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }

        val layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)
        layoutParams.addRule(RelativeLayout.BELOW,R.id.customHeadView)
        webView?.layoutParams = layoutParams
        relativeLayout.addView(webView)
        webView?.webViewClient = object :WebViewClient(){

            override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                LogUtil.e("=================")
                handler?.proceed()
                super.onReceivedSslError(view, handler, error);
            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                dialog.show()
                LogUtil.e("=================$url")
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                dialog.dismiss()
                super.onPageFinished(view, url)
            }
        }
        webView?.loadUrl(url)
    }



    override fun onDestroy() {
        if (webView!=null){
            val parent = webView!!.parent as ViewGroup
            parent.removeView(webView)
        }
        webView?.removeView(webView)
        webView?.destroy()

        super.onDestroy()
    }
}