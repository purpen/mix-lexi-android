package com.basemodule.ui
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView

import com.basemodule.tools.LogUtil



abstract class BaseFragment : Fragment() {
    protected var TAG: String = javaClass.simpleName

    protected abstract val layout: Int
    private lateinit var contentView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        LogUtil.e("onCreate()$TAG")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        contentView = inflater.inflate(layout, null)
        return contentView
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        loadData()
        installListener()
    }

    open fun initView() {

    }


    open fun installListener() {}

    override fun onStart() {
        super.onStart()
    }

//    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
//        if (isVisibleToUser){
//            GlideUtil.resumeRequests()
//        }else{
//            GlideUtil.pauseRequests()
//        }
//        super.setUserVisibleHint(isVisibleToUser)
//    }
//
//    override fun onHiddenChanged(hidden: Boolean) {
//        if (hidden){
//            GlideUtil.pauseRequests()
//        }else{
//            GlideUtil.resumeRequests()
//        }
//        super.onHiddenChanged(hidden)
//    }

    override fun onStop() {
        super.onStop()
    }


    override fun onDestroy() {
        super.onDestroy()
        unbindDrawables(contentView) // <---This should be the ID of this fragments (ScreenSlidePageFragment) layout
    }


    private fun unbindDrawables(view: View) {
        if (view.background != null) {
            view.background.callback = null
        }
        if (view is ViewGroup && view !is AdapterView<*>) {
            for (i in 0 until view.childCount) {
                unbindDrawables(view.getChildAt(i))
            }
            view.removeAllViews()
        }
    }

    override fun onDetach() {
        super.onDetach()
    }

    open fun loadData() {

    }
}
