package com.lexivip.lexi.publishShopWindow
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SimpleItemAnimator
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseFragment
import com.lexivip.lexi.GridSpacingItemDecoration
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.ProductBean
import com.lexivip.lexi.eventBusMessge.MessageRequestGoodsImages
import kotlinx.android.synthetic.main.fragment_select_goods.*
import org.greenrobot.eventbus.EventBus

class SelectGoodsFragment : BaseFragment(), SelectGoodsContract.View {
    private val dialog: WaitingDialog by lazy { WaitingDialog(activity) }

    private val adapterGrid: AdapterGridImage by lazy { AdapterGridImage(R.layout.adapter_pure_imageview) }


    override val layout: Int = R.layout.fragment_select_goods

    private var whichPage: Int = PAGE_LIKE

    private val presenter: SelectGoodsPresenter by lazy { SelectGoodsPresenter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments == null) return
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

    override fun initView() {
        recyclerView.layoutManager = GridLayoutManager(activity, 3)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapterGrid
        recyclerView.addItemDecoration(GridSpacingItemDecoration(3, DimenUtil.dp2px(1.0), false))
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
    }


    override fun loadData() {
        presenter.loadData(whichPage)
    }

    override fun setNewData(data: List<ProductBean>) {
        adapterGrid.setNewData(data)
    }


    override fun addData(products: MutableList<ProductBean>) {
        adapterGrid.addData(products)
    }

    override fun loadMoreComplete() {
        adapterGrid.loadMoreComplete()
    }


    override fun loadMoreEnd() {
        adapterGrid.loadMoreEnd()
    }

    override fun loadMoreFail() {
        adapterGrid.loadMoreFail()
    }

    override fun installListener() {
        adapterGrid.setOnLoadMoreListener({
            presenter.loadMoreData()
        }, recyclerView)

        adapterGrid.setOnItemClickListener { _, view, position ->
            val item = adapterGrid.getItem(position) ?: return@setOnItemClickListener
            EventBus.getDefault().post(MessageRequestGoodsImages(item.rid,item.store_rid))
        }
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