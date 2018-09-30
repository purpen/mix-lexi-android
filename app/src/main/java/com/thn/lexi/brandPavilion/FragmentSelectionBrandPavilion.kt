package com.thn.lexi.brandPavilion
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.Util
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseFragment
import com.thn.lexi.AppApplication
import com.thn.lexi.GlideImageLoader
import com.thn.lexi.R
import com.thn.lexi.RecyclerViewDivider
import com.thn.lexi.discoverLifeAesthetics.ShowWindowBean
import com.thn.lexi.discoverLifeAesthetics.ShowWindowDetailActivity
import com.thn.lexi.index.explore.ExploreBannerBean
import com.youth.banner.BannerConfig
import com.youth.banner.listener.OnBannerListener
import kotlinx.android.synthetic.main.fragment_swipe_refresh_recyclerview.*
import kotlinx.android.synthetic.main.header_selection_brand_pavilion.view.*


class FragmentSelectionBrandPavilion : BaseFragment(), SelectionBrandPavilionContract.View {
    private val dialog: WaitingDialog by lazy { WaitingDialog(activity) }
    override val layout: Int = R.layout.fragment_swipe_refresh_recyclerview
    private val presenter: SelectionBrandPavilionPresenter by lazy { SelectionBrandPavilionPresenter(this) }
    private val adapter: AdapterFeatureBrandPavilion by lazy { AdapterFeatureBrandPavilion(R.layout.adapter_show_window) }
    private val adapterSelectionBrandPavilion: AdapterSelectionBrandPavilion by lazy { AdapterSelectionBrandPavilion(R.layout.adapter_header_selection_brand_pavilion) }
    private lateinit var headerView:View
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
        headerView.banner.setImageLoader(GlideImageLoader(R.dimen.dp0))
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
    override fun setBannerData(banner_images: List<ExploreBannerBean.DataBean.BannerImagesBean>) {
        val list = ArrayList<String>()
        for (item in banner_images){
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

        headerView.banner.setOnBannerListener { position->
            ToastUtil.showInfo("跳转品牌馆详情$position")
//                startActivity(Intent())
        }
        adapterSelectionBrandPavilion.setOnItemClickListener { adapter, view, position ->
            ToastUtil.showInfo("跳转品牌馆详情$position")
//            val item = adapter.getItem(position)
//            val intent = Intent(context, ShowWindowDetailActivity::class.java)
//            intent.putExtra(ShowWindowDetailActivity::class.java.simpleName,showWindowBean)
//            startActivity(intent)
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