package com.lexivip.lexi.view.emotionkeyboardview.fragment

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.LinearLayout
import com.lexivip.lexi.AppApplication

import com.lexivip.lexi.R
import com.lexivip.lexi.view.emotionkeyboardview.EmojiIndicatorView
import com.lexivip.lexi.view.emotionkeyboardview.adapter.EmotionGridViewAdapter
import com.lexivip.lexi.view.emotionkeyboardview.adapter.EmotionPagerAdapter
import com.lexivip.lexi.view.emotionkeyboardview.utils.DisplayUtils
import com.lexivip.lexi.view.emotionkeyboardview.utils.EmotionUtils
import com.lexivip.lexi.view.emotionkeyboardview.utils.GlobalOnItemClickManagerUtils

import java.util.ArrayList

/**
 * Created by zejian
 * Time  16/1/5 下午4:32
 * Email shinezejian@163.com
 * Description:可替换的模板表情，gridview实现
 */
class EmotiomComplateFragment : BaseFragment() {
    private var emotionPagerGvAdapter: EmotionPagerAdapter? = null
    private var vp_complate_emotion_layout: ViewPager? = null
    private var ll_point_group: EmojiIndicatorView? = null//表情面板对应的点列表
    private var emotion_map_type: Int = 0


    companion object {
        fun newInstance(bundle: Bundle): EmotiomComplateFragment {
            val fragment = EmotiomComplateFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    /**
     * 创建与Fragment对象关联的View视图时调用
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_complate_emotion, container, false)

        initView(rootView)
        initListener()
        return rootView
    }



    /**
     * 初始化view控件
     */
    protected fun initView(rootView: View) {
        vp_complate_emotion_layout = rootView.findViewById<View>(R.id.vp_complate_emotion_layout) as ViewPager
        ll_point_group = rootView.findViewById<View>(R.id.ll_point_group) as EmojiIndicatorView
        //获取map的类型
        emotion_map_type = arguments!!.getInt(FragmentFactory.EMOTION_MAP_TYPE)
        initEmotion()
    }

    /**
     * 初始化监听器
     */
    protected fun initListener() {

        vp_complate_emotion_layout!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            internal var oldPagerPos = 0
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                ll_point_group!!.playByStartPointToNext(oldPagerPos, position)
                oldPagerPos = position
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

    /**
     * 初始化表情面板
     * 思路：获取表情的总数，按每行存放7个表情，动态计算出每个表情所占的宽度大小（包含间距），
     * 而每个表情的高与宽应该是相等的，这里我们约定只存放3行
     * 每个面板最多存放7*3=21个表情，再减去一个删除键，即每个面板包含20个表情
     * 根据表情总数，循环创建多个容量为20的List，存放表情，对于大小不满20进行特殊
     * 处理即可。
     */
    private fun initEmotion() {
        // 获取屏幕宽度
        val screenWidth = DisplayUtils.getScreenWidthPixels(activity!!)
        // item的间距
        val spacing = DisplayUtils.dp2px(activity!!, 12f)
        // 动态计算item的宽度和高度
        val itemWidth = (screenWidth - spacing * 8) / 7
        //动态计算gridview的总高度
        val gvHeight = itemWidth * 3 + spacing * 6

        val emotionViews = ArrayList<GridView>()
        var emotionNames: MutableList<String> = ArrayList()
        // 遍历所有的表情的key
        for (emojiName in EmotionUtils.getEmojiMap(emotion_map_type)!!.keys) {
            emotionNames.add(emojiName)
            // 每20个表情作为一组,同时添加到ViewPager对应的view集合中
            if (emotionNames.size == 20) {
                val gv = createEmotionGridView(emotionNames, screenWidth, spacing, itemWidth, gvHeight)
                emotionViews.add(gv)
                // 添加完一组表情,重新创建一个表情名字集合
                emotionNames = ArrayList()
            }
        }

        // 判断最后是否有不足20个表情的剩余情况
        if (emotionNames.size > 0) {
            val gv = createEmotionGridView(emotionNames, screenWidth, spacing, itemWidth, gvHeight)
            emotionViews.add(gv)
        }

        //初始化指示器
        ll_point_group!!.initIndicator(emotionViews.size)
        // 将多个GridView添加显示到ViewPager中
        emotionPagerGvAdapter = EmotionPagerAdapter(emotionViews)
        vp_complate_emotion_layout!!.adapter = emotionPagerGvAdapter
        val params = LinearLayout.LayoutParams(screenWidth, gvHeight)
        vp_complate_emotion_layout!!.layoutParams = params


    }

    /**
     * 创建显示表情的GridView
     */
    private fun createEmotionGridView(emotionNames: List<String>, gvWidth: Int, padding: Int, itemWidth: Int, gvHeight: Int): GridView {
        // 创建GridView
        val gv = GridView(activity)
        //设置点击背景透明
        gv.setSelector(android.R.color.transparent)
        //设置7列
        gv.numColumns = 7
        gv.setPadding(padding, padding, padding, padding)
        gv.horizontalSpacing = padding
        gv.verticalSpacing = padding * 2
        //设置GridView的宽高
        val params = ViewGroup.LayoutParams(gvWidth, gvHeight)
        gv.layoutParams = params
        // 给GridView设置表情图片
        val adapter = EmotionGridViewAdapter(AppApplication.getContext(), emotionNames, itemWidth, emotion_map_type)
        gv.adapter = adapter
        //设置全局点击事件
        gv.onItemClickListener = GlobalOnItemClickManagerUtils.getInstance(AppApplication.getContext())?.getOnItemClickListener(emotion_map_type)
        return gv
    }


}
