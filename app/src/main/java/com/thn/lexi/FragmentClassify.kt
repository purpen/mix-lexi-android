package com.thn.lexi

import com.basemodule.ui.BaseFragment

class FragmentClassify:BaseFragment() {
    override val layout: Int = R.layout.fragment_charactoristic

    companion object {
        @JvmStatic fun newInstance(): FragmentClassify = FragmentClassify()
    }

}