package com.lexivip.lexi.index.discover

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SimpleItemAnimator
import android.support.v7.widget.StaggeredGridLayoutManager
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.basemodule.tools.*
import com.basemodule.ui.BaseActivity
import com.lexivip.lexi.*
import com.lexivip.lexi.beans.BrandPavilionBean
import com.lexivip.lexi.beans.CommentBean
import com.lexivip.lexi.beans.LifeWillBean
import com.lexivip.lexi.beans.ProductBean
import com.lexivip.lexi.discoverLifeAesthetics.CommentSuccessBean
import com.lexivip.lexi.discoverLifeAesthetics.IOnSendCommentListener
import com.lexivip.lexi.discoverLifeAesthetics.ShowWindowCommentListBean
import com.lexivip.lexi.index.detail.GoodsDetailActivity
import com.lexivip.lexi.index.explore.editorRecommend.EditorRecommendAdapter
import com.lexivip.lexi.net.WebUrl
import com.lexivip.lexi.shareUtil.ShareUtil
import com.lexivip.lexi.user.login.LoginActivity
import com.lexivip.lexi.user.login.UserProfileUtil
import com.lexivip.lexi.view.emotionkeyboardview.fragment.EmotionMainFragment
import com.yanyusong.y_divideritemdecoration.Y_Divider
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration
import kotlinx.android.synthetic.main.acticity_artical_detail.*
import kotlinx.android.synthetic.main.footer_comment_count.view.*
import kotlinx.android.synthetic.main.footer_view_article_detail.view.*
import kotlinx.android.synthetic.main.header_view_article_detail.view.*
import org.json.JSONObject

class ArticleDetailActivity : BaseActivity(), ArticleDetailContract.View {
    private val dialog: WaitingDialog by lazy { WaitingDialog(this) }
    override val layout: Int = R.layout.acticity_artical_detail
    private val presenter: ArticleDetailPresenter by lazy { ArticleDetailPresenter(this) }
    private val adapterRelateStories: AdapterGuessLike by lazy { AdapterGuessLike(R.layout.adapter_zc_manifest) }
    private val adapterRecommend: EditorRecommendAdapter by lazy { EditorRecommendAdapter(R.layout.adapter_editor_recommend) }
    private lateinit var listDescription: ArrayList<AdapterArticleDetail.MultipleItem>
    private lateinit var adapter: AdapterArticleDetail
    private val adapterArticleCommentList: ArticleDetailCommentListAdapter by lazy { ArticleDetailCommentListAdapter(R.layout.adapter_comment_list, presenter) }
    private var data: ArticleDetailBean.DataBean? = null
    private var rid: String? = null
    private lateinit var channelName: String
    private lateinit var headerView: View
    private lateinit var footerView: View
    private var brandPavilionBean: BrandPavilionBean? = null
    //父级评论id
    private var pid: String = "0"
    private var emotionMainFragment: EmotionMainFragment? = null
    private var isGoods:Boolean=false
    private var cover:String?=null
    private var title:String?=null
    override fun getIntentData() {
        rid = intent.getStringExtra(TAG)
        channelName = intent.getStringExtra(ArticleDetailActivity::class.java.name)
    }


    override fun initView() {
        headerView = View.inflate(this, R.layout.header_view_article_detail, null)
        headerView.textViewBrowserNum.setCompoundDrawables(Util.getDrawableWidthDimen(R.mipmap.icon_show_password, R.dimen.dp14, R.dimen.dp14), null, null, null)

        footerView = View.inflate(this, R.layout.footer_view_article_detail, null)
        initEmotionFragment()
        listDescription = ArrayList()
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.setHasFixedSize(false)
        recyclerView.layoutManager = linearLayoutManager
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        adapter = AdapterArticleDetail(listDescription, channelName)
        recyclerView.adapter = adapter

        initArticleComments()
        initRecommendGoods()
        initRelateStories()
        adapter.setHeaderView(headerView)
        adapter.addFooterView(footerView)
        adapter.setHeaderFooterEmpty(true, true)
    }

