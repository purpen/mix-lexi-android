package com.lexivip.lexi.coupon

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import com.basemodule.tools.*
import com.basemodule.ui.BaseFragment
import com.lexivip.lexi.*
import com.lexivip.lexi.beans.CouponBean
import kotlinx.android.synthetic.main.fragment_swipe_refresh_recyclerview.*

class FragmentUserCoupon : BaseFragment(), UserCouponContract.View {
    override val layout: Int = R.layout.fragment_swipe_refresh_recyclerview

    private var whichPage: String? = null


    private val presenter: UserCouponPresenter by lazy { UserCouponPresenter(this) }

    private val list: ArrayList<AdapterUserCoupon.MultipleItem> by lazy { ArrayList<AdapterUserCoupon.MultipleItem>() }

    private val adapter: AdapterUserCoupon by lazy { AdapterUserCoupon(list) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        whichPage = arguments?.getString(FragmentUserCoupon::class.java.simpleName)

    }

    companion object {
        @JvmStatic
        fun newInstance(page: String): FragmentUserCoupon {
            val fragment = FragmentUserCoupon()
            val bundle = Bundle()
            bundle.putString(FragmentUserCoupon::class.java.simpleName, page)
            fragment.arguments = bundle
            return fragment
        }

        const val PAGE_BRAND_PAVILION: String = "PAGE_BRAND_PAVILION"
        const val PAGE_LX: String = "PAGE_LX"
        const val PAGE_INVALID: String = "PAGE_INVALID"
    }


    override fun loadData() {
        if (!TextUtils.isEmpty(whichPage)) presenter.loadData(false, whichPage!!)
    }

    override fun initView() {
        loadingView.visibility = View.VISIBLE
        loadingView.setOffsetTop(DimenUtil.dp2px(90.0))
        swipeRefreshLayout.setBackgroundColor(Util.getColor(R.color.color_f5f7f9))
        swipeRefreshLayout.setColorSchemeColors(Util.getColor(R.color.color_6ed7af))
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(AppApplication.getContext(), R.color.color_f5f7f9, recyclerView))
        val view = View(activity)
        adapter.addHeaderView(view)
        adapter.emptyView = View.inflate(activity,R.layout.empty_view_coupon,null)
    }


    override fun installListener() {
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            adapter.setEnableLoadMore(false)
            if (!TextUtils.isEmpty(whichPage)) presenter.loadData(true, whichPage!!)
        }

        adapter.setOnLoadMoreListener({
            if (!TextUtils.isEmpty(whichPage)) presenter.loadMoreData()
        }, recyclerView)
    }

    /**
     * 更新数据
     */
    private fun updateData(coupons: List<CouponBean>, isSetNew: Boolean) {
        if (isSetNew) list.clear()
        for (item in coupons) {
            when (whichPage) {
                PAGE_INVALID -> { //失效券
                    item.is_expired = true
                    when (item.type) {
                        1 -> { //品牌馆券
                            list.add(AdapterUserCoupon.MultipleItem(item, AdapterUserCoupon.MultipleItem.ITEM_TYPE_BRAND))
                        }
                        2 -> {// 官方券
                            list.add(AdapterUserCoupon.MultipleItem(item, AdapterUserCoupon.MultipleItem.ITEM_TYPE_LX))
                        }
                    }
                }
                PAGE_BRAND_PAVILION -> { //品牌券
                    item.amount = item.coupon.amount
                    item.code = item.coupon.code
                    item.start_at = item.get_at
                    item.expired_at = item.end_at
                    item.type = item.coupon.type
                    item.type_text = item.coupon.type_text
                    item.min_amount = item.coupon.min_amount
                    list.add(AdapterUserCoupon.MultipleItem(item, AdapterUserCoupon.MultipleItem.ITEM_TYPE_BRAND))
                    //跳转品牌馆
                    adapter.setOnItemClickListener { _, _, position ->
                        val multipleItem = adapter.getItem(position) ?: return@setOnItemClickListener
                        val rid = multipleItem.coupon.store_rid
                        PageUtil.jump2BrandPavilionActivity(rid)
                    }
                }

                PAGE_LX -> { //官方券
                    list.add(AdapterUserCoupon.MultipleItem(item, AdapterUserCoupon.MultipleItem.ITEM_TYPE_LX))
                    //跳转主页
                    adapter.setOnItemClickListener { _, _, position ->
                        val multipleItem = adapter.getItem(position) ?: return@setOnItemClickListener
                        val intent = Intent(activity,MainActivity::class.java)
                        intent.putExtra(MainActivity::class.java.simpleName,UserCouponActivity::class.java.simpleName)
                        startActivity(intent)
                    }
                }
            }
        }
    }

    override fun setNewData(coupons: List<CouponBean>) {
        updateData(coupons, true)
        adapter.setNewData(list)
        swipeRefreshLayout.isRefreshing = false
    }

    override fun addData(coupons: MutableList<CouponBean>) {
        updateData(coupons, false)
        adapter.addData(list)
        adapter.setEnableLoadMore(true)
    }


    override fun loadMoreComplete() {
        adapter.loadMoreComplete()
    }

    override fun loadMoreEnd() {
        adapter.loadMoreEnd()
    }

    override fun loadMoreFail() {
        adapter.loadMoreFail()
    }

    override fun setPresenter(presenter: UserCouponContract.Presenter?) {
        setPresenter(presenter)
    }


    override fun showLoadingView() {
        loadingView.show()
    }


    override fun dismissLoadingView() {
        loadingView.dismiss()
    }

    override fun showError(string: String) {
        ToastUtil.showError(string)
    }

}