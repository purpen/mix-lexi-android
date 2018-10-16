package com.thn.lexi.index.discover

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import com.basemodule.tools.*
import com.basemodule.ui.BaseActivity
import com.thn.lexi.R
import kotlinx.android.synthetic.main.acticity_artical_detail.*
import kotlinx.android.synthetic.main.header_view_article_detail.view.*

class ArticleDetailActivity : BaseActivity(), ArticleDetailContract.View {
    private val dialog: WaitingDialog by lazy { WaitingDialog(this) }
    override val layout: Int = R.layout.acticity_artical_detail
    private val presenter: ArticleDetailPresenter by lazy { ArticleDetailPresenter(this) }
    private lateinit var listDescription: ArrayList<AdapterArticleDetail.MultipleItem>
    private lateinit var adapter: AdapterArticleDetail
    private var data:ArticleDetailBean.DataBean?=null
    private var rid: String? = null
    private lateinit var headerView: View
    override fun getIntentData() {
        rid = intent.getStringExtra(TAG)
    }


    override fun initView() {
        headerView = View.inflate(this,R.layout.header_view_article_detail,null)
        headerView.textViewBrowserNum.setCompoundDrawables(Util.getDrawableWidthDimen(R.mipmap.icon_show_password,R.dimen.dp14,R.dimen.dp14),null,null,null)
        listDescription = ArrayList()
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.setHasFixedSize(false)
        recyclerView.layoutManager = linearLayoutManager
        adapter = AdapterArticleDetail(listDescription)
        recyclerView.adapter = adapter
        adapter.setHeaderView(headerView)
        adapter.setHeaderFooterEmpty(true,true)
    }

    override fun requestNet() {
        if (TextUtils.isEmpty(rid)) return
        presenter.loadData(false, rid!!)
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
    override fun setData(data: ArticleDetailBean.DataBean) {
        this.data = data
        GlideUtil.loadImageWithFading(data.cover,headerView.imageViewCover)
        headerView.textViewArticleType.text = data.channel_name
        headerView.textViewArticleTitle.text = data.title
        headerView.textViewDate.text = DateUtil.getDateByTimestamp(data.published_at,"yyyy.MM.dd")
        headerView.textViewBrowserNum.text = "${data.browse_count}"
        GlideUtil.loadCircleImageWidthDimen(data.user_avator,headerView.imageViewHeader,DimenUtil.dp2px(25.0))
        headerView.textViewName.text = data.user_name
        setUserFocusState()

        if (data.deal_content==null) return

        for (item in data.deal_content) {

            when(item.type){
                "text" ->{
                    listDescription.add(AdapterArticleDetail.MultipleItem(item, AdapterArticleDetail.MultipleItem.TEXT_ITEM_TYPE))
                }
                "image" ->{
                    listDescription.add(AdapterArticleDetail.MultipleItem(item, AdapterArticleDetail.MultipleItem.IMAGE_ITEM_TYPE))
                }
                "product" ->{
                    listDescription.add(AdapterArticleDetail.MultipleItem(item, AdapterArticleDetail.MultipleItem.PRODUCT_ITEM_TYPE))
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
}