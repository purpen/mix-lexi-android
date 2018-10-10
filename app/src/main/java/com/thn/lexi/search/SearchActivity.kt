package com.thn.lexi.search

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.text.*
import android.util.TypedValue
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.basemodule.tools.*
import com.basemodule.ui.BaseActivity
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.acticity_search.*
import com.google.gson.reflect.TypeToken
import com.thn.lexi.*
import com.thn.lexi.beans.ProductBean
import com.thn.lexi.index.detail.GoodsDetailActivity
import com.thn.lexi.user.login.UserProfileUtil


class SearchActivity : BaseActivity(), SearchContract.View {

    private val dialog: WaitingDialog by lazy { WaitingDialog(this) }

    private val presenter: SearchPresenter by lazy { SearchPresenter(this) }

    private val adapterRecent: AdapterSearchRecentLookGoods by lazy { AdapterSearchRecentLookGoods(R.layout.adapter_editor_recommend) }
    private val adapterHotRecommendPavilion: AdapterHotRecommendPavilion by lazy { AdapterHotRecommendPavilion(R.layout.adapter_hot_recommend_pavilion) }
    private val adapterHotSearch: AdapterHotSearch by lazy { AdapterHotSearch(R.layout.adapter_hot_search) }
    private val adapterFuzzyMatch: AdapterFuzzyMatch by lazy { AdapterFuzzyMatch(R.layout.adapter_fuzzy_match_search) }

