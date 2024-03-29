package com.lexivip.lexi.search
import android.content.Intent
import android.support.v4.view.ViewPager
import android.text.InputType
import android.text.TextUtils
import com.basemodule.ui.BaseActivity
import com.basemodule.ui.BaseFragment
import com.basemodule.ui.CustomFragmentPagerAdapter
import com.lexivip.lexi.R
import kotlinx.android.synthetic.main.acticity_search_result.*


class SearchResultActivity : BaseActivity(){

    private lateinit var adapter:CustomFragmentPagerAdapter

    override val layout: Int = R.layout.acticity_search_result

    private lateinit var searchString:String

    private var page:Int = -1

    override fun getIntentData() {
        if(intent.hasExtra(TAG)){
            searchString = intent.getStringExtra(TAG)
        }
        if (intent.hasExtra(FuzzyWordMatchListBean::class.java.simpleName)){
            val searchItemsBean = intent.getParcelableExtra<FuzzyWordMatchListBean.DataBean.SearchItemsBean>(FuzzyWordMatchListBean::class.java.simpleName)
            searchString = searchItemsBean.name
            page = searchItemsBean.target_type
        }
    }

    override fun initView() {
        customHeadView.setHeadSearchShow(true)
        customHeadView.editTextSearch.isFocusable = false
        customHeadView.editTextSearch.isFocusableInTouchMode = false
        customHeadView.editTextSearch.maxEms =15
        customHeadView.editTextSearch.maxLines =1
        customHeadView.editTextSearch.ellipsize = TextUtils.TruncateAt.END
        if (!TextUtils.isEmpty(searchString)) {
            customHeadView.editTextSearch.setText(searchString)
            customHeadView.editTextSearch.setSelection(searchString.length)
        }
        customHeadView.editTextSearch.inputType = InputType.TYPE_TEXT_FLAG_MULTI_LINE
        setUpViewPager()
    }

    private fun setUpViewPager() {
        val titles = resources.getStringArray(R.array.strings_search_titles)
        val fragments = ArrayList<BaseFragment>()
        fragments.add(FragmentSearchGoods.newInstance(searchString))
        fragments.add(FragmentSearchBrandPavilion.newInstance(searchString))
        fragments.add(FragmentSearchUserList.newInstance(searchString))

        adapter = CustomFragmentPagerAdapter(supportFragmentManager, fragments, titles.toMutableList())
        customViewPager.adapter = adapter
        customViewPager.offscreenPageLimit = fragments.size
        customViewPager.setPagingEnabled(false)
        slidingTabLayout.setViewPager(customViewPager)
        slidingTabLayout.getTitleView(0).textSize = 20f
        when(page){ //默认就是商品
            2->{ //品牌馆
                slidingTabLayout.setCurrentTab(1,true)
            }

            3->{ //用户
                slidingTabLayout.setCurrentTab(2,true)
            }
        }
    }


    override fun installListener() {

        customHeadView.editTextSearch.setOnClickListener {
            val intent = Intent(this,SearchActivity::class.java)
            intent.putExtra(SearchActivity::class.java.simpleName,searchString)
            startActivity(intent)
        }

        customViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                val count = customViewPager.childCount
                for (i in 0 until count) {
                    if (i == position) {
                        slidingTabLayout.getTitleView(i).textSize = 20f
                    } else {
                        slidingTabLayout.getTitleView(i).textSize = 17f
                    }
                }

            }

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }
        })
    }

}
