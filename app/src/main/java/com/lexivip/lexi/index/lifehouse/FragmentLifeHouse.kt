package com.lexivip.lexi.index.lifehouse

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.graphics.Typeface
import android.net.Uri
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SimpleItemAnimator
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.LayoutAnimationController
import android.view.animation.TranslateAnimation
import android.widget.RelativeLayout
import com.basemodule.tools.*
import com.basemodule.ui.BaseFragment
import com.lexivip.lexi.*
import com.lexivip.lexi.album.ImageCropActivity
import com.lexivip.lexi.album.ImageUtils
import com.lexivip.lexi.album.PicturePickerUtils
import com.lexivip.lexi.beans.ProductBean
import com.lexivip.lexi.eventBusMessge.MessageUpDown
import com.lexivip.lexi.index.detail.GoodsDetailActivity
import com.lexivip.lexi.index.lifehouse.newProductExpress.NewProductExpressActivity
import com.lexivip.lexi.index.selection.HeadImageAdapter
import com.lexivip.lexi.index.selection.HeadLineBean
import com.lexivip.lexi.net.WebUrl
import com.lexivip.lexi.search.AdapterSearchGoods
import com.lexivip.lexi.selectionGoodsCenter.SelectionGoodsCenterActivity
import com.lexivip.lexi.shareUtil.ShareUtil
import com.lexivip.lexi.user.login.LoginActivity
import com.lexivip.lexi.user.login.UserProfileUtil
import com.lexivip.lexi.view.AutoScrollAdapter
import com.smart.dialog.widget.ActionSheetDialog
import com.yanyusong.y_divideritemdecoration.Y_Divider
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration
import kotlinx.android.synthetic.main.fragment_life_house.*
import kotlinx.android.synthetic.main.header_welcome_in_week.view.*
import kotlinx.android.synthetic.main.view_no_lifehouse.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONArray
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.io.File

class FragmentLifeHouse : BaseFragment(), LifeHouseContract.View, View.OnClickListener, EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {
    private val presenter: LifeHousePresenter by lazy { LifeHousePresenter(this) }
    override val layout: Int = R.layout.fragment_life_house
    private val listNotice: ArrayList<HeadLineBean.DataBean.HeadlinesBean> by lazy { ArrayList<HeadLineBean.DataBean.HeadlinesBean>() }

    private val adapterNewGoodsExpress: NewGoodsExpressAdapter by lazy { NewGoodsExpressAdapter(R.layout.adapter_editor_recommend) }
    private val list: ArrayList<AdapterSearchGoods.MultipleItem> by lazy { ArrayList<AdapterSearchGoods.MultipleItem>() }
    private val listRecommend: ArrayList<SmallBRecommendAdapter.MultipleItem> by lazy { ArrayList<SmallBRecommendAdapter.MultipleItem>() }

    /**
     * 本周最受欢迎加载更多
     */
    private val adapterWelcomeInWeek: AdapterSearchGoods by lazy { AdapterSearchGoods(list) }

    /**
     * 小B推荐
     */
    private val adapterSmallBRecommend: SmallBRecommendAdapter by lazy { SmallBRecommendAdapter(listRecommend) }

    private var price: String? = null
    private var goodsId: String? = null
    private var storeId: String? = null
    private var logo: String = ""
    private lateinit var headerLifeHouse: View

    companion object {
        @JvmStatic
        fun newInstance(): FragmentLifeHouse = FragmentLifeHouse()
    }

    override fun initView() {
        loadingView.setOffsetTop(DimenUtil.dp2px(103.0))
        EventBus.getDefault().register(this)
        val gridLayoutManager = CustomGridLayoutManager(AppApplication.getContext(), 2)
        gridLayoutManager.orientation = GridLayoutManager.VERTICAL
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = adapterWelcomeInWeek
        val colorWhite = Util.getColor(android.R.color.white)
        recyclerView.setBackgroundColor(colorWhite)
        adapterWelcomeInWeek.setSpanSizeLookup { _, position ->
            adapterWelcomeInWeek.data[position].spanSize
        }
        recyclerView.addItemDecoration(DividerItemDecoration(AppApplication.getContext()))
        if (UserProfileUtil.isSmallB()) textViewShare.visibility = View.VISIBLE
        initLifeHouseHeader()
    }


