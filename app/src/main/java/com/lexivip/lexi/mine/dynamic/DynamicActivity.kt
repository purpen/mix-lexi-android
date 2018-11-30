package com.lexivip.lexi.mine.dynamic

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SimpleItemAnimator
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import com.basemodule.tools.*
import com.basemodule.ui.BaseActivity
import com.flyco.dialog.listener.OnBtnClickL
import com.flyco.dialog.widget.NormalDialog
import com.lexivip.lexi.ImageSizeConfig
import com.lexivip.lexi.PageUtil
import com.lexivip.lexi.R
import com.lexivip.lexi.discoverLifeAesthetics.ShowWindowDetailBean
import com.lexivip.lexi.publishShopWindow.PublishShopWindowActivity
import com.lexivip.lexi.user.login.LoginActivity
import com.lexivip.lexi.user.login.UserProfileUtil
import kotlinx.android.synthetic.main.activity_mine_dynamic.*
import kotlinx.android.synthetic.main.view_head_mine_dynamic.view.*


class DynamicActivity : BaseActivity(), DynamicContract.View {

    private val presenter: DynamicPresenter by lazy { DynamicPresenter(this) }

    private val adapter: AdapterDynamicAdapter by lazy { AdapterDynamicAdapter(R.layout.adapter_mine_dynamic) }

    private val dialog: WaitingDialog by lazy { WaitingDialog(this) }

    override val layout: Int = R.layout.activity_mine_dynamic

    private lateinit var headerView: View

    private var firstInPage = true

    private var uid: String = UserProfileUtil.getUserId()

    override fun getIntentData() {
        uid = intent.getStringExtra(TAG)
    }

    override fun initView() {
        customHeadView.setHeadCenterTxtShow(true, R.string.title_dynamic_activity)
        swipeRefreshLayout.setColorSchemeColors(Util.getColor(R.color.color_6ed7af))
        swipeRefreshLayout.isRefreshing = false
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        headerView = LayoutInflater.from(this).inflate(R.layout.view_head_mine_dynamic, null)
        GlideUtil.loadImageWithDimen(R.mipmap.icon_bg_dynamic,headerView.imageViewHeader,ScreenUtil.getScreenWidth(),DimenUtil.dp2px(147.0),ImageSizeConfig.DEFAULT)
        if (TextUtils.equals(uid, UserProfileUtil.getUserId())) {
            headerView.linearLayoutPublishWindow.visibility = View.VISIBLE
        } else {
            headerView.buttonFocusUser.visibility = View.GONE
        }

        adapter.addHeaderView(headerView)
        adapter.setHeaderAndEmpty(true)
        adapter.emptyView = LayoutInflater.from(this).inflate(R.layout.empty_view_dynamic, null)
    }

    override fun setPresenter(presenter: DynamicContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun onResume() {
        if (!firstInPage) presenter.loadData(true,uid)
        super.onResume()
    }

    override fun requestNet() {
        presenter.loadData(false, uid)
    }


    override fun setNewData(data: DynamicBean.DataBean) {
        firstInPage = false
        swipeRefreshLayout.isRefreshing = false
        GlideUtil.loadCircleImageWidthDimen(data.user_avatar, headerView.imageViewAvatar, DimenUtil.getDimensionPixelSize(R.dimen.dp60), ImageSizeConfig.SIZE_AVA)
        headerView.textViewName.text = data.username
        adapter.setNewData(data.lines)
    }

    override fun addData(data: DynamicBean.DataBean) {
        adapter.addData(data.lines)
    }

    override fun installListener() {
        headerView.linearLayoutPublishWindow.setOnClickListener {
            //跳转拼接橱窗
            startActivity(Intent(this, PublishShopWindowActivity::class.java))
        }

        headerView.buttonFocusUser.setOnClickListener {
            if (UserProfileUtil.isLogin()) { //关注用户
                ToastUtil.showInfo("关注用户")
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }

        //item点击
        adapter.setOnItemClickListener { _, _, position ->
            val shopWindowBean = adapter.getItem(position) ?: return@setOnItemClickListener
            PageUtil.jump2ShopWindowDetailActivity(shopWindowBean.rid)
        }

        //item子view点击
        adapter.setOnItemChildClickListener { _, view, position ->
            val shopWindowBean = adapter.getItem(position) ?: return@setOnItemChildClickListener
            when (view.id) {
                R.id.imageViewMore -> {
                    showDeleteDialog(shopWindowBean.rid, position)
                }

                R.id.textViewLike -> { //喜欢
                    presenter.favoriteShowWindow(shopWindowBean.rid, shopWindowBean.is_like, position, view)
                }

                R.id.textViewComment -> { //评论列表
                    val dataBean = ShowWindowDetailBean.DataBean()
                    dataBean.rid = shopWindowBean.rid
                    dataBean.is_like = shopWindowBean.is_like
                    dataBean.like_count = shopWindowBean.like_count
                    dataBean.comment_count = shopWindowBean.comment_count
                    PageUtil.jump2ShopWindowCommentListActivity(dataBean)
                }
            }
        }

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            adapter.setEnableLoadMore(false)
            presenter.loadData(true, uid)
        }

        adapter.setOnLoadMoreListener({
            presenter.loadMoreData()
        }, recyclerView)


    }

    /**
     * 设置喜欢状态
     */
    override fun setFavorite(b: Boolean, position: Int) {
        val item = adapter.getItem(position) ?: return
        item.is_like = b
        if (b) {
            item.like_count++
        } else {
            item.like_count--
        }
        adapter.notifyItemChanged(position + 1)
    }

    private fun showDeleteDialog(rid: String, position: Int) {
        val color333 = Util.getColor(R.color.color_333)
        val white = Util.getColor(android.R.color.white)
        val dialog = NormalDialog(this)
        dialog.isTitleShow(false)
                .bgColor(white)
                .cornerRadius(4f)
                .content(Util.getString(R.string.text_delete_shop_window_confirm))
                .contentGravity(Gravity.CENTER)
                .contentTextColor(color333)
                .contentTextSize(16f)
                .dividerColor(Util.getColor(R.color.color_ccc))
                .btnText(Util.getString(R.string.text_qd), Util.getString(R.string.text_cancel))
                .btnTextSize(15f, 15f)
                .btnTextColor(color333, Util.getColor(R.color.color_6ed7af))
                .btnPressColor(white)
                .widthScale(0.85f)
                .show()
        dialog.setOnBtnClickL(OnBtnClickL {
            dialog.dismiss()
            presenter.deleteShopWindow(rid, position)
        }, OnBtnClickL {
            dialog.dismiss()
        })
    }

    /**
     * 删除橱窗
     */
    override fun deleteShopWindow(position: Int) {
        adapter.remove(position)
    }

    override fun loadMoreEnd() {
        adapter.loadMoreEnd()
    }

    override fun loadMoreComplete() {
        adapter.loadMoreComplete()
    }

    override fun loadMoreFail() {
        adapter.loadMoreFail()
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
