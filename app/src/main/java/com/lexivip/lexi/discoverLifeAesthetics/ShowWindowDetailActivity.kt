package com.lexivip.lexi.discoverLifeAesthetics

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.*
import android.widget.*
import com.basemodule.tools.*
import com.basemodule.ui.BaseActivity
import com.lexivip.lexi.*
import com.lexivip.lexi.beans.CommentBean
import com.lexivip.lexi.beans.ProductBean
import com.lexivip.lexi.beans.ShopWindowBean
import com.lexivip.lexi.index.explore.editorRecommend.EditorRecommendAdapter
import com.lexivip.lexi.index.selection.DiscoverLifeAdapter
import com.lexivip.lexi.net.WebUrl
import com.lexivip.lexi.shareUtil.ShareUtil
import com.lexivip.lexi.user.login.LoginActivity
import com.lexivip.lexi.user.login.UserProfileUtil
import com.lexivip.lexi.view.emotionkeyboardview.fragment.EmotionMainFragment
import com.yanyusong.y_divideritemdecoration.Y_Divider
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration
import kotlinx.android.synthetic.main.activity_show_window_detail.*
import kotlinx.android.synthetic.main.footer_comment_count.view.*
import kotlinx.android.synthetic.main.view_show_window_image3.view.*
import kotlinx.android.synthetic.main.view_show_window_image5.view.*
import kotlinx.android.synthetic.main.view_show_window_image7.view.*
import org.greenrobot.eventbus.EventBus
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class ShowWindowDetailActivity : BaseActivity(), ShowWindowDetailContract.View ,EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks{
    private val presenter: ShowWindowDetailPresenter by lazy { ShowWindowDetailPresenter(this) }

    private lateinit var rid: String
    private var shopWindow: ShowWindowDetailBean.DataBean? = null

    private val adapterGuessLike: EditorRecommendAdapter by lazy { EditorRecommendAdapter(R.layout.adapter_editor_recommend) }

    private val adapterRelateShowWindow: DiscoverLifeAdapter by lazy { DiscoverLifeAdapter(R.layout.adapter_discover_life) }

    private val adapter: ShopWindowDetailCommentListAdapter by lazy { ShopWindowDetailCommentListAdapter(R.layout.adapter_comment_list, presenter) }

    private var emotionMainFragment: EmotionMainFragment? = null
    //父级评论id
    private var pid: String = "0"

    private var imagrUrl:String?=null
    private var title:String?=null

    override val layout: Int = R.layout.activity_show_window_detail

    override fun getIntentData() {
        if (intent.hasExtra(ShowWindowDetailActivity::class.java.simpleName)) {
            rid = intent.getStringExtra(TAG)
        }
    }

    override fun setPresenter(presenter: ShowWindowDetailContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun initView() {
        loadingView.setOffsetTop(DimenUtil.dp2px(50.0))
//        EventBus.getDefault().register(this)
        customHeadView.setHeadCenterTxtShow(true, R.string.title_show_case)
        initGuessLike()
        initRelateShowWindow()
    }

    /**
     * 初始化EmotionMainFragment
     */
    private fun initEmotionFragment() {
        if (shopWindow == null) return
        //构建传递参数
        val bundle = Bundle()
        //绑定主内容编辑框
        bundle.putBoolean(EmotionMainFragment.BIND_TO_EDITTEXT, true)
        //隐藏控件
        bundle.putBoolean(EmotionMainFragment.HIDE_BAR_EDITTEXT_AND_BTN, false)

        bundle.putBoolean(EmotionMainFragment.IS_LIKE, shopWindow!!.is_like)
        bundle.putBoolean(EmotionMainFragment.IS_SHOP_WINDOW, true)
        bundle.putInt(EmotionMainFragment.LIKE_COUNTS, shopWindow!!.like_count)

        emotionMainFragment = EmotionMainFragment.newInstance(bundle)
        emotionMainFragment!!.bindToContentView(scrollViewGoodsDetail)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayoutEmotion, emotionMainFragment)
        transaction.addToBackStack(null)
        //提交修改
        transaction.commit()

        emotionMainFragment!!.setOnSendCommentListener(object : IOnSendCommentListener {
            override fun onSend(sendButton: Button, editText: EditText) {
                if (UserProfileUtil.isLogin()) {
                    val content = editText.text.trim().toString()
                    if (TextUtils.isEmpty(content)) {
                        ToastUtil.showInfo("请先输入评论")
                        return
                    }
                    presenter.submitComment(shopWindow!!.rid, pid, content, sendButton)
                    editText.text.clear()
                    emotionMainFragment!!.hideKeyBoard()
                    relativeLayoutBar.visibility = View.VISIBLE
                } else {
                    startActivity(Intent(applicationContext, LoginActivity::class.java))
                }
            }
        })
    }

    override fun requestNet() {
        presenter.loadData(rid, false)
    }

    /**
     * 设置橱窗详情数据
     */
    override fun setShowWindowData(data: ShowWindowDetailBean.DataBean?) {
        if (data == null) return
        shopWindow = data
        initEmotionFragment()
        if (data.comment_count > 0) {
            textViewCommentTitle.visibility = View.VISIBLE
            recyclerViewComment.visibility = View.VISIBLE
            viewLineComment.visibility = View.VISIBLE
            val linearLayoutManager = CustomLinearLayoutManager(applicationContext)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            linearLayoutManager.setScrollEnabled(false)
            recyclerViewComment.layoutManager = linearLayoutManager
            recyclerViewComment.adapter = adapter
            recyclerViewComment.addItemDecoration(DividerItemDecoration(applicationContext))
            adapter.setNewData(data.comments)

            adapter.setWindowData(data)
            val view = View.inflate(this, R.layout.footer_comment_count, null)
            view.textViewCommentCount.text = "查看全部" + data.comment_count + "条评论"
            adapter.addFooterView(view)
            view.setOnClickListener {
                if (shopWindow == null) return@setOnClickListener
                PageUtil.jump2ShopWindowCommentListActivity(shopWindow!!)
            }
        } else {
            viewLineComment.visibility = View.GONE
            textViewCommentTitle.visibility = View.GONE
            recyclerViewComment.visibility = View.GONE
        }

        GlideUtil.loadCircleImageWidthDimen(data.user_avatar, imageViewAvatar, DimenUtil.getDimensionPixelSize(R.dimen.dp30),ImageSizeConfig.SIZE_AVA)
        textViewName.text = data.user_name
        if (data.is_official) {
            textViewName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.icon_show_window_official, 0)
        } else {
            textViewName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        }

        if (data.like_count > 0) {
            textViewLikeCount.text = "${data.like_count}"
        } else {
            textViewLikeCount.text = ""
        }

        if (data.is_like) {
            imageViewLike.setImageResource(R.mipmap.icon_click_favorite_selected)
        } else {
            imageViewLike.setImageResource(R.mipmap.icon_click_favorite_normal)
        }

        if (data.comment_count > 0) {
            textViewComment.text = "${data.comment_count}"
        } else {
            textViewComment.text = ""
        }

        textViewTitle1.text = data.title
        this.title=data.title

        textViewTitle2.text = data.description

        if (TextUtils.equals(UserProfileUtil.getUserId(), data.uid)) {
            buttonFocus.visibility = View.GONE
        } else {
            buttonFocus.visibility = View.VISIBLE
            if (data.is_follow) {
                buttonFocus.text = Util.getString(R.string.text_focused)
                buttonFocus.setTextColor(Util.getColor(R.color.color_949ea6))
                buttonFocus.setBackgroundResource(R.drawable.bg_round_coloreff3f2)
            } else {
                buttonFocus.text = Util.getString(R.string.text_focus)
                buttonFocus.setTextColor(Util.getColor(android.R.color.white))
                buttonFocus.setBackgroundResource(R.drawable.bg_round_color5fe4b1)
            }
        }

        setGoodsImages(data)

        val arrayList = ArrayList<String>()
        for (tag in data.keywords) {
            arrayList.add("#$tag")
        }
        //设置标签
        tagGroup.setTags(arrayList)
        tagGroup.setOnTagClickListener { tag: String ->
            PageUtil.jump2RelateShopWindowActivity(tag.removePrefix("#"))
        }
    }


    /**
     * 设置橱窗产品图片
     */
    private fun setGoodsImages(data: ShowWindowDetailBean.DataBean) {
        val products = data.products
        var size = products.size
        val list = ArrayList<String>()

        //图片只能3,5,7张
        if (size < 3) return



        for (product in products) {
            list.add(product.cover)
        }
        imagrUrl=list[0]
        val screenW = ScreenUtil.getScreenWidth()
        val dp2: Int by lazy { DimenUtil.dp2px(2.0) }
        when (size) {
            3 -> {
                val dp124: Int by lazy { screenW / 3 }
                val dp250: Int by lazy { dp124 * 2 + dp2 / 2 }
                val layoutParams250: RelativeLayout.LayoutParams by lazy { RelativeLayout.LayoutParams(dp250, dp250) }
                val layoutParams31: RelativeLayout.LayoutParams by lazy { RelativeLayout.LayoutParams(dp124, dp124) }
                val layoutParams32: RelativeLayout.LayoutParams by lazy { RelativeLayout.LayoutParams(dp124, dp124) }
                val view = View.inflate(this, R.layout.view_show_window_image3, null)
                linearLayoutBox.addView(view)
                GlideUtil.loadImageWithDimen(list[0], view.imageView30,dp250,ImageSizeConfig.SIZE_P500)
                GlideUtil.loadImageWithDimen(list[1], view.imageView31,dp124,ImageSizeConfig.SIZE_P30X2)
                GlideUtil.loadImageWithDimen(list[2], view.imageView32,dp124,ImageSizeConfig.SIZE_P30X2)
                view.imageView30.layoutParams = layoutParams250
                view.imageView31.layoutParams = layoutParams31
                layoutParams31.addRule(RelativeLayout.END_OF, R.id.imageView30)
                layoutParams31.marginStart = dp2
                layoutParams32.addRule(RelativeLayout.BELOW, R.id.imageView31)
                layoutParams32.addRule(RelativeLayout.ALIGN_LEFT, R.id.imageView31)
                layoutParams32.topMargin = dp2 / 2
                view.relativeLayoutImage32.layoutParams = layoutParams32
                view.imageView30.setOnClickListener {
                    if (products[0] == null) return@setOnClickListener
                    PageUtil.jump2GoodsDetailActivity(products[0].rid)
                }

                view.imageView31.setOnClickListener {
                    if (products[1] == null) return@setOnClickListener
                    PageUtil.jump2GoodsDetailActivity(products[1].rid)
                }
                view.imageView32.setOnClickListener {
                    if (products[2] == null) return@setOnClickListener
                    PageUtil.jump2GoodsDetailActivity(products[2].rid)
                }
            }

            5 -> {
                val dp230: Int by lazy { screenW * 230 / 375 }
                val dp215: Int by lazy { screenW * 215 / 375 }
                val dp161: Int by lazy { dp215 * 161 / 215 }
                val dp114: Int by lazy { (dp230 - dp2) / 2 }
                val dp143: Int by lazy { screenW - dp230 }
                val dp158: Int by lazy { screenW - dp215 - dp2 }
                val layoutParams230: RelativeLayout.LayoutParams by lazy { RelativeLayout.LayoutParams(dp230, dp230) }
                val layoutParams114: RelativeLayout.LayoutParams by lazy { RelativeLayout.LayoutParams(dp143, dp114) }
                val layoutParams52: RelativeLayout.LayoutParams by lazy { RelativeLayout.LayoutParams(dp143, dp114) }
                val layoutParams53: RelativeLayout.LayoutParams by lazy { RelativeLayout.LayoutParams(dp215, dp161) }
                val layoutParams54: RelativeLayout.LayoutParams by lazy { RelativeLayout.LayoutParams(dp158, dp161) }
                val view = View.inflate(this, R.layout.view_show_window_image5, null)
                view.imageView50.layoutParams = layoutParams230
                view.imageView51.layoutParams = layoutParams114
                layoutParams114.addRule(RelativeLayout.END_OF, R.id.imageView50)
                layoutParams114.marginStart = dp2
                layoutParams52.addRule(RelativeLayout.BELOW, R.id.imageView51)
                layoutParams52.addRule(RelativeLayout.ALIGN_LEFT, R.id.imageView51)
                layoutParams52.topMargin = dp2
                view.imageView52.layoutParams = layoutParams52

                layoutParams53.addRule(RelativeLayout.BELOW, R.id.imageView50)
                layoutParams53.topMargin = dp2
                view.imageView53.layoutParams = layoutParams53

                layoutParams54.addRule(RelativeLayout.ALIGN_TOP, R.id.imageView53)
                layoutParams54.addRule(RelativeLayout.END_OF, R.id.imageView53)
                layoutParams54.leftMargin = dp2
                view.imageView54.layoutParams = layoutParams54
                linearLayoutBox.addView(view)
                GlideUtil.loadImageWithDimen(list[0], view.imageView50,dp230,ImageSizeConfig.SIZE_P500)
                GlideUtil.loadImageWithDimen(list[1], view.imageView51,dp143,dp114,ImageSizeConfig.SIZE_P30X2)
                GlideUtil.loadImageWithDimen(list[2], view.imageView52,dp143,dp114,ImageSizeConfig.SIZE_P30X2)
                GlideUtil.loadImageWithDimen(list[3], view.imageView53,dp215,dp161,ImageSizeConfig.SIZE_P30X2)
                GlideUtil.loadImageWithDimen(list[4], view.imageView54,dp158,dp161,ImageSizeConfig.SIZE_P30X2)
                view.imageView50.setOnClickListener {
                    if (products[0] == null) return@setOnClickListener
                    PageUtil.jump2GoodsDetailActivity(products[0].rid)
                }
                view.imageView51.setOnClickListener {
                    if (products[1] == null) return@setOnClickListener
                    PageUtil.jump2GoodsDetailActivity(products[1].rid)
                }

                view.imageView52.setOnClickListener {
                    if (products[2] == null) return@setOnClickListener
                    PageUtil.jump2GoodsDetailActivity(products[2].rid)
                }
                view.imageView53.setOnClickListener {
                    if (products[3] == null) return@setOnClickListener
                    PageUtil.jump2GoodsDetailActivity(products[3].rid)
                }

                view.imageView54.setOnClickListener {
                    if (products[4] == null) return@setOnClickListener
                    PageUtil.jump2GoodsDetailActivity(products[4].rid)
                }
            }

            7 -> {
                val dp215: Int by lazy { screenW * 215 / 375 }
                val dp158: Int by lazy { screenW - dp215 }
                val dp78: Int by lazy { (dp158 - dp2) / 2 }
                val dp136: Int by lazy { dp215 - dp78 - dp2 }
                val oneThirdScreenW: Int by lazy { (screenW - 2 * dp2) / 3 }
                val layoutParamsImageView70: RelativeLayout.LayoutParams by lazy { RelativeLayout.LayoutParams(dp78, dp78) }
                val layoutParamsImageView71: RelativeLayout.LayoutParams by lazy { RelativeLayout.LayoutParams(dp78, dp78) }
                val layoutParamsImageView72: RelativeLayout.LayoutParams by lazy { RelativeLayout.LayoutParams(dp215, dp215) }
                val layoutParamsImageView73: RelativeLayout.LayoutParams by lazy { RelativeLayout.LayoutParams(dp158, dp136) }
                val layoutParamsImageView74: RelativeLayout.LayoutParams by lazy { RelativeLayout.LayoutParams(oneThirdScreenW, oneThirdScreenW) }
                val layoutParamsImageView75: RelativeLayout.LayoutParams by lazy { RelativeLayout.LayoutParams(oneThirdScreenW, oneThirdScreenW) }
                val layoutParamsImageView76: RelativeLayout.LayoutParams by lazy { RelativeLayout.LayoutParams(oneThirdScreenW, oneThirdScreenW) }
                val view = View.inflate(this, R.layout.view_show_window_image7, null)
                view.imageView70.layoutParams = layoutParamsImageView70

                layoutParamsImageView71.leftMargin = dp2
                layoutParamsImageView71.addRule(RelativeLayout.END_OF, R.id.imageView70)
                view.imageView71.layoutParams = layoutParamsImageView71
                layoutParamsImageView72.addRule(RelativeLayout.END_OF, R.id.imageView71)
                layoutParamsImageView72.leftMargin = dp2
                view.imageView72.layoutParams = layoutParamsImageView72

                layoutParamsImageView73.addRule(RelativeLayout.BELOW, R.id.imageView70)
                layoutParamsImageView73.topMargin = dp2
                view.imageView73.layoutParams = layoutParamsImageView73

                layoutParamsImageView74.addRule(RelativeLayout.BELOW, R.id.imageView73)
                layoutParamsImageView74.topMargin = dp2
                view.imageView74.layoutParams = layoutParamsImageView74

                layoutParamsImageView75.addRule(RelativeLayout.ALIGN_TOP, R.id.imageView74)
                layoutParamsImageView75.addRule(RelativeLayout.END_OF, R.id.imageView74)
                layoutParamsImageView75.leftMargin = dp2
                view.imageView75.layoutParams = layoutParamsImageView75

                layoutParamsImageView76.addRule(RelativeLayout.ALIGN_TOP, R.id.imageView74)
                layoutParamsImageView76.addRule(RelativeLayout.END_OF, R.id.imageView75)
                layoutParamsImageView76.leftMargin = dp2
                view.imageView76.layoutParams = layoutParamsImageView76
                linearLayoutBox.addView(view)
                GlideUtil.loadImageWithDimen(list[0], view.imageView70,dp78,ImageSizeConfig.SIZE_P500)
                GlideUtil.loadImageWithDimen(list[1], view.imageView71,dp78,ImageSizeConfig.SIZE_P30X2)
                GlideUtil.loadImageWithDimen(list[2], view.imageView72,dp215,ImageSizeConfig.SIZE_P30X2)
                GlideUtil.loadImageWithDimen(list[3], view.imageView73,dp158,dp136,ImageSizeConfig.SIZE_P30X2)
                GlideUtil.loadImageWithDimen(list[4], view.imageView74,oneThirdScreenW,ImageSizeConfig.SIZE_P30X2)
                GlideUtil.loadImageWithDimen(list[5], view.imageView75,oneThirdScreenW,ImageSizeConfig.SIZE_P30X2)
                GlideUtil.loadImageWithDimen(list[6], view.imageView76,oneThirdScreenW,ImageSizeConfig.SIZE_P30X2)

                view.imageView70.setOnClickListener {
                    if (products[0] == null) return@setOnClickListener
                    PageUtil.jump2GoodsDetailActivity(products[0].rid)
                }
                view.imageView71.setOnClickListener {
                    if (products[1] == null) return@setOnClickListener
                    PageUtil.jump2GoodsDetailActivity(products[1].rid)
                }
                view.imageView72.setOnClickListener {
                    if (products[2] == null) return@setOnClickListener
                    PageUtil.jump2GoodsDetailActivity(products[2].rid)
                }
                view.imageView73.setOnClickListener {
                    if (products[3] == null) return@setOnClickListener
                    PageUtil.jump2GoodsDetailActivity(products[3].rid)
                }
                view.imageView74.setOnClickListener {
                    if (products[4] == null) return@setOnClickListener
                    PageUtil.jump2GoodsDetailActivity(products[4].rid)
                }
                view.imageView75.setOnClickListener {
                    if (products[5] == null) return@setOnClickListener
                    PageUtil.jump2GoodsDetailActivity(products[5].rid)
                }
                view.imageView76.setOnClickListener {
                    if (products[6] == null) return@setOnClickListener
                    PageUtil.jump2GoodsDetailActivity(products[6].rid)
                }
            }
        }
    }

    /**
     * 相关橱窗
     */
    private fun initRelateShowWindow() {
        presenter.getRelateShowWindow(rid)
        val linearLayoutManager = LinearLayoutManager(applicationContext)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerViewShowWindow.setHasFixedSize(true)
        recyclerViewShowWindow.layoutManager = linearLayoutManager
        recyclerViewShowWindow.adapter = adapterRelateShowWindow
        recyclerViewShowWindow.addItemDecoration(RecyclerViewDivider(AppApplication.getContext(), LinearLayoutManager.HORIZONTAL, resources.getDimensionPixelSize(R.dimen.dp10), Util.getColor(android.R.color.transparent)))
    }

    /**
     * 设置相关橱窗数据
     */
    override fun setRelateShowWindowData(windows: List<ShopWindowBean>) {
        if (windows.isEmpty()) {
            relativeLayoutRelate.visibility = View.GONE
            recyclerViewShowWindow.visibility = View.GONE
            lineRelate.visibility = View.GONE
            viewSeparatorRelate.visibility = View.GONE
        } else {
            relativeLayoutRelate.visibility = View.VISIBLE
            recyclerViewShowWindow.visibility = View.VISIBLE
            lineRelate.visibility = View.VISIBLE
            viewSeparatorRelate.visibility = View.VISIBLE
        }
        adapterRelateShowWindow.setNewData(windows)
    }


    /**
     * 初始化猜你喜欢
     */
    private fun initGuessLike() {
        if (!UserProfileUtil.isLogin()) return
        presenter.getGuessLike(rid)
        val linearLayoutManager = LinearLayoutManager(applicationContext)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerViewGuess.setHasFixedSize(true)
        recyclerViewGuess.layoutManager = linearLayoutManager
        recyclerViewGuess.adapter = adapterGuessLike
        recyclerViewGuess.addItemDecoration(RecyclerViewDivider(AppApplication.getContext(), LinearLayoutManager.HORIZONTAL, resources.getDimensionPixelSize(R.dimen.dp10), Util.getColor(android.R.color.transparent)))
    }

    /**
     * 设置猜你喜欢界面
     */
    override fun setGuessLikeData(products: List<ProductBean>) {
        if (products.isEmpty()) {
            relativeLayoutGuess.visibility = View.GONE
            recyclerViewGuess.visibility = View.GONE
            lineGuess.visibility = View.GONE
            line15Comment.visibility = View.GONE
        } else {
            relativeLayoutGuess.visibility = View.VISIBLE
            recyclerViewGuess.visibility = View.VISIBLE
            lineGuess.visibility = View.VISIBLE
            line15Comment.visibility = View.VISIBLE
        }
        adapterGuessLike.setNewData(products)
    }


    override fun installListener() {
        //点击输入框获取焦点
        textViewInput.setOnClickListener {
            emotionMainFragment!!.requestFocus()
        }

        val intArray = IntArray(2)
        textViewInput.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            textViewInput.getLocationOnScreen(intArray)
            if (intArray[1] > ScreenUtil.getScreenHeight() * 2 / 3) { //键盘被关闭
                if (emotionMainFragment == null) return@addOnLayoutChangeListener
                if (emotionMainFragment!!.isUserInputEmpty()) {
                    relativeLayoutBar.visibility = View.VISIBLE
                }
            } else { //键盘打开
                relativeLayoutBar.visibility = View.INVISIBLE
                emotionMainFragment!!.requestFocus()

            }
        }

        adapter.setOnItemChildClickListener { _, view, position ->

            val commentsBean = adapter.getItem(position) ?: return@setOnItemChildClickListener

            when (view.id) {
                R.id.textViewReply -> { //将被回复的评论id最为pid
                    emotionMainFragment!!.showKeyBoard()
                    pid = commentsBean.comment_id
                    emotionMainFragment!!.setEditTextHint("回复${commentsBean.user_name}:")
                }

                R.id.textViewPraise -> { //点赞
                    if (UserProfileUtil.isLogin()) {
                        presenter.praiseComment(commentsBean.comment_id, commentsBean.is_praise, position, view, false)
                    }else{
                        PageUtil.jump2LoginActivity()
                    }
                }
            }
        }

        /**
         * 外部点击重置输入状态
         */
        adapter.setOnItemClickListener { _, _, _ ->
            resetInputState()
        }

        /**
         * 外部点击重置输入状态
         */
        linearLayout.setOnClickListener {
            resetInputState()
        }

        buttonFocus.setOnClickListener { view ->
            if (shopWindow == null) return@setOnClickListener
            if (UserProfileUtil.isLogin()) {
                if (shopWindow!!.is_follow) {
                    presenter.unfocusUser(shopWindow!!.uid, view)
                } else {
                    presenter.focusUser(shopWindow!!.uid, view)
                }
            } else {
                PageUtil.jump2LoginActivity()
            }


        }


        imageViewLike.setOnClickListener { view ->
            if (shopWindow == null) return@setOnClickListener
            if (UserProfileUtil.isLogin()) {
                presenter.favoriteShowWindow(shopWindow!!.rid, imageViewLike, shopWindow!!.is_like, textViewLikeCount)
            }else{
                PageUtil.jump2LoginActivity()
            }
        }

        //跳转评论列表
        relativeLayoutComment.setOnClickListener { view ->
            if (shopWindow == null) return@setOnClickListener
            PageUtil.jump2ShopWindowCommentListActivity(shopWindow!!)
        }

        textViewShare.setOnClickListener { view ->
            //val dialog = DistributeShareDialog(this)
            //dialog.show()
            share()
        }


        //猜你喜欢点击
        adapterGuessLike.setOnItemClickListener { _, _, position ->
            val productsBean = adapterGuessLike.getItem(position) ?: return@setOnItemClickListener
            PageUtil.jump2GoodsDetailActivity(productsBean.rid)
        }


        //设置橱窗点击
        adapterRelateShowWindow.setOnItemClickListener { _, _, position ->
            val windowsBean = adapterRelateShowWindow.getItem(position)
                    ?: return@setOnItemClickListener
            PageUtil.jump2ShopWindowDetailActivity(windowsBean.rid)
        }
    }

    /**
     * 重置输入状态
     */
    private fun resetInputState() {
        if (emotionMainFragment!!.isUserInputEmpty()) {
            pid = "0"
            emotionMainFragment!!.setEditTextHint(getString(R.string.text_add_comment))
        }
        emotionMainFragment!!.hideKeyBoard()
    }

    /**
     * 更新评论点赞状态
     */
    override fun setPraiseCommentState(b: Boolean, position: Int, isSubAdapter: Boolean) {

        val commentsBean = adapter.getItem(position) as CommentBean
        if (b) {
            commentsBean.is_praise = true
            commentsBean.praise_count += 1
        } else {
            commentsBean.is_praise = false
            if (commentsBean.praise_count > 0) {
                commentsBean.praise_count -= 1
            }
        }
        adapter.notifyItemChanged(position)

    }

    /**
     * 重新设置评论数
     */
    override fun noticeCommentSuccess(data: CommentSuccessBean.DataBean) {
        if (shopWindow == null) return
        textViewComment.text = "${shopWindow!!.comment_count++}"
        EventBus.getDefault().post(shopWindow)
    }

    /**
     * 设置用户关注状态
     */
    override fun setUserFocusState(b: Boolean) {
        if (b) {
            shopWindow?.is_follow = b
            buttonFocus.text = Util.getString(R.string.text_focused)
            buttonFocus.setTextColor(Util.getColor(R.color.color_949ea6))
            buttonFocus.setBackgroundResource(R.drawable.bg_round_coloreff3f2)
        } else {
            shopWindow?.is_follow = b
            buttonFocus.text = Util.getString(R.string.text_focus)
            buttonFocus.setTextColor(Util.getColor(android.R.color.white))
            buttonFocus.setBackgroundResource(R.drawable.bg_round_color5fe4b1)
        }
        EventBus.getDefault().post(shopWindow)
    }

    /**
     * 设置喜欢
     */
    override fun setFavorite(b: Boolean, view1: ImageView, textViewLikeCount: TextView) {
        if (shopWindow == null) return
        if (b) {
            shopWindow!!.is_like = true
            view1.setImageResource(R.mipmap.icon_click_favorite_selected)
            shopWindow!!.like_count += 1
            textViewLikeCount.text = "${shopWindow!!.like_count}"
        } else {
            shopWindow!!.is_like = false
            view1.setImageResource(R.mipmap.icon_click_favorite_normal)
            shopWindow!!.like_count -= 1
            textViewLikeCount.text = "${shopWindow!!.like_count}"
        }
        EventBus.getDefault().post(shopWindow)
    }

    override fun goPage() {

    }

    override fun showLoadingView() {
        loadingView.show()
    }

    override fun dismissLoadingView() {
        loadingView.dismiss()
    }

    override fun showError(string: String) {
        loadingView.showError()
    }

    internal inner class DividerItemDecoration(context: Context) : Y_DividerItemDecoration(context) {
        private val color: Int = Util.getColor(R.color.color_eee)
        override fun getDivider(itemPosition: Int): Y_Divider? {
            val count = adapter.itemCount
            val divider: Y_Divider?
            divider = when (itemPosition) {
                count - 2 -> {
                    Y_DividerBuilder()
                            .setBottomSideLine(false, color, 0f, 0f, 0f)
                            .create()
                }
                count - 1 -> {
                    Y_DividerBuilder()
                            .setBottomSideLine(false, color, 0f, 0f, 0f)
                            .create()
                }

                else -> {
                    Y_DividerBuilder()
                            .setBottomSideLine(true, color, 0.5f, 20f, 0f)
                            .create()
                }
            }

            return divider
        }
    }

    override fun onBackPressed() {
        /**
         * 判断是否拦截返回键操作
         */
        if (!emotionMainFragment!!.isInterceptBackPress()) {
            finish()
        }
    }

    override fun onDestroy() {
//        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    @AfterPermissionGranted(Constants.REQUEST_CODE_SHARE)
    private fun share(){
        val perms = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (EasyPermissions.hasPermissions(AppApplication.getContext(), *perms)) {
            LogUtil.e("有么有调用塞")
            val share= ShareUtil(this)
            share.shareWindow(WebUrl.WINDOW,WebUrl.AUTH_WINDOW,imagrUrl,title,"",rid,rid)
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_photo), Constants.REQUEST_CODE_SHARE, *perms)
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
