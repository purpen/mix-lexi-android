package com.thn.lexi.search

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.text.InputType
import android.text.TextUtils
import android.util.TypedValue
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.basemodule.tools.*
import com.basemodule.ui.BaseActivity
import com.thn.lexi.R
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.acticity_search.*
import com.google.gson.reflect.TypeToken
import com.thn.lexi.AppApplication
import com.thn.lexi.JsonUtil
import com.thn.lexi.RecyclerViewDivider
import com.thn.lexi.beans.ProductBean
import com.thn.lexi.index.detail.GoodsDetailActivity


class SearchActivity : BaseActivity(), SearchContract.View {

    private val dialog: WaitingDialog by lazy { WaitingDialog(this) }

    private val presenter: SearchPresenter by lazy { SearchPresenter(this) }

    private val adapterRecent: AdapterSearchRecentLookGoods by lazy { AdapterSearchRecentLookGoods(R.layout.adapter_editor_recommend) }

    override val layout: Int = R.layout.acticity_search

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent==null) return
        if (intent.hasExtra(TAG)){
            val searchString = intent.getStringExtra(TAG)
            editTextSearch.setText(searchString)
            editTextSearch.setSelection(searchString.length)
        }
    }

    override fun initView() {
        editTextSearch.inputType = InputType.TYPE_TEXT_FLAG_MULTI_LINE
        initSearchHistory()
        initRecentLookGoods()
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

    override fun setRecentLookData(products: List<ProductBean>) {
        adapterRecent.setNewData(products)
    }

    override fun requestNet() {
        presenter.getUserRecentLook()
    }

    override fun installListener() {
        //点击搜索
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
                    if (historyList.size>10){

                        SPUtil.write(Constants.SEARCH_HISTORY, JsonUtil.list2Json(historyList.subList(0,10)))
                    }else{
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
            relativeSearch.visibility = View.GONE
        }

        //最近查看商品点击
        adapterRecent.setOnItemClickListener { _, _, position ->
            val productBean = adapterRecent.getItem(position) as ProductBean
            val intent = Intent(this, GoodsDetailActivity::class.java)
            intent.putExtra(GoodsDetailActivity::class.java.simpleName, productBean)
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
}