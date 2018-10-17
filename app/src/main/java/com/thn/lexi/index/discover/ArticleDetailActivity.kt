package com.thn.lexi.index.discover

import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.text.TextUtils
import android.view.View
import com.basemodule.tools.*
import com.basemodule.ui.BaseActivity
import com.thn.lexi.AppApplication
import com.thn.lexi.CustomStaggerGridLayoutManager
import com.thn.lexi.R
import com.thn.lexi.RecyclerViewDivider
import com.thn.lexi.beans.LifeWillBean
import com.thn.lexi.beans.ProductBean
import com.thn.lexi.index.detail.GoodsDetailActivity
import com.thn.lexi.index.explore.editorRecommend.EditorRecommendAdapter
import com.yanyusong.y_divideritemdecoration.Y_Divider
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration
import kotlinx.android.synthetic.main.acticity_artical_detail.*
import kotlinx.android.synthetic.main.footer_view_article_detail.view.*
import kotlinx.android.synthetic.main.footer_view_stagger_recyclerview.view.*
import kotlinx.android.synthetic.main.header_view_article_detail.view.*
import org.json.JSONObject

class ArticleDetailActivity : BaseActivity(), ArticleDetailContract.View {
    private val dialog: WaitingDialog by lazy { WaitingDialog(this) }
    override val layout: Int = R.layout.acticity_artical_detail
    private val presenter: ArticleDetailPresenter by lazy { ArticleDetailPresenter(this) }
    private val adapterRelateStories: AdapterGuessLike by lazy { AdapterGuessLike(R.layout.adapter_zc_manifest) }
    private val adapterRecommend: EditorRecommendAdapter by lazy { EditorRecommendAdapter(R.layout.adapter_editor_recommend) }
    private lateinit var listDescription: ArrayList<AdapterArticleDetail.MultipleItem>
    private lateinit var adapter: AdapterArticleDetail
    private var data:ArticleDetailBean.DataBean?=null
    private var rid: String? = null
    private lateinit var channelName: String
    private lateinit var headerView: View
    private lateinit var footerView: View
    private lateinit var footerView1: View
    override fun getIntentData() {
        rid = intent.getStringExtra(TAG)
        channelName = intent.getStringExtra(ArticleDetailActivity::class.java.name)
    }


    override fun initView() {
        headerView = View.inflate(this,R.layout.header_view_article_detail,null)
        headerView.textViewBrowserNum.setCompoundDrawables(Util.getDrawableWidthDimen(R.mipmap.icon_show_password,R.dimen.dp14,R.dimen.dp14),null,null,null)

        footerView = View.inflate(this,R.layout.footer_view_article_detail,null)
        footerView1 = View.inflate(this,R.layout.footer_view_stagger_recyclerview,null)

        listDescription = ArrayList()
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.setHasFixedSize(false)
        recyclerView.layoutManager = linearLayoutManager

        adapter = AdapterArticleDetail(listDescription,channelName)
        recyclerView.adapter = adapter
        initRecommendGoods()
        initRelateStories()
        adapter.setHeaderView(headerView)
        adapter.addFooterView(footerView)
        adapter.addFooterView(footerView1)
        adapter.setHeaderFooterEmpty(true,true)
    }

    /**
     * 初始化推荐商品
     */
    private fun initRecommendGoods() {
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        footerView.recyclerViewRecommend.setHasFixedSize(true)
        footerView.recyclerViewRecommend.layoutManager = linearLayoutManager
        footerView.recyclerViewRecommend.adapter = adapterRecommend
        footerView.recyclerViewRecommend.addItemDecoration(RecyclerViewDivider(AppApplication.getContext(), LinearLayoutManager.HORIZONTAL, resources.getDimensionPixelSize(R.dimen.dp10), Util.getColor(android.R.color.transparent)))

    }

    /**
     * 初始化相关故事
     */
    private fun initRelateStories() {
        val staggeredGridLayoutManager = CustomStaggerGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        staggeredGridLayoutManager.setScrollEnabled(false)
        footerView1.recyclerViewRelateStory.layoutManager = staggeredGridLayoutManager
        footerView1.recyclerViewRelateStory.adapter = adapterRelateStories
        footerView1.recyclerViewRelateStory.addItemDecoration(DividerItemDecoration(AppApplication.getContext()))
    }

    override fun requestNet() {
        if (TextUtils.isEmpty(rid)) return
        presenter.loadData(false, rid!!)
        presenter.getRelateStories(rid!!)
        presenter.getRecommendProducts(rid!!)
    }

    /**
     * 设置推荐商品
     */
    override fun setRecommendProductsData(products: List<ProductBean>) {
        if (products.isEmpty()) headerView.linearLayoutRecommendProduct.visibility = View.GONE
        adapterRecommend.setNewData(products)
    }

    /**
     * 设置相关故事数据
     */

    override fun setRelateStoriesData(data: MutableList<LifeWillBean>) {
        if (data.isEmpty()) headerView.linearLayoutRelateStory.visibility = View.GONE
        adapterRelateStories.setNewData(data)
    }

    /**
     * 设置用户关注状态
     */
    override fun setFocusState(followed_status: Int) {
        data?.is_follow = followed_status != 0
        setUserFocusState()
    }

