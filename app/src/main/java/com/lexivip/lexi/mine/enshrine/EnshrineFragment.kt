package com.lexivip.lexi.mine.enshrine

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.Util
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseFragment
import com.basemodule.ui.WrapContentViewPager
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.R
import com.lexivip.lexi.RecyclerViewDivider
import com.lexivip.lexi.ScrollableHelper
import com.lexivip.lexi.beans.ProductBean
import com.lexivip.lexi.index.detail.GoodsDetailActivity
import com.lexivip.lexi.mine.like.AdapterLikeGoods
import com.lexivip.lexi.mine.enshrine.recentLook.AllRecentLookGoodsActivity
import com.lexivip.lexi.user.login.UserProfileUtil
import kotlinx.android.synthetic.main.empty_user_center.*
import kotlinx.android.synthetic.main.fragment_user_enshrine.*

class EnshrineFragment : BaseFragment(), EnshrineContract.View, ScrollableHelper.ScrollableContainer {
    private val dialog: WaitingDialog by lazy { WaitingDialog(activity) }

    private val adapterRecent: AdapterLikeGoods by lazy { AdapterLikeGoods(R.layout.adapter_pure_imageview) }


    private val adapterWishOrder: AdapterLikeGoods by lazy { AdapterLikeGoods(R.layout.adapter_pure_imageview) }


    override val layout: Int = R.layout.fragment_user_enshrine
    private lateinit var presenter: EnshrinePresenter

    private var isWishOrderLoaded: Boolean = false
    private var isRecentLookLoaded: Boolean = false
    private var uid: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uid = arguments?.getString(EnshrineFragment::class.java.simpleName)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = super.onCreateView(inflater, container, savedInstanceState)
        if (container is WrapContentViewPager) {
            container.setObjectForPosition(1, rootView)
        }
        return rootView
    }

    companion object {
        @JvmStatic
        fun newInstance(): EnshrineFragment = EnshrineFragment()

        fun newInstance(userId: String): EnshrineFragment {
            val fragment = EnshrineFragment()
            val bundle = Bundle()
            bundle.putString(EnshrineFragment::class.java.simpleName, userId)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initView() {
        presenter = EnshrinePresenter(this)
        initRecentLook()
        initWishOrder()
    }

    /**
     * 最近查看
     */
    private fun initRecentLook() {
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerViewRecent.setHasFixedSize(true)
        recyclerViewRecent.layoutManager = linearLayoutManager
        recyclerViewRecent.adapter = adapterRecent
        recyclerViewRecent.addItemDecoration(RecyclerViewDivider(AppApplication.getContext(), LinearLayoutManager.HORIZONTAL, resources.getDimensionPixelSize(R.dimen.dp10), Util.getColor(android.R.color.transparent)))
    }

    /**
     * 设置最近查看数据
     */
    override fun setRecentLookData(products: List<ProductBean>) {
        isRecentLookLoaded = true
        adapterRecent.setNewData(products)
        if (products.isEmpty()) relativeLayoutRecentLook.visibility = View.GONE
        setEmptyView()
    }


    override fun setPresenter(presenter: EnshrineContract.Presenter?) {
        setPresenter(presenter)
    }

    /**
     * 心愿单
     */
    private fun initWishOrder() {
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerViewWishOrder.setHasFixedSize(true)
        recyclerViewWishOrder.layoutManager = linearLayoutManager
        recyclerViewWishOrder.adapter = adapterWishOrder
        recyclerViewWishOrder.addItemDecoration(RecyclerViewDivider(AppApplication.getContext(), LinearLayoutManager.HORIZONTAL, resources.getDimensionPixelSize(R.dimen.dp10), Util.getColor(android.R.color.transparent)))
    }


    /**
     * 设置心愿单
     */
    override fun setWishOrderData(products: List<ProductBean>) {
        isWishOrderLoaded = true
        adapterWishOrder.setNewData(products)
        if (products.isEmpty()) relativeLayoutWishOrder.visibility = View.GONE

        setEmptyView()
    }

    /**
     * 都加载完毕都无数据
     */
    private fun setEmptyView() {
        if (isRecentLookLoaded && isWishOrderLoaded) {

            if (adapterRecent.data.isEmpty() && adapterWishOrder.data.isEmpty()) {
                linearLayoutEmpty.visibility = View.VISIBLE
                imageViewEmptyView.setImageResource(R.mipmap.icon_no_favorite_goods)
                val emptyStr: String
                if (TextUtils.equals(uid, UserProfileUtil.getUserId())) {
                    emptyStr = getString(R.string.text_no_favorite_goods)
                } else {
                    emptyStr = getString(R.string.text_other_no_favorite_goods)
                }
                textViewEmptyDesc.text = emptyStr
            }
        }
    }

    override fun installListener() {

        //查看最近全部
        textViewMoreRecent.setOnClickListener {
            val intent = Intent(activity, AllRecentLookGoodsActivity::class.java)
            intent.putExtra(AllRecentLookGoodsActivity::class.java.simpleName, uid)
            startActivity(intent)
        }

        //最近查看item点击
        adapterRecent.setOnItemClickListener { _, _, position ->
            val item = adapterRecent.getItem(position)
            val intent = Intent(activity, GoodsDetailActivity::class.java)
            intent.putExtra(GoodsDetailActivity::class.java.simpleName, item)
            startActivity(intent)
        }

        //心愿单item点击
        adapterWishOrder.setOnItemClickListener { _, _, position ->
            val item = adapterWishOrder.getItem(position)
            val intent = Intent(activity, GoodsDetailActivity::class.java)
            intent.putExtra(GoodsDetailActivity::class.java.simpleName, item)
            startActivity(intent)
        }

    }

    fun refreshData() {
        if (TextUtils.isEmpty(uid)) {
            presenter.getUserRecentLook(true)
            presenter.getWishOrder(true)
        } else {
            presenter.getOtherUserRecentLook(uid!!, true)
            presenter.getOtherUserWishOrder(uid!!, true)
        }

    }

    override fun onResume() { //自己需要每次都更新
        super.onResume()
        //没登录或者不是自己都不执行后面代码
        if (!UserProfileUtil.isLogin() || !TextUtils.isEmpty(uid)) return
        presenter.getUserRecentLook(false)

        presenter.getWishOrder(false)
    }


    override fun loadData() { //别人只需加载一次
        if (!TextUtils.isEmpty(uid)) {
            presenter.getOtherUserRecentLook(uid!!, false)

            presenter.getOtherUserWishOrder(uid!!, false)
        }
    }


    override fun getScrollableView(): View {
        return nestedScrollView
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


}