    /**
     * 初始化生活馆Header
     */
    private fun initLifeHouseHeader() {
        headerLifeHouse = LayoutInflater.from(context).inflate(R.layout.header_welcome_in_week, null)
        adapterWelcomeInWeek.setHeaderView(headerLifeHouse)
        //馆长推荐/分销商品
        headerLifeHouse.recyclerViewSmallBRecommend.setHasFixedSize(true)
        val linearLayoutManager0 = LinearLayoutManager(activity)
        linearLayoutManager0.orientation = LinearLayoutManager.HORIZONTAL
        (headerLifeHouse.recyclerViewSmallBRecommend.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        headerLifeHouse.recyclerViewSmallBRecommend.setHasFixedSize(true)
        headerLifeHouse.recyclerViewSmallBRecommend.layoutManager = linearLayoutManager0
        headerLifeHouse.recyclerViewSmallBRecommend.adapter = adapterSmallBRecommend
        headerLifeHouse.recyclerViewSmallBRecommend.addItemDecoration(RecyclerViewDivider(AppApplication.getContext(), LinearLayoutManager.HORIZONTAL, DimenUtil.dp2px(10.0), Util.getColor(android.R.color.transparent)))
        adapterSmallBRecommend.setHeaderAndEmpty(true)
        adapterSmallBRecommend.setEmptyView(R.layout.empty_view_distribute_goods, recyclerView.parent as ViewGroup)

        //新品速递
        headerLifeHouse.recyclerViewNewGoodsExpress.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        (headerLifeHouse.recyclerViewNewGoodsExpress.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        headerLifeHouse.recyclerViewNewGoodsExpress.setHasFixedSize(true)
        headerLifeHouse.recyclerViewNewGoodsExpress.layoutManager = linearLayoutManager
        headerLifeHouse.recyclerViewNewGoodsExpress.adapter = adapterNewGoodsExpress
        headerLifeHouse.recyclerViewNewGoodsExpress.addItemDecoration(RecyclerViewDivider(AppApplication.getContext(), LinearLayoutManager.HORIZONTAL, DimenUtil.dp2px(10.0), Util.getColor(android.R.color.transparent)))

        if (!UserProfileUtil.isLogin() || !UserProfileUtil.isSmallB()) {//没有登录或者不是小B
            presenter.getHeadLine()
            headerLifeHouse.relativeLayoutNoLifeHouse.visibility = View.VISIBLE
            headerLifeHouse.autoScrollRecyclerView.visibility = View.VISIBLE
            val linearLayoutManager = LinearLayoutManager(activity)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            headerLifeHouse.autoScrollRecyclerView.setHasFixedSize(true)
            headerLifeHouse.autoScrollRecyclerView.layoutManager = linearLayoutManager
            GlideUtil.loadImageWithDimen(R.mipmap.icon_bg_no_lifehouse, headerLifeHouse.imageViewBgNoLifeHouse, ScreenUtil.getScreenWidth(), DimenUtil.dp2px(215.0), ImageSizeConfig.DEFAULT)
            GlideUtil.loadImageWithDimenAndRadius(R.mipmap.icon_bg_tou_tiao, headerLifeHouse.imageViewOpenHouseGuide, DimenUtil.dp2px(4.0), ScreenUtil.getScreenWidth() - DimenUtil.dp2px(30.0), DimenUtil.dp2px(100.0), ImageSizeConfig.DEFAULT)
            //添加
//            headerLifeHouse.autoScrollRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//                override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
//                    super.onScrollStateChanged(recyclerView, newState)
//                }
//
//                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                    LogUtil.e("==============$dy")
//                    val position = linearLayoutManager.findLastVisibleItemPosition()
//                    for (i in 0 until recyclerView.layoutManager.childCount) {
//                        val childAt = recyclerView.layoutManager.findViewByPosition(position);
//                        if (i == position) {
//                            childAt.setBackgroundResource(R.drawable.bg_colorccff5f9ce6_round)
//                        } else {
//                            childAt.setBackgroundResource(R.drawable.bg_colorcc000000_round)
//                        }
//                    }
//                }
//            })
        } else {
            headerLifeHouse.recyclerViewSmallBRecommend.visibility = View.VISIBLE
            headerLifeHouse.linearLayoutSmallB.visibility = View.VISIBLE
        }

        if (SPUtil.readBool(Constants.TIPS_LIFE_HOUSE_GRADE_CLOSE)) {
            headerLifeHouse.relativeLayoutOpenTips.visibility = View.GONE
        }
    }

    /**
     * 设置通知数据
     */
    override fun setHeadLineData(headlines: List<HeadLineBean.DataBean.HeadlinesBean>) {
        listNotice.clear()
        listNotice.addAll(headlines)
        if (headlines.isEmpty()) return
        headerLifeHouse.autoScrollRecyclerView.adapter = AutoScrollAdapter(listNotice, headerLifeHouse.autoScrollRecyclerView)
        headerLifeHouse.autoScrollRecyclerView.start()
    }

    /**
     * 设置新品速递
     */
    override fun setNewProductsExpressData(products: List<ProductBean>) {
        adapterNewGoodsExpress.setNewData(products)
    }

    /**
     * 设置新上架数据
     */
    override fun setNewPublishProductsData(products: List<ProductBean>) { //选品中心三张图
        if (products.isEmpty()) return
        val size = DimenUtil.getDimensionPixelSize(R.dimen.dp4)
        GlideUtil.loadImageWithRadius(products[0].cover, headerLifeHouse.imageView0, size)
        if (products.size > 1) GlideUtil.loadImageWithRadius(products[1].cover, headerLifeHouse.imageView1, size)
        if (products.size > 2) GlideUtil.loadImageWithRadius(products[2], headerLifeHouse.imageView2, size)

    }

    /**
     * 设置编辑生活馆数据
     */
    override fun setEditLifeHouseData(title: String, description: String) {
        headerLifeHouse.textViewTitle.text = title
        headerLifeHouse.textViewDesc.text = description
    }

    /**
     * 设置生活馆信息
     */
    override fun setLifeHouseData(data: LifeHouseBean.DataBean) {
        logo = data.logo
        GlideUtil.loadCircleImageWidthDimen(data.logo, headerLifeHouse.circleImageView, DimenUtil.dp2px(28.0), ImageSizeConfig.SIZE_AVA)

        GlideUtil.loadImageWithRadius(data.logo, headerLifeHouse.imageViewCover, DimenUtil.getDimensionPixelSize(R.dimen.dp4))

        headerLifeHouse.textViewTitle.text = data.name
        headerLifeHouse.textViewDesc.text = data.description

//        LogUtil.e("${data.phases};;;;" + data.phases_description)

        val layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.leftMargin = DimenUtil.dp2px(13.0)
        layoutParams.rightMargin = DimenUtil.dp2px(13.0)
        when (data.phases) {
            1 -> {//实习馆主
                headerLifeHouse.imageViewPractice.visibility = View.VISIBLE
                layoutParams.topMargin = DimenUtil.dp2px(25.0)
                headerLifeHouse.textViewContent.layoutParams = layoutParams
                headerLifeHouse.textViewContent.text = "成功在30天内销售3笔订单即可成为正式的达人馆主哦，如一个月内未达标准生活馆将被关闭，如重新申请需单独联系乐喜辅导员申请。"
            }

            2 -> { //正式馆主
                headerLifeHouse.buttonCpyNum.visibility = View.VISIBLE
                headerLifeHouse.textViewName.visibility = View.VISIBLE
                headerLifeHouse.imageViewPractice.visibility = View.GONE
                layoutParams.topMargin = DimenUtil.dp2px(36.0)
                headerLifeHouse.textViewName.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_success_open_life_house, 0, 0, 0)
                headerLifeHouse.textViewName.text = "恭喜你拥有生活馆"
                headerLifeHouse.textViewContent.layoutParams = layoutParams
                headerLifeHouse.textViewContent.text = "如何快速成交订单获取攻略，请搜索关注乐喜官网公众号，添加乐喜辅导员微信，加入生活馆店主群。"
            }
        }
    }

    /**
     * 设置看过的用户信息
     */
    override fun setLookPeopleData(data: LookPeopleBean.DataBean) {
        val count = data.count
        if (count == 0) return
        headerLifeHouse.textViewLook.visibility = View.VISIBLE
        val string = SpannableString("生活馆被浏览过 ${data.browse_number} 次 ")
        val boldSpan = StyleSpan(Typeface.BOLD)
        string.setSpan(boldSpan, 8, string.length - 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        string.setSpan(ForegroundColorSpan(Util.getColor(R.color.color_333)), 8, string.length - 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        headerLifeHouse.textViewLook.text = string


        headerLifeHouse.relativeLayoutHeaders.visibility = View.VISIBLE


        val urlList = ArrayList<String>()
        for (item in data.users) {
            urlList.add(item.avatar)
            if (urlList.size == 13) break
        }

        //反转头像
        urlList.reverse()

        val recyclerView = headerLifeHouse.recyclerViewHeader

        //反转布局
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.setHasFixedSize(true)
        val headImageAdapter = HeadImageAdapter(R.layout.item_head_imageview)
        recyclerView.adapter = headImageAdapter
        if (recyclerView.itemDecorationCount == 0) {
            recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    super.getItemOffsets(outRect, view, parent, state)
                    if (parent.getChildAdapterPosition(view) >= 0 && parent.getChildAdapterPosition(view) != urlList.size - 1) {
                        outRect.left = -DimenUtil.getDimensionPixelSize(R.dimen.dp5)
                    }
                }
            })
        }

        headImageAdapter.setNewData(urlList)
    }