    /**
     * 设置用户关注状态
     */
    private fun setUserFocusState() {
        if(data==null) return
        if (data!!.is_follow) {
            headerView.textViewFocus.text = Util.getString(R.string.text_focused)
            headerView.textViewFocus.setBackgroundResource(R.drawable.bg_round_coloreff3f2)
            headerView.textViewFocus.setTextColor(Util.getColor(R.color.color_949ea6))
        } else {
            headerView.textViewFocus.text = Util.getString(R.string.text_focus)
            headerView.textViewFocus.setBackgroundResource(R.drawable.bg_round_color5fe4b1)
            headerView.textViewFocus.setTextColor(Util.getColor(android.R.color.white))
        }
    }


    /**
     * 设置文章详情数据
     */
    override fun setData(data: JSONObject) {
        val bean = ArticleDetailBean.DataBean()
        bean.is_follow = data.optBoolean("is_follow")
        bean.uid = data.optString("uid")
        bean.title = data.optString("title")
        this.data = bean

        val cover = data.optString("cover")
        val channelName = data.optString("channel_name")
        val title = data.optString("title")
        val publishedAt = data.optLong("published_at")
        val browseCount = data.optInt("browse_count")
        val userAvatar = data.optString("user_avator")
        val userName = data.optString("user_name")
        val dealContent = data.optJSONArray("deal_content")

        GlideUtil.loadImageWithFading(cover,headerView.imageViewCover)
        headerView.textViewArticleType.text = channelName

        headerView.textViewArticleTitle.text = title
        headerView.textViewDate.text = DateUtil.getDateByTimestamp(publishedAt,"yyyy.MM.dd")
        headerView.textViewBrowserNum.text = "$browseCount"
        GlideUtil.loadCircleImageWidthDimen(userAvatar,headerView.imageViewHeader,DimenUtil.dp2px(25.0))
        headerView.textViewName.text = userName
        setUserFocusState()

        val maxIndex = dealContent.length() - 1
        for (i in 0..maxIndex) {
            val item = dealContent[i] as JSONObject
            val type = item.optString("type")
            val isBigProduct = item.optBoolean("big_picture")
            when(type){
                "text" ->{
                    listDescription.add(AdapterArticleDetail.MultipleItem(item, AdapterArticleDetail.MultipleItem.TEXT_ITEM_TYPE))
                }
                "image" ->{
                    listDescription.add(AdapterArticleDetail.MultipleItem(item, AdapterArticleDetail.MultipleItem.IMAGE_ITEM_TYPE))
                }
                "product" ->{
                    if (isBigProduct){
                        listDescription.add(AdapterArticleDetail.MultipleItem(item, AdapterArticleDetail.MultipleItem.LARGE_PRODUCT_ITEM_TYPE))
                    }else{
                        listDescription.add(AdapterArticleDetail.MultipleItem(item, AdapterArticleDetail.MultipleItem.SMALL_PRODUCT_ITEM_TYPE))
                    }

                }

                "blockquote" ->{

                }
                "ol" ->{

                }
            }
        }

        adapter.setNewData(listDescription)
    }

    override fun installListener() {

        imageViewBack.setOnClickListener { finish() }

        imageViewShare.setOnClickListener{
            ToastUtil.showInfo("分享")
        }

        //关注用户
        headerView.textViewFocus.setOnClickListener {
            if (data==null) return@setOnClickListener
            presenter.focusUser(data!!.uid,headerView.textViewFocus, data!!.is_follow)
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var dySum = 0
            var dp150 = DimenUtil.dp2px(150.0)
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                dySum += dy
                if (dySum <= 0) {
                    textViewTitle.text = ""
                    imageViewBack.setImageResource(R.mipmap.icon_return_white)
                    imageViewShare.setImageResource(R.mipmap.icon_share_white)
                    relativeLayoutHeader.setBackgroundResource(R.mipmap.icon_bg_goods_detail_head)
                } else if (dySum > dp150) {
                  textViewTitle.text = data?.title
                    imageViewBack.setImageResource(R.mipmap.icon_nav_back)
                    imageViewShare.setImageResource(R.mipmap.icon_click_share)
                    relativeLayoutHeader.setBackgroundColor(Util.getColor(android.R.color.white))
                }
            }
        })

        //跳转商品详情
        adapterRecommend.setOnItemClickListener { _, _, position ->
            val item = adapterRecommend.getItem(position)
            val intent = Intent(this,GoodsDetailActivity::class.java)
            intent.putExtra(GoodsDetailActivity::class.java.simpleName,item)
            startActivity(intent)
        }

        //跳转商品详情
        adapterRelateStories.setOnItemClickListener { _, _, position ->
            val item = adapterRelateStories.getItem(position) ?: return@setOnItemClickListener
            val intent = Intent(this, ArticleDetailActivity::class.java)
            intent.putExtra(ArticleDetailActivity::class.java.simpleName,item.rid)
            intent.putExtra(ArticleDetailActivity::class.java.name,item.channel_name)
            startActivity(intent)
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


    override fun setPresenter(presenter: ArticleDetailContract.Presenter?) {
        setPresenter(presenter)
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