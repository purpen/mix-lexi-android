package com.thn.lexi.discoverLifeAesthetics

import android.content.Intent
import android.graphics.Paint
import android.graphics.Rect
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.view.*
import android.widget.RelativeLayout
import com.basemodule.tools.*
import com.basemodule.ui.BaseActivity
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import com.thn.lexi.RecyclerViewDivider
import com.thn.lexi.index.explore.EditorRecommendAdapter
import com.thn.lexi.index.explore.EditorRecommendBean
import com.thn.lexi.index.lifehouse.DistributeShareDialog
import com.thn.lexi.index.selection.DiscoverLifeAdapter
import com.thn.lexi.index.selection.DiscoverLifeBean
import kotlinx.android.synthetic.main.activity_show_window_detail.*
import kotlinx.android.synthetic.main.view_show_window_image3.view.*
import kotlinx.android.synthetic.main.view_show_window_image5.view.*
import kotlinx.android.synthetic.main.view_show_window_image7.view.*

class ShowWindowDetailActivity : BaseActivity(), ShowWindowDetailContract.View {

    private val dialog: WaitingDialog by lazy { WaitingDialog(this) }

    private val presenter: ShowWindowDetailPresenter by lazy { ShowWindowDetailPresenter(this) }

    private lateinit var shopWindowsBean: ShowWindowBean.DataBean.ShopWindowsBean

    private val adapterGuessLike: EditorRecommendAdapter by lazy { EditorRecommendAdapter(R.layout.adapter_editor_recommend) }

    private val adapterRelateShowWindow: DiscoverLifeAdapter by lazy { DiscoverLifeAdapter(R.layout.adapter_discover_life) }

    override val layout: Int = R.layout.activity_show_window_detail
    private var layoutChangeListener: View.OnLayoutChangeListener? = null

    override fun getIntentData() {
        if (intent.hasExtra(ShowWindowDetailActivity::class.java.simpleName)) {
            shopWindowsBean = intent.getParcelableExtra(ShowWindowDetailActivity::class.java.simpleName)
        }
    }

    override fun setPresenter(presenter: ShowWindowDetailContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun initView() {
        customHeadView.setHeadCenterTxtShow(true, R.string.title_show_case)
        editText.inputType = InputType.TYPE_TEXT_FLAG_MULTI_LINE
        editText.setSingleLine(false)
        initShowWindow()
        initGuessLike()
        initRelateShowWindow()


        val layoutParams1 = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, DimenUtil.getDimensionPixelSize(R.dimen.dp50))
        layoutParams1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        relativeLayoutBar.layoutParams = layoutParams1
    }

    override fun requestNet() {
        presenter.loadData(shopWindowsBean.rid, false)
    }

    /**
     * 设置橱窗详情数据
     */
    override fun setShowWindowData(data: ShowWindowDetailBean.DataBean?) {
        //TODO  代替initShowWindow()设置数据
    }

    /**
     * 初始化橱窗
     */
    @Deprecated("see setShowWindowData")
    private fun initShowWindow() {
        GlideUtil.loadCircleImageWidthDimen(shopWindowsBean.user_avatar, imageViewAvatar, DimenUtil.getDimensionPixelSize(R.dimen.dp30))
        textViewName.text = shopWindowsBean.user_name
        if (shopWindowsBean.is_official) {
            textViewName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.icon_show_window_official, 0)
        } else {
            textViewName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        }

        shopWindowsBean.like_count = 1000
        if (shopWindowsBean.like_count > 0) {
            imageViewLike.setImageResource(R.mipmap.icon_click_favorite_selected)
            textViewLike.text = "${shopWindowsBean.like_count}"
        } else {
            imageViewLike.setImageResource(R.mipmap.icon_click_favorite_normal)
            textViewLike.text = ""
        }

        if (shopWindowsBean.comment_count > 0) {
            textViewComment.text = "${shopWindowsBean.comment_count}"
        } else {
            textViewComment.text = ""
        }

        textViewTitle1.text = shopWindowsBean.title

        textViewTitle2.text = shopWindowsBean.description


        if (shopWindowsBean.is_follow) {
            buttonFocus.text = Util.getString(R.string.text_focused)
        } else {
            buttonFocus.text = Util.getString(R.string.text_focus)
        }

        setGoodsImages()

