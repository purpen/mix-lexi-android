package com.lexivip.lexi.selectionGoodsCenter

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.basemodule.ui.BaseFragment
import com.basemodule.ui.CustomFragmentPagerAdapter
import com.lexivip.lexi.*
import com.lexivip.lexi.index.bean.BannerImageBean
import com.lexivip.lexi.index.selection.HeadLineBean
import com.lexivip.lexi.user.login.UserProfileUtil
import kotlinx.android.synthetic.main.fragment_goods_recommend.*
import kotlinx.android.synthetic.main.view_notice_item_view.view.*
import kotlinx.android.synthetic.main.view_selection_goods_center_recommend.*
import android.view.LayoutInflater
import android.widget.ImageView
import com.basemodule.tools.*
import com.zhouwei.mzbanner.MZBannerView
import com.zhouwei.mzbanner.holder.MZViewHolder


class FragmentGoodsRecommend : BaseFragment(), GoodsRecommendContract.View, View.OnClickListener {
    private val dialog: WaitingDialog by lazy { WaitingDialog(activity) }
    override val layout: Int = R.layout.fragment_goods_recommend
    private val presenter: GoodsRecommendPresenter by lazy { GoodsRecommendPresenter(this) }
    private var views: ArrayList<View> = ArrayList()
    private val fragment0: BaseFragment by lazy { FragmentHotGoods.newInstance() }
    private val fragment1: BaseFragment by lazy { FragmentOfficialRecommend.newInstance() }
    private val fragment2: BaseFragment by lazy { FragmentFirstPublish.newInstance() }
    private val fragments: ArrayList<BaseFragment> by lazy { ArrayList<BaseFragment>() }
    private var page: Int = 1

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

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            if (upMarqueeView != null) {
                upMarqueeView.startFlipping()
            }
        } else {
            if (upMarqueeView != null) {
                upMarqueeView.stopFlipping()
            }
        }
    }

    /**
     * 初始化头条
     */
    private fun initNotice() {
        if (UserProfileUtil.isSmallB() || UserProfileUtil.isBigB()) {
            relativeLayoutUserGuide.visibility = View.GONE
        } else {
            presenter.getHeadLine()
            relativeLayoutUserGuide.visibility = View.VISIBLE
        }
    }

    /**
     * 设置头条数据
     */
    override fun setHeadLineData(data: MutableList<HeadLineBean.DataBean.HeadlinesBean>) {
        var size = data.size

        var i = 0
        while (i < size) {
            //设置滚动的单个布局
            val noticeView = View.inflate(activity, R.layout.view_notice_item_view, null)
            setNoticeTextViewData(data[i], noticeView.tv1)
            if (size > i + 1) {
                setNoticeTextViewData(data[i + 1], noticeView.tv2)
            } else {
                noticeView.tv2.visibility = View.GONE
            }

            views.add(noticeView)
            i += 2
        }
        upMarqueeView.setViews(views)

    }

    /**
     * 设置通知
     */
    private fun setNoticeTextViewData(bean: HeadLineBean.DataBean.HeadlinesBean, textView: TextView) {
        if (bean.username == null) bean.username = ""
        when (bean.event) {
            1 -> { //开通生活馆 人名蓝色
                val content = "${bean.username} ${bean.time}${bean.time_info}开通了自己的生活馆"
                val string = SpannableString(content)
                string.setSpan(ForegroundColorSpan(Util.getColor(R.color.color_6ed7af)), 0, bean.username.toString().length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                textView.text = string
            }

            2 -> {//售出3单成为正式馆主
                textView.text = "${bean.username} 售出${bean.quantity}单成为正式馆主"
            }
            3 -> {//刚刚售出1单
                val content = "「${bean.username}」的生活馆 ${bean.time}${bean.time_info} 售出${bean.quantity}单"
                val string = SpannableString(content)
                string.setSpan(ForegroundColorSpan(Util.getColor(R.color.color_f5a43c)), content.indexOf("售"), content.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                textView.text = string
            }
            4 -> {//售出单数
                val content = "「${bean.username}」的生活馆 ${bean.time}${bean.time_info} 售出${bean.quantity}单"
                val string = SpannableString(content)
                string.setSpan(ForegroundColorSpan(Util.getColor(R.color.color_f5a43c)), content.indexOf("售"), content.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                textView.text = string
            }
        }
    }

    private val mzBannerView:MZBannerView<BannerImageBean> by lazy { MZBannerView<BannerImageBean>(context!!) }

    /**
     * 初始化banner
     */
    private fun initBanner() {
        val height = ScreenUtil.getScreenWidth() * 140 / 375
        linearLayoutViewPager.clipChildren = false
        mzBannerView.clipChildren = false
        mzBannerView.setIndicatorVisible(false)
        mzBannerView.viewPager.pageMargin = -DimenUtil.dp2px(10.0)
        mzBannerView.setCanLoop(true)
        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height)
        mzBannerView.layoutParams = layoutParams
        linearLayoutViewPager.addView(mzBannerView)
        presenter.getBanners()
    }

    /**
     * 设置Banner数据
     */
    override fun setBannerData(banner_images: List<BannerImageBean>) {
        mzBannerView.setBannerPageClickListener { _, i ->

            PageUtil.jump2GoodsDetailActivity(banner_images[i].link)
        }
        // 设置数据
        mzBannerView.setPages(banner_images) { BannerViewHolder() }
        mzBannerView.start()
    }

    class BannerViewHolder : MZViewHolder<BannerImageBean> {
        private var imageView: ImageView? = null
        override fun createView(context: Context): View {
            val view = LayoutInflater.from(context).inflate(R.layout.banner_item, null)
            imageView = view.findViewById(R.id.banner_image) as ImageView
            return view
        }

        override fun onBind(context: Context, p1: Int, item: BannerImageBean) {
            val width = ScreenUtil.getScreenWidth() * 290 / 375
            val height = width * 140 / 290
            GlideUtil.loadImageWithDimenAndRadius<String>(item.image,imageView, DimenUtil.dp2px(4.0), width, height, ImageSizeConfig.DEFAULT)
        }
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

    override fun onResume() {
        super.onResume()
        mzBannerView.start()
    }

    override fun onPause() {
        super.onPause()
        mzBannerView.pause()
    }

}