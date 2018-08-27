package com.thn.lexi.view.emotionkeyboardview.fragment

import android.os.Bundle
import android.support.v4.app.Fragment

/**
 * Created by zejian
 * Time  16/1/7 上午11:40
 * Email shinezejian@163.com
 * Description:产生fragment的工厂类
 */
class FragmentFactory private constructor() {

    /**
     * 获取fragment的方法
     * @param emotionType 表情类型，用于判断使用哪个map集合的表情
     */
    fun getFragment(emotionType: Int): Fragment {
        val bundle = Bundle()

        bundle.putInt(FragmentFactory.EMOTION_MAP_TYPE, emotionType)

        return EmotiomComplateFragment.newInstance(bundle)
    }

    companion object {

        val EMOTION_MAP_TYPE = "EMOTION_MAP_TYPE"
        private var factory: FragmentFactory? = null

        /**
         * 双重检查锁定，获取工厂单例对象
         * @return
         */
        val singleFactoryInstance: FragmentFactory?
            get() {
                if (factory == null) {
                    synchronized(FragmentFactory::class.java) {
                        if (factory == null) {
                            factory = FragmentFactory()
                        }
                    }
                }
                return factory
            }
    }

}
