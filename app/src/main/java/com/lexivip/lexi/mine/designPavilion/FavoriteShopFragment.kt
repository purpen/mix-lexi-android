package com.lexivip.lexi.mine.designPavilion

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseFragment
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.DividerItemDecoration
import com.lexivip.lexi.R
import com.lexivip.lexi.brandHouse.BrandHouseActivity
import kotlinx.android.synthetic.main.empty_user_center.view.*
import kotlinx.android.synthetic.main.fragment_favorite_shop.*

class FavoriteShopFragment : BaseFragment(), FavoriteDesignContract.View {
    private val dialog: WaitingDialog by lazy { WaitingDialog(activity) }
    override val layout: Int = R.layout.fragment_recyclerview
    private lateinit var presenter: FavoriteDesignPresenter
    private val adapter: AdapterDesignPavilion by lazy { AdapterDesignPavilion(R.layout.adapter_design_pavilion) }
    private lateinit var emptyHeaderView: View

    companion object {
        @JvmStatic
        fun newInstance(): FavoriteShopFragment = FavoriteShopFragment()
    }

    override fun initView() {
        presenter = FavoriteDesignPresenter(this)
//        swipeRefreshLayout.setColorSchemeColors(Util.getColor(R.color.color_6ed7af))
//        swipeRefreshLayout.isRefreshing = false
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        val view = View(activity)
        emptyHeaderView = LayoutInflater.from(context).inflate(R.layout.empty_user_center, null)
        adapter.addHeaderView(view)
        recyclerView.addItemDecoration(DividerItemDecoration(AppApplication.getContext(), R.color.color_f5f7f9, recyclerView))
    }

    override fun setPresenter(presenter: FavoriteDesignContract.Presenter?) {
        setPresenter(presenter)
    }


    override fun installListener() {

//        swipeRefreshLayout.setOnRefreshListener {
//            adapter.setEnableLoadMore(false)
//            loadData()
//        }

        //关注品牌馆
        adapter.setOnItemChildClickListener { _, _, position ->
            val pavilionBean = adapter.getItem(position) as DesignPavilionBean
            if (pavilionBean.followed_status == 1) { //点击取消关注
                presenter.focusBrandPavilion(pavilionBean.rid, false, position)
            } else {
                presenter.focusBrandPavilion(pavilionBean.rid, true, position)
            }
        }

        //品牌馆点击
        adapter.setOnItemClickListener { _, _, position ->
            val pavilionBean = adapter.getItem(position) as DesignPavilionBean
            val intent = Intent(activity, BrandHouseActivity::class.java)
            intent.putExtra("rid",pavilionBean.rid)
            startActivity(intent)
        }

        adapter.setOnLoadMoreListener({
            presenter.loadMoreData()
        }, recyclerView)
    }

    /**
     *  设置品牌馆关注状态
     */
    override fun setBrandPavilionFocusState(favorite: Boolean, position: Int) {
        val pavilionBean = adapter.getItem(position) as DesignPavilionBean
        if (favorite) {
            pavilionBean.followed_status = 1
        } else {
            pavilionBean.followed_status = 0
        }

        adapter.notifyDataSetChanged()
    }

    override fun loadData() {
        presenter.loadData()
    }


    override fun setNewData(data: MutableList<DesignPavilionBean>) {
        adapter.setNewData(data)
        adapter.setEnableLoadMore(true)
        if (adapter.data.isEmpty()) {
            emptyHeaderView.imageView.setImageResource(R.mipmap.icon_no_life_house)
            emptyHeaderView.textViewDesc.text = getString(R.string.text_no_focus_life_house)
            adapter.setHeaderView(emptyHeaderView)
        }
    }


    override fun addData(products: MutableList<DesignPavilionBean>) {
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

    override fun showError(string: String) {
        adapter.loadMoreFail()
//        swipeRefreshLayout.isRefreshing = false
        ToastUtil.showError(string)
    }

    override fun goPage() {

    }
}