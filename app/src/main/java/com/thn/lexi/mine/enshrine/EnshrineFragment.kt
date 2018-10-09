package com.thn.lexi.mine.enshrine
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.Util
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseFragment
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import com.thn.lexi.RecyclerViewDivider
import com.thn.lexi.beans.ProductBean
import com.thn.lexi.index.detail.GoodsDetailActivity
import com.thn.lexi.mine.like.AdapterLikeGoods
import com.thn.lexi.mine.AdapterMineFavorite
import com.thn.lexi.mine.enshrine.recentLook.AllRecentLookGoodsActivity
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import kotlinx.android.synthetic.main.view_head_mine_enshrine.view.*

class EnshrineFragment : BaseFragment(), EnshrineContract.View {
    private val dialog: WaitingDialog by lazy { WaitingDialog(activity) }

    private val adapterRecent: AdapterLikeGoods by lazy { AdapterLikeGoods(R.layout.adapter_pure_imageview) }

    private val adapterMineFavorite: AdapterMineFavorite by lazy { AdapterMineFavorite(R.layout.adapter_pure_imageview) }

    private val adapterWishOrder: AdapterLikeGoods by lazy { AdapterLikeGoods(R.layout.adapter_pure_imageview) }

    private lateinit var headerView:View

    override val layout: Int = R.layout.fragment_recyclerview
    private lateinit var presenter: EnshrinePresenter

    companion object {
        @JvmStatic
        fun newInstance(): EnshrineFragment = EnshrineFragment()
    }

    override fun initView() {
        presenter = EnshrinePresenter(this)

        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapterMineFavorite

        headerView = LayoutInflater.from(context).inflate(R.layout.view_head_mine_enshrine, null)
        adapterMineFavorite.addHeaderView(headerView)
        adapterMineFavorite.setHeaderAndEmpty(true)

        initRecentLook()

        initWishOrder()

    }

    /**
     * 最近查看
     */
    private fun initRecentLook() {
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        headerView.recyclerViewRecent.setHasFixedSize(true)
        headerView.recyclerViewRecent.layoutManager = linearLayoutManager
        headerView.recyclerViewRecent.adapter = adapterRecent
        headerView.recyclerViewRecent.addItemDecoration(RecyclerViewDivider(AppApplication.getContext(), LinearLayoutManager.HORIZONTAL, resources.getDimensionPixelSize(R.dimen.dp10), Util.getColor(android.R.color.transparent)))
    }

    /**
     * 设置最近查看数据
     */
    override fun setRecentLookData(products: List<ProductBean>) {
        adapterRecent.setNewData(products)
        if (products.isEmpty()) headerView.relativeLayoutRecentLook.visibility = View.GONE
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
        headerView.recyclerViewWishOrder.setHasFixedSize(true)
        headerView.recyclerViewWishOrder.layoutManager = linearLayoutManager
        headerView.recyclerViewWishOrder.adapter = adapterWishOrder
        headerView.recyclerViewWishOrder.addItemDecoration(RecyclerViewDivider(AppApplication.getContext(), LinearLayoutManager.HORIZONTAL, resources.getDimensionPixelSize(R.dimen.dp10), Util.getColor(android.R.color.transparent)))
    }


    /**
     * 设置心愿单
     */
    override fun setWishOrderData(products: List<ProductBean>) {
        adapterWishOrder.setNewData(products)
        if (products.isEmpty()) headerView.relativeLayoutWishOrder.visibility = View.GONE
    }

    override fun installListener() {

        //查看最近全部
        headerView.textViewMoreRecent.setOnClickListener {
//            startActivity(Intent(activity,AllRecentLookGoodsActivity::class.java))
        }

        //最近查看item点击
        adapterRecent.setOnItemClickListener { _, _, position ->
            val item = adapterRecent.getItem(position)
            val intent = Intent(activity, GoodsDetailActivity::class.java)
            intent.putExtra(GoodsDetailActivity::class.java.simpleName,item)
            startActivity(intent)
        }

        //心愿单item点击
        adapterWishOrder.setOnItemClickListener { _, _, position ->
            val item = adapterRecent.getItem(position)
            val intent = Intent(activity, GoodsDetailActivity::class.java)
            intent.putExtra(GoodsDetailActivity::class.java.simpleName,item)
            startActivity(intent)
        }

    }




    override fun loadData() {
        presenter.getUserRecentLook()

        presenter.getWishOrder()
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