        //设置标签
        tagGroup.setTags(listOf("#生活", "#美学", "#夏天girl", "#东东"))
        tagGroup.setOnTagClickListener { tag: String? -> ToastUtil.showInfo(tag) }
    }


    /**
     * 设置橱窗产品图片
     */
    private fun setGoodsImages() {
        // 设置3张产品图
        if (shopWindowsBean.products.isEmpty()) return

        val list = ArrayList<String>()
        val size = shopWindowsBean.products.size

        //图片只能3,5,7张
        if (size < 3) return

        val view: View

        for (product in shopWindowsBean.products) {
            list.add(product.cover)
        }

        when (size) {
            3 -> {
                view = LayoutInflater.from(applicationContext).inflate(R.layout.view_show_window_image3, null)
                linearLayoutBox.addView(view)
                GlideUtil.loadImageWithFading(list[0], view.imageView30)
                GlideUtil.loadImageWithFading(list[1], view.imageView31)
                GlideUtil.loadImageWithFading(list[2], view.imageView32)
            }

            5 -> {
                view = LayoutInflater.from(applicationContext).inflate(R.layout.view_show_window_image5, null)
                linearLayoutBox.addView(view)
                GlideUtil.loadImageWithFading(list[0], view.imageView50)
                GlideUtil.loadImageWithFading(list[1], view.imageView51)
                GlideUtil.loadImageWithFading(list[2], view.imageView52)
                GlideUtil.loadImageWithFading(list[3], view.imageView53)
                GlideUtil.loadImageWithFading(list[4], view.imageView54)
            }

            7 -> {
                view = LayoutInflater.from(applicationContext).inflate(R.layout.view_show_window_image7, null)
                linearLayoutBox.addView(view)
                GlideUtil.loadImageWithFading(list[0], view.imageView70)
                GlideUtil.loadImageWithFading(list[1], view.imageView71)
                GlideUtil.loadImageWithFading(list[2], view.imageView72)
                GlideUtil.loadImageWithFading(list[3], view.imageView73)
                GlideUtil.loadImageWithFading(list[4], view.imageView74)
                GlideUtil.loadImageWithFading(list[5], view.imageView75)
                GlideUtil.loadImageWithFading(list[6], view.imageView76)
            }
        }
    }

    /**
     * 相关橱窗
     */
    private fun initRelateShowWindow() {
        presenter.getRelateShowWindow(shopWindowsBean.rid)
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
    override fun setRelateShowWindowData(windows: List<DiscoverLifeBean.DataBean.ShopWindowsBean>) {

        var demos = ArrayList<DiscoverLifeBean.DataBean.ShopWindowsBean>()

        for (i in 0..3) {
            val windowsBean = DiscoverLifeBean.DataBean.ShopWindowsBean()
            demos.add(windowsBean)
        }

        for (item in demos) {
            item.title = "发现生活美学"
            item.description = "生活美学好"
            val list = ArrayList<DiscoverLifeBean.DataBean.ShopWindowsBean.ProductsBean>()
            for (i in 0..3) {
                val productsBean = DiscoverLifeBean.DataBean.ShopWindowsBean.ProductsBean()
                productsBean.cover = "http://c.hiphotos.baidu.com/image/h%3D300/sign=87d6daed02f41bd5c553eef461d881a0/f9198618367adab4b025268587d4b31c8601e47b.jpg"
                list.add(productsBean)
            }
            item.products = list
            item.avatar = "http://imgtu.5011.net/uploads/content/20170209/4934501486627131.jpg"
        }

        adapterRelateShowWindow.setNewData(demos)
//        adapterDiscoverLife.setNewData(windows)
    }


    /**
     * 初始化猜你喜欢
     */
    private fun initGuessLike() {
        presenter.getGuessLike(shopWindowsBean.rid)
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
    override fun setGuessLikeData(products: List<EditorRecommendBean.DataBean.ProductsBean>) {
        adapterGuessLike.setNewData(products)
    }


    override fun installListener() {


        editText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
            override fun afterTextChanged(s: Editable) { //动态设置底部栏高度
                val lineCount = editText.lineCount
                var height = DimenUtil.getDimensionPixelSize(R.dimen.dp50) + editText.lineHeight * (lineCount - 1)
                val layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, height)
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
                relativeLayoutBar.layoutParams = layoutParams
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
        })

        buttonFocus.setOnClickListener { view ->
            //TODO 记得修改为真实UID
            if (shopWindowsBean.is_follow) {
                presenter.unfocusUser("uid", view)
            } else {
                presenter.focusUser("uid", view)
            }
        }


        relativeLayoutLike.setOnClickListener { view ->
            if (shopWindowsBean.is_like) {
                presenter.unfavoriteShowWindow(shopWindowsBean.rid, view)
            } else {
                presenter.favoriteShowWindow(shopWindowsBean.rid, view)
            }
        }

        //跳转评论列表
        relativeLayoutComment.setOnClickListener { view ->
            val intent = Intent(applicationContext, ShowWindowCommentListActivity::class.java)
            intent.putExtra(ShowWindowCommentListActivity::class.java.simpleName, shopWindowsBean.rid)
            startActivity(intent)
        }

        textViewShare.setOnClickListener { view ->
            val dialog = DistributeShareDialog(this)
            dialog.show()
        }


        //猜你喜欢点击
        adapterGuessLike.setOnItemClickListener { adapter, view, position ->
            val productsBean = adapterGuessLike.getItem(position) as EditorRecommendBean.DataBean.ProductsBean
            ToastUtil.showInfo("商品详情=" + position)
        }


        //设置橱窗点击
        adapterRelateShowWindow.setOnItemClickListener { adapter, view, position ->
            val windowsBean = adapterRelateShowWindow.getItem(position) as DiscoverLifeBean.DataBean.ShopWindowsBean
            val intent = Intent(applicationContext, ShowWindowDetailActivity::class.java)
//            intent.putExtra(ShowWindowDetailActivity::class.java.simpleName,windowsBean.rid)
            startActivity(intent)
        }

        // 发送评论
        editText.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                val content = editText.text.toString().trim()
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.showInfo(Util.getString(R.string.hint_input_comment))
                } else {
                    presenter.sendComment(shopWindowsBean.rid, "", content)
                }
                return@setOnKeyListener true
            }

            return@setOnKeyListener false
        }

    }

    /**
     * 重新设置评论数
     */
    override fun setCommentState() {
        textViewComment.text = "" + (shopWindowsBean.comment_count + 1)
    }

    /**
     * 设置用户关注状态
     */
    override fun setUserFocusState(b: Boolean) {
        if (b) {
            buttonFocus.text = Util.getString(R.string.text_focused)
        } else {
            buttonFocus.text = Util.getString(R.string.text_focus)
        }
    }

    /**
     * 设置喜欢
     */
    override fun setFavorite(b: Boolean) {
        if (b) {
            imageViewLike.setImageResource(R.mipmap.icon_click_favorite_selected)
            textViewLike.text = "" + (shopWindowsBean.like_count + 1)
        } else {
            imageViewLike.setImageResource(R.mipmap.icon_click_favorite_normal)
            textViewLike.text = "" + (shopWindowsBean.like_count - 1)
        }
    }

    override fun goPage() {

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
}
