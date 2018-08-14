package com.thn.lexi.goods.lifehouse

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Rect
import android.net.Uri
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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
import com.flyco.dialog.listener.OnBtnClickL
import com.flyco.dialog.widget.ActionSheetDialog
import com.flyco.dialog.widget.NormalDialog
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import com.thn.lexi.album.ImageCropActivity
import com.thn.lexi.album.ImageUtils
import com.thn.lexi.album.PicturePickerUtils
import com.thn.lexi.goods.explore.EditorRecommendBean
import com.thn.lexi.goods.selection.GridSpaceDecoration
import com.thn.lexi.goods.selection.HeadImageAdapter
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
    private var page: Int = 1
    private lateinit var adapter: LifeHouseAdapter
    private lateinit var adapterWelcomeInWeek: WelcomeInWeekAdapter

    private lateinit var headerLifeHouse: View

    companion object {
        @JvmStatic
        fun newInstance(): FragmentLifeHouse = FragmentLifeHouse()
    }

    override fun initView() {

        EventBus.getDefault().register(this)

        adapter = LifeHouseAdapter(R.layout.adapter_curator_recommend)
        adapter.setEmptyView(R.layout.empty_view_distribute_goods,recyclerView.parent as ViewGroup)
        adapter.setHeaderFooterEmpty(true,true)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        swipeRefreshLayout.setColorSchemeColors(Util.getColor(R.color.color_6ed7af))
        swipeRefreshLayout.isRefreshing = false
        initLifeHouseHeader()
        initWelcomeInWeek()
    }

    /**
     * 初始化生活馆Header
     */
    private fun initLifeHouseHeader() {

        presenter.getLifeHouse()

        presenter.getLookPeople()

        headerLifeHouse = LayoutInflater.from(context).inflate(R.layout.header_welcome_in_week, null)


        if (SPUtil.readBool(Constants.TIPS_LIFE_HOUSE_GRADE_CLOSE)) {
            headerLifeHouse.relativeLayoutOpenTips.visibility = View.GONE
        }

        val str1 = "http://imgtu.5011.net/uploads/content/20170209/4934501486627131.jpg"
        val str2 = "http://tx.haiqq.com/uploads/allimg/170504/0641415410-1.jpg"
        val str3 = "http://up.qqjia.com/z/18/tu20457_2.jpg"

        val size = DimenUtil.getDimensionPixelSize(R.dimen.dp4)
        GlideUtil.loadImageWithRadius(str1, headerLifeHouse.imageView0, size)
        GlideUtil.loadImageWithRadius(str2, headerLifeHouse.imageView1, size)
        GlideUtil.loadImageWithRadius(str3, headerLifeHouse.imageView2, size)

        adapter.addHeaderView(headerLifeHouse)
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

        GlideUtil.loadImageWithRadius(data.logo, headerLifeHouse.imageViewCover, DimenUtil.getDimensionPixelSize(R.dimen.dp4))

        headerLifeHouse.textViewTitle.text = data.name
        headerLifeHouse.textViewDesc.text = data.description
        when (data.phases) {
            1 -> {//实习馆主
                headerLifeHouse.textViewName.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_practice_life_house, 0, 0, 0)
                headerLifeHouse.textViewName.text = "当前为实习馆主"
                headerLifeHouse.textViewContent.text = data.phases_description
            }

            2 -> { //正式馆主
                headerLifeHouse.textViewType.visibility = View.GONE
                headerLifeHouse.textViewName.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_success_open_life_house, 0, 0, 0)
                headerLifeHouse.textViewName.text = "恭喜你拥有生活馆"
                headerLifeHouse.textViewContent.text = data.phases_description
            }
        }
    }

    /**
     * 设置看过的用户信息
     */
    override fun setLookPeopleData(users: List<LookPeopleBean.DataBean.UsersBean>) {
        val count = users.size

        if (count==0) return

        headerLifeHouse.textViewLook.visibility = View.VISIBLE
        val string = SpannableString("$count 人浏览过生活馆")
        string.setSpan(ForegroundColorSpan(Util.getColor(R.color.color_333)), 0, count + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        headerLifeHouse.textViewLook.text = string


        headerLifeHouse.relativeLayoutHeaders.visibility = View.VISIBLE
        if (count < 999) {
            headerLifeHouse.textViewHeaders.text = "$count"
        } else {
            headerLifeHouse.textViewHeaders.text = "+999"
        }

        val urlList = ArrayList<String>()
        for (item in users) {
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
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
                    super.getItemOffsets(outRect, view, parent, state)
                    if (parent.getChildAdapterPosition(view) >= 0 && parent.getChildAdapterPosition(view) != urlList.size - 1) {
                        outRect.left = -parent.context.resources.getDimensionPixelSize(R.dimen.dp5)
                    }
                }
            })
        }

        headImageAdapter.setNewData(urlList)
    }

    /**
     * 初始化本周最受欢迎
     */
    private fun initWelcomeInWeek() {

        presenter.getWelcomeInWeek()

        val footerWelcome = LayoutInflater.from(context).inflate(R.layout.footer_welcome_in_week, null)

        val recyclerViewWelcome = footerWelcome.recyclerViewWelcome

        adapterWelcomeInWeek = WelcomeInWeekAdapter(R.layout.adapter_editor_recommend)
        val gridLayoutManager = GridLayoutManager(activity, 2)
        gridLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerViewWelcome.setHasFixedSize(true)
        recyclerViewWelcome.layoutManager = gridLayoutManager
        recyclerViewWelcome.adapter = adapterWelcomeInWeek
        recyclerViewWelcome.addItemDecoration(GridSpaceDecoration(resources.getDimensionPixelSize(R.dimen.dp10), resources.getDimensionPixelSize(R.dimen.dp20)))
        adapter.addFooterView(footerWelcome)
    }

    /**
     * 设置本周最受欢迎数据
     */
    override fun setWelcomeInWeekData(products: List<EditorRecommendBean.DataBean.ProductsBean>) {
        adapterWelcomeInWeek.setNewData(products)
    }

    override fun setPresenter(presenter: LifeHouseContract.Presenter?) {
        setPresenter(presenter)
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.imageViewEdit -> {
                val title = headerLifeHouse.textViewTitle.text
                val description = headerLifeHouse.textViewDesc.text
                val dialog = EditLifeHouseDialog(activity, presenter, title, description)
                dialog.show()
            }

            R.id.imageViewCover -> {
                val stringItems = Util.getStringArray(R.array.strings_photo_titles)
                val dialog = ActionSheetDialog(activity, stringItems, null)
                dialog.itemTextColor(Util.getColor(R.color.color_333))
                val animation = TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                        0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f)
                animation.interpolator = DecelerateInterpolator()
                dialog.layoutAnimation(LayoutAnimationController(animation, 0.12f))
                dialog.isTitleShow(false).show()

                dialog.setOnOperItemClickL { parent, view, position, id ->
                    when (position) {
                        0 -> cameraTask()

                        1 -> albumTask()
                    }
                    dialog.dismiss()
                }
            }

            R.id.imageViewTipsClose -> {
                headerLifeHouse.relativeLayoutOpenTips.visibility = View.GONE
                SPUtil.write(Constants.TIPS_LIFE_HOUSE_GRADE_CLOSE, true)
            }
        }


    }

    /**
     * 删除分销商品
     */
    override fun deleteDistributeGoods(position: Int) {
        adapter.remove(position)
    }

    /**
     * 设置是否喜欢
     */
    override fun setFavorite(b: Boolean, position: Int) {
        val item = adapter.getItem(position) as DistributionGoodsBean.DataBean.ProductsBean
        item.is_like = b
        adapter.notifyDataSetChanged()
    }

    override fun installListener() {
        headerLifeHouse.imageViewEdit.setOnClickListener(this)

        headerLifeHouse.imageViewCover.setOnClickListener(this)

        headerLifeHouse.imageViewTipsClose.setOnClickListener(this)


        adapter.setOnItemClickListener { adapter, view, position ->
            ToastUtil.showInfo("position==$position")
        }


        adapter.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { adapter, viewClicked, position ->
            val productsBean = adapter.getItem(position) as DistributionGoodsBean.DataBean.ProductsBean
            when (viewClicked.id) {
                R.id.imageViewDelete -> {
                    showDeleteDialog(productsBean.rid, position)
                }

                R.id.textView4 -> {
                    if (productsBean.is_like) {
                        presenter.unfavoriteGoods(productsBean.rid, position,viewClicked)
                    } else {
                        presenter.favoriteGoods(productsBean.rid, position,viewClicked)
                    }
                }
                R.id.textView5 -> {
                    val dialog =  DistributeShareDialog(activity)
                    dialog.show()
                }
            }
        }

//        adapterBrandPavilion.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
//            val item = adapter.getItem(position) as GoodsData.DataBean.ProductsBean
//            when(view.id){
//                R.id.imageViewShop->ToastUtil.showInfo("去店铺")
//
//                R.id.imageViewGoods0,R.id.imageViewGoods1,R.id.imageViewGoods2->{
//                    //TODO 去商品详情
//                    startActivity(Intent(activity,GoodsDetailActivity::class.java))
//                }
//
//                R.id.buttonFocus ->{
//                    if (item.isFavorite) {
//                        presenter.unFocusBrandPavilion("店铺id")
//                    } else {
//                        presenter.focusBrandPavilion("店铺id")
//                    }
//                }
//
//                R.id.textView4 -> {
//                    val popupWindow = GoodsSpecPopupWindow(activity, item, R.layout.dialog_purchase_goods, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
//                    popupWindow.show()
//                }
//                R.id.textView5 -> {
//                    val dialog = DialogPlus.newDialog(context)
//                            .setContentHolder(ViewHolder(CenterShareView(context)))
//                            .setGravity(Gravity.CENTER)
//                            .create()
//                    dialog.show()
//                }
//            }
//
//        }

//
//        adapter.setOnItemClickListener { adapter, view, position ->
//            val item = adapter.getItem(position) as GoodsData.DataBean.ProductsBean
//            val intent = Intent(activity, GoodsDetailActivity::class.java)
//            intent.putExtra(GoodsDetailActivity::class.java.simpleName, item.rid)
//            startActivity(intent)
//        }

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
//            adapter.setEnableLoadMore(false)
//            loadData()
        }

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
                .contentTextSize(16f)
                .dividerColor(Util.getColor(R.color.color_ccc))
                .btnText(Util.getString(R.string.text_qd), Util.getString(R.string.text_cancel))
                .btnTextSize(15f, 15f)
                .btnTextColor(color333, Util.getColor(R.color.color_6ed7af))
                .btnPressColor(white)
                .widthScale(0.85f)
                .show()
        dialog.setOnBtnClickL(OnBtnClickL {
            dialog.dismiss()
            presenter.deleteDistributeGoods(rid, position)
        }, OnBtnClickL {
            dialog.dismiss()
        })
    }

    override fun loadData() {
        //店铺编号
        val sid = ""
        page = 1
        presenter.loadData(sid, page)
    }


    override fun setNewData(data: List<DistributionGoodsBean.DataBean.ProductsBean>) {
        swipeRefreshLayout.isRefreshing = false
        adapter.setNewData(data)
        adapter.setEnableLoadMore(true)
        ++page
    }

    override fun addData(products: List<DistributionGoodsBean.DataBean.ProductsBean>) {
        adapter.addData(products)
        ++page
    }

    override fun loadMoreComplete() {
        adapter.loadMoreComplete()
    }

    override fun loadMoreEnd() {
        adapter.loadMoreEnd()
    }

    override fun showLoadingView() {
        if (!swipeRefreshLayout.isRefreshing) dialog.show()
    }

    override fun dismissLoadingView() {
        dialog.dismiss()
    }

    override fun showInfo(s: String) {
        ToastUtil.showInfo(s)
    }

    override fun showError(string: String) {
        ToastUtil.showInfo(string)
        swipeRefreshLayout.isRefreshing = false
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
        if (EasyPermissions.hasPermissions(AppApplication.getContext(), Manifest.permission.CAMERA)) {
            openCamera()
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_camera),
                    Constants.REQUEST_CODE_CAPTURE_CAMERA, Manifest.permission.CAMERA)
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

        LogUtil.e("requestCode=$requestCode;resultCode=$resultCode")

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
        val byteArray = ImageUtils.bitmap2ByteArray(message.bitmap)
        presenter.getUploadToken(byteArray)
        setLifeHouseLogo(byteArray)
    }



    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

}