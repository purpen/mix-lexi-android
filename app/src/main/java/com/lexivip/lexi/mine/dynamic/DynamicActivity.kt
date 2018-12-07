package com.lexivip.lexi.mine.dynamic

import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SimpleItemAnimator
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.basemodule.tools.*
import com.basemodule.ui.BaseActivity
import com.flyco.dialog.listener.OnBtnClickL
import com.flyco.dialog.widget.NormalDialog
import com.lexivip.lexi.ImageSizeConfig
import com.lexivip.lexi.PageUtil
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.ShopWindowBean
import com.lexivip.lexi.discoverLifeAesthetics.ShowWindowDetailBean
import com.lexivip.lexi.publishShopWindow.PublishShopWindowActivity
import com.lexivip.lexi.user.login.LoginActivity
import com.lexivip.lexi.user.login.UserProfileUtil
import com.yanyusong.y_divideritemdecoration.Y_Divider
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration
import kotlinx.android.synthetic.main.activity_mine_dynamic.*
import kotlinx.android.synthetic.main.empty_view_dynamic.view.*
import kotlinx.android.synthetic.main.view_head_mine_dynamic.view.*


class DynamicActivity : BaseActivity(), DynamicContract.View {

    private val presenter: DynamicPresenter by lazy { DynamicPresenter(this) }

    private val adapter: AdapterDynamicAdapter by lazy { AdapterDynamicAdapter(R.layout.adapter_mine_dynamic) }

    private val dialog: WaitingDialog by lazy { WaitingDialog(this) }

    private var data: DynamicBean.DataBean? = null

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
        recyclerView.addItemDecoration(DividerItemDecoration(this))
        headerView = LayoutInflater.from(this).inflate(R.layout.view_head_mine_dynamic, null)
        GlideUtil.loadImageWithDimen(R.mipmap.icon_bg_dynamic, headerView.imageViewHeader, ScreenUtil.getScreenWidth(), DimenUtil.dp2px(147.0), ImageSizeConfig.DEFAULT)
        val emptyView = LayoutInflater.from(this).inflate(R.layout.empty_view_dynamic, null)
        adapter.emptyView = emptyView
        if (TextUtils.equals(uid, UserProfileUtil.getUserId())) {
            emptyView.textViewTips.text = getString(R.string.text_no_dynamic)
            headerView.linearLayoutPublishWindow.visibility = View.VISIBLE
        } else {
            emptyView.textViewTips.text = getString(R.string.text_other_no_dynamic)
            headerView.buttonFocusUser.visibility = View.VISIBLE
        }


        headerView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,DimenUtil.dp2px(200.0))

        adapter.addHeaderView(headerView)
        adapter.setHeaderAndEmpty(true)
    }

    override fun setPresenter(presenter: DynamicContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun onResume() {
        if (!firstInPage) presenter.loadData(true, uid)
        super.onResume()
    }

    override fun requestNet() {
        presenter.loadData(false, uid)
    }


    override fun setNewData(data: DynamicBean.DataBean) {
        this.data = data
        setUserFocusState(data.followed_status)
        firstInPage = false
        swipeRefreshLayout.isRefreshing = false
        GlideUtil.loadCircleImageWidthDimen(data.user_avatar, headerView.imageViewAvatar, DimenUtil.getDimensionPixelSize(R.dimen.dp60), ImageSizeConfig.SIZE_AVA)
        headerView.textViewName.text = data.username
        adapter.setNewData(setUserHeadImage(data.lines,data.user_avatar))
    }

    /**
     * 设置用户头像
     */
    fun setUserHeadImage(shopWindows:List<ShopWindowBean>,url:String):List<ShopWindowBean>{
        for (item in shopWindows){
            item.user_avatar = url
        }
        return shopWindows
    }

    override fun addData(data: DynamicBean.DataBean) {
        adapter.addData(setUserHeadImage(data.lines,data.user_avatar))
    }

    /**
     * 设置用户关注状态
     */
    override fun setUserFocusState(followed_status: Int) {
        if (data != null) data!!.followed_status = followed_status
        setFocusState(followed_status)
    }

    /**
     * 设置用户关注状态
     */
    private fun setFocusState(followed_status: Int) {
        when (followed_status) {
            0 -> { //未关注
                headerView.buttonFocusUser.text = Util.getString(R.string.text_focus)
                headerView.buttonFocusUser.compoundDrawablePadding = DimenUtil.dp2px(4.0)
                headerView.buttonFocusUser.setBackgroundResource(R.drawable.bg_round_color5fe4b1)
                headerView.buttonFocusUser.setTextColor(Util.getColor(android.R.color.white))
                headerView.buttonFocusUser.setCompoundDrawables(Util.getDrawableWidthDimen(R.mipmap.icon_add_white, R.dimen.dp10, R.dimen.dp10), null, null, null)
            }

            1 -> { //已关注
                headerView.buttonFocusUser.text = Util.getString(R.string.text_focused)
                headerView.buttonFocusUser.setBackgroundResource(R.drawable.bg_round_coloreff3f2)
                headerView.buttonFocusUser.setTextColor(Util.getColor(R.color.color_949ea6))
                headerView.buttonFocusUser.setPadding(0, 0, 0, 0)
                headerView.buttonFocusUser.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            }

            2 -> { //相互关注
                headerView.buttonFocusUser.text = Util.getString(R.string.text_focused_each_other)
                headerView.buttonFocusUser.setTextColor(Util.getColor(R.color.color_949ea6))
                headerView.buttonFocusUser.setBackgroundResource(R.drawable.bg_round_coloreff3f2)
                headerView.buttonFocusUser.setPadding(0, 0, 0, 0)
                headerView.buttonFocusUser.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            }

        }
    }

    override fun installListener() {
        headerView.linearLayoutPublishWindow.setOnClickListener {
            //跳转拼接橱窗
            startActivity(Intent(this, PublishShopWindowActivity::class.java))
        }

        headerView.buttonFocusUser.setOnClickListener {
            if (UserProfileUtil.isLogin()) { //关注用户
                presenter.focusUser(uid, headerView.buttonFocusUser, data!!.followed_status)
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
                .dividerColor(Util.getColor(R.color.color_eee))
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

    internal inner class DividerItemDecoration(context: Context) : Y_DividerItemDecoration(context) {
        private val color: Int = Util.getColor(R.color.color_f5f7f9)
        override fun getDivider(itemPosition: Int): Y_Divider? {
            val count = adapter.itemCount
            var divider: Y_Divider? = null
            when (itemPosition) {
                count - 2 -> {

                    divider = Y_DividerBuilder()
                            .setBottomSideLine(false, color, 0f, 0f, 0f)
                            .create()
                }

                count - 1 -> {
                    divider = Y_DividerBuilder()
                            .setBottomSideLine(false, color, 0f, 0f, 0f)
                            .create()
                }

                else -> {
                    divider = Y_DividerBuilder()
                            .setBottomSideLine(true, color, 10f, 0f, 0f)
                            .create()
                }
            }

            return divider
        }
    }

}
