package com.lexivip.lexi.mine.designPavilion
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseFragment
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.DividerItemDecoration
import com.lexivip.lexi.R
import com.lexivip.lexi.ScrollableHelper
import com.lexivip.lexi.brandHouse.BrandHouseActivity
import com.lexivip.lexi.user.login.UserProfileUtil
import kotlinx.android.synthetic.main.empty_user_center.view.*
import kotlinx.android.synthetic.main.fragment_favorite_shop.*

class FavoriteShopFragment : BaseFragment(), FavoriteDesignContract.View, ScrollableHelper.ScrollableContainer {
    private val dialog: WaitingDialog by lazy { WaitingDialog(activity) }
    override val layout: Int = R.layout.fragment_recyclerview
    private lateinit var presenter: FavoriteDesignPresenter
    private val adapter: AdapterDesignPavilion by lazy { AdapterDesignPavilion(R.layout.adapter_design_pavilion) }
    private lateinit var emptyHeaderView: View

    private var uid: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uid = arguments?.getString(FavoriteShopFragment::class.java.simpleName)
    }

    companion object {
        @JvmStatic
        fun newInstance(): FavoriteShopFragment = FavoriteShopFragment()

        fun newInstance(userId: String): FavoriteShopFragment {
            val fragment = FavoriteShopFragment()
            val bundle = Bundle()
            bundle.putString(FavoriteShopFragment::class.java.simpleName, userId)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initView() {
        presenter = FavoriteDesignPresenter(this)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        val view = View(activity)
        emptyHeaderView = LayoutInflater.from(context).inflate(R.layout.empty_user_center, null)
        emptyHeaderView.visibility = View.VISIBLE
        adapter.addHeaderView(view)
        recyclerView.addItemDecoration(DividerItemDecoration(AppApplication.getContext(), R.color.color_f5f7f9, recyclerView))
    }

    override fun setPresenter(presenter: FavoriteDesignContract.Presenter?) {
        setPresenter(presenter)
    }


    override fun installListener() {

        //关注品牌馆
        adapter.setOnItemChildClickListener { _, v, position ->
            val pavilionBean = adapter.getItem(position) as DesignPavilionBean
            if (pavilionBean.followed_status == 1) { //点击取消关注
                presenter.focusBrandPavilion(pavilionBean.rid, false, position, v)
            } else {
                presenter.focusBrandPavilion(pavilionBean.rid, true, position, v)
            }
        }

        //品牌馆点击
        adapter.setOnItemClickListener { _, _, position ->
            val pavilionBean = adapter.getItem(position) as DesignPavilionBean
            val intent = Intent(activity, BrandHouseActivity::class.java)
            intent.putExtra("rid", pavilionBean.rid)
            startActivity(intent)
        }

        adapter.setOnLoadMoreListener({
            if (TextUtils.isEmpty(uid)) {
                presenter.loadMoreData()
            } else {
                presenter.loadMoreData(uid!!)
            }
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

    fun refreshData() {
        if (TextUtils.isEmpty(uid)) {
            presenter.loadData(true)
        } else {
            presenter.loadData(true, uid!!)
        }
    }

    override fun onResume() {
        super.onResume()
        //没登录或者不是自己都不执行后面代码
        if (!UserProfileUtil.isLogin() || !TextUtils.isEmpty(uid)) return
        presenter.loadData(true)
    }

    override fun loadData() {
        if (!TextUtils.isEmpty(uid)) {
            presenter.loadData(true, uid!!)
        }
    }


    override fun setNewData(data: MutableList<DesignPavilionBean>) {
        adapter.setNewData(data)
        adapter.setEnableLoadMore(true)
        if (adapter.data.isEmpty()) {
            emptyHeaderView.imageViewEmptyView.setImageResource(R.mipmap.icon_no_life_house)
            val emptyStr: String
            if (TextUtils.equals(uid, UserProfileUtil.getUserId())) {
                emptyStr = getString(R.string.text_no_focus_life_house)
            } else {
                emptyStr = getString(R.string.text_other_no_focus_life_house)
            }
            emptyHeaderView.textViewEmptyDesc.text = emptyStr
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

    override fun getScrollableView(): View {
        return recyclerView
    }

    override fun showError(string: String) {
        adapter.loadMoreFail()
        ToastUtil.showError(string)
    }

    override fun goPage() {

    }


}