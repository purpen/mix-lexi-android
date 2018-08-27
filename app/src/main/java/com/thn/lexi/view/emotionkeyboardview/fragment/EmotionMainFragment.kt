package com.thn.lexi.view.emotionkeyboardview.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.LogUtil
import com.basemodule.tools.Util

import com.thn.lexi.R
import com.thn.lexi.view.emotionkeyboardview.EmotionKeyboard
import com.thn.lexi.view.emotionkeyboardview.NoHorizontalScrollerViewPager
import com.thn.lexi.view.emotionkeyboardview.adapter.HorizontalRecyclerviewAdapter
import com.thn.lexi.view.emotionkeyboardview.adapter.NoHorizontalScrollerVPAdapter
import com.thn.lexi.view.emotionkeyboardview.model.ImageModel
import com.thn.lexi.view.emotionkeyboardview.utils.EmotionUtils
import com.thn.lexi.view.emotionkeyboardview.utils.GlobalOnItemClickManagerUtils
import com.thn.lexi.view.emotionkeyboardview.utils.SharedPreferencedUtils
import kotlinx.android.synthetic.main.fragment_main_emotion.view.*
import kotlinx.android.synthetic.main.include_emotion_bar.*

import java.util.ArrayList

/**
 * Created by zejian
 * Time  16/1/6 下午5:26
 * Email shinezejian@163.com
 * Description:表情主界面
 */
class EmotionMainFragment : BaseFragment() {
    private var CurrentPosition = 0
    //底部水平tab
    private var recyclerview_horizontal: RecyclerView? = null
    private var horizontalRecyclerviewAdapter: HorizontalRecyclerviewAdapter? = null
    //表情面板
    private lateinit var mEmotionKeyboard: EmotionKeyboard

    private lateinit var editTextComment: EditText
    private lateinit var imageViewChangeEmotion: ImageView
    private lateinit var buttonSend: Button
    private lateinit var relativeLayoutLike: RelativeLayout
    private lateinit var emotionLayout: LinearLayout


    private var showEmojiKeyBoard: Boolean = false

    //需要绑定的内容view
    private var contentView: View? = null

    //不可横向滚动的ViewPager
    private var viewPager: NoHorizontalScrollerViewPager? = null

    //是否绑定当前Bar的编辑框,默认true,即绑定。
    //false,则表示绑定contentView,此时外部提供的contentView必定也是EditText
    private var isBindToBarEditText = true

    //是否隐藏bar上的编辑框和发生按钮,默认不隐藏
    private var isHidenBarEditTextAndBtn = false

    internal var fragments: MutableList<Fragment> = ArrayList()


    /**
     * 创建与Fragment对象关联的View视图时调用
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_main_emotion, container, false)
//        isHidenBarEditTextAndBtn = savedInstanceState?.getBoolean(EmotionMainFragment.HIDE_BAR_EDITTEXT_AND_BTN)
        //获取判断绑定对象的参数
//        isBindToBarEditText = savedInstanceState?.getBoolean(EmotionMainFragment.BIND_TO_EDITTEXT)
        initView(rootView)

        mEmotionKeyboard = EmotionKeyboard.with(activity)
                .setEmotionView(emotionLayout)//绑定表情面板
                .bindToContent(contentView)//绑定内容view
                .bindToEditText(editTextComment)//判断绑定那种EditView
                .bindToEmotionButton(imageViewChangeEmotion)//绑定表情按钮
                .build()
        initListener()
        initDatas()
        //创建全局监听
        val globalOnItemClickManager = GlobalOnItemClickManagerUtils.getInstance(activity!!)

        if (isBindToBarEditText) {
            //绑定当前Bar的编辑框
            globalOnItemClickManager!!.attachToEditText(editTextComment)

        } else {
            // false,则表示绑定contentView,此时外部提供的contentView必定也是EditText
            globalOnItemClickManager!!.attachToEditText(contentView as EditText?)
            mEmotionKeyboard!!.bindToEditText(contentView as EditText?)
        }
        return rootView
    }

    /**
     * 绑定内容view
     * @param contentView
     * @return
     */
    fun bindToContentView(contentView: View) {
        this.contentView = contentView
    }

    /**
     * 初始化view控件
     */
    protected fun initView(rootView: View) {
        viewPager = rootView.findViewById<NoHorizontalScrollerViewPager>(R.id.vp_emotionview_layout)
        recyclerview_horizontal = rootView.findViewById<RecyclerView>(R.id.recyclerview_horizontal)

        emotionLayout = rootView.findViewById<LinearLayout>(R.id.ll_emotion_layout)


        editTextComment = rootView.findViewById<EditText>(R.id.editTextComment)
        buttonSend = rootView.findViewById<Button>(R.id.buttonSend)
        imageViewChangeEmotion = rootView.findViewById<ImageView>(R.id.imageViewChangeEmotion)
        relativeLayoutLike = rootView.findViewById<RelativeLayout>(R.id.relativeLayoutLike)

    }