    /**
     * 根据角标整理数据
     */
    private fun formatData(data: List<ProductBean>): ArrayList<AdapterSearchGoods.MultipleItem> {
        val curList = ArrayList<AdapterSearchGoods.MultipleItem>()
        val size = data.size - 1
        for (i in 0..size) {
            if (i == 4 || i == 9) {
                curList.add(AdapterSearchGoods.MultipleItem(data[i], AdapterSearchGoods.MultipleItem.ITEM_TYPE_SPAN2, AdapterSearchGoods.MultipleItem.ITEM_SPAN2_SIZE))
            } else {
                if (i < 4 && i % 2 == 1 || i in 5..8 && i % 2 == 0) {
                    data[i].isRight = true
                }
                curList.add(AdapterSearchGoods.MultipleItem(data[i], AdapterSearchGoods.MultipleItem.ITEM_TYPE_SPAN1, AdapterSearchGoods.MultipleItem.ITEM_SPAN1_SIZE))
            }

        }
        return curList
    }


    /**
     * 设置本周最受欢迎数据
     */
    override fun setWelcomeInWeekData(products: List<ProductBean>) {
        adapterWelcomeInWeek.setNewData(formatData(products))
    }

    override fun setPresenter(presenter: LifeHouseContract.Presenter?) {
        setPresenter(presenter)
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.imageViewEdit, R.id.textViewTitle -> { //编辑生活馆
                val title = headerLifeHouse.textViewTitle.text
                val description = headerLifeHouse.textViewDesc.text
                val dialog = EditLifeHouseDialog(activity, presenter, title, description)
                dialog.show()
            }

            R.id.imageViewCover -> { // 换生活馆logo
                val stringItems = Util.getStringArray(R.array.strings_photo_titles)
                val dialog = ActionSheetDialog(activity, stringItems, null)
                dialog.itemTextColor(Util.getColor(R.color.color_333))
                val animation = TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                        0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f)
                animation.interpolator = DecelerateInterpolator()
                dialog.layoutAnimation(LayoutAnimationController(animation, 0f))
                dialog.isTitleShow(false).show()

                dialog.setOnOperItemClickL { _, _, position, _ ->
                    when (position) {
                        0 -> cameraTask()

                        1 -> albumTask()
                    }
                    dialog.dismiss()
                }
            }

