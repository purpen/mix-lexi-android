package com.thn.lexi.view.emotionkeyboardview.utils

import android.content.Context
import android.view.KeyEvent
import android.view.View
import android.widget.AdapterView
import android.widget.EditText

import com.thn.lexi.view.emotionkeyboardview.adapter.EmotionGridViewAdapter


/**
 * Created by zejian
 * Time  16/1/8 下午5:05
 * Email shinezejian@163.com
 * Description:点击表情的全局监听管理类
 */
class GlobalOnItemClickManagerUtils {

    private var mEditText: EditText? = null//输入框

    fun attachToEditText(editText: EditText?) {
        mEditText = editText
    }

    fun getOnItemClickListener(emotion_map_type: Int?): AdapterView.OnItemClickListener {
        return AdapterView.OnItemClickListener { parent, view, position, id ->
            val itemAdapter = parent.adapter

            if (itemAdapter is EmotionGridViewAdapter) {
                // 点击的是表情

                if (position == itemAdapter.count - 1) {
                    // 如果点击了最后一个回退按钮,则调用删除键事件
                    mEditText!!.dispatchKeyEvent(KeyEvent(
                            KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL))
                } else {
                    // 如果点击了表情,则添加到输入框中
                    val emotionName = itemAdapter.getItem(position)

                    // 获取当前光标位置,在指定位置上添加表情图片文本
                    val curPosition = mEditText!!.selectionStart
                    val sb = StringBuilder(mEditText!!.text.toString())
                    sb.insert(curPosition, emotionName)

                    // 特殊文字处理,将表情等转换一下
                    mEditText!!.setText(SpanStringUtils.getEmotionContent(emotion_map_type,
                            mContext!!, mEditText, sb.toString()))

                    // 将光标设置到新增完表情的右侧
                    mEditText!!.setSelection(curPosition + emotionName.length)
                }

            }
        }
    }

    companion object {

        private var instance: GlobalOnItemClickManagerUtils? = null
        private var mContext: Context? = null

        fun getInstance(context: Context): GlobalOnItemClickManagerUtils? {
            mContext = context
            if (instance == null) {
                synchronized(GlobalOnItemClickManagerUtils::class.java) {
                    if (instance == null) {
                        instance = GlobalOnItemClickManagerUtils()
                    }
                }
            }
            return instance
        }
    }

}
