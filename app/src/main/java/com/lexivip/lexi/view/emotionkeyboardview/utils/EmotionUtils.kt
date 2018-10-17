package com.lexivip.lexi.view.emotionkeyboardview.utils

import android.support.v4.util.ArrayMap

import com.lexivip.lexi.R


/**
 * @author : zejian
 * @time : 2016年1月5日 上午11:32:33
 * @email : shinezejian@163.com
 * @description :表情加载类,可自己添加多种表情，分别建立不同的map存放和不同的标志符即可
 */
object EmotionUtils {

    /**
     * 表情类型标志符
     */
    val EMOTION_CLASSIC_TYPE = 0x0001//经典表情

    /**
     * key-表情文字;
     * value-表情图片资源
     */
    var EMPTY_MAP: ArrayMap<String, Int>
    var EMOTION_CLASSIC_MAP: ArrayMap<String, Int>


    init {
        EMPTY_MAP = ArrayMap()
        EMOTION_CLASSIC_MAP = ArrayMap()

        EMOTION_CLASSIC_MAP["[呵呵]"] = R.mipmap.d_hehe
        EMOTION_CLASSIC_MAP["[嘻嘻]"] = R.mipmap.d_xixi
        EMOTION_CLASSIC_MAP["[哈哈]"] = R.mipmap.d_haha
        EMOTION_CLASSIC_MAP["[爱你]"] = R.mipmap.d_aini
        EMOTION_CLASSIC_MAP["[挖鼻屎]"] = R.mipmap.d_wabishi
        EMOTION_CLASSIC_MAP["[吃惊]"] = R.mipmap.d_chijing
        EMOTION_CLASSIC_MAP["[晕]"] = R.mipmap.d_yun
        EMOTION_CLASSIC_MAP["[泪]"] = R.mipmap.d_lei
        EMOTION_CLASSIC_MAP["[馋嘴]"] = R.mipmap.d_chanzui
        EMOTION_CLASSIC_MAP["[抓狂]"] = R.mipmap.d_zhuakuang
        EMOTION_CLASSIC_MAP["[哼]"] = R.mipmap.d_heng
        EMOTION_CLASSIC_MAP["[可爱]"] = R.mipmap.d_keai
        EMOTION_CLASSIC_MAP["[怒]"] = R.mipmap.d_nu
        EMOTION_CLASSIC_MAP["[汗]"] = R.mipmap.d_han
        EMOTION_CLASSIC_MAP["[害羞]"] = R.mipmap.d_haixiu
        EMOTION_CLASSIC_MAP["[睡觉]"] = R.mipmap.d_shuijiao
        EMOTION_CLASSIC_MAP["[钱]"] = R.mipmap.d_qian
        EMOTION_CLASSIC_MAP["[偷笑]"] = R.mipmap.d_touxiao
        EMOTION_CLASSIC_MAP["[笑cry]"] = R.mipmap.d_xiaoku
        EMOTION_CLASSIC_MAP["[doge]"] = R.mipmap.d_doge
        EMOTION_CLASSIC_MAP["[喵喵]"] = R.mipmap.d_miao
        EMOTION_CLASSIC_MAP["[酷]"] = R.mipmap.d_ku
        EMOTION_CLASSIC_MAP["[衰]"] = R.mipmap.d_shuai
        EMOTION_CLASSIC_MAP["[闭嘴]"] = R.mipmap.d_bizui
        EMOTION_CLASSIC_MAP["[鄙视]"] = R.mipmap.d_bishi
        EMOTION_CLASSIC_MAP["[花心]"] = R.mipmap.d_huaxin
        EMOTION_CLASSIC_MAP["[鼓掌]"] = R.mipmap.d_guzhang
        EMOTION_CLASSIC_MAP["[悲伤]"] = R.mipmap.d_beishang
        EMOTION_CLASSIC_MAP["[思考]"] = R.mipmap.d_sikao
        EMOTION_CLASSIC_MAP["[生病]"] = R.mipmap.d_shengbing
        EMOTION_CLASSIC_MAP["[亲亲]"] = R.mipmap.d_qinqin
        EMOTION_CLASSIC_MAP["[怒骂]"] = R.mipmap.d_numa
        EMOTION_CLASSIC_MAP["[太开心]"] = R.mipmap.d_taikaixin
        EMOTION_CLASSIC_MAP["[懒得理你]"] = R.mipmap.d_landelini
        EMOTION_CLASSIC_MAP["[右哼哼]"] = R.mipmap.d_youhengheng
        EMOTION_CLASSIC_MAP["[左哼哼]"] = R.mipmap.d_zuohengheng
        EMOTION_CLASSIC_MAP["[嘘]"] = R.mipmap.d_xu
        EMOTION_CLASSIC_MAP["[委屈]"] = R.mipmap.d_weiqu
        EMOTION_CLASSIC_MAP["[吐]"] = R.mipmap.d_tu
        EMOTION_CLASSIC_MAP["[可怜]"] = R.mipmap.d_kelian
        EMOTION_CLASSIC_MAP["[打哈气]"] = R.mipmap.d_dahaqi
        EMOTION_CLASSIC_MAP["[挤眼]"] = R.mipmap.d_jiyan
        EMOTION_CLASSIC_MAP["[失望]"] = R.mipmap.d_shiwang
        EMOTION_CLASSIC_MAP["[顶]"] = R.mipmap.d_ding
        EMOTION_CLASSIC_MAP["[疑问]"] = R.mipmap.d_yiwen
        EMOTION_CLASSIC_MAP["[困]"] = R.mipmap.d_kun
        EMOTION_CLASSIC_MAP["[感冒]"] = R.mipmap.d_ganmao
        EMOTION_CLASSIC_MAP["[拜拜]"] = R.mipmap.d_baibai
        EMOTION_CLASSIC_MAP["[黑线]"] = R.mipmap.d_heixian
        EMOTION_CLASSIC_MAP["[阴险]"] = R.mipmap.d_yinxian
        EMOTION_CLASSIC_MAP["[打脸]"] = R.mipmap.d_dalian
        EMOTION_CLASSIC_MAP["[傻眼]"] = R.mipmap.d_shayan
        EMOTION_CLASSIC_MAP["[猪头]"] = R.mipmap.d_zhutou
        EMOTION_CLASSIC_MAP["[熊猫]"] = R.mipmap.d_xiongmao
        EMOTION_CLASSIC_MAP["[兔子]"] = R.mipmap.d_tuzi
    }

    /**
     * 根据名称获取当前表情图标R值
     * @param EmotionType 表情类型标志符
     * @param imgName 名称
     * @return
     */
    fun getImgByName(EmotionType: Int?, imgName: String): Int {
        var integer: Int? = null
        when (EmotionType) {
            EMOTION_CLASSIC_TYPE -> integer = EMOTION_CLASSIC_MAP[imgName]
            else -> LogUtils.e("the emojiMap is null!! Handle Yourself ")
        }
        return integer ?: -1
    }

    /**
     * 根据类型获取表情数据
     * @param EmotionType
     * @return
     */
    fun getEmojiMap(EmotionType: Int?): ArrayMap<String, Int>? {
        var EmojiMap: ArrayMap<*, *>? = null
        when (EmotionType) {
            EMOTION_CLASSIC_TYPE -> EmojiMap = EMOTION_CLASSIC_MAP
            else -> EmojiMap = EMPTY_MAP
        }
        return EmojiMap
    }
}
