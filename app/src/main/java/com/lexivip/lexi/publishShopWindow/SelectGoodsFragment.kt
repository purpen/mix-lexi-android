package com.lexivip.lexi.publishShopWindow
import android.os.Bundle
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseFragment
import com.lexivip.lexi.R

class SelectGoodsFragment: BaseFragment(),SelectGoodsContract.View {
    private val dialog: WaitingDialog by lazy { WaitingDialog(activity) }

    private val adapterGrid:AdapterGridImage by lazy {AdapterGridImage(R.layout.adapter_pure_imageview)}

    override val layout: Int = R.layout.fragment_select_goods

    private var whichPage: Int = PAGE_LIKE

    private val presenter: SelectGoodsPresenter by lazy { SelectGoodsPresenter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments==null) return
        whichPage = arguments!!.getInt(SelectGoodsFragment::class.java.simpleName, PAGE_LIKE)
    }

    companion object {
        const val PAGE_LIKE = 0
        const val PAGE_WISH_ORDER = 1
        const val PAGE_RECENT_LOOK = 2

        @JvmStatic
        fun newInstance(whichPage: Int): SelectGoodsFragment {
            val fragment = SelectGoodsFragment()
            val bundle = Bundle()
            bundle.putInt(SelectGoodsFragment::class.java.simpleName, whichPage)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun loadData() {
        presenter.loadData(whichPage)
    }


    override fun loadMoreComplete() {
        super.loadMoreComplete()
    }


    override fun loadMoreEnd() {
        super.loadMoreEnd()
    }

    override fun loadMoreFail() {
        super.loadMoreFail()
    }

    override fun installListener() {
        presenter.loadMoreData()
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

    override fun setPresenter(presenter: SelectGoodsContract.Presenter?) {
        setPresenter(presenter)
    }
}