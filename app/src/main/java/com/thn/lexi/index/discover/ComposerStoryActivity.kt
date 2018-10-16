package com.thn.lexi.index.discover

import android.content.Context
import android.content.Intent
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.Util
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseActivity
import com.thn.lexi.AppApplication
import com.thn.lexi.CustomStaggerGridLayoutManager
import com.thn.lexi.R
import com.thn.lexi.beans.LifeWillBean
import com.thn.lexi.index.selection.ZCManifestAdapter
import com.yanyusong.y_divideritemdecoration.Y_Divider
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration
import kotlinx.android.synthetic.main.acticity_header_recyclerview.*

class ComposerStoryActivity : BaseActivity(), ComposerStoryContract.View {
    private val dialog: WaitingDialog by lazy { WaitingDialog(this) }
    override val layout: Int = R.layout.acticity_header_recyclerview
    private val presenter: ComposerStoryPresenter by lazy { ComposerStoryPresenter(this) }
    private val adapter: ZCManifestAdapter by lazy { ZCManifestAdapter(R.layout.adapter_zc_manifest) }

    private var imageId: Int = R.mipmap.icon_image_composer

    override fun getIntentData() {
        imageId = intent.getIntExtra(TAG, R.mipmap.icon_image_composer)
    }

    override fun initView() {
        val titleId: Int
        when (imageId) {
            R.mipmap.icon_image_composer -> {//创作人
                titleId = R.string.text_composer_story
            }
            R.mipmap.icon_image_seeding -> { //种草清单
                titleId = R.string.text_seeding_note
            }
            R.mipmap.icon_image_life_record -> { //生活志
                titleId = R.string.text_life_records
            }
            R.mipmap.icon_image_hand_make -> { //手作教学
                titleId = R.string.text_hand_make_teach
            }
            else -> {
                titleId = R.string.text_composer_story
            }
        }

        customHeadView.setHeadCenterTxtShow(true, titleId)
        swipeRefreshLayout.setColorSchemeColors(Util.getColor(R.color.color_6ed7af))
        recyclerView.setPadding(DimenUtil.dp2px(15.0), 0, DimenUtil.dp2px(15.0), 0)
        val staggeredGridLayoutManager = CustomStaggerGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = staggeredGridLayoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(AppApplication.getContext()))
        val view = View(this)
        view.setPadding(0,DimenUtil.dp2px(15.0),0,0)
        adapter.setHeaderView(view)
    }

    override fun requestNet() {
        presenter.loadData(false, imageId)
    }

    override fun setNewData(life_records: List<LifeWillBean>) {
        adapter.setNewData(life_records)
        swipeRefreshLayout.isRefreshing = false
    }

    override fun addData(life_records: List<LifeWillBean>) {
        adapter.addData(life_records)
        adapter.setEnableLoadMore(true)
    }

    override fun installListener() {
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            adapter.setEnableLoadMore(false)
            presenter.loadData(true, imageId)
        }

        adapter.setOnLoadMoreListener({
            presenter.loadMoreData()
        }, recyclerView)

        //条目点击
        adapter.setOnItemClickListener { _, _, position ->
            val item = adapter.getItem(position) ?: return@setOnItemClickListener
            val intent = Intent(this, ArticleDetailActivity::class.java)
            intent.putExtra(ArticleDetailActivity::class.java.simpleName, item.rid)
            startActivity(intent)
        }
    }

    override fun loadMoreComplete() {
        adapter.loadMoreComplete()
    }

    override fun loadMoreEnd() {
        adapter.loadMoreEnd()
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

    override fun setPresenter(presenter: ComposerStoryContract.Presenter?) {
        setPresenter(presenter)
    }

    private inner class DividerItemDecoration constructor(context: Context) : Y_DividerItemDecoration(context) {
        private val color: Int = Util.getColor(android.R.color.white)
        private val height = 20f
        override fun getDivider(itemPosition: Int): Y_Divider? {
            val divider: Y_Divider?
            if (itemPosition % 2 == 0) {
                divider = Y_DividerBuilder()
                        .setBottomSideLine(true, color, height, 0f, 0f)
                        .setLeftSideLine(true, color, 10f, 0f, 0f)
                        .create()
            } else {
                divider = Y_DividerBuilder()
                        .setBottomSideLine(true, color, height, 0f, 0f)
                        .create()
            }
            return divider
        }
    }
}