package com.lexivip.lexi.publishShopWindow

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
import com.google.gson.reflect.TypeToken
import com.lexivip.lexi.*
import com.lexivip.lexi.eventBusMessge.MessageShopWindowTag
import kotlinx.android.synthetic.main.acticity_show_window_tags.*
import kotlinx.android.synthetic.main.header_fuzzy_match_shop_window_tags.view.*
import org.greenrobot.eventbus.EventBus

class ShopWindowTagsActivity : BaseActivity(), ShopWindowTagsContract.View {

    private val dialog: WaitingDialog by lazy { WaitingDialog(this) }

    private val presenter: ShopWindowTagsPresenter by lazy { ShopWindowTagsPresenter(this) }

    private val adapterHotTag: AdapterShopWindowHotTags by lazy { AdapterShopWindowHotTags(R.layout.adapter_shop_window_hot_tags) }
    private val adapterFuzzyMatch: AdapterFuzzyMatch by lazy { AdapterFuzzyMatch(R.layout.adapter_fuzzy_match_tags) }
    private val searchHeaderView: View by lazy { View.inflate(this, R.layout.header_fuzzy_match_shop_window_tags, null) }
    override val layout: Int = R.layout.acticity_show_window_tags

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent == null) return
        if (intent.hasExtra(TAG)) {
            val searchString = intent.getStringExtra(TAG)
            editTextSearchTag.setText(searchString)
            editTextSearchTag.setSelection(searchString.length)
        }
    }

    override fun initView() {
        customHeadView.setHeadCenterTxtShow(true, R.string.title_add_tag)
        editTextSearchTag.inputType = InputType.TYPE_TEXT_FLAG_MULTI_LINE
        initSearchHistory()
        initHotTags()
        initFuzzyWordSearch()
    }

    private fun initHotTags() {
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerViewHotTags.setHasFixedSize(true)
        recyclerViewHotTags.layoutManager = linearLayoutManager
        recyclerViewHotTags.adapter = adapterHotTag
    }

    /**
     * 添加标签记录
     */
    private fun initSearchHistory() {
        val historyList: ArrayList<String>
        val historyString = SPUtil.read(Constants.ADD_TAG_HISTORY)
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
                private val color333 = Util.getColor(R.color.color_333)
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
                    textView.text = "# $t"
                    textView.setTextColor(color333)
                    textView.setBackgroundResource(R.drawable.bg_radius_round_f5f7f9)
                    return textView
                }
            }
            tagFlowLayout.adapter = adapterHistory

            //点击返回编辑橱窗，添加被点击标签
            tagFlowLayout.setOnTagClickListener { _, position, _ ->
                val item = adapterHistory.getItem(position)
                sendAddTagMessage(item)
                true
            }
        }

    }

    override fun setPresenter(presenter: ShopWindowTagsContract.Presenter?) {
        setPresenter(presenter)
    }


    /**
     * 初始化模糊搜索
     */
    private fun initFuzzyWordSearch() {
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerViewFuzzyMatch.setHasFixedSize(true)
        recyclerViewFuzzyMatch.layoutManager = linearLayoutManager
        recyclerViewFuzzyMatch.adapter = adapterFuzzyMatch
        val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DimenUtil.dp2px(64.0))
        searchHeaderView.layoutParams = params
        adapterFuzzyMatch.setHeaderView(searchHeaderView)
    }


    override fun requestNet() {
        presenter.getHotShopWindowTags()
    }

    /**
     * 设置热门橱窗标签
     */
    override fun setHotTags(keywords: List<HotShopWindowTagsBean.DataBean.KeywordsBean>) {
        adapterHotTag.setNewData(keywords)
    }

    /**
     * 设置模糊匹配数据
     */
    override fun setFuzzyWordListData(search_items: List<FuzzyMatchTagsBean.DataBean.KeywordsBean>) {
        adapterFuzzyMatch.setNewData(search_items)
        setFuzzyMatchHeader()
    }

    /**
     * 设置更多模糊匹配数据
     */
    override fun setMoreFuzzyWordListData(keywords: List<FuzzyMatchTagsBean.DataBean.KeywordsBean>) {
        adapterFuzzyMatch.addData(keywords)
        setFuzzyMatchHeader()
    }

    /**
     * 设置搜索标签的请求头
     */
    private fun setFuzzyMatchHeader() {
        val searchString = editTextSearchTag.text.trim().toString()
        if (TextUtils.isEmpty(searchString)) return
        val list = adapterFuzzyMatch.data
        var showHeader = true
        for (item in list) {
            if (TextUtils.equals(item.name, searchString)) {
                showHeader = false
                break
            }
        }
        if (showHeader) {
            searchHeaderView.visibility = View.VISIBLE
            searchHeaderView.textView.text = "# $searchString"
        } else {
            searchHeaderView.visibility = View.GONE
        }
    }


    override fun loadMoreComplete() {
        adapterFuzzyMatch.loadMoreComplete()
    }

    override fun loadMoreEnd() {
        adapterFuzzyMatch.loadMoreEnd()
    }

    /**
     * 添加标签成功
     */
    override fun setAddTagSuccess(searchString: String) {
        val historyList: ArrayList<String>
        val historyString = SPUtil.read(Constants.ADD_TAG_HISTORY)
        if (TextUtils.isEmpty(historyString)) {
            historyList = ArrayList()
            historyList.add(searchString)
        } else {
            historyList = JsonUtil.getGson().fromJson(historyString, object : TypeToken<List<String>>() {}.type)
            if (historyList.contains(searchString)) {
                historyList.remove(searchString)
            }
            historyList.add(0, searchString)
        }

        //保存最新10条搜索记录
        if (historyList.size > 10) {
            SPUtil.write(Constants.ADD_TAG_HISTORY, JsonUtil.list2Json(historyList.subList(0, 10)))
        } else {
            SPUtil.write(Constants.ADD_TAG_HISTORY, JsonUtil.list2Json(historyList))
        }
    }

    override fun installListener() {

        searchHeaderView.textViewAddTag.setOnClickListener {
            //添加标签
            val searchString = editTextSearchTag.text.trim().toString()
            if (TextUtils.isEmpty(searchString)) return@setOnClickListener
            presenter.addShopWindowTag(searchString)
        }

        adapterFuzzyMatch.setOnLoadMoreListener({
            presenter.getMoreFuzzyWordList()
        }, recyclerViewFuzzyMatch)

        editTextSearchTag.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (TextUtils.isEmpty(s)) {
                    recyclerViewFuzzyMatch.visibility = View.GONE
                    imageViewClearInput.visibility = View.GONE
                    return
                }
                imageViewClearInput.visibility = View.VISIBLE
                if (!recyclerViewFuzzyMatch.isShown) {
                    recyclerViewFuzzyMatch.visibility = View.VISIBLE
                }
                presenter.getFuzzyWordList(s.toString().trim())
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })


        //清空输入框
        imageViewClearInput.setOnClickListener {
            editTextSearchTag.text.clear()
            imageViewClearInput.visibility = View.GONE
        }


        //删除历史记录
        imageViewDeleteHistory.setOnClickListener {
            SPUtil.clear(Constants.ADD_TAG_HISTORY)
            relativeLayoutSearchHistory.visibility = View.GONE
        }


        /**
         * 模糊匹配条目点击
         */
        adapterFuzzyMatch.setOnItemClickListener { _, _, position ->
            val item = adapterFuzzyMatch.getItem(position)?:return@setOnItemClickListener
            sendAddTagMessage(item.name)
        }

        /**
         * 热门搜索点击
         */
        adapterHotTag.setOnItemClickListener { _, _, position ->
            val item = adapterHotTag.getItem(position) ?: return@setOnItemClickListener
            sendAddTagMessage(item.name)
        }
    }

    /**
     * 添加选择标签标签
     */
    private fun sendAddTagMessage(tag: String) {
        EventBus.getDefault().post(MessageShopWindowTag(tag))
        finish()
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