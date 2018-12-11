package com.lexivip.lexi.index.lifehouse

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.net.Uri
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SimpleItemAnimator
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.LayoutAnimationController
import android.view.animation.TranslateAnimation
import com.basemodule.tools.*
import com.basemodule.ui.BaseFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.lexivip.lexi.*
import com.lexivip.lexi.album.ImageCropActivity
import com.lexivip.lexi.album.ImageUtils
import com.lexivip.lexi.album.PicturePickerUtils
import com.lexivip.lexi.beans.ProductBean
import com.lexivip.lexi.eventBusMessge.MessageUpDown
import com.lexivip.lexi.index.detail.GoodsDetailActivity
import com.lexivip.lexi.index.selection.HeadImageAdapter
import com.lexivip.lexi.net.WebUrl
import com.lexivip.lexi.search.AdapterSearchGoods
import com.lexivip.lexi.selectionGoodsCenter.SelectionGoodsCenterActivity
import com.lexivip.lexi.shareUtil.ShareUtil
import com.lexivip.lexi.user.login.UserProfileUtil
import com.smart.dialog.listener.OnBtnClickL
import com.smart.dialog.widget.ActionSheetDialog
import com.smart.dialog.widget.NormalDialog
import com.yanyusong.y_divideritemdecoration.Y_Divider
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration
import kotlinx.android.synthetic.main.footer_welcome_in_week.view.*
import kotlinx.android.synthetic.main.fragment_life_house.*
import kotlinx.android.synthetic.main.header_welcome_in_week.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONArray
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.io.File

class FragmentLifeHouse : BaseFragment(), LifeHouseContract.View, View.OnClickListener, EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {
    private val dialog: WaitingDialog by lazy { WaitingDialog(activity) }
    private val presenter: LifeHousePresenter by lazy { LifeHousePresenter(this) }
    override val layout: Int = R.layout.fragment_life_house
    private val adapter: LifeHouseAdapter by lazy { LifeHouseAdapter(R.layout.adapter_curator_recommend) }
    private val list: ArrayList<AdapterSearchGoods.MultipleItem> by lazy { ArrayList<AdapterSearchGoods.MultipleItem>() }
    private val adapterWelcomeInWeek: AdapterSearchGoods by lazy { AdapterSearchGoods(list) }
    private var price:String?=null
    private var goodsId:String?=null
    private var storeId:String?=null
    private lateinit var headerLifeHouse: View

    companion object {
        @JvmStatic
        fun newInstance(): FragmentLifeHouse = FragmentLifeHouse()
    }

    override fun initView() {
        EventBus.getDefault().register(this)
        adapter.setEmptyView(R.layout.empty_view_distribute_goods, recyclerView.parent as ViewGroup)
        adapter.setHeaderFooterEmpty(true, true)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        initLifeHouseHeader(false)
        initWelcomeInWeek(false)
    }


    /**
     * 初始化生活馆Header
     */
    private fun initLifeHouseHeader(isRefresh:Boolean) {

        presenter.getLifeHouse()

        presenter.getLookPeople()

        presenter.getNewPublishProducts(isRefresh)

        if (isRefresh) return

        headerLifeHouse = LayoutInflater.from(context).inflate(R.layout.header_welcome_in_week, null)


        if (SPUtil.readBool(Constants.TIPS_LIFE_HOUSE_GRADE_CLOSE)) {
            headerLifeHouse.relativeLayoutOpenTips.visibility = View.GONE
        }

        adapter.setHeaderView(headerLifeHouse)
    }

    override fun setNewPublishProductsData(products: List<ProductBean>) {
        if (products.isEmpty()) return
        val size = DimenUtil.getDimensionPixelSize(R.dimen.dp4)
        GlideUtil.loadImageWithRadius(products[0].cover, headerLifeHouse.imageView0, size)
        if (products.size > 1) GlideUtil.loadImageWithRadius(products[1].cover, headerLifeHouse.imageView1, size)
        if (products.size > 2) GlideUtil.loadImageWithRadius(products[2], headerLifeHouse.imageView2, size)

    }

