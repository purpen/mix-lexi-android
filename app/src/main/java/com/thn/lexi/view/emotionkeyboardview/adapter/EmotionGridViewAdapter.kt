package com.thn.lexi.view.emotionkeyboardview.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView.LayoutParams
import android.widget.BaseAdapter
import android.widget.ImageView


import com.thn.lexi.R
import com.thn.lexi.view.emotionkeyboardview.utils.EmotionUtils

/**
 * Created by zejian
 * Time  16/1/7 下午4:46
 * Email shinezejian@163.com
 * Description:
 */
class EmotionGridViewAdapter(private val context: Context, private val emotionNames: List<String>, private val itemWidth: Int, private val emotion_map_type: Int) : BaseAdapter() {

    override fun getCount(): Int {
        // +1 最后一个为删除按钮
        return emotionNames.size + 1
    }

    override fun getItem(position: Int): String {
        return emotionNames[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val iv_emotion = ImageView(context)
        // 设置内边距
        iv_emotion.setPadding(itemWidth / 8, itemWidth / 8, itemWidth / 8, itemWidth / 8)
        val params = LayoutParams(itemWidth, itemWidth)
        iv_emotion.layoutParams = params

        //判断是否为最后一个item
        if (position == count - 1) {
            iv_emotion.setImageResource(R.mipmap.compose_emotion_delete)
        } else {
            val emotionName = emotionNames[position]
            iv_emotion.setImageResource(EmotionUtils.getImgByName(emotion_map_type, emotionName))
        }

        return iv_emotion
    }

}
