package com.lexivip.lexi.brandPavilion
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.basemodule.tools.*
import com.basemodule.ui.BaseFragment
import com.lexivip.lexi.*
import com.lexivip.lexi.index.bean.BannerImageBean
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.fragment_swipe_refresh_recyclerview.*
import kotlinx.android.synthetic.main.header_selection_brand_pavilion.view.*


class FragmentSelectionBrandPavilion : BaseFragment(), SelectionBrandPavilionContract.View {
    private val dialog: WaitingDialog by lazy { WaitingDialog(activity) }
    override val layout: Int = R.layout.fragment_swipe_refresh_recyclerview
    private val presenter: SelectionBrandPavilionPresenter by lazy { SelectionBrandPavilionPresenter(this) }
    private val adapter: AdapterFeatureBrandPavilion by lazy { AdapterFeatureBrandPavilion(R.layout.adapter_show_window) }
    private val adapterSelectionBrandPavilion: AdapterSelectionBrandPavilion by lazy { AdapterSelectionBrandPavilion(R.layout.adapter_header_selection_brand_pavilion) }
    private lateinit var headerView: View

    private var pavilionBannerList: List<BannerImageBean>? = null

    companion object {
        @JvmStatic
        fun newInstance(): FragmentSelectionBrandPavilion = FragmentSelectionBrandPavilion()
    }

    override fun setPresenter(presenter: SelectionBrandPavilionContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun initView() {
        swipeRefreshLayout.setColorSchemeColors(Util.getColor(R.color.color_6ed7af))
        swipeRefreshLayout.isEnabled = false
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        initHeaderView()
    }

    private fun initHeaderView() {
        presenter.getBanners()
        headerView = View.inflate(activity, R.layout.header_selection_brand_pavilion, null)

        adapter.setHeaderView(headerView)
        headerView.banner.setImageLoader(GlideImageLoader(R.dimen.dp0, ScreenUtil.getScreenWidth(), DimenUtil.dp2px(360.0)))
        headerView.banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
        headerView.banner.setIndicatorGravity(BannerConfig.CENTER)


        presenter.getSelectionPavilion()
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        headerView.recyclerViewHeader.layoutManager = linearLayoutManager
        headerView.recyclerViewHeader.adapter = adapterSelectionBrandPavilion
        val divider = RecyclerViewDivider(AppApplication.getContext(), LinearLayoutManager.HORIZONTAL, DimenUtil.dp2px(10.0), Util.getColor(android.R.color.transparent))
        headerView.recyclerViewHeader.addItemDecoration(divider)
    }

    /**
     * 设置Banner图
     */
    override fun setBannerData(banner_images: List<BannerImageBean>) {
        pavilionBannerList = banner_images
        val list = ArrayList<String>()
        for (item in banner_images) {
            list.add(item.image)
        }
        headerView.banner.setImages(list)
        headerView.banner.start()
    }

    /**
     * 设置精选品牌馆数据
     */
    override fun setSelectionPavilionData(handpick_store: List<SelectionBrandPavilionListBean.DataBean.HandpickStoreBean>) {
        adapterSelectionBrandPavilion.setNewData(handpick_store)
    }

    override fun installListener() {

        headerView.banner.setOnBannerListener { position ->
            val item = pavilionBannerList?.get(position) ?: return@setOnBannerListener
            PageUtil.jump2BrandPavilionActivity(item.link)
        }

        adapterSelectionBrandPavilion.setOnItemClickListener { _, _, position ->
            val item = adapterSelectionBrandPavilion.getItem(position)
                    ?: return@setOnItemClickListener
            PageUtil.jump2BrandPavilionActivity(item.rid)
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

    override fun goPage() {

    }


    override fun onStart() {
        super.onStart()
        //开始轮播
        headerView.banner.startAutoPlay()
    }

    override fun onStop() {
        super.onStop()
        //结束轮播
        headerView.banner.stopAutoPlay()
    }
}