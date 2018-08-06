package com.thn.lexi.goods.lifehouse
import android.support.v7.widget.LinearLayoutManager
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseFragment
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import com.thn.lexi.RecyclerViewDivider
import kotlinx.android.synthetic.main.fragment_life_house.*

class FragmentLifeHouse:BaseFragment(),LifeHouseContract.View {
    private val dialog: WaitingDialog by lazy { WaitingDialog(activity) }
    private val presenter: LifeHousePresenter by lazy { LifeHousePresenter(this) }
    override val layout: Int = R.layout.fragment_life_house
    private var page: Int = 1
    private lateinit var adapter: LifeHouseAdapter

    companion object {
        @JvmStatic fun newInstance(): FragmentLifeHouse = FragmentLifeHouse()
    }

    override fun initView() {
        swipeRefreshLayout.setColorSchemeColors(resources.getColor(R.color.color_6ed7af))
        swipeRefreshLayout.isRefreshing = false
        adapter = LifeHouseAdapter(R.layout.adapter_curator_recommend)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(RecyclerViewDivider(AppApplication.getContext(), LinearLayoutManager.VERTICAL, resources.getDimensionPixelSize(R.dimen.dp15), resources.getColor(android.R.color.transparent)))
    }


    override fun setPresenter(presenter: LifeHouseContract.Presenter?) {
        setPresenter(presenter)
    }



    override fun installListener() {


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
        if (!swipeRefreshLayout.isRefreshing) dialog?.show()
    }

    override fun dismissLoadingView() {
        dialog?.dismiss()
    }

    override fun showError(string: String) {
        swipeRefreshLayout.isRefreshing = false
        adapter.loadMoreFail()
    }
    override fun goPage() {

    }

}