package com.thn.lexi
import android.view.View
import com.basemodule.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_main2.*
import kotlinx.android.synthetic.main.view_custom_headview.view.*

class MainFragment2 : BaseFragment() {
    companion object {
        fun newInstance(): MainFragment2 {
            return MainFragment2()
        }
    }

    override val layout: Int = R.layout.fragment_main2

    override fun initView() {
        customHeadView.head_goback.visibility = View.GONE
        customHeadView.setHeadCenterTxtShow(true,R.string.title_inbox)
    }

}