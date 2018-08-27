package com.thn.lexi.view.emotionkeyboardview.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.thn.lexi.R


/**
 * Created by zejian
 * Time  16/1/7 上午10:26
 * Email shinezejian@163.com
 * Description:
 */
class Fragment1 : BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = LayoutInflater.from(activity).inflate(R.layout.fragment_one, null)
        val tv = rootView.findViewById(R.id.tv) as TextView

        tv.text = savedInstanceState?.getString("Interge")
        return rootView
    }

    companion object {
        fun newInstance(bundle: Bundle): Fragment1 {
            val fragment = Fragment1()
            fragment.arguments = bundle
            return fragment
        }
    }
}