    /**
     * 初始化EmotionMainFragment
     */
    private fun initEmotionFragment() {
        //构建传递参数
        val bundle = Bundle()
        //绑定主内容编辑框
        bundle.putBoolean(EmotionMainFragment.BIND_TO_EDITTEXT, true)
        //隐藏控件
        bundle.putBoolean(EmotionMainFragment.HIDE_BAR_EDITTEXT_AND_BTN, false)

        if (data != null) {
//        bundle.putBoolean(EmotionMainFragment.IS_LIKE, shopWindowData.is_like)
//        bundle.putInt(EmotionMainFragment.LIKE_COUNTS, shopWindowData.like_count)
        }

        emotionMainFragment = EmotionMainFragment.newInstance(bundle)
        emotionMainFragment!!.bindToContentView(recyclerView)
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
                    presenter.submitComment(data!!.rid, pid, content, sendButton)
                    editText.text.clear()
                    emotionMainFragment!!.hideKeyBoard()
                    relativeLayoutBar.visibility = View.VISIBLE
                } else {
                    startActivity(Intent(applicationContext, LoginActivity::class.java))
                }
            }
        })
    }

    /**
     * 发布文章评论成功
     */
    override fun noticeCommentSuccess(data: CommentSuccessBean.DataBean) {
        if (this.data == null) return
        textViewCommentCount.text = "${this.data!!.comment_count++}"
    }


    /**
     * 初始化评论详情
     */
    private fun initArticleComments() {
        val linearLayoutManager = CustomLinearLayoutManager(applicationContext)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        linearLayoutManager.setScrollEnabled(false)
        footerView.recyclerViewComment.layoutManager = linearLayoutManager
        footerView.recyclerViewComment.adapter = adapterArticleCommentList
        footerView.recyclerViewComment.addItemDecoration(CommentDividerItemDecoration(applicationContext))
        (footerView.recyclerViewComment.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
    }

    /**
     * 初始化推荐商品
     */
    private fun initRecommendGoods() {
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        footerView.recyclerViewRecommend.setHasFixedSize(true)
        footerView.recyclerViewRecommend.layoutManager = linearLayoutManager
        footerView.recyclerViewRecommend.adapter = adapterRecommend
        footerView.recyclerViewRecommend.addItemDecoration(RecyclerViewDivider(AppApplication.getContext(), LinearLayoutManager.HORIZONTAL, resources.getDimensionPixelSize(R.dimen.dp10), Util.getColor(android.R.color.transparent)))

    }

    /**
     * 初始化相关故事
     */
    private fun initRelateStories() {
        val staggeredGridLayoutManager = CustomStaggerGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        staggeredGridLayoutManager.setScrollEnabled(false)
        footerView.recyclerViewRelateStory.layoutManager = staggeredGridLayoutManager
        footerView.recyclerViewRelateStory.adapter = adapterRelateStories
        footerView.recyclerViewRelateStory.addItemDecoration(DividerItemDecoration(AppApplication.getContext()))
    }

    override fun requestNet() {
        if (TextUtils.isEmpty(rid)) return
        presenter.loadData(false, rid!!)
        presenter.getArticleComments(rid!!)
        presenter.getRelateStories(rid!!)
        presenter.getRecommendProducts(rid!!)
    }

    /**
     * 设置评论列表
     */
    override fun setCommentListData(data: ShowWindowCommentListBean.DataBean) {
        initEmotionFragment()
        if (data.count > 0) {
            textViewCommentCount.text = Util.getNumberString(data.count)
            footerView.textViewCommentTitle.visibility = View.VISIBLE
            footerView.recyclerViewComment.visibility = View.VISIBLE
            footerView.line15Comment.visibility = View.VISIBLE
            adapterArticleCommentList.setNewData(data.comments)
            val view = View.inflate(this, R.layout.footer_comment_count, null)
            view.textViewCommentCount.text = "查看全部" + data.count + "条评论"
            adapterArticleCommentList.addFooterView(view)
            view.setOnClickListener {
                ToastUtil.showInfo("查看评论列表")
                //            if (shopWindow == null) return@setOnClickListener
                //            PageUtil.jump2ShopWindowCommentListActivity(shopWindow!!)
            }
        } else {
            footerView.line15Comment.visibility = View.GONE
            footerView.textViewCommentTitle.visibility = View.GONE
            footerView.recyclerViewComment.visibility = View.GONE
        }
    }

    /**
     * 设置推荐商品
     */
    override fun setRecommendProductsData(products: List<ProductBean>) {
        if (products.isEmpty()) {
            footerView.linearLayoutRecommendProduct.visibility = View.GONE
        }else{
            isGoods=true
        }
        adapterRecommend.setNewData(products)
    }

    /**
     * 设置相关故事数据
     */

    override fun setRelateStoriesData(data: MutableList<LifeWillBean>) {
        if (data.isEmpty()) footerView.linearLayoutRelateStory.visibility = View.GONE
        adapterRelateStories.setNewData(data)
    }

    /**
     * 设置用户关注状态
     */
    override fun setFocusState(followed_status: Int) {
        data?.is_follow = followed_status != 0
        setUserFocusState()
    }

    /**
     * 设置用户关注状态
     */
    private fun setUserFocusState() {
        if (data == null) return
        if (data!!.is_follow) {
            headerView.textViewFocus.text = Util.getString(R.string.text_focused)
            headerView.textViewFocus.setBackgroundResource(R.drawable.bg_round_coloreff3f2)
            headerView.textViewFocus.setTextColor(Util.getColor(R.color.color_949ea6))
        } else {
            headerView.textViewFocus.text = Util.getString(R.string.text_focus)
            headerView.textViewFocus.setBackgroundResource(R.drawable.bg_round_color5fe4b1)
            headerView.textViewFocus.setTextColor(Util.getColor(android.R.color.white))
        }
    }


    /**
     * 设置文章详情数据
     */
    override fun setData(data: JSONObject) {
        val bean = ArticleDetailBean.DataBean()
        bean.is_follow = data.optBoolean("is_follow")
        bean.uid = data.optString("uid")
        bean.title = data.optString("title")
        bean.is_user = data.optBoolean("is_user")
        bean.praise_count = data.optInt("praise_count")
        bean.comment_count = data.optInt("comment_count")
        bean.is_praise = data.optBoolean("is_praise")
        bean.rid = data.optString("rid")
        this.data = bean

        cover = data.optString("cover")
        val channelName = data.optString("channel_name")
        title = data.optString("title")
        val publishedAt = data.optLong("published_at")
        val browseCount = data.optInt("browse_count")
        val userAvatar = data.optString("user_avator")
        val userName = data.optString("user_name")
        val dealContent = data.optJSONArray("deal_content")
        val recommendStore = data.optJSONObject("recommend_store")

        if (bean.is_praise) {
            imageViewPraise.setImageResource(R.mipmap.icon_paise_large_red)
        } else {
            imageViewPraise.setImageResource(R.mipmap.icon_praise_large_gray)
        }

        textViewPraiseCount.text = Util.getNumberString(bean.praise_count)
        GlideUtil.loadImageWithDimenAndRadius(cover, headerView.imageViewCover, 0, ScreenUtil.getScreenWidth(), DimenUtil.dp2px(250.0), ImageSizeConfig.SIZE_AVABG)
        headerView.textViewArticleType.text = channelName

        headerView.textViewArticleTitle.text = title
        headerView.textViewDate.text = DateUtil.getDateByTimestamp(publishedAt, "yyyy.MM.dd")
        headerView.textViewBrowserNum.text = "$browseCount"
        GlideUtil.loadCircleImageWidthDimen(userAvatar, headerView.imageViewHeader, DimenUtil.dp2px(25.0), ImageSizeConfig.SIZE_AVA)
        headerView.textViewName.text = userName
        setUserFocusState()

        val maxIndex = dealContent.length() - 1
        for (i in 0..maxIndex) {
            val item = dealContent[i] as JSONObject
            val type = item.optString("type")
            val isBigProduct = item.optBoolean("big_picture")
            when (type) {
                "text" -> {
                    listDescription.add(AdapterArticleDetail.MultipleItem(item, AdapterArticleDetail.MultipleItem.TEXT_ITEM_TYPE))
                }
                "image" -> {
                    listDescription.add(AdapterArticleDetail.MultipleItem(item, AdapterArticleDetail.MultipleItem.IMAGE_ITEM_TYPE))
                }
                "product" -> {
                    if (isBigProduct) {
                        listDescription.add(AdapterArticleDetail.MultipleItem(item, AdapterArticleDetail.MultipleItem.LARGE_PRODUCT_ITEM_TYPE))
                    } else {
                        listDescription.add(AdapterArticleDetail.MultipleItem(item, AdapterArticleDetail.MultipleItem.SMALL_PRODUCT_ITEM_TYPE))
                    }

                }

                "blockquote" -> {

                }
                "ol" -> {

                }
            }
        }

        adapter.setNewData(listDescription)


        if (recommendStore == null) {
            footerView.relativeLayoutLifeHouse.visibility = View.GONE
            return
        } else {
            footerView.relativeLayoutLifeHouse.visibility = View.VISIBLE
        }

        brandPavilionBean = BrandPavilionBean()
        val isFollowStore = recommendStore.optBoolean("is_follow_store")
        val productCount = recommendStore.optInt("product_counts")
        val storeName = recommendStore.optString("store_name")
        val storeRid = recommendStore.optString("store_rid")
        val storeLogo = recommendStore.optString("store_logo")
        brandPavilionBean!!.is_followed = isFollowStore
        brandPavilionBean!!.product_count = productCount
        brandPavilionBean!!.name = storeName
        brandPavilionBean!!.rid = storeRid

        if (isFollowStore) {
            footerView.buttonFocus.text = Util.getString(R.string.text_focused)
            footerView.buttonFocus.setTextColor(Util.getColor(R.color.color_949ea6))
            footerView.buttonFocus.setBackgroundResource(R.drawable.bg_round_coloreff3f2)
            footerView.buttonFocus.setPadding(0, 0, 0, 0)
            footerView.buttonFocus.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        } else {
            footerView.buttonFocus.text = Util.getString(R.string.text_focus)
            footerView.buttonFocus.setTextColor(Util.getColor(android.R.color.white))
            footerView.buttonFocus.setBackgroundResource(R.drawable.bg_round_color5fe4b1)
            footerView.buttonFocus.setCompoundDrawables(Util.getDrawableWidthDimen(R.mipmap.icon_focus_pavilion, R.dimen.dp13, R.dimen.dp12), null, null, null)
        }

        val dp60 = DimenUtil.dp2px(60.0)
        GlideUtil.loadImageWithDimenAndRadius(storeLogo, footerView.imageView, 0, dp60, ImageSizeConfig.SIZE_AVA)
        footerView.textViewNum.text = "${productCount}件商品"
        footerView.textViewPavilionName.text = storeName


    }

    //设置品牌馆关注状态
    override fun setBrandPavilionFocusState(favorite: Boolean) {
        if (favorite) {
            footerView.buttonFocus.text = Util.getString(R.string.text_focused)
            footerView.buttonFocus.setTextColor(Util.getColor(R.color.color_949ea6))
            footerView.buttonFocus.setBackgroundResource(R.drawable.bg_round_coloreff3f2)
            footerView.buttonFocus.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            footerView.buttonFocus.setPadding(0, 0, 0, 0)
        } else {
            footerView.buttonFocus.text = Util.getString(R.string.text_focus)
            footerView.buttonFocus.setTextColor(Util.getColor(android.R.color.white))
            footerView.buttonFocus.setBackgroundResource(R.drawable.bg_round_color5fe4b1)
            footerView.buttonFocus.setCompoundDrawables(Util.getDrawableWidthDimen(R.mipmap.icon_focus_pavilion, R.dimen.dp13, R.dimen.dp12), null, null, null)
        }

        brandPavilionBean?.is_followed = favorite
    }


    override fun setHeadPavilionFocusState(favorite: Boolean) {
        if (data == null) return
        data!!.is_follow = favorite
        setUserFocusState()
    }

    /**
     * 设置
     */
    override fun setPraiseCommentState(b: Boolean, position: Int, subAdapter: Boolean) {
        val commentsBean = adapterArticleCommentList.getItem(position) as CommentBean
        if (b) {
            commentsBean.is_praise = true
            commentsBean.praise_count += 1
        } else {
            commentsBean.is_praise = false
            if (commentsBean.praise_count > 0) {
                commentsBean.praise_count -= 1
            }
        }
        adapterArticleCommentList.notifyItemChanged(position)
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
     * 设置用户文章点赞状态
     */
    override fun praiseArticleSuccess(b: Boolean) {
        if (data == null) return
        data!!.is_praise = b
        if (b) {
            data!!.praise_count++
            textViewPraiseCount.text = Util.getNumberString(data!!.praise_count)
            imageViewPraise.setImageResource(R.mipmap.icon_paise_large_red)
        } else {
            data!!.praise_count--
            textViewPraiseCount.text = Util.getNumberString(data!!.praise_count)
            imageViewPraise.setImageResource(R.mipmap.icon_praise_large_gray)
        }
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

        /**
         * 外部点击重置输入状态
         */
        adapterArticleCommentList.setOnItemClickListener { _, _, _ ->
            resetInputState()
        }

        /**
         * 外部点击重置输入状态
         */
        relativeLayout.setOnClickListener {
            resetInputState()
        }


        //评论子view点击
        adapterArticleCommentList.setOnItemChildClickListener { _, view, position ->
            val commentsBean = adapterArticleCommentList.getItem(position)
                    ?: return@setOnItemChildClickListener

            when (view.id) {
                R.id.textViewReply -> { //将被回复的评论id最为pid
                    emotionMainFragment!!.showKeyBoard()
                    pid = commentsBean.comment_id
                    emotionMainFragment!!.setEditTextHint("回复${commentsBean.user_name}:")
                }

                R.id.textViewPraise -> { //点赞
                    if (UserProfileUtil.isLogin()) {
                        presenter.praiseComment(commentsBean.comment_id, commentsBean.is_praise, position, view, false)
                    } else {
                        PageUtil.jump2LoginActivity()
                    }
                }
            }
        }

        //分享
        textViewShare.setOnClickListener{
            if(isGoods){
                val shareUtil=ShareUtil(this,WebUrl.GRASS+rid,title,"",WebUrl.AUTH_ARTICLE_GOODS+rid,cover)
            }else{
                val shareUtil=ShareUtil(this,WebUrl.GRASS+rid,title,"",WebUrl.AUTH_ARTICLE+rid,cover)
            }
        }

        //用户点赞文章
        relativeLayoutPraise.setOnClickListener {
            if (data == null) return@setOnClickListener
            presenter.praiseArticle(data!!.rid, data!!.is_praise, relativeLayoutPraise)
        }

        footerView.buttonFocus.setOnClickListener { v ->

            if (brandPavilionBean == null) return@setOnClickListener
            if (UserProfileUtil.isLogin()) {
                presenter.focusBrandPavilion(brandPavilionBean!!.rid, !brandPavilionBean!!.is_followed, v, false)
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }

        footerView.relativeLayoutLifeHouse.setOnClickListener {
            if (brandPavilionBean == null) return@setOnClickListener
            PageUtil.jump2BrandPavilionActivity(brandPavilionBean!!.rid)
        }

        imageViewBack.setOnClickListener { finish() }

//        imageViewShare.setOnClickListener {
//            ToastUtil.showInfo("分享")
//        }

        //关注用户
        headerView.textViewFocus.setOnClickListener {
            if (UserProfileUtil.isLogin()) {
                if (data == null) return@setOnClickListener
                if (data!!.is_user) {
                    presenter.focusUser(data!!.uid, headerView.textViewFocus, data!!.is_follow)
                } else { //此时uid是店铺id
                    presenter.focusBrandPavilion(data!!.uid, !data!!.is_follow, headerView.textViewFocus, true)
                }
            } else { //跳转登录
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var dySum = 0
            var dp250 = DimenUtil.dp2px(250.0)
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                dySum += dy
                if (dySum < dp250) {
                    textViewTitle.text = ""
                    imageViewBack.setImageResource(R.mipmap.icon_return_white)
//                    imageViewShare.setImageResource(R.mipmap.icon_share_white)
                    relativeLayoutHeader.setBackgroundResource(R.drawable.bg_gradient_color000)
                } else if (dySum > dp250) {
                    textViewTitle.text = data?.title
                    imageViewBack.setImageResource(R.mipmap.icon_nav_back)
//                    imageViewShare.setImageResource(R.mipmap.icon_click_share)
                    relativeLayoutHeader.setBackgroundColor(Util.getColor(android.R.color.white))
                }
            }
        })

        //跳转商品详情
        adapterRecommend.setOnItemClickListener { _, _, position ->
            val item = adapterRecommend.getItem(position)
            val intent = Intent(this, GoodsDetailActivity::class.java)
            intent.putExtra(GoodsDetailActivity::class.java.simpleName, item)
            startActivity(intent)
        }

        //跳转商品详情
        adapterRelateStories.setOnItemClickListener { _, _, position ->
            val item = adapterRelateStories.getItem(position) ?: return@setOnItemClickListener
            val intent = Intent(this, ArticleDetailActivity::class.java)
            intent.putExtra(ArticleDetailActivity::class.java.simpleName, item.rid)
            intent.putExtra(ArticleDetailActivity::class.java.name, item.channel_name)
            startActivity(intent)
        }

    }

    override fun showLoadingView() {
        dialog.show()
    }

    override fun dismissLoadingView() {
        dialog.dismiss()
    }

    override fun showError(string: String) {
        ToastUtil.showError(string)
    }


    override fun setPresenter(presenter: ArticleDetailContract.Presenter?) {
        setPresenter(presenter)
    }

    private inner class DividerItemDecoration constructor(context: Context) : Y_DividerItemDecoration(context) {
        private val color: Int = Util.getColor(android.R.color.white)
        private val height = 20f
        override fun getDivider(itemPosition: Int): Y_Divider? {
            var divider: Y_Divider? = null
            if (itemPosition % 2 != 0) {
                divider = Y_DividerBuilder()
                        .setBottomSideLine(true, color, height, 0f, 0f)
                        .setLeftSideLine(true, color, 10f, 0f, 0f)
                        .create()
            } else {
                divider = Y_DividerBuilder()
                        .setBottomSideLine(true, color, height, 0f, 0f)
                        .create()
            }
            return divider
        }
    }


    internal inner class CommentDividerItemDecoration(context: Context) : Y_DividerItemDecoration(context) {
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
                            .setBottomSideLine(true, color, 0.5f, 15f, 0f)
                            .create()
                }
            }

            return divider
        }
    }
}