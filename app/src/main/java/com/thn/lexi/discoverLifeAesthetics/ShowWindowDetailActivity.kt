package com.thn.lexi.discoverLifeAesthetics
import android.content.Intent
import android.graphics.Rect
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
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

class ShowWindowDetailActivity : BaseActivity(), ShowWindowDetailContract.View {

    private val dialog: WaitingDialog by lazy { WaitingDialog(this) }

    private val presenter: ShowWindowDetailPresenter by lazy { ShowWindowDetailPresenter(this) }

    private lateinit var shopWindowsBean: ShowWindowBean.DataBean.ShopWindowsBean

    private val adapterGuessLike: EditorRecommendAdapter by lazy { EditorRecommendAdapter(R.layout.adapter_editor_recommend) }

    private val adapterRelateShowWindow: DiscoverLifeAdapter by lazy { DiscoverLifeAdapter(R.layout.adapter_discover_life) }

    override val layout: Int = R.layout.activity_show_window_detail


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
        initShowWindow()
        initGuessLike()
        initRelateShowWindow()
    }

    override fun requestNet() {
        presenter.loadData(shopWindowsBean.rid,false)
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

        shopWindowsBean.like_count =1000
        if (shopWindowsBean.like_count>0) {
            imageViewLike.setImageResource(R.mipmap.icon_click_favorite_selected)
            textViewLike.text = "${shopWindowsBean.like_count}"
        } else {
            imageViewLike.setImageResource(R.mipmap.icon_click_favorite_normal)
            textViewLike.text = ""
        }

        if (shopWindowsBean.comment_count>0){
            textViewComment.text = "${shopWindowsBean.comment_count}"
        }else{
            textViewComment.text = ""
        }

        textViewTitle1.text = shopWindowsBean.title

        textViewTitle2.text = shopWindowsBean.description


        if (shopWindowsBean.is_follow) {
            buttonFocus.text = Util.getString(R.string.text_focused)
        } else {
            buttonFocus.text = Util.getString(R.string.text_focus)
        }

        // 设置3张产品图
        if (shopWindowsBean.products.isEmpty()) return

        val list = ArrayList<String>()
        val size = shopWindowsBean.products.size
        if (size <= 3) {
            for (product in shopWindowsBean.products) {
                list.add(product.cover)
            }
        } else {
            val subList = shopWindowsBean.products.subList(0, 3)
            for (product in subList) {
                list.add(product.cover)
            }
        }

        val list1 = ArrayList<ShowWindowProductAdapter.MultipleItem>()

        for (i in list.indices) {
            if (i == 0) {//第一张图占2列宽
                list1.add(ShowWindowProductAdapter.MultipleItem(list[i], ShowWindowProductAdapter.MultipleItem.ITEM_TYPE_SPAN2, ShowWindowProductAdapter.MultipleItem.ITEM_SPAN2_SIZE))
            } else {//占1列
                list1.add(ShowWindowProductAdapter.MultipleItem(list[i], ShowWindowProductAdapter.MultipleItem.ITEM_TYPE_SPAN1, ShowWindowProductAdapter.MultipleItem.ITEM_SPAN1_SIZE))
            }
        }

        val showWindowProductAdapter = ShowWindowProductAdapter(list1, size)
        val gridLayoutManager = GridLayoutManager(AppApplication.getContext(), 2)
        gridLayoutManager.orientation = GridLayoutManager.HORIZONTAL
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = showWindowProductAdapter
        showWindowProductAdapter.setSpanSizeLookup { _, position ->
            list1[position].spanSize
        }

        if (recyclerView.itemDecorationCount == 0) {
            val size = DimenUtil.getDimensionPixelSize(R.dimen.dp2)
            recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
                    super.getItemOffsets(outRect, view, parent, state)
                    val position = parent.getChildAdapterPosition(view)
                    if (position == 0) {
                        outRect.right = size
                    } else {
                        outRect.right = 0
                    }

                    if (position == 1) {
                        outRect.bottom = size
                    } else {
                        outRect.bottom = 0
                    }
                }
            })
        }


        //设置标签
        tagGroup.setTags(listOf("#生活", "#美学", "#夏天girl", "#东东"))
        tagGroup.setOnTagClickListener { tag: String? -> ToastUtil.showInfo(tag) }
    }


    /**
     * 相关橱窗
     */
    private fun initRelateShowWindow() {
        presenter.getRelateShowWindow()
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
        presenter.getGuessLike()
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

        buttonFocus.setOnClickListener{ view ->
            //TODO 记得修改为真实UID
            if(shopWindowsBean.is_follow){
                presenter.unfocusUser("uid",view)
            }else{
                presenter.focusUser("uid",view)
            }
        }


        relativeLayoutLike.setOnClickListener { view ->
            if (shopWindowsBean.is_like) {
                presenter.unfavoriteShowWindow(shopWindowsBean.rid, view)
            } else {
                presenter.favoriteShowWindow(shopWindowsBean.rid, view)
            }
        }

        relativeLayoutComment.setOnClickListener { view ->
            ToastUtil.showInfo("去评论界面")
        }

        textViewShare.setOnClickListener { view ->
            val dialog = DistributeShareDialog(this)
            dialog.show()
        }


        //猜你喜欢点击
        adapterGuessLike.setOnItemClickListener { adapter, view, position ->
            val productsBean = adapterGuessLike.getItem(position) as EditorRecommendBean.DataBean.ProductsBean
            ToastUtil.showInfo("商品详情="+position)
        }


        //设置橱窗点击
        adapterRelateShowWindow.setOnItemClickListener { adapter, view, position ->
            val windowsBean = adapterRelateShowWindow.getItem(position) as DiscoverLifeBean.DataBean.ShopWindowsBean
            val intent = Intent(applicationContext, ShowWindowDetailActivity::class.java)
//            intent.putExtra(ShowWindowDetailActivity::class.java.simpleName,windowsBean.rid)
            startActivity(intent)
        }
    }

    /**
     * 设置用户关注状态
     */
    override fun setUserFocusState(b: Boolean) {
        if (b){
            buttonFocus.text = Util.getString(R.string.text_focused)
        }else{
            buttonFocus.text = Util.getString(R.string.text_focus)
        }
    }

    /**
     * 设置喜欢
     */
    override fun setFavorite(b: Boolean) {
        if (b) {
            imageViewLike.setImageResource(R.mipmap.icon_click_favorite_selected)
            textViewLike.text = ""+(shopWindowsBean.like_count+1)
        } else {
            imageViewLike.setImageResource(R.mipmap.icon_click_favorite_normal)
            textViewLike.text = ""+(shopWindowsBean.like_count-1)
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
