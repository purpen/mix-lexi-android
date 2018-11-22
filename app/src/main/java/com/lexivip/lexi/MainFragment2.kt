package com.lexivip.lexi
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.basemodule.tools.*
import com.basemodule.ui.BaseFragment
import com.lexivip.lexi.beans.LifeWillBean
import com.lexivip.lexi.index.bean.BannerImageBean
import com.lexivip.lexi.index.discover.*
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
        val customGridLayoutManager = CustomGridLayoutManager(AppApplication.getContext(),2)
        customGridLayoutManager.setScrollEnabled(false)
        recyclerViewWonderfulStory.layoutManager = customGridLayoutManager
        recyclerViewWonderfulStory.adapter = adapterWonderfulStory
        recyclerViewWonderfulStory.addItemDecoration(GridSpacingItemDecoration(2,DimenUtil.dp2px(10.0),DimenUtil.dp2px(20.0),false))
    }

    /**
     * 初始化猜你喜欢
     */
    private fun initGuessLike() {
        val customGridLayoutManager = CustomGridLayoutManager(AppApplication.getContext(),2)
        customGridLayoutManager.setScrollEnabled(false)
        recyclerViewGuess.layoutManager = customGridLayoutManager
        recyclerViewGuess.adapter = adapterGuessLike
        recyclerViewGuess.addItemDecoration(GridSpacingItemDecoration(2,DimenUtil.dp2px(10.0),DimenUtil.dp2px(20.0),false))
    }

    /**
     * 初始化生活志
     */
    private fun initLifeWill() {
        val imageIds = listOf(R.mipmap.icon_image_composer, R.mipmap.icon_image_seeding, R.mipmap.icon_image_life_record, R.mipmap.icon_image_hand_make)
        val titles = listOf(getString(R.string.text_composer_story), getString(R.string.text_seeding_note), getString(R.string.text_life_records), getString(R.string.text_hand_make_teach))
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
        val contentW = ScreenUtil.getScreenWidth() - DimenUtil.dp2px(30.0)
        banner.setImageLoader(GlideImageLoader(R.dimen.dp4,contentW,DimenUtil.dp2px(178.0),ImageSizeConfig.DEFAULT))
        banner.setIndicatorGravity(BannerConfig.RIGHT)
    }

    override fun loadData() {
        presenter.getBanner()
        presenter.getLifeWill()
        presenter.getGuessLike()
        presenter.getWonderfulStory()
    }

    /**
     * 界面不可见停止滚动
     */
    override fun onHiddenChanged(hidden: Boolean) {
        if (hidden){
            if (banner!=null) banner.stopAutoPlay()
        }else{
            if (banner!=null) banner.startAutoPlay()
        }
        super.onHiddenChanged(hidden)
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
        //生活主题
        adapterLifeWillSubject.setOnItemClickListener { _, _, position ->
            val item = adapterLifeWillSubject.getItem(position) ?: return@setOnItemClickListener
            val intent = Intent(activity,ComposerStoryActivity::class.java)
            intent.putExtra(ComposerStoryActivity::class.java.simpleName,item.imageId)
            startActivity(intent)
        }

        //猜你喜欢
        adapterGuessLike.setOnItemClickListener { _, _, position ->
            val item = adapterGuessLike.getItem(position) ?: return@setOnItemClickListener
            PageUtil.jump2ArticleDetailActivity(item)
        }

        //精彩故事
        adapterWonderfulStory.setOnItemClickListener { _, _, position ->
            val item = adapterWonderfulStory.getItem(position)?:return@setOnItemClickListener
            PageUtil.jump2ArticleDetailActivity(item)
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