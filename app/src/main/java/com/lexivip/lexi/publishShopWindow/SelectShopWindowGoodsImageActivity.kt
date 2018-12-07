package com.lexivip.lexi.publishShopWindow

import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SimpleItemAnimator
import android.text.TextUtils
import android.util.SparseArray
import com.basemodule.tools.LogUtil
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.Util
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseActivity
import com.basemodule.ui.BaseFragment
import com.basemodule.ui.CustomFragmentPagerAdapter
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.ProductBean
import com.lexivip.lexi.eventBusMessge.MessageAddGoodsImages
import com.lexivip.lexi.eventBusMessge.MessageRequestGoodsImages
import kotlinx.android.synthetic.main.acticity_select_shop_window_goods_image.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class SelectShopWindowGoodsImageActivity : BaseActivity(), SelectShopWindowGoodsImageContract.View {
    private val dialog: WaitingDialog by lazy { WaitingDialog(this) }
    private val presenter: SelectShopWindowGoodsImagePresenter by lazy { SelectShopWindowGoodsImagePresenter(this) }
    private val adapterSelectedImage: AdapterSelectOneGoodsImage by lazy { AdapterSelectOneGoodsImage(R.layout.adapter_select_one_goods_image) }
    override val layout: Int = R.layout.acticity_select_shop_window_goods_image
    private var pos: Int = 0
    private lateinit var productsMap: SparseArray<ProductBean>
    //设计馆rid
    private var storeRid: String = ""
    //商品rid
    private var rid: String = ""
    private var selectedProductImage: SelectGoodsImageBean.DataBean.ImagesBean? = null

    override fun getIntentData() {
        val bundle = intent.extras
        pos = bundle.getInt(TAG, 0)
        productsMap = bundle.getSparseParcelableArray(SelectShopWindowGoodsImageActivity::class.java.name)
    }

    override fun initView() {
        EventBus.getDefault().register(this)
        customHeadView.setHeadCenterTxtShow(true, R.string.title_select_goods)
        setUpViewPager()
        recyclerViewSelected.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewSelected.setHasFixedSize(true)
        recyclerViewSelected.adapter = adapterSelectedImage
        (recyclerViewSelected.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
    }

    private fun setUpViewPager() {
        val titles = resources.getStringArray(R.array.strings_select_goods_titles)
        val fragments = ArrayList<BaseFragment>()
        fragments.add(SelectGoodsFragment.newInstance(SelectGoodsFragment.PAGE_LIKE))
        fragments.add(SelectGoodsFragment.newInstance(SelectGoodsFragment.PAGE_WISH_ORDER))
        fragments.add(SelectGoodsFragment.newInstance(SelectGoodsFragment.PAGE_RECENT_LOOK))

        val adapter = CustomFragmentPagerAdapter(supportFragmentManager, fragments, titles.toMutableList())
        customViewPager.adapter = adapter
        customViewPager.offscreenPageLimit = fragments.size
        customViewPager.setPagingEnabled(false)
        slidingTabLayout.setViewPager(customViewPager)
        slidingTabLayout.getTitleView(0).textSize = 20f
    }

    //当选择图片
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onChangeGoods(message: MessageRequestGoodsImages) {
        storeRid = message.storeRid
        rid = message.rid
        presenter.loadGoodsImageById(message.rid)
    }

    /**
     * 设置商品图片
     */
    override fun setNewData(images: List<SelectGoodsImageBean.DataBean.ImagesBean>) {
        if (!images.isEmpty()) {
            images[0].selected = true
            images[0].store_rid = storeRid
            selectedProductImage = images[0]
        }
        adapterSelectedImage.setNewData(images)
        button.isEnabled = true
        button.setTextColor(Util.getColor(android.R.color.white))
    }

    override fun installListener() {
        //点击确定
        button.setOnClickListener {
            if (selectedProductImage == null) return@setOnClickListener
            var sameStoreRidCount = 0
            for (i in 0..6) {//橱窗该位置已有图且选图不重复则执行替换操作
                val bean = productsMap[i] ?: break
                if (i != pos) {
                    if (TextUtils.equals(bean.rid,rid)){
                        ToastUtil.showInfo("商品已添加至橱窗")
                        return@setOnClickListener
                    }

                    if (TextUtils.equals(bean.store_rid,storeRid)) {
                        sameStoreRidCount++
                    }
                    if (sameStoreRidCount == 2) {
                        ToastUtil.showInfo("一个橱窗最多添加一个品牌馆两件商品")
                        return@setOnClickListener
                    }
                }
            }

            EventBus.getDefault().post(MessageAddGoodsImages(pos, selectedProductImage!!.view_url, selectedProductImage!!.id, rid,storeRid))
            finish()
        }

        //待选图片点击
        adapterSelectedImage.setOnItemClickListener { _, _, position ->
            val data = adapterSelectedImage.data
            val item = adapterSelectedImage.getItem(position) ?: return@setOnItemClickListener
            for (imageBean in data) {
                imageBean.selected = false
                imageBean.store_rid = storeRid
            }
            item.selected = true
            selectedProductImage = item
            adapterSelectedImage.notifyDataSetChanged()
        }



        customViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                val count = customViewPager.childCount
                for (i in 0 until count) {
                    if (i == position) {
                        slidingTabLayout.getTitleView(i).textSize = 20f
                    } else {
                        slidingTabLayout.getTitleView(i).textSize = 17f
                    }
                }

            }

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }
        })
    }


    override fun setPresenter(presenter: SelectShopWindowGoodsImageContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun showLoadingView() {
        dialog.show()
    }

    override fun showError(string: String) {
        ToastUtil.showError(string)
    }

    override fun dismissLoadingView() {
        dialog.dismiss()
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }
}