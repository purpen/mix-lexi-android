package com.lexivip.lexi.search
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseFragment
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.DividerItemDecoration
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.BrandPavilionBean
import com.lexivip.lexi.brandHouse.BrandHouseActivity
import com.lexivip.lexi.user.login.LoginActivity
import com.lexivip.lexi.user.login.UserProfileUtil
import kotlinx.android.synthetic.main.fragment_favorite_shop.*

class FragmentSearchBrandPavilion : BaseFragment(), SearchBrandPavilionContract.View {
    private val dialog: WaitingDialog by lazy { WaitingDialog(activity) }
    override val layout: Int = R.layout.fragment_recyclerview
    private lateinit var presenter: SearchBrandPavilionPresenter
    private val adapter: AdapterSearchBrandPavilion by lazy { AdapterSearchBrandPavilion(R.layout.adapter_design_pavilion) }

    private var searchString: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchString = arguments?.getString(FragmentSearchBrandPavilion::class.java.simpleName)
    }

    companion object {
        @JvmStatic
        fun newInstance(searchString: String): FragmentSearchBrandPavilion {
            val fragment = FragmentSearchBrandPavilion()
            val bundle = Bundle()
            bundle.putString(FragmentSearchBrandPavilion::class.java.simpleName, searchString)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initView() {
        presenter = SearchBrandPavilionPresenter(this)
//        swipeRefreshLayout.setColorSchemeColors(Util.getColor(R.color.color_6ed7af))
//        swipeRefreshLayout.isRefreshing = false
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
//        val view = View(activity)
//        adapter.addHeaderView(view)
        recyclerView.addItemDecoration(DividerItemDecoration(AppApplication.getContext(), R.color.color_f5f7f9, recyclerView))
//        adapter.emptyView =
    }

    override fun setPresenter(presenter: SearchBrandPavilionContract.Presenter?) {
        setPresenter(presenter)
    }


    override fun installListener() {

//        swipeRefreshLayout.setOnRefreshListener {
//            adapter.setEnableLoadMore(false)
//            loadData()
//        }
        adapter.setOnItemClickListener { _, _, position ->
            val item = adapter.getItem(position) ?: return@setOnItemClickListener
            val intent = Intent(activity, BrandHouseActivity::class.java)
            intent.putExtra("rid", item.rid)
            startActivity(intent)
        }

        //关注品牌馆
        adapter.setOnItemChildClickListener { _, _, position ->
            if (UserProfileUtil.isLogin()){
                val pavilionBean = adapter.getItem(position) ?: return@setOnItemChildClickListener
                if (pavilionBean.is_follow_store) { //点击取消关注
                    presenter.focusBrandPavilion(pavilionBean.rid, false, position)
                } else {
                    presenter.focusBrandPavilion(pavilionBean.rid, true, position)
                }
            }else{
                startActivity(Intent(activity,LoginActivity::class.java))
            }
        }

        adapter.setOnLoadMoreListener({
            presenter.loadMoreData()
        }, recyclerView)
    }


    //设置品牌馆关注状态
    override fun setBrandPavilionFocusState(favorite: Boolean, position: Int) {
        val pavilionBean = adapter.getItem(position) ?: return
        pavilionBean.is_follow_store = favorite
        adapter.notifyDataSetChanged()
    }


    override fun loadData() {
        if (TextUtils.isEmpty(searchString)) return
        presenter.loadData(searchString!!)
    }


    override fun setNewData(data: MutableList<BrandPavilionBean>) {
        adapter.setNewData(data)
        adapter.setEnableLoadMore(true)
    }


    override fun addData(products: MutableList<BrandPavilionBean>) {
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