    /**
     * 初始化监听器
     */
    protected fun initListener() {

        editTextComment.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) { //动态设置底部栏高度

                if (!TextUtils.isEmpty(s)) {
                    relativeLayoutLike.visibility = View.GONE
                    buttonSend.visibility = View.VISIBLE
                    buttonSend.isEnabled = true
                    buttonSend.setTextColor(Util.getColor(R.color.color_6ed7af))
                } else {
                    relativeLayoutLike.visibility = View.VISIBLE
                    buttonSend.visibility = View.GONE
                    buttonSend.isEnabled = false
                    buttonSend.setTextColor(Util.getColor(R.color.color_999))
                }

//                val lineCount = editTextComment.lineCount

//                var height = DimenUtil.getDimensionPixelSize(R.dimen.dp50) + editTextComment.lineHeight * (lineCount - 1)

//                val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height)

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
        })
    }

    /**
     * 数据操作,这里是测试数据，请自行更换数据
     */
    protected fun initDatas() {
        replaceFragment()
        val list = ArrayList<ImageModel>()
        for (i in fragments.indices) {
            if (i == 0) {
                val model1 = ImageModel()
                model1.icon = resources.getDrawable(R.drawable.ic_emotion)
                model1.flag = "经典笑脸"
                model1.isSelected = true
                list.add(model1)
            } else {
                val model = ImageModel()
                model.icon = resources.getDrawable(R.drawable.ic_plus)
                model.flag = "其他笑脸$i"
                model.isSelected = false
                list.add(model)
            }
        }

        //记录底部默认选中第一个
        CurrentPosition = 0
        SharedPreferencedUtils.setInteger(activity, CURRENT_POSITION_FLAG, CurrentPosition)

        //底部tab
        horizontalRecyclerviewAdapter = HorizontalRecyclerviewAdapter(activity, list)
        recyclerview_horizontal!!.setHasFixedSize(true)//使RecyclerView保持固定的大小,这样会提高RecyclerView的性能
        recyclerview_horizontal!!.adapter = horizontalRecyclerviewAdapter
        recyclerview_horizontal!!.layoutManager = GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false)
        //初始化recyclerview_horizontal监听器
        horizontalRecyclerviewAdapter!!.setOnClickItemListener(object : HorizontalRecyclerviewAdapter.OnClickItemListener {
            override fun onItemClick(view: View, position: Int, datas: List<ImageModel>) {
                //获取先前被点击tab
                val oldPosition = SharedPreferencedUtils.getInteger(activity, CURRENT_POSITION_FLAG, 0)
                //修改背景颜色的标记
                datas[oldPosition].isSelected = false
                //记录当前被选中tab下标
                CurrentPosition = position
                datas[CurrentPosition].isSelected = true
                SharedPreferencedUtils.setInteger(activity, CURRENT_POSITION_FLAG, CurrentPosition)
                //通知更新，这里我们选择性更新就行了
                horizontalRecyclerviewAdapter!!.notifyItemChanged(oldPosition)
                horizontalRecyclerviewAdapter!!.notifyItemChanged(CurrentPosition)
                //viewpager界面切换
                viewPager!!.setCurrentItem(position, false)
            }

            override fun onItemLongClick(view: View, position: Int, datas: List<ImageModel>) {}
        })


    }

    private fun replaceFragment() {
        //创建fragment的工厂类
        val factory = FragmentFactory.singleFactoryInstance
        //创建修改实例
        val f1 = factory?.getFragment(EmotionUtils.EMOTION_CLASSIC_TYPE) as EmotiomComplateFragment
        fragments.add(f1)
        var b: Bundle?
        for (i in 0..6) {
            b = Bundle()
            b.putString("Interge", "Fragment-$i")
            val fg = Fragment1.newInstance(b)
            fragments.add(fg)
        }

        val adapter = NoHorizontalScrollerVPAdapter(activity!!.supportFragmentManager, fragments)
        viewPager!!.adapter = adapter
    }

    companion object {
        fun newInstance(bundle: Bundle): EmotionMainFragment {
            val fragment = EmotionMainFragment()
            fragment.arguments = bundle
            return fragment
        }

        //是否绑定当前Bar的编辑框的flag
        val BIND_TO_EDITTEXT = "bind_to_edittext"
        //是否隐藏bar上的编辑框和发生按钮
        val HIDE_BAR_EDITTEXT_AND_BTN = "hide bar's editText and btn"

        //当前被选中底部tab
        private val CURRENT_POSITION_FLAG = "CURRENT_POSITION_FLAG"
    }


    /**
     * 是否拦截返回键操作，如果此时表情布局未隐藏，先隐藏表情布局
     * @return true则隐藏表情布局，拦截返回键操作
     * false 则不拦截返回键操作
     */
    fun isInterceptBackPress(): Boolean {
        if (mEmotionKeyboard.interceptBackPress()) {
            relativeLayoutLike.visibility = View.VISIBLE
            buttonSend.visibility = View.GONE
            return true
        } else {
            return false
        }
    }

}


