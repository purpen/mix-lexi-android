package com.lexivip.lexi.discoverLifeAesthetics
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.view.*
import android.widget.RelativeLayout
import com.basemodule.tools.*
import com.basemodule.ui.BaseActivity
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.PageUtil
import com.lexivip.lexi.R
import com.lexivip.lexi.RecyclerViewDivider
import com.lexivip.lexi.beans.ProductBean
import com.lexivip.lexi.beans.ShopWindowBean
import com.lexivip.lexi.index.explore.editorRecommend.EditorRecommendAdapter
import com.lexivip.lexi.index.lifehouse.DistributeShareDialog
import com.lexivip.lexi.index.selection.DiscoverLifeAdapter
import kotlinx.android.synthetic.main.activity_show_window_detail.*
import kotlinx.android.synthetic.main.view_show_window_image3.view.*
import kotlinx.android.synthetic.main.view_show_window_image5.view.*
import kotlinx.android.synthetic.main.view_show_window_image7.view.*

class ShowWindowDetailActivity : BaseActivity(), ShowWindowDetailContract.View {

    private val dialog: WaitingDialog by lazy { WaitingDialog(this) }

    private val presenter: ShowWindowDetailPresenter by lazy { ShowWindowDetailPresenter(this) }

    private lateinit var rid: String
    private var shopWindow: ShowWindowDetailBean.DataBean?=null

    private val adapterGuessLike: EditorRecommendAdapter by lazy { EditorRecommendAdapter(R.layout.adapter_editor_recommend) }

    private val adapterRelateShowWindow: DiscoverLifeAdapter by lazy { DiscoverLifeAdapter(R.layout.adapter_discover_life) }

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
        customHeadView.setHeadCenterTxtShow(true, R.string.title_show_case)
        editText.inputType = InputType.TYPE_TEXT_FLAG_MULTI_LINE
        editText.setSingleLine(false)
        initGuessLike()
        initRelateShowWindow()
    }

    override fun requestNet() {
        presenter.loadData(rid, false)
    }

    /**
     * 设置橱窗详情数据
     */
    override fun setShowWindowData(data: ShowWindowDetailBean.DataBean?) {
        if(data==null) return
        shopWindow = data
        GlideUtil.loadCircleImageWidthDimen(data.user_avatar, imageViewAvatar, DimenUtil.getDimensionPixelSize(R.dimen.dp30))
        textViewName.text = data.user_name
        if (data.is_official) {
            textViewName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.icon_show_window_official, 0)
        } else {
            textViewName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        }

        if (data.like_count > 0) {
            imageViewLike.setImageResource(R.mipmap.icon_click_favorite_selected)
            textViewLike.text = "${data.like_count}"
        } else {
            imageViewLike.setImageResource(R.mipmap.icon_click_favorite_normal)
            textViewLike.text = ""
        }

        if (data.comment_count > 0) {
            textViewComment.text = "${data.comment_count}"
        } else {
            textViewComment.text = ""
        }

        textViewTitle1.text = data.title

        textViewTitle2.text = data.description


        if (data.is_follow) {
            buttonFocus.text = Util.getString(R.string.text_focused)
        } else {
            buttonFocus.text = Util.getString(R.string.text_focus)
        }

        setGoodsImages(data)

        val arrayList = ArrayList<String>()
        for (tag in data.keywords){
            arrayList.add("#$tag")
        }
        //设置标签
        tagGroup.setTags(arrayList)
//        tagGroup.setOnTagClickListener { tag: String? -> ToastUtil.showInfo(tag) }
    }


    /**
     * 设置橱窗产品图片
     */
    private fun setGoodsImages(data: ShowWindowDetailBean.DataBean) {
        val products = data.products
        val size = products.size
        val list = ArrayList<String>()

        //图片只能3,5,7张
        if (size < 3) return

        val view: View

        for (product in products) {
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
        adapterRelateShowWindow.setNewData(windows)
    }


    /**
     * 初始化猜你喜欢
     */
    private fun initGuessLike() {
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
        adapterGuessLike.setNewData(products)
    }


    override fun installListener() {


        editText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
            override fun afterTextChanged(s: Editable) { //动态设置底部栏高度
                val lineCount = editText.lineCount
                val height = DimenUtil.getDimensionPixelSize(R.dimen.dp50) + editText.lineHeight * (lineCount - 1)
                val layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, height)
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
                relativeLayoutBar.layoutParams = layoutParams
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
        })

        buttonFocus.setOnClickListener { view ->
            if (shopWindow==null) return@setOnClickListener
            if (shopWindow!!.is_follow) {
                presenter.unfocusUser(shopWindow!!.uid, view)
            } else {
                presenter.focusUser(shopWindow!!.uid, view)
            }
        }


        relativeLayoutLike.setOnClickListener { view ->
            if (shopWindow==null) return@setOnClickListener
            if (shopWindow!!.is_like) {
                presenter.unfavoriteShowWindow(shopWindow!!.rid, view)
            } else {
                presenter.favoriteShowWindow(shopWindow!!.rid, view)
            }
        }

        //跳转评论列表
        relativeLayoutComment.setOnClickListener { view ->
            val intent = Intent(applicationContext, ShowWindowCommentListActivity::class.java)
            intent.putExtra(ShowWindowCommentListActivity::class.java.simpleName,rid)
            startActivity(intent)
        }

        textViewShare.setOnClickListener { view ->
            val dialog = DistributeShareDialog(this)
            dialog.show()
        }


        //猜你喜欢点击
        adapterGuessLike.setOnItemClickListener { _, _, position ->
            val productsBean = adapterGuessLike.getItem(position)?:return@setOnItemClickListener
            PageUtil.jump2GoodsDetailActivity(productsBean.rid)
        }


        //设置橱窗点击
        adapterRelateShowWindow.setOnItemClickListener { _, _, position ->
            val windowsBean = adapterRelateShowWindow.getItem(position)?:return@setOnItemClickListener
          PageUtil.jump2ShopWindowDetailActivity(windowsBean.rid)
        }

        // 发送评论
        editText.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                val content = editText.text.toString().trim()
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.showInfo(Util.getString(R.string.hint_input_comment))
                } else {
                    presenter.sendComment(rid, "", content)
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
        if (shopWindow==null) return
        textViewComment.text = "${shopWindow!!.comment_count++}"
    }

    /**
     * 设置用户关注状态
     */
    override fun setUserFocusState(b: Boolean) {
        if (b) {
            shopWindow?.is_follow = b
            buttonFocus.text = Util.getString(R.string.text_focused)
        } else {
            shopWindow?.is_follow = b
            buttonFocus.text = Util.getString(R.string.text_focus)
        }
    }

    /**
     * 设置喜欢
     */
    override fun setFavorite(b: Boolean) {
        if (shopWindow == null) return
        if (b) {
            imageViewLike.setImageResource(R.mipmap.icon_click_favorite_selected)
            textViewLike.text = "${shopWindow!!.like_count++}"
        } else {
            imageViewLike.setImageResource(R.mipmap.icon_click_favorite_normal)
            textViewLike.text = "${shopWindow!!.like_count--}"
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
