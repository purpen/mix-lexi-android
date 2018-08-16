package com.thn.lexi.selectionGoodsCenter

import android.support.v7.widget.LinearLayoutManager
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseFragment
import com.thn.lexi.R
import com.thn.lexi.index.explore.ExploreBannerBean
import android.view.View
import com.basemodule.ui.CustomFragmentPagerAdapter
import com.thn.lexi.index.selection.CardScaleHelper
import com.thn.lexi.index.selection.HeadLineBean

import kotlinx.android.synthetic.main.fragment_goods_recommend.*
import kotlinx.android.synthetic.main.view_selection_goods_center_recommend.*

class FragmentGoodsRecommend : BaseFragment(), GoodsRecommendContract.View, View.OnClickListener {
    private val dialog: WaitingDialog by lazy { WaitingDialog(activity) }
    override val layout: Int = R.layout.fragment_goods_recommend
    private val presenter: GoodsRecommendPresenter by lazy { GoodsRecommendPresenter(this) }

    private val fragment0: BaseFragment by lazy { FragmentHotGoods.newInstance() }
    private val fragment1: BaseFragment by lazy { FragmentOfficialRecommend.newInstance() }
    private val fragment2: BaseFragment by lazy { FragmentFirstPublish.newInstance() }

    private val fragments:ArrayList<BaseFragment> by lazy { ArrayList<BaseFragment>() }

    private var page: Int = 1

    private lateinit var adapterGoodsRecommendBanner: AdapterGoodsRecommendBanner

    companion object {
        @JvmStatic
        fun newInstance(): FragmentGoodsRecommend = FragmentGoodsRecommend()
    }

    override fun initView() {
        initBanner()
        initNotice()
        setUpViewPager()
    }

    private fun setUpViewPager() {
        val titles = resources.getStringArray(R.array.strings_selection_goods_subtitles)

        fragments.add(fragment0)
        fragments.add(fragment1)
        fragments.add(fragment2)

        val adapter = CustomFragmentPagerAdapter(childFragmentManager, fragments, titles.asList())
        customViewPager.adapter = adapter
        customViewPager.offscreenPageLimit = fragments.size
        customViewPager.setPagingEnabled(false)
        slidingTabLayout.setViewPager(customViewPager)
    }

    /**
     * 初始化头条
     */
    private fun initNotice() {
        presenter.getHeadLine()
    }

    /**
     * 设置头条数据
     */
    override fun setHeadLineData(data: MutableList<HeadLineBean.DataBean.HeadlinesBean>) {
        val contentPavilion = data[0].line_text
        val pavilionTime = data[0].time

        val contentOrders = data[1].line_text
        val orderCount= data[1].time

//        if (!TextUtils.isEmpty(name1)){
//            val openInfo = SpannableString("设计师${contentPavilion}10秒前开了自己的设计馆")
//            val start = 3
//            val end = start + name1.length
//            openInfo.setSpan(ForegroundColorSpan(Util.getColor(R.color.color_6ed7af)),start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//            textViewOpenInfo.text = openInfo
//        }
//
//        if (!TextUtils.isEmpty(name2) && !TextUtils.isEmpty(orderCount)){
//            val orderInfo = SpannableString("${name2}设计馆1小时售出${orderCount}单")
//            val start = name2.length + 8
//            val end = start + orderCount.length
//            orderInfo.setSpan(ForegroundColorSpan(Util.getColor(R.color.color_f4b329)),start , end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//            textViewOrderInfo.text = orderInfo
//        }

    }

    /**
     * 初始化banner
     */
    private fun initBanner() {
        presenter.getBanners()
        val manager = LinearLayoutManager(context)
        manager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerViewBanner.layoutManager = manager
        adapterGoodsRecommendBanner = AdapterGoodsRecommendBanner(R.layout.adapter_selection_banner)
        recyclerViewBanner.adapter = adapterGoodsRecommendBanner
    }

    /**
     * 设置Banner数据
     */
    override fun setBannerData(banner_images: List<ExploreBannerBean.DataBean.BannerImagesBean>) {
        val list = ArrayList<String>()
        for (item in banner_images) {
            list.add(item.image)
        }
        adapterGoodsRecommendBanner.setNewData(list)
        val mCardScaleHelper = CardScaleHelper()
        mCardScaleHelper.currentItemPos = 0
        mCardScaleHelper.attachToRecyclerView(recyclerViewBanner)
    }

    override fun setPresenter(presenter: GoodsRecommendContract.Presenter?) {
        setPresenter(presenter)
    }


    override fun installListener() {

//        textViewGuessPic.setOnClickListener(this)
//        textViewCouponCenter.setOnClickListener(this)
//        textViewExemptionMail.setOnClickListener(this)

//        adapter.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { adapter, view_selection_goods_center_recommend, position ->
//            val item = adapter.getItem(position) as GoodsData.DataBean.ProductsBean
//            when (view_selection_goods_center_recommend.id) {
//                R.id.textView4 -> {
//                    val popupWindow = GoodsSpecPopupWindow(activity, item, R.layout.dialog_purchase_goods, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
//                    popupWindow.show()
//                }
//            }
//        }


//        adapter.setOnItemClickListener { adapter, view_selection_goods_center_recommend, position ->
//            val item = adapter.getItem(position) as GoodsData.DataBean.ProductsBean
//            val intent = Intent(activity, GoodsDetailActivity::class.java)
//            intent.putExtra(GoodsDetailActivity::class.java.simpleName, item.rid)
//            startActivity(intent)
//        }



    }

    override fun onClick(v: View) {
//        when (v.id) {
//            R.id.textViewGuessPic -> ToastUtil.showInfo("猜图")
//            R.id.textViewCouponCenter -> ToastUtil.showInfo("领券中心")
//            R.id.textViewExemptionMail -> ToastUtil.showInfo("包邮专区")
//        }
    }


    override fun loadData() {
        page = 1
        presenter.loadData("", page)
    }





    override fun showLoadingView() {
        dialog.show()
    }

    override fun dismissLoadingView() {
        dialog.dismiss()
    }

    override fun showError(string: String) {

    }

    override fun goPage() {

    }

    override fun onStart() {
        super.onStart()
//        hotBanner.startAutoPlay()
    }

    override fun onStop() {
        super.onStop()
//        hotBanner.stopAutoPlay()
    }
}