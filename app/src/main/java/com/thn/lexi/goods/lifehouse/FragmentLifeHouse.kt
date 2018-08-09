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
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.LayoutAnimationController
import android.view.animation.TranslateAnimation
import com.basemodule.tools.*
import com.basemodule.ui.BaseFragment
import com.flyco.dialog.widget.ActionSheetDialog
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import com.thn.lexi.album.ImageCropActivity
import com.thn.lexi.album.ImageUtils
import com.thn.lexi.album.PicturePickerUtils
import com.thn.lexi.goods.explore.EditorRecommendBean
import com.thn.lexi.goods.selection.GoodSelectionAdapter
import com.thn.lexi.goods.selection.GridSpaceDecoration
import com.thn.lexi.goods.selection.HeadImageAdapter
import kotlinx.android.synthetic.main.footer_welcome_in_week.view.*
import kotlinx.android.synthetic.main.fragment_life_house.*
import kotlinx.android.synthetic.main.header_welcome_in_week.view.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.io.File

class FragmentLifeHouse:BaseFragment(),LifeHouseContract.View,View.OnClickListener,EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {
    private val dialog: WaitingDialog by lazy { WaitingDialog(activity) }
    private val presenter: LifeHousePresenter by lazy { LifeHousePresenter(this) }
    override val layout: Int = R.layout.fragment_life_house
    private var page: Int = 1
    private lateinit var adapter: LifeHouseAdapter
    private lateinit var adapterWelcomeInWeek : GoodSelectionAdapter



    private lateinit var headerLifeHouse: View

    companion object {
        @JvmStatic fun newInstance(): FragmentLifeHouse = FragmentLifeHouse()
    }

    override fun initView() {
        adapter = LifeHouseAdapter(R.layout.adapter_curator_recommend)
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

        headerLifeHouse = LayoutInflater.from(context).inflate(R.layout.header_welcome_in_week,null)




        val str1= "http://imgtu.5011.net/uploads/content/20170209/4934501486627131.jpg"
        val str2= "http://tx.haiqq.com/uploads/allimg/170504/0641415410-1.jpg"
        val str3= "http://up.qqjia.com/z/18/tu20457_2.jpg"

        val size = DimenUtil.getDimensionPixelSize(R.dimen.dp4)
        GlideUtil.loadImageWithRadius(str1,headerLifeHouse.imageView0,size)
        GlideUtil.loadImageWithRadius(str2,headerLifeHouse.imageView1,size)
        GlideUtil.loadImageWithRadius(str3,headerLifeHouse.imageView2,size)

        GlideUtil.loadImageWithRadius(str1,headerLifeHouse.imageViewCover,size)

        adapter.addHeaderView(headerLifeHouse)
    }


    /**
     * 设置生活馆信息
     */
    override fun setLifeHouseData(data: LifeHouseBean.DataBean) {
        headerLifeHouse.textViewTitle.text = data.name
        headerLifeHouse.textViewDesc.text = data.description
    }

    /**
     * 设置看过的用户信息
     */
    override fun setLookPeopleData(users: List<LookPeopleBean.DataBean.UsersBean>) {
        val count = users.size
        val string = SpannableString("$count 人浏览过生活馆")
        string.setSpan(ForegroundColorSpan(Util.getColor(R.color.color_333)),0,count+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        headerLifeHouse.textViewLook.text = string


        if (count<999){
            headerLifeHouse.textViewHeaders.text = "$count"
        }else{
            headerLifeHouse.textViewHeaders.text = "+999"
        }

        val urlList = ArrayList<String>()
        for (item in users){
            urlList.add(item.avatar)
        }

        //反转头像
        urlList.reverse()

        val recyclerView = headerLifeHouse.recyclerViewHeader
        val linearLayoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,true)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.setHasFixedSize(true)
        val headImageAdapter = HeadImageAdapter(R.layout.item_head_imageview)
        recyclerView.adapter = headImageAdapter
        if (recyclerView.itemDecorationCount == 0) {
            recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
                    super.getItemOffsets(outRect, view, parent, state)
                    if (parent.getChildAdapterPosition(view) >= 0 && parent.getChildAdapterPosition(view)!=urlList.size-1) {
                        outRect.left = - parent.context.resources.getDimensionPixelSize(R.dimen.dp5)
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

        val footerWelcome = LayoutInflater.from(context).inflate(R.layout.footer_welcome_in_week,null)

        val recyclerViewWelcome = footerWelcome.recyclerViewWelcome

        adapterWelcomeInWeek = GoodSelectionAdapter(R.layout.adapter_editor_recommend)
        val gridLayoutManager = GridLayoutManager(activity,2)
        gridLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerViewWelcome.setHasFixedSize(true)
        recyclerViewWelcome.layoutManager = gridLayoutManager
        recyclerViewWelcome.adapter = adapterWelcomeInWeek
        recyclerViewWelcome.addItemDecoration(GridSpaceDecoration(resources.getDimensionPixelSize(R.dimen.dp10),resources.getDimensionPixelSize(R.dimen.dp20)))
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
        when(v.id){
            R.id.imageViewEdit ->{
                val title = headerLifeHouse.textViewTitle.text
                val description = headerLifeHouse.textViewDesc.text
                 val dialog = EditLifeHouseDialog(activity,presenter,title,description)
                dialog.show()
                dialog.setCanceledOnTouchOutside(false)
            }

            R.id.imageViewCover ->{
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
        }
    }

    override fun installListener() {
        headerLifeHouse.imageViewEdit.setOnClickListener(this)

        headerLifeHouse.imageViewCover.setOnClickListener(this)

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

}