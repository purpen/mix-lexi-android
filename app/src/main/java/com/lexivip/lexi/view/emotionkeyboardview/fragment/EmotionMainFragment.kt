package com.lexivip.lexi.view.emotionkeyboardview.fragment

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
import com.basemodule.tools.ScreenUtil
import com.basemodule.tools.Util
import com.lexivip.lexi.R
import com.lexivip.lexi.discoverLifeAesthetics.IOnFavoriteClickListener
import com.lexivip.lexi.discoverLifeAesthetics.IOnSendCommentListener
import com.lexivip.lexi.view.emotionkeyboardview.EmotionKeyboard
import com.lexivip.lexi.view.emotionkeyboardview.NoHorizontalScrollerViewPager
import com.lexivip.lexi.view.emotionkeyboardview.adapter.HorizontalRecyclerviewAdapter
import com.lexivip.lexi.view.emotionkeyboardview.adapter.NoHorizontalScrollerVPAdapter
import com.lexivip.lexi.view.emotionkeyboardview.model.ImageModel
import com.lexivip.lexi.view.emotionkeyboardview.utils.EmotionUtils
import com.lexivip.lexi.view.emotionkeyboardview.utils.GlobalOnItemClickManagerUtils
import com.lexivip.lexi.view.emotionkeyboardview.utils.SharedPreferencedUtils
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
    private lateinit var imageViewLike: ImageView
    private lateinit var textViewLikeCount: TextView

    private var onSendCommentListener: IOnSendCommentListener? = null
    private var onFavoriteClickListener: IOnFavoriteClickListener? = null

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

    fun setOnSendCommentListener(listener: IOnSendCommentListener) {
        this.onSendCommentListener = listener
    }

    fun setOnFavoriteClickListener(listener: IOnFavoriteClickListener) {
        this.onFavoriteClickListener = listener
    }

    /**
     * 创建与Fragment对象关联的View视图时调用
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_main_emotion, container, false)
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
            mEmotionKeyboard.bindToEditText(contentView as EditText?)
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


    private lateinit var inputBar: LinearLayout

    /**
     * 初始化view控件
     */
    protected fun initView(rootView: View) {
        viewPager = rootView.findViewById(R.id.vp_emotionview_layout)
        recyclerview_horizontal = rootView.findViewById(R.id.recyclerview_horizontal)

        emotionLayout = rootView.findViewById(R.id.ll_emotion_layout)
        inputBar = rootView.findViewById(R.id.include_emotion_view)

        editTextComment = rootView.findViewById(R.id.editTextComment)
        val editTextW = ScreenUtil.getScreenWidth()*274/375
        val params = LinearLayout.LayoutParams(editTextW,LinearLayout.LayoutParams.WRAP_CONTENT)
        params.leftMargin = DimenUtil.dp2px(15.0)
        params.topMargin = DimenUtil.dp2px(8.0)
        params.bottomMargin = DimenUtil.dp2px(8.0)
        editTextComment.layoutParams = params
        buttonSend = rootView.findViewById(R.id.buttonSend)
        imageViewChangeEmotion = rootView.findViewById(R.id.imageViewChangeEmotion)
        relativeLayoutLike = rootView.findViewById(R.id.relativeLayoutLike)
        imageViewLike = rootView.findViewById(R.id.imageViewLike)
        textViewLikeCount = rootView.findViewById(R.id.textViewLikeCount)
        setFavoriteData()
    }

    /**
     * 判断用户输入是否为空
     */
    fun isUserInputEmpty(): Boolean {
        return editTextComment.text.isEmpty()
    }

    /**
     * 初始化监听器
     */
    protected fun initListener() {

        val intArray = IntArray(2)

        editTextComment.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            editTextComment.getLocationOnScreen(intArray)
            if (intArray[1] > ScreenUtil.getScreenHeight() * 2 / 3) { //键盘被关闭
                if (editTextComment.text.isEmpty()) {
                    relativeLayoutLike.visibility = View.VISIBLE
                    buttonSend.visibility = View.GONE
                }
            } else { //键盘打开
                relativeLayoutLike.visibility = View.GONE
                buttonSend.visibility = View.VISIBLE
            }
//            LogUtil.e("editTextComment y===="+intArray[1]+"ScreenUtil.getScreenHeight()*2/3==="+ScreenUtil.getScreenHeight()*2/3)
        }


        editTextComment.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) { //动态设置底部栏高度

                if (!TextUtils.isEmpty(s)) {
//                    relativeLayoutLike.visibility = View.GONE
//                    buttonSend.visibility = View.VISIBLE
                    buttonSend.isEnabled = true
                    buttonSend.setTextColor(Util.getColor(R.color.color_6ed7af))
                } else {
//                    relativeLayoutLike.visibility = View.VISIBLE
//                    buttonSend.visibility = View.GONE
                    buttonSend.isEnabled = false
                    buttonSend.setTextColor(Util.getColor(R.color.color_999))
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
        })

        //发布评论
        buttonSend.setOnClickListener { view ->
            if (onSendCommentListener == null) return@setOnClickListener
            onSendCommentListener!!.onSend(buttonSend, editTextComment)
        }

        //喜欢点击
        imageViewLike.setOnClickListener {
            if (onFavoriteClickListener == null) return@setOnClickListener
            onFavoriteClickListener!!.onClick(imageViewLike, textViewLikeCount)
        }
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

        //喜欢数量
        const val LIKE_COUNTS = "LIKE_COUNTS"
        //是否喜欢
        const val IS_LIKE = "IS_LIKE"
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

    fun setEditTextHint(text: String) {
        editTextComment.hint = text
    }

    /**
     * 设置喜欢数据
     */
    private fun setFavoriteData() {
        if (arguments == null) return
        val isLike = arguments!!.getBoolean(IS_LIKE, false)
        if (isLike) {
            imageViewLike!!.setImageResource(R.mipmap.icon_click_favorite_selected)
        } else {
            imageViewLike!!.setImageResource(R.mipmap.icon_click_favorite_normal)
        }
        val likeCount = arguments!!.getInt(LIKE_COUNTS, 0)
        if (likeCount == 0) {
            textViewLikeCount!!.text = ""
        } else {
            textViewLikeCount!!.text = "$likeCount"
        }
    }


    /**
     * 打开键盘
     */
    fun showKeyBoard() {
        mEmotionKeyboard.showKeyBorad()
    }

    /**
     * 关闭键盘
     */
    fun hideKeyBoard(){
        mEmotionKeyboard.hideKeyBoard()
    }

    /**
     * 重置底部输入栏状态
     */
    fun resetInputBarState() {
        setEditTextHint(getString(R.string.text_add_comment))
        relativeLayoutLike.visibility = View.VISIBLE
        buttonSend.visibility = View.GONE
    }
}