            R.id.imageViewTipsClose -> { //关闭提示
                headerLifeHouse.relativeLayoutOpenTips.visibility = View.GONE
                SPUtil.write(Constants.TIPS_LIFE_HOUSE_GRADE_CLOSE, true)
            }


            R.id.textViewSelectGoodsCenter -> { //选品中心
                startActivity(Intent(activity, SelectionGoodsCenterActivity::class.java))
            }
        }


    }


    /**
     * 设置是否喜欢
     */
    override fun setFavorite(b: Boolean, position: Int) {
//        val item = adapter.getItem(position) as ProductBean
//        if (b) {
//            item.like_count += 1
//        } else {
//            item.like_count -= 1
//        }
//        item.is_like = b
//        adapter.notifyItemChanged(position + 1)
    }

    override fun installListener() {

        headerLifeHouse.textViewAllNewGoodsExpress.setOnClickListener {
            //新品速递列表
            startActivity(Intent(activity, NewProductExpressActivity::class.java))
        }

        headerLifeHouse.textViewAllRecommend.setOnClickListener {
            //馆主推荐列表
            val intent = Intent(activity, SmallBRecommendGoodsListActivity::class.java)
            intent.putExtra(SmallBRecommendGoodsListActivity::class.java.simpleName, logo)
            startActivity(intent)
        }

        refreshLayout.setRefreshHeader(CustomRefreshHeader(AppApplication.getContext()))
        refreshLayout.setEnableOverScrollDrag(false)
        refreshLayout.isEnableLoadMore = false
        refreshLayout.setOnRefreshListener {
            refreshLayout.finishRefresh(1000/*,false*/);//传入false表示刷新失败
            presenter.getWelcomeInWeek(true)
            presenter.getNewProducts(true)
            if (!UserProfileUtil.isLogin() || !UserProfileUtil.isSmallB()) presenter.getHeadLine()
            if (UserProfileUtil.isLogin()) {
                presenter.getNewPublishProducts(true)
                if (UserProfileUtil.isSmallB()) {
                    presenter.loadData(true)
                    presenter.getLifeHouse(true)
                    presenter.getLookPeople(true)
                }
            }
            //新品速递
        }

        //小B推荐
        adapterSmallBRecommend.setOnItemClickListener { _, _, position ->
            val size = adapterSmallBRecommend.data.size
            if (size <= 10 && position == size - 1) return@setOnItemClickListener
            if (size > 10 && position == size - 1) {
                val intent = Intent(activity, SmallBRecommendGoodsListActivity::class.java)
                intent.putExtra(SmallBRecommendGoodsListActivity::class.java.simpleName, logo)
                startActivity(intent)
                return@setOnItemClickListener
            }
            val item = adapterSmallBRecommend.getItem(position) ?: return@setOnItemClickListener
            PageUtil.jump2GoodsDetailActivity(item.product.rid)
        }

        adapterNewGoodsExpress.setOnItemClickListener { _, _, position ->
            val item = adapterNewGoodsExpress.getItem(position) ?: return@setOnItemClickListener
            PageUtil.jump2GoodsDetailActivity(item.rid)
        }

        adapterWelcomeInWeek.setOnLoadMoreListener({
            // 加载更多本周最受欢迎
            presenter.loadMoreData()
        }, recyclerView)

        textViewShare.setOnClickListener {
            share()
        }

        headerLifeHouse.buttonCpyNum.setOnClickListener {
            //复制微信
            Util.setContent2ClipBoard(getString(R.string.text_wechat_num))
            ToastUtil.showInfo("复制成功去添加微信")
        }

        headerLifeHouse.buttonOpenShop.setOnClickListener {
            LogUtil.e("=================")
            //我要开馆
            if (UserProfileUtil.isLogin()) {
                PageUtil.jump2OpenLifeHouseActivity("https://h5.lexivip.com/shop/guide", R.string.title_open_life_house)
            } else {
                startActivity(Intent(activity, LoginActivity::class.java))
            }
        }

        headerLifeHouse.imageViewEdit.setOnClickListener(this)
        headerLifeHouse.textViewTitle.setOnClickListener(this)

        headerLifeHouse.imageViewCover.setOnClickListener(this)

        headerLifeHouse.imageViewTipsClose.setOnClickListener(this)

        headerLifeHouse.textViewSelectGoodsCenter.setOnClickListener(this)

//        adapter.setOnItemClickListener { adapter, _, position ->
//            val item = adapter.getItem(position) as ProductBean
//            val intent = Intent(activity, GoodsDetailActivity::class.java)
//            intent.putExtra(GoodsDetailActivity::class.java.simpleName, item)
//            startActivity(intent)
//        }


//        adapter.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { adapter, viewClicked, position ->
//            val productsBean = adapter.getItem(position) as ProductBean
//            when (viewClicked.id) {
//                R.id.imageViewDelete -> {
//                    showDeleteDialog(productsBean.rid, position)
//                }
//
//                R.id.textView4 -> {
//                    if (productsBean.is_like) {
//                        presenter.unfavoriteGoods(productsBean.rid, position, viewClicked)
//                    } else {
//                        presenter.favoriteGoods(productsBean.rid, position, viewClicked)
//                    }
//                }
//                R.id.textView5 -> {
//                    /*val dialog = DistributeShareDialog(activity)
//                    dialog.show()*/
//                    /*val share=ShareUtil(activity,WebUrl.GOODS+this.adapter.data.get(position).product_rid,
//                            this.adapter.data.get(position).name,
//                            "",WebUrl.AUTH_GOODS+this.adapter.data.get(position).product_rid,
//                            this.adapter.data.get(position).cover)*/
//                    goodsId = this.adapter.data[position].rid
//                    storeId = this.adapter.data[position].store_rid
//                    price = this.adapter.data[position].commission_price
//                    shareGoods()
//                }
//            }
//        }

        //加载更多生活馆分销商品
//        adapter.setOnLoadMoreListener({
//            presenter.loadMoreData()
//        }, recyclerView)


        adapterWelcomeInWeek.setOnItemClickListener { _, _, position ->
            val item = adapterWelcomeInWeek.getItem(position) ?: return@setOnItemClickListener
            val intent = Intent(activity, GoodsDetailActivity::class.java)
            intent.putExtra(GoodsDetailActivity::class.java.simpleName, item.product)
            startActivity(intent)
        }


        //添加监听
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!UserProfileUtil.isLogin()) headerLifeHouse.autoScrollRecyclerView.start()
                }
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!UserProfileUtil.isLogin() && recyclerView.scrollState != RecyclerView.SCROLL_STATE_IDLE) headerLifeHouse.autoScrollRecyclerView.stop()
                if (recyclerView.scrollState == RecyclerView.SCROLL_STATE_SETTLING || recyclerView.scrollState == RecyclerView.SCROLL_STATE_IDLE) return
                if (Math.abs(dy) < 20) return
                if (dy > 0) {
                    EventBus.getDefault().post(MessageUpDown(true))
                } else {
                    EventBus.getDefault().post(MessageUpDown(false))
                }
            }
        })
    }


    override fun loadData() {
        presenter.getWelcomeInWeek(false)
        presenter.getNewProducts(false)
        if (UserProfileUtil.isLogin()) {
            if (UserProfileUtil.isSmallB()) {
                presenter.getNewPublishProducts(false)
                presenter.loadData(false)
                presenter.getLifeHouse(false)
                presenter.getLookPeople(false)
            }
        }

    }


    override fun setNewData(data: List<ProductBean>) {//设置小B推荐数据
        if (data.isNotEmpty()) headerLifeHouse.relativeLayoutSmallBHeader.visibility = View.VISIBLE
        listRecommend.clear()
        for (item in data) {
            listRecommend.add(SmallBRecommendAdapter.MultipleItem(item, SmallBRecommendAdapter.MultipleItem.ITEM_TYPE_GOODS))
        }

        //如果没有数据则不加没有更多
        if (listRecommend.isNotEmpty()) listRecommend.add(SmallBRecommendAdapter.MultipleItem(ProductBean(), SmallBRecommendAdapter.MultipleItem.ITEM_TYPE_END))

        adapterSmallBRecommend.setNewData(listRecommend)
    }


    override fun addData(products: List<ProductBean>) {
        adapterWelcomeInWeek.addData(formatData(products))
    }

    override fun loadMoreComplete() {
        adapterWelcomeInWeek.loadMoreComplete()
    }

    override fun loadMoreEnd() {
        adapterWelcomeInWeek.loadMoreEnd()
    }

    override fun showLoadingView() {
        loadingView.show()
    }

    override fun dismissLoadingView() {
        loadingView.dismiss()
    }

    override fun showInfo(s: String) {
        ToastUtil.showInfo(s)
    }

    override fun showError(string: String) {
        ToastUtil.showInfo(string)
    }

    override fun goPage() {

    }


    @AfterPermissionGranted(Constants.REQUEST_CODE_PICK_IMAGE)
    private fun albumTask() {
        if (EasyPermissions.hasPermissions(AppApplication.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            ImageUtils.getImageFromAlbum(this, 1)
        } else {
            // 申请权限。
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_photo),
                    Constants.REQUEST_CODE_PICK_IMAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    @AfterPermissionGranted(Constants.REQUEST_CODE_CAPTURE_CAMERA)
    private fun cameraTask() {
        val perms = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (EasyPermissions.hasPermissions(AppApplication.getContext(), *perms)) {
            openCamera()
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_camera),
                    Constants.REQUEST_CODE_CAPTURE_CAMERA, *perms)
        }
    }

    /**
     * 邀请好友开馆
     */
    @AfterPermissionGranted(Constants.REQUEST_CODE_SHARE)
    private fun share() {
        val perms = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (EasyPermissions.hasPermissions(AppApplication.getContext(), *perms)) {
            val shareUtil: ShareUtil = ShareUtil(activity)
            shareUtil.shareLife(WebUrl.AUTH_LIFE, UserProfileUtil.storeId(), UserProfileUtil.storeId(), 2)
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_photo), Constants.REQUEST_CODE_SHARE, *perms)
        }
    }

    /**
     * 分享商品
     */
    @AfterPermissionGranted(Constants.REQUEST_CODE_SHARE_GOODS)
    private fun shareGoods() {
        val perms = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (EasyPermissions.hasPermissions(AppApplication.getContext(), *perms)) {
            val shareUtil: ShareUtil = ShareUtil(activity)
            shareUtil.shareGoods(WebUrl.AUTH_GOODS, goodsId, goodsId + "-" + storeId, price, 4)
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_photo), Constants.REQUEST_CODE_SHARE_GOODS, *perms)
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
        ImageUtils.getImageFromCamera(activity, ImageUtils.getUriForFile(context, mCurrentPhotoFile))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

