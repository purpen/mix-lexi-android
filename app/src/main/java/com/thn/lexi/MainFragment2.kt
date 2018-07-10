package com.thn.lexi

import android.view.View
import com.basemodule.ui.BaseFragment
import com.thn.lexi.message.MessageContract
import com.thn.lexi.message.MessagePresenter
import kotlinx.android.synthetic.main.fragment_main2.*
import kotlinx.android.synthetic.main.view_custom_headview.view.*

class MainFragment2 : BaseFragment(), MessageContract.View {
    private var page: Int = 1
    private lateinit var presenter: MessageContract.Presenter

    companion object {
        fun newInstance(): MainFragment2 {
            return MainFragment2()
        }
    }

    override val layout: Int = R.layout.fragment_main2

    override fun setPresenter(presenter: MessageContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun initView() {
        presenter = MessagePresenter(this)
        customHeadView.head_goback.visibility = View.GONE
        customHeadView.setHeadCenterTxtShow(true, R.string.title_inbox)
    }

    override fun loadData() {
        presenter.loadData(page, "")
    }


    override fun showLoadingView() {

    }

    override fun dismissLoadingView() {

    }

    override fun showError(string: String) {

    }

    override fun goPage() {

    }

}