    override val layout: Int = R.layout.acticity_search

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent == null) return
        if (intent.hasExtra(TAG)) {
            val searchString = intent.getStringExtra(TAG)
            editTextSearch.setText(searchString)
            editTextSearch.setSelection(searchString.length)
        }
    }

    override fun initView() {
        editTextSearch.inputType = InputType.TYPE_TEXT_FLAG_MULTI_LINE
        initSearchHistory()
        if (UserProfileUtil.isLogin()) initRecentLookGoods()
        initHotRecommendPavilion()
        initHotSearch()
        initFuzzyWordSearch()
    }

    /**
     * 搜索记录
     */
    private fun initSearchHistory() {
        val historyList: ArrayList<String>
        val historyString = SPUtil.read(Constants.SEARCH_HISTORY)
        if (TextUtils.isEmpty(historyString)) {
            relativeLayoutSearchHistory.visibility = View.GONE
        } else {
            relativeLayoutSearchHistory.visibility = View.VISIBLE
            historyList = JsonUtil.getGson().fromJson(historyString, object : TypeToken<List<String>>() {}.type)
            val dp6 = DimenUtil.dp2px(6.0)
            val dp10 = DimenUtil.dp2px(10.0)
            val dp15 = DimenUtil.dp2px(15.0)
            val dp27 = DimenUtil.dp2px(27.0)
            val layoutParams = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, dp27)
            layoutParams.rightMargin = dp10
            layoutParams.bottomMargin = dp15
            val adapterHistory = object : TagAdapter<String>(historyList) {
                override fun getView(parent: FlowLayout, position: Int, t: String): View {
                    val textView = TextView(applicationContext)
                    textView.layoutParams = layoutParams
                    textView.setPadding(dp10, dp6, dp10, dp6)
                    textView.includeFontPadding = false
                    textView.gravity = Gravity.CENTER
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
                    textView.maxLines = 1
                    textView.maxEms = 12
                    textView.ellipsize = TextUtils.TruncateAt.END
                    textView.text = t
                    textView.setTextColor(Util.getColor(R.color.color_999))
                    textView.setBackgroundResource(R.drawable.bg_radius_round_f5f7f9)
                    return textView
                }
            }
            tagFlowLayout.adapter = adapterHistory

            //点击跳转搜索结果页
            tagFlowLayout.setOnTagClickListener { _, position, _ ->
                val item = adapterHistory.getItem(position)
                val intent = Intent(applicationContext, SearchResultActivity::class.java)
                intent.putExtra(SearchResultActivity::class.java.simpleName, item)
                startActivity(intent)
                true
            }
        }

    }

    override fun setPresenter(presenter: SearchContract.Presenter?) {
        setPresenter(presenter)
    }

    /**
     * 最近查看商品
     */
    private fun initRecentLookGoods() {
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerViewRecentGoods.setHasFixedSize(true)
        recyclerViewRecentGoods.layoutManager = linearLayoutManager
        recyclerViewRecentGoods.adapter = adapterRecent
        recyclerViewRecentGoods.addItemDecoration(RecyclerViewDivider(AppApplication.getContext(), LinearLayoutManager.HORIZONTAL, resources.getDimensionPixelSize(R.dimen.dp10), Util.getColor(android.R.color.transparent)))
    }

    /**
     * 最近查看商品
     */
    override fun setRecentLookData(products: List<ProductBean>) {
        if (products.isEmpty()) {
            linearLayoutRecentGoods.visibility = View.GONE
        } else {
            linearLayoutRecentGoods.visibility = View.VISIBLE
            adapterRecent.setNewData(products)
        }
    }

    /**
     * 初始化热门品牌馆
     */
    private fun initHotRecommendPavilion() {
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerViewHotRecommend.setHasFixedSize(true)
        recyclerViewHotRecommend.layoutManager = linearLayoutManager
        recyclerViewHotRecommend.adapter = adapterHotRecommendPavilion
        recyclerViewHotRecommend.addItemDecoration(RecyclerViewDivider(AppApplication.getContext(), LinearLayoutManager.HORIZONTAL, DimenUtil.dp2px(30.0), Util.getColor(android.R.color.transparent)))
    }

    override fun setHotRecommendPavilionData(hot_recommends: MutableList<SearchHotRecommendPavilionBean.DataBean.HotRecommendsBean>) {
        val recommendsBean = SearchHotRecommendPavilionBean.DataBean.HotRecommendsBean()
        recommendsBean.coverId = R.mipmap.icon_order_made
        recommendsBean.recommend_title = "接单订制"
        hot_recommends.add(0,recommendsBean)
        adapterHotRecommendPavilion.setNewData(hot_recommends)
    }

    /**
     * 初始化热门搜索
     */
    private fun initHotSearch(){
        val linearLayoutManager = CustomLinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerViewHotSearch.setHasFixedSize(true)
        linearLayoutManager.setScrollEnabled(false)
        recyclerViewHotSearch.layoutManager = linearLayoutManager
        recyclerViewHotSearch.adapter = adapterHotSearch
    }

    /**
     * 初始化模糊搜索
     */
    private fun initFuzzyWordSearch(){
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerViewFuzzyMatch.setHasFixedSize(true)
        recyclerViewFuzzyMatch.layoutManager = linearLayoutManager
        recyclerViewFuzzyMatch.adapter = adapterFuzzyMatch
    }

    /**
     * 设置热门搜索数据
     */
    override fun setHotSearchData(search_items: List<HotSearchBean.DataBean.SearchItemsBean>) {
        adapterHotSearch.setNewData(search_items)
    }

    override fun requestNet() {
        presenter.getUserRecentLook()
        presenter.getHotRecommendPavilion()
        presenter.getHotSearch()
    }

    /**
     * 设置模糊匹配数据
     */
    override fun setFuzzyWordListData(search_items: List<FuzzyWordMatchListBean.DataBean.SearchItemsBean>) {
        adapterFuzzyMatch.setNewData(search_items)
    }


    override fun installListener() {
        editTextSearch.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (TextUtils.isEmpty(s)) {
                    recyclerViewFuzzyMatch.visibility = View.GONE
                    return
                }

                if (!recyclerViewFuzzyMatch.isShown){
                    recyclerViewFuzzyMatch.visibility = View.VISIBLE
                }
                presenter.getFuzzyWordList(s.toString().trim())
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        //点击搜索按钮
        editTextSearch.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                val content = editTextSearch.text.toString().trim()
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.showInfo(Util.getString(R.string.hint_input_search_text))
                } else {
                    val historyList: ArrayList<String>
                    val historyString = SPUtil.read(Constants.SEARCH_HISTORY)
                    if (TextUtils.isEmpty(historyString)) {
                        historyList = ArrayList()
                        historyList.add(content)
                    } else {
                        historyList = JsonUtil.getGson().fromJson(historyString, object : TypeToken<List<String>>() {}.type)
                        if (historyList.contains(content)) {
                            historyList.remove(content)
                        }
                        historyList.add(0, content)
                    }

                    //保存最新10条搜索记录
                    if (historyList.size > 10) {

                        SPUtil.write(Constants.SEARCH_HISTORY, JsonUtil.list2Json(historyList.subList(0, 10)))
                    } else {
                        SPUtil.write(Constants.SEARCH_HISTORY, JsonUtil.list2Json(historyList))
                    }

                    //跳转搜索结果
                    val intent = Intent(applicationContext, SearchResultActivity::class.java)
                    intent.putExtra(SearchResultActivity::class.java.simpleName, content)
                    startActivity(intent)

                }
                return@setOnKeyListener true
            }

            return@setOnKeyListener false
        }


        //清空输入框
        imageViewClear.setOnClickListener {
            editTextSearch.text.clear()
        }

        textViewCancel.setOnClickListener {
            finish()
        }

        //删除历史记录
        imageViewDeleteHistory.setOnClickListener {
            SPUtil.clear(Constants.SEARCH_HISTORY)
            relativeLayoutSearchHistory.visibility = View.GONE
        }

        //最近查看商品点击
        adapterRecent.setOnItemClickListener { _, _, position ->
            val productBean = adapterRecent.getItem(position) as ProductBean
            val intent = Intent(this, GoodsDetailActivity::class.java)
            intent.putExtra(GoodsDetailActivity::class.java.simpleName, productBean)
            startActivity(intent)
        }

        /**
         * 模糊匹配条目点击
         */
        adapterFuzzyMatch.setOnItemClickListener { _, _, position ->
            val item = adapterFuzzyMatch.getItem(position)
            val intent = Intent(applicationContext, SearchResultActivity::class.java)
            intent.putExtra(FuzzyWordMatchListBean::class.java.simpleName, item)
            startActivity(intent)
        }

        /**
         * 热门搜索点击
         */
        adapterHotSearch.setOnItemClickListener { _, _, position ->
            val item = adapterHotSearch.getItem(position)
            val intent = Intent(applicationContext, SearchResultActivity::class.java)
            intent.putExtra(SearchResultActivity::class.java.simpleName, item!!.query_word)
            startActivity(intent)
        }

        /**
         * 热门推荐点击
         */
        adapterHotRecommendPavilion.setOnItemClickListener { _, _, position ->

            if (position==0){ //可定制商品列表
                ToastUtil.showInfo("跳转接单订制")
                return@setOnItemClickListener
            }

            val item = adapterHotRecommendPavilion.getItem(position)

            when(item!!.target_type){
                1 ->{ //跳转商品详情
                    val productBean = ProductBean()
                    productBean.rid = item.rid
                    productBean.is_distributed = item.is_distributed
                    productBean.is_custom_made = item.is_custom_made
                    productBean.store_rid = item.store_rid
                    val intent = Intent(this, GoodsDetailActivity::class.java)
                    intent.putExtra(GoodsDetailActivity::class.java.simpleName, productBean)
                    startActivity(intent)
                }
                2 ->{ //跳转品牌馆
//                    val intent = Intent(this, SearchResultActivity::class.java)
//                    intent.putExtra(SearchResultActivity::class.java.simpleName, item.rid)
//                    startActivity(intent)
                }
            }
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
}