//        LogUtil.e("requestCode=$requestCode;resultCode=$resultCode")

        if (resultCode != Activity.RESULT_OK) return

        when (requestCode) {
            Constants.REQUEST_CODE_CAPTURE_CAMERA -> {
                if (null == mCurrentPhotoFile) return
                toCropActivity(ImageUtils.getUriForFile(context, mCurrentPhotoFile))
            }
            Constants.REQUEST_CODE_PICK_IMAGE -> {
                var mSelected = PicturePickerUtils.obtainResult(data)
                if (mSelected == null || mSelected.isEmpty()) {
                    return
                }
                toCropActivity(mSelected[0])
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun toCropActivity(uri: Uri?) {
        val intent = Intent(context, ImageCropActivity::class.java)
        intent.putExtra(FragmentLifeHouse::class.java.simpleName, uri)
        startActivity(intent)

    }


    override fun setLifeHouseLogoData(ids: JSONArray) {
        val logoId = "${ids[0]}"
        presenter.uploadLifeHouseLogoId(logoId)
    }

    /**
     * 显示头像
     */
    private fun setLifeHouseLogo(byteArray: ByteArray) {
        GlideUtil.loadImageWithRadius(byteArray, headerLifeHouse.imageViewCover, DimenUtil.getDimensionPixelSize(R.dimen.dp4))
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onClipComplete(message: ImageCropActivity.MessageCropComplete) {
        if (FragmentLifeHouse::class.java.simpleName.equals(message.simpleName)) {
            val byteArray = ImageUtils.bitmap2ByteArray(message.bitmap)
            presenter.getUploadToken(byteArray)
            setLifeHouseLogo(byteArray)
        }
    }

    //上架成功刷新生活馆数据
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun putAwaySuccess(productBean: ProductBean) {
        presenter.loadData(true)
    }

    override fun onResume() {
//        if (!UserProfileUtil.isLogin()) headerLifeHouse.linearLayoutNotice.start()
        if (!UserProfileUtil.isLogin()) headerLifeHouse.autoScrollRecyclerView.start()
        super.onResume()
    }

    override fun onPause() {
//        if (!UserProfileUtil.isLogin()) headerLifeHouse.linearLayoutNotice.stop()
        if (!UserProfileUtil.isLogin()) headerLifeHouse.autoScrollRecyclerView.stop()
        super.onPause()
    }

    override fun onDestroy() {
//        if (!UserProfileUtil.isLogin()) headerLifeHouse.linearLayoutNotice.destroy()
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    private inner class DividerItemDecoration constructor(context: Context) : Y_DividerItemDecoration(context) {
        private val color: Int = Util.getColor(android.R.color.white)
        private val height = 20f
        override fun getDivider(itemPosition: Int): Y_Divider? {
            val count = adapterWelcomeInWeek.itemCount
            val divider: Y_Divider
            when (itemPosition) {
                0 -> {
                    divider = Y_DividerBuilder()
                            .setBottomSideLine(true, color, 10f, 0f, 0f)
                            .create()
                    return divider
                }

                count - 1 -> {
                    divider = Y_DividerBuilder()
                            .setBottomSideLine(true, color, height, 0f, 0f)
                            .create()
                }
                else -> {
                    val item = adapterWelcomeInWeek.getItem(itemPosition - 1) as AdapterSearchGoods.MultipleItem
                    if (item.product.isRight) {
                        divider = Y_DividerBuilder()
                                .setBottomSideLine(true, color, height, 0f, 0f)
                                .setLeftSideLine(true, color, 10f, 0f, 0f)
                                .create()
                    } else {
                        divider = Y_DividerBuilder()
                                .setBottomSideLine(true, color, height, 0f, 0f)
                                .setLeftSideLine(true, color, 15f, 0f, 0f)
                                .create()
                    }
                }
            }
            return divider
        }
    }
}