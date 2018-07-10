package com.thn.lexi
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.thn.lexi.goods.*
import com.thn.lexi.goods.detail.GoodsDetailActivity
import kotlinx.android.synthetic.main.fragment_charactoristic.*

class FragmentCharacteristic : BaseFragment(), CharacteristicContract.View {
    private val dialog: WaitingDialog? by lazy { WaitingDialog(activity) }
    override val layout: Int = R.layout.fragment_charactoristic
    private lateinit var presenter: CharacteristicPresenter
    private var page: Int = 1
    private lateinit var adapter: GoodsAdapter

    companion object {
        @JvmStatic
        fun newInstance(): FragmentCharacteristic = FragmentCharacteristic()
    }

    override fun initView() {
        presenter = CharacteristicPresenter(this)
        adapter = GoodsAdapter(R.layout.adapter_goods_layout,activity)
        swipeRefreshLayout.setColorSchemeColors(resources.getColor(R.color.color_6ed7af))
        swipeRefreshLayout.isRefreshing = false
        val linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(RecyclerViewDivider(AppApplication.getContext(),LinearLayoutManager.VERTICAL,resources.getDimensionPixelSize(R.dimen.dp10),resources.getColor(R.color.color_d1d1d1)))
    }

    override fun setPresenter(presenter: CharacteristicContract.Presenter?) {
        setPresenter(presenter)
    }


    override fun installListener() {
        adapter.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
            ToastUtil.showInfo("$position")
        }

        adapter.setCustomItemClickListener(object :GoodsAdapter.OnCustomItemClickListener{
            override fun onItemClick(id: Int, adapterPosition: Int?) {
                ToastUtil.showInfo("$adapterPosition")
//                presenter.favoriteGoods()
            }
        })


        adapter.setOnItemClickListener { adapter, view, position ->
            val item =  adapter.getItem(position) as GoodsData.DataBean.ProductsBean
            val intent = Intent(activity, GoodsDetailActivity::class.java)
            intent.putExtra(GoodsDetailActivity::class.java.simpleName,item.rid)
            startActivity(intent)
        }

        swipeRefreshLayout.setOnRefreshListener {
            adapter.setEnableLoadMore(false)
            loadData()
        }

        adapter.setOnLoadMoreListener({
            presenter.loadMoreData("",page)
        },recyclerView)
    }

    override fun loadData() {
        page = 1
        presenter.loadData("", page)
    }

    override fun setNewData(data: List<GoodsData.DataBean.ProductsBean>) {
        swipeRefreshLayout.isRefreshing = false
        adapter.setNewData(data)
        adapter.setEnableLoadMore(true)
        ++page
    }

    override fun addData(products: List<GoodsData.DataBean.ProductsBean>) {
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
        if (!swipeRefreshLayout.isRefreshing) dialog?.show()
    }

    override fun dismissLoadingView() {
        dialog?.dismiss()
    }

    override fun showError(string: String) {
        swipeRefreshLayout.isRefreshing = false
        adapter.loadMoreFail()
        ToastUtil.showError(string)
    }

    override fun goPage() {

    }
}