    /**
     * 设置编辑生活馆数据
     */
    override fun setEditLifeHouseData(bean: LifeHouseBean) {
        headerLifeHouse.textViewTitle.text = bean.data.name
        headerLifeHouse.textViewDesc.text = bean.data.description
    }

    /**
     * 设置生活馆信息
     */
    override fun setLifeHouseData(data: LifeHouseBean.DataBean) {
        adapter.setBrandLogo(data.logo)
        GlideUtil.loadImageWithRadius(data.logo, headerLifeHouse.imageViewCover, DimenUtil.getDimensionPixelSize(R.dimen.dp4))

        headerLifeHouse.textViewTitle.text = data.name
        headerLifeHouse.textViewDesc.text = data.description

        LogUtil.e("${data.phases};;;;"+data.phases_description)

        when (data.phases) {
            1 -> {//实习馆主
                headerLifeHouse.textViewName.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_practice_life_house, 0, 0, 0)
                headerLifeHouse.textViewName.text = "当前为实习馆主"
                headerLifeHouse.textViewContent.text = data.phases_description
            }

            2 -> { //正式馆主
                headerLifeHouse.textViewName.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_success_open_life_house, 0, 0, 0)
                headerLifeHouse.textViewName.text = "恭喜你拥有生活馆"
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
        val string = SpannableString("${data.browse_number} 人浏览过生活馆")
        val end = count.toString().length
        string.setSpan(ForegroundColorSpan(Util.getColor(R.color.color_333)), 0, end + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        headerLifeHouse.textViewLook.text = string


        headerLifeHouse.relativeLayoutHeaders.visibility = View.VISIBLE
        if (count < 999) {
            headerLifeHouse.textViewHeaders.text = "$count"
        } else {
            headerLifeHouse.textViewHeaders.text = "+999"
        }

        val urlList = ArrayList<String>()
        for (item in data.users) {
            urlList.add(item.avatar)
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

        //这部分头像不需要点击
//        val size = data.users.size
//        headImageAdapter.setOnItemClickListener { _, _, position ->
//            val uid = data.users[size - position - 1].uid
//            if (TextUtils.isEmpty(uid) || TextUtils.equals(UserProfileUtil.getUserId(), uid)) return@setOnItemClickListener
//            PageUtil.jump2OtherUserCenterActivity(uid)
//        }
    }

    /**
     * 初始化本周最受欢迎
     */
    private fun initWelcomeInWeek(isRefresh:Boolean) {

        presenter.getWelcomeInWeek()

        if (isRefresh) return

        val footerWelcome = LayoutInflater.from(context).inflate(R.layout.footer_welcome_in_week, null)

        val recyclerViewWelcome = footerWelcome.recyclerViewWelcome

        val gridLayoutManager = CustomGridLayoutManager(AppApplication.getContext(), 2)
        gridLayoutManager.setScrollEnabled(false)
        gridLayoutManager.orientation = GridLayoutManager.VERTICAL
        recyclerViewWelcome.setHasFixedSize(true)
        recyclerViewWelcome.layoutManager = gridLayoutManager
        recyclerViewWelcome.adapter = adapterWelcomeInWeek
        val colorWhite = Util.getColor(android.R.color.white)
        recyclerViewWelcome.setBackgroundColor(colorWhite)
        adapterWelcomeInWeek.setSpanSizeLookup { _, position ->
            adapterWelcomeInWeek.data[position].spanSize
        }
        recyclerViewWelcome.addItemDecoration(DividerItemDecoration(AppApplication.getContext()))
        adapter.addFooterView(footerWelcome)
    }

    /**
     * 根据角标整理数据
     */
    private fun formatData(data: List<ProductBean>): ArrayList<AdapterSearchGoods.MultipleItem> {
        val curList = ArrayList<AdapterSearchGoods.MultipleItem>()
        val size = data.size - 1
        for (i in 0..size) {
            if (i % 10 == 4 || i % 10 == 9) {
                curList.add(AdapterSearchGoods.MultipleItem(data[i], AdapterSearchGoods.MultipleItem.ITEM_TYPE_SPAN2, AdapterSearchGoods.MultipleItem.ITEM_SPAN2_SIZE))
            } else {
                data[i].isRight = (i % 10 == 1 || i % 10 == 3 || i % 10 == 6 || i % 10 == 8)
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
        val item = adapter.getItem(position) as ProductBean
        if (b) {
            item.like_count += 1
        } else {
            item.like_count -= 1
        }
        item.is_like = b
        adapter.notifyItemChanged(position + 1)
    }

    override fun installListener() {

        refreshLayout.setRefreshHeader(CustomRefreshHeader(AppApplication.getContext()))
        refreshLayout.setEnableOverScrollDrag(false)
        refreshLayout.isEnableLoadMore = false
        refreshLayout.setOnRefreshListener {
            initLifeHouseHeader(true)
            initWelcomeInWeek(true)
            refreshLayout.finishRefresh(1000/*,false*/);//传入false表示刷新失败
        }

        textViewShare.setOnClickListener {
            //
            //ToastUtil.showInfo("分享生活馆")
            //val share=ShareUtil(activity,WebUrl.)
            share()
        }

        headerLifeHouse.buttonCpyNum.setOnClickListener {
            Util.setContent2ClipBoard(getString(R.string.text_wechat_num))
            ToastUtil.showInfo("复制成功去添加微信")
        }

        headerLifeHouse.imageViewEdit.setOnClickListener(this)
        headerLifeHouse.textViewTitle.setOnClickListener(this)

        headerLifeHouse.imageViewCover.setOnClickListener(this)

        headerLifeHouse.imageViewTipsClose.setOnClickListener(this)

        headerLifeHouse.textViewSelectGoodsCenter.setOnClickListener(this)

        adapter.setOnItemClickListener { adapter, _, position ->
            val item = adapter.getItem(position) as ProductBean
            val intent = Intent(activity, GoodsDetailActivity::class.java)
            intent.putExtra(GoodsDetailActivity::class.java.simpleName, item)
            startActivity(intent)
        }


        adapter.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { adapter, viewClicked, position ->
            val productsBean = adapter.getItem(position) as ProductBean
            when (viewClicked.id) {
                R.id.imageViewDelete -> {
                    showDeleteDialog(productsBean.rid, position)
                }

                R.id.textView4 -> {
                    if (productsBean.is_like) {
                        presenter.unfavoriteGoods(productsBean.rid, position, viewClicked)
                    } else {
                        presenter.favoriteGoods(productsBean.rid, position, viewClicked)
                    }
                }
                R.id.textView5 -> {
                    /*val dialog = DistributeShareDialog(activity)
                    dialog.show()*/
                    /*val share=ShareUtil(activity,WebUrl.GOODS+this.adapter.data.get(position).product_rid,
                            this.adapter.data.get(position).name,
                            "",WebUrl.AUTH_GOODS+this.adapter.data.get(position).product_rid,
                            this.adapter.data.get(position).cover)*/
                    goodsId=this.adapter.data[position].rid
                    storeId=this.adapter.data[position].store_rid
                    price=this.adapter.data[position].commission_price
                    shareGoods()
                }
            }
        }

        //加载更多生活馆分销商品
        adapter.setOnLoadMoreListener({
            presenter.loadMoreData()
        }, recyclerView)


        adapterWelcomeInWeek.setOnItemClickListener { _, _, position ->
            val item = adapterWelcomeInWeek.getItem(position) ?: return@setOnItemClickListener
            val intent = Intent(activity, GoodsDetailActivity::class.java)
            intent.putExtra(GoodsDetailActivity::class.java.simpleName, item.product)
            startActivity(intent)
        }


        //添加监听
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (recyclerView.scrollState == RecyclerView.SCROLL_STATE_SETTLING || recyclerView.scrollState == RecyclerView.SCROLL_STATE_IDLE) return
                if (Math.abs(dy) < 20) return
                if (dy > 0) {
                    EventBus.getDefault().post(MessageUpDown(true))
                } else {
                    EventBus.getDefault().post(MessageUpDown(false))
                }
            }
        })


//        swipeRefreshLayout.setOnRefreshListener {
//            swipeRefreshLayout.isRefreshing = true
//            adapter.setEnableLoadMore(false)
//            loadData()
//        }

//        adapter.setOnLoadMoreListener({
//            presenter.loadMoreData("", page)
//        }, recyclerView)
    }

    private fun showDeleteDialog(rid: String, position: Int) {
        val color333 = Util.getColor(R.color.color_333)
        val white = Util.getColor(android.R.color.white)
        val dialog = NormalDialog(activity)
        dialog.isTitleShow(false)
                .bgColor(white)
                .cornerRadius(4f)
                .content(Util.getString(R.string.text_unshelve_confirm))
                .contentGravity(Gravity.CENTER)
                .contentTextColor(color333)
                .contentTextSize(14f)
                .dividerColor(Util.getColor(R.color.color_ccc))
                .btnText(Util.getString(R.string.text_cancel),Util.getString(R.string.text_qd))
                .btnTextSize(18f, 18f)
                .setRightBtnBgColor(Util.getColor(R.color.color_6ed7af))
                .btnTextColor(color333, white)
                .btnPressColor(white)
                .widthScale(0.85f)
                .show()
        dialog.setOnBtnClickL(OnBtnClickL {
            dialog.dismiss()
        }, OnBtnClickL {
            presenter.deleteDistributeGoods(rid, position)
            //删除分销商品
            adapter.remove(position)
            dialog.dismiss()
        })
    }

    override fun loadData() {
        presenter.loadData(false)
    }


    override fun setNewData(data: List<ProductBean>) {
        adapter.setNewData(data)
        adapter.setEnableLoadMore(true)
    }


    override fun addData(products: List<ProductBean>) {
        adapter.addData(products)
    }

    override fun loadMoreComplete() {
        adapter.loadMoreComplete()
    }

    override fun loadMoreEnd() {
        adapter.loadMoreEnd()
    }

    override fun showLoadingView() {
        dialog.show()
    }

    override fun dismissLoadingView() {
        dialog.dismiss()
    }

    override fun showInfo(s: String) {
        ToastUtil.showInfo(s)
    }

    override fun showError(string: String) {
        ToastUtil.showInfo(string)
        adapter.loadMoreFail()
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
            val shareUtil:ShareUtil= ShareUtil(activity)
            shareUtil.shareLife(WebUrl.AUTH_LIFE, UserProfileUtil.storeId(),UserProfileUtil.storeId(),2)
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_photo), Constants.REQUEST_CODE_SHARE, *perms)
        }
    }

    /**
     * 分享商品
     */
    @AfterPermissionGranted(Constants.REQUEST_CODE_SHARE_GOODS)
    private fun shareGoods(){
        val perms = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (EasyPermissions.hasPermissions(AppApplication.getContext(), *perms)) {
            val shareUtil:ShareUtil= ShareUtil(activity)
            shareUtil.shareGoods(WebUrl.AUTH_GOODS, goodsId, goodsId+"-"+ storeId,price,4)
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

    override fun onDestroy() {
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
                count - 1 -> {
                    divider = Y_DividerBuilder()
                            .setBottomSideLine(true, color, height, 0f, 0f)
                            .create()
                }
                else -> {
                    val item = adapterWelcomeInWeek.getItem(itemPosition) as AdapterSearchGoods.MultipleItem
                    if (item.product.isRight) {
                        divider = Y_DividerBuilder()
                                .setBottomSideLine(true, color, height, 0f, 0f)
                                .setLeftSideLine(true, color, 5f, 0f, 0f)
                                .create()
                    } else {
                        divider = Y_DividerBuilder()
                                .setBottomSideLine(true, color, height, 0f, 0f)
                                .create()
                    }
                }
            }
            return divider
        }
    }
}