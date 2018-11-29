package com.lexivip.lexi.mine.like

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import com.basemodule.tools.*
import com.basemodule.ui.BaseFragment
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.PageUtil
import com.lexivip.lexi.R
import com.lexivip.lexi.RecyclerViewDivider
import com.lexivip.lexi.beans.ProductBean
import com.lexivip.lexi.beans.ShopWindowBean
import com.lexivip.lexi.mine.*
import com.lexivip.lexi.mine.like.likeGoods.AllLikeGoodsActivity
import com.lexivip.lexi.mine.like.likeShopWindow.LikeShopWindowActivity
import com.lexivip.lexi.user.login.UserProfileUtil
import kotlinx.android.synthetic.main.adapter_goods_like.view.*
import kotlinx.android.synthetic.main.adapter_item_show_window.view.*
import kotlinx.android.synthetic.main.empty_user_center.view.*
import kotlinx.android.synthetic.main.fragment_recyclerview.*

class FavoriteFragment : BaseFragment(), FavoriteContract.View {
    private val dialog: WaitingDialog by lazy { WaitingDialog(AppManager.getAppManager().currentActivity()) }

    private val adapterLikeGoods: AdapterLikeGoods by lazy { AdapterLikeGoods(R.layout.adapter_pure_imageview) }

    private val adapterMineFavorite: AdapterMineFavorite by lazy { AdapterMineFavorite(R.layout.adapter_pure_imageview) }

    private val adapterLikeShowWindow: AdapterLikeShowWindow by lazy { AdapterLikeShowWindow(R.layout.adapter_show_window_like) }

    private val presenter: FavoritePresenter by lazy { FavoritePresenter(this) }
    private lateinit var headerView: View

    private lateinit var emptyHeaderView: View

    private var uid: String? = null

    override val layout: Int = R.layout.fragment_recyclerview

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uid = arguments?.getString(FavoriteFragment::class.java.simpleName)
    }

    companion object {
        @JvmStatic
        fun newInstance(): FavoriteFragment = FavoriteFragment()

        fun newInstance(userId: String): FavoriteFragment {
            val fragment = FavoriteFragment()
            val bundle = Bundle()
            bundle.putString(FavoriteFragment::class.java.simpleName, userId)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initView() {

        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapterMineFavorite

        headerView = LayoutInflater.from(context).inflate(R.layout.view_head_mine_favorite, null)
        emptyHeaderView = LayoutInflater.from(context).inflate(R.layout.empty_user_center, null)

        initGoodsLike()
        initShowWindowLike()

        adapterMineFavorite.addHeaderView(headerView)
        adapterMineFavorite.setHeaderAndEmpty(true)

    }

    override fun onResume() {
        super.onResume()
        //没登录或者不是自己都不执行后面代码
        if (!UserProfileUtil.isLogin() || !TextUtils.isEmpty(uid)) return
        presenter.getUserGoodsLike()
        presenter.getShowWindowLike()
    }

    private fun initShowWindowLike() {
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        headerView.recyclerViewShowWindow.setHasFixedSize(true)
        headerView.recyclerViewShowWindow.layoutManager = linearLayoutManager
        headerView.recyclerViewShowWindow.adapter = adapterLikeShowWindow
        headerView.recyclerViewShowWindow.addItemDecoration(RecyclerViewDivider(AppApplication.getContext(), LinearLayoutManager.HORIZONTAL, resources.getDimensionPixelSize(R.dimen.dp10), Util.getColor(android.R.color.transparent)))
    }

    /**
     * 设置喜欢的橱窗数据
     */
    override fun setShowWindowData(shop_windows: List<ShopWindowBean>) {
        adapterLikeShowWindow.setNewData(shop_windows)
        if (shop_windows.isEmpty()) {
            headerView.linearLayoutLikeWindow.visibility = View.GONE
        }else{
            headerView.linearLayoutLikeWindow.visibility = View.VISIBLE
        }
    }

    override fun setPresenter(presenter: FavoriteContract.Presenter?) {
        setPresenter(presenter)
    }

    /**
     * 喜欢的商品
     */
    private fun initGoodsLike() {
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        headerView.recyclerViewGoodsLike.setHasFixedSize(true)
        headerView.recyclerViewGoodsLike.layoutManager = linearLayoutManager
        headerView.recyclerViewGoodsLike.adapter = adapterLikeGoods
        headerView.recyclerViewGoodsLike.addItemDecoration(RecyclerViewDivider(AppApplication.getContext(), LinearLayoutManager.HORIZONTAL, resources.getDimensionPixelSize(R.dimen.dp10), Util.getColor(android.R.color.transparent)))
    }

    /**
     * 设置猜你喜欢界面
     */
    override fun setGoodsLikeData(products: List<ProductBean>) {
        adapterLikeGoods.setNewData(products)
        if (products.isEmpty()) {
            headerView.linearLayoutGoodsLike.visibility = View.GONE
            emptyHeaderView.imageView.setImageResource(R.mipmap.icon_no_favorite_goods)
            val emptyStr:String
            if (TextUtils.equals(uid,UserProfileUtil.getUserId())){
                emptyStr = getString(R.string.text_no_favorite_things)
                emptyHeaderView.textViewDesc1.visibility = View.VISIBLE
            }else{
                emptyStr = getString(R.string.text_other_no_favorite_things)
            }
            emptyHeaderView.textViewDesc.text = emptyStr
            adapterMineFavorite.setHeaderView(emptyHeaderView)
        } else {
            headerView.linearLayoutGoodsLike.visibility = View.VISIBLE
        }
    }


    override fun installListener() {
        headerView.textViewMoreGoodsLike.setOnClickListener {
            val intent = Intent(activity, AllLikeGoodsActivity::class.java)
            intent.putExtra(AllLikeGoodsActivity::class.java.simpleName,uid)
            startActivity(intent)
        }

        headerView.textViewMoreWindowLike.setOnClickListener {
            val intent = Intent(activity, LikeShopWindowActivity::class.java)
            intent.putExtra(LikeShopWindowActivity::class.java.simpleName,uid)
            startActivity(intent)
        }

        adapterLikeGoods.setOnItemClickListener { _, _, position ->
            val item = adapterLikeGoods.getItem(position) ?: return@setOnItemClickListener
            PageUtil.jump2GoodsDetailActivity(item.rid)
        }
    }


    override fun loadData() {//不是自己走下面代码
        if (!TextUtils.isEmpty(uid)) {
            presenter.getOtherUserGoodsLike(uid!!)
            presenter.getOtherUserShowWindowLike(uid!!)
        }
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