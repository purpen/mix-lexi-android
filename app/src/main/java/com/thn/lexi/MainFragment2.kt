package com.thn.lexi

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.Util
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseFragment
import com.thn.lexi.beans.LifeWillBean
import com.thn.lexi.index.bean.BannerImageBean
import com.thn.lexi.index.discover.*
import com.yanyusong.y_divideritemdecoration.Y_Divider
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.fragment_main2.*
import kotlinx.android.synthetic.main.view_custom_headview.view.*

/**
 * 发现
 */
class MainFragment2 : BaseFragment(), DiscoverContract.View {
    private val dialog: WaitingDialog by lazy { WaitingDialog(activity) }
    private lateinit var presenter: DiscoverPresenter
    private val adapterLifeWillSubject: AdapterLifeWillSubject by lazy { AdapterLifeWillSubject(R.layout.adapter_life_will) }
    private val adapterGuessLike: AdapterGuessLike by lazy { AdapterGuessLike(R.layout.adapter_zc_manifest) }
    private val adapterWonderfulStory: AdapterGuessLike by lazy { AdapterGuessLike(R.layout.adapter_zc_manifest) }
    companion object {
        fun newInstance(): MainFragment2 {
            return MainFragment2()
        }
    }

    override val layout: Int = R.layout.fragment_main2

    override fun setPresenter(presenter: DiscoverContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun initView() {
        presenter = DiscoverPresenter(this)
        customHeadView.head_goback.visibility = View.GONE
        customHeadView.setHeadCenterTxtShow(true, R.string.title_discover)
        initBanner()
        initLifeWill()
        initGuessLike()
        initWonderfulStory()
    }

    /**
     * 初始化精彩故事
     */
    private fun initWonderfulStory() {
        val staggeredGridLayoutManager = CustomStaggerGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        staggeredGridLayoutManager.setScrollEnabled(false)
        recyclerViewWonderfulStory.layoutManager = staggeredGridLayoutManager
        recyclerViewWonderfulStory.adapter = adapterWonderfulStory
        recyclerViewWonderfulStory.addItemDecoration(DividerItemDecoration(AppApplication.getContext()))
    }

    /**
     * 初始化猜你喜欢
     */
    private fun initGuessLike() {
        val staggeredGridLayoutManager = CustomStaggerGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        staggeredGridLayoutManager.setScrollEnabled(false)
        recyclerViewGuess.layoutManager = staggeredGridLayoutManager
        recyclerViewGuess.adapter = adapterGuessLike
        recyclerViewGuess.addItemDecoration(DividerItemDecoration(AppApplication.getContext()))
    }

    /**
     * 初始化生活志
     */
    private fun initLifeWill() {
        val imageIds = listOf(R.mipmap.icon_image_composer, R.mipmap.icon_image_seeding, R.mipmap.icon_image_life_record, R.mipmap.icon_image_hand_make)
        val titles = listOf("创作人故事", "种草笔记", "生活记事", "手作教学")
        val list = ArrayList<LifeWillSubjectBean>()
        var lifeWillSubjectBean: LifeWillSubjectBean
        for (i in imageIds.indices) {
            lifeWillSubjectBean = LifeWillSubjectBean()
            lifeWillSubjectBean.imageId = imageIds[i]
            lifeWillSubjectBean.title = titles[i]
            list.add(lifeWillSubjectBean)
        }
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerViewLifeStory.setHasFixedSize(true)
        recyclerViewLifeStory.layoutManager = linearLayoutManager
        recyclerViewLifeStory.adapter = adapterLifeWillSubject
        recyclerViewLifeStory.addItemDecoration(RecyclerViewDivider(AppApplication.getContext(), LinearLayoutManager.HORIZONTAL, DimenUtil.getDimensionPixelSize(R.dimen.dp10), Util.getColor(android.R.color.transparent)))
        adapterLifeWillSubject.setNewData(list)
    }


    private fun initBanner() {
        banner.setImageLoader(GlideImageLoader(R.dimen.dp4))
        banner.setIndicatorGravity(BannerConfig.RIGHT)
    }

    override fun loadData() {
        presenter.getBanner()
        presenter.getLifeWill()
        presenter.getGuessLike()
        presenter.getWonderfulStory()
    }

    /**
     * 猜你喜欢
     */
    override fun setWonderfulStoryData(life_records: List<LifeWillBean>) {
        adapterWonderfulStory.setNewData(life_records)
    }

    /**
     * 设置猜你喜欢数据
     */
    override fun setGuessLikeData(life_records: List<LifeWillBean>) {
        adapterGuessLike.setNewData(life_records)
    }

    /**
     * 设置Banner图
     */
    override fun setBannerData(banner_images: List<BannerImageBean>) {
        if (banner_images.isEmpty()) return
        val list = ArrayList<String>()
        for (item in banner_images) {
            list.add(item.image)
        }
        banner.setImages(list)
        banner.start()
        banner.setOnBannerListener { position ->
            PageUtil.banner2Page(banner_images[position])
        }
    }

    override fun installListener() {
        adapterLifeWillSubject.setOnItemClickListener { _, _, position ->
            val item = adapterLifeWillSubject.getItem(position)
            ToastUtil.showInfo(item?.title)
        }

        adapterGuessLike.setOnItemClickListener { _, _, position ->
            val item = adapterGuessLike.getItem(position)
            ToastUtil.showInfo(item?.title)
        }
        adapterWonderfulStory.setOnItemClickListener { _, _, position ->
            val item = adapterWonderfulStory.getItem(position)
            ToastUtil.showInfo(item?.title)
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

    private inner class DividerItemDecoration constructor(context: Context) : Y_DividerItemDecoration(context) {
        private val color: Int = Util.getColor(android.R.color.white)
        private val height = 20f
        override fun getDivider(itemPosition: Int): Y_Divider? {
            var divider: Y_Divider? = null
            if (itemPosition % 2 != 0) {
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