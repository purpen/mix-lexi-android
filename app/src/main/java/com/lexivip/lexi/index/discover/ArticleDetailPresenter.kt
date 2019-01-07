package com.lexivip.lexi.index.discover

import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import com.basemodule.tools.Constants
import com.basemodule.tools.LogUtil
import com.basemodule.tools.ToastUtil
import com.lexivip.lexi.JsonUtil
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.CommentBean
import com.lexivip.lexi.discoverLifeAesthetics.CommentSuccessBean
import com.lexivip.lexi.discoverLifeAesthetics.ShowWindowCommentListBean
import com.lexivip.lexi.discoverLifeAesthetics.SubCommentsBean
import com.lexivip.lexi.discoverLifeAesthetics.UserFocusState
import com.lexivip.lexi.index.explore.editorRecommend.EditorRecommendBean
import com.lexivip.lexi.net.NetStatusBean
import org.json.JSONObject
import java.io.IOException

class ArticleDetailPresenter(view: ArticleDetailContract.View) : ArticleDetailContract.Presenter {

    private var view: ArticleDetailContract.View = checkNotNull(view)

    private val dataSource: ArticleDetailModel by lazy { ArticleDetailModel() }

    private var rid: String = ""

    private var page:Int = 1

    override fun loadData(isRefresh: Boolean, rid: String) {
        this.rid = rid
        dataSource.loadData(rid, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                if (!isRefresh) view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val response = JSONObject(json)
                val isSuccess = response.optBoolean("success")
                val status = response.optJSONObject("status")
                val data = response.optJSONObject("data")
                if (isSuccess) {
                    view.setData(data)
                } else {
                    view.showError(status.getString("message"))
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 关注用户
     */
    override fun focusUser(uid: String, v: View, isFollow: Boolean) {
        dataSource.focusUser(uid, isFollow, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                v.isEnabled = false
            }

            override fun onSuccess(json: String) {
                v.isEnabled = true
                val userFocusState = JsonUtil.fromJson(json, UserFocusState::class.java)
                if (userFocusState.success) {
                    view.setFocusState(userFocusState.data.followed_status)
                } else {
                    view.showError(userFocusState.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                v.isEnabled = true
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 加载相关故事
     */
    override fun getRelateStories(rid: String) {
        dataSource.getRelateStories(rid, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val storyListBean = JsonUtil.fromJson(json, StoryListBean::class.java)
                if (storyListBean.success) {
                    view.setRelateStoriesData(storyListBean.data.life_records)
                } else {
                    view.showError(storyListBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 获取推荐商品
     */
    override fun getRecommendProducts(rid: String) {
        dataSource.getRecommendProducts(rid, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val editorRecommendBean = JsonUtil.fromJson(json, EditorRecommendBean::class.java)
                if (editorRecommendBean.success) {
                    view.setRecommendProductsData(editorRecommendBean.data.products)
                } else {
                    view.showError(editorRecommendBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 关注/取消品牌馆
     */
    override fun focusBrandPavilion(store_rid: String, isFavorite: Boolean, v: View, isHeaderPavilion: Boolean) {
        dataSource.focusBrandPavilion(store_rid, isFavorite, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                v.isEnabled = false
                if (isHeaderPavilion) {
                    view.setHeadPavilionFocusState(isFavorite)
                } else {
                    view.setBrandPavilionFocusState(isFavorite)
                }
            }

            override fun onSuccess(json: String) {
                v.isEnabled = true
                val netStatusBean = JsonUtil.fromJson(json, NetStatusBean::class.java)
                if (netStatusBean.success) {
//                    view.setBrandPavilionFocusState(isFavorite)
                } else {
                    view.showError(netStatusBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                v.isEnabled = true
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }


    /**
     * 获取3条文章的评论
     */
    override fun getArticleComments(rid: String) {
        val pageSize = "3"
        dataSource.getArticleComments(page,rid,pageSize,object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val commentListBean = JsonUtil.fromJson(json, ShowWindowCommentListBean::class.java)
                if (commentListBean.success) {
                    view.setCommentListData(commentListBean.data)
                } else {
                    view.showError(commentListBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 获取文章的评论
     */
    override fun getArticleComments(rid: String,isRefresh: Boolean) {
        this.rid = rid
        if (isRefresh) page = 1
        dataSource.getArticleComments(page,rid,Constants.PAGE_SIZE,object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val commentListBean = JsonUtil.fromJson(json, ShowWindowCommentListBean::class.java)
                if (commentListBean.success) {
                    view.setCommentListData(commentListBean.data)
                    page++
                } else {
                    view.showError(commentListBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 获取文章的评论
     */
    override fun loadMoreArticleComments() {
        dataSource.getArticleComments(page,rid,Constants.PAGE_SIZE,object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val showWindowCommentBean = JsonUtil.fromJson(json, ShowWindowCommentListBean::class.java)
                if (showWindowCommentBean.success) {
                    val comments = showWindowCommentBean.data.comments
                    if (comments.isEmpty()) {
                        view.loadMoreEnd()
                    } else {
                        view.loadMoreComplete()
                        view.addData(comments)
                        ++page
                    }
                } else {
                    view.loadMoreFail()
                    view.showError(showWindowCommentBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }



    /**
     * 对评论点赞
     */
    override fun praiseComment(comment_id: String, isPraise: Boolean, position: Int, view1: View, isSubAdapter: Boolean) {
        dataSource.praiseComment(comment_id, isPraise, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view1.isEnabled = false
            }

            override fun onSuccess(json: String) {
                view1.isEnabled = true
                val favoriteBean = JsonUtil.fromJson(json, NetStatusBean::class.java)
                if (favoriteBean.success) {
                    view.setPraiseCommentState(!isPraise, position, isSubAdapter)
                } else {
                    view.showError(favoriteBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view1.isEnabled = true
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 对子评论点赞
     */
    override fun praiseComment(view1: View, commentBean: CommentBean, adapter: ArticleSubCommentListAdapter) {
        dataSource.praiseComment(commentBean.comment_id, commentBean.is_praise, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view1.isEnabled = false
            }

            override fun onSuccess(json: String) {
                view1.isEnabled = true
                val favoriteBean = JsonUtil.fromJson(json, NetStatusBean::class.java)
                if (favoriteBean.success) {
                    if (commentBean.is_praise) {
                        commentBean.is_praise = false
                        commentBean.praise_count -= 1
                    } else {
                        commentBean.is_praise = true
                        commentBean.praise_count += 1
                    }
                    adapter.notifyDataSetChanged()
                } else {
                    view.showError(favoriteBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view1.isEnabled = true
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }




    /**
     * 加载子评论
     */
    fun loadMoreSubComments(item: CommentBean, view1: View, articleCommentListAdapter: ArticleCommentListAdapter) {
        dataSource.loadMoreSubComments(item.subCommentPage, item.comment_id, object : IDataSource.HttpRequestCallBack {

            override fun onStart() {
                view1.isEnabled = false
            }

            override fun onSuccess(json: String) {
                view1.isEnabled = true
                val subCommentsBean = JsonUtil.fromJson(json, SubCommentsBean::class.java)
                if (subCommentsBean.success) {
                    val sub_comments = item.sub_comments
                    if (item.subCommentPage == 1) sub_comments.clear()
                    if (subCommentsBean.data != null && subCommentsBean.data.comments != null) sub_comments.addAll(subCommentsBean.data.comments)
                    item.subCommentPage++
                    articleCommentListAdapter.notifyDataSetChanged()
                } else {
                    view.showError(subCommentsBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view1.isEnabled = true
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }


    /**
     * 提交评论
     */
    override fun submitComment(rid: String,pid:String, replyId: String, content: String, sendButton: Button) {
        dataSource.submitComment(rid, pid,replyId, content, object : IDataSource.HttpRequestCallBack {

            override fun onStart() {
                sendButton.isEnabled = false
            }

            override fun onSuccess(json: String) {
                sendButton.isEnabled = true
                val commentSuccessBean = JsonUtil.fromJson(json, CommentSuccessBean::class.java)
                if (commentSuccessBean.success) {
                    view.noticeCommentSuccess(commentSuccessBean.data)
                    ToastUtil.showSuccess("发送评论成功")
                } else {
                    view.showError(commentSuccessBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                sendButton.isEnabled = true
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 点赞文章
     */
    override fun praiseArticle(rid: String,isPraise: Boolean, view1: View) {
        dataSource.praiseArticle(rid,isPraise,object : IDataSource.HttpRequestCallBack {

            override fun onStart() {
                view1.isEnabled = false
            }

            override fun onSuccess(json: String) {
                view1.isEnabled = true
                val netStatusBean = JsonUtil.fromJson(json, NetStatusBean::class.java)
                if (netStatusBean.success) {
                    view.praiseArticleSuccess(!isPraise)
                } else {
                    view.showError(netStatusBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view1.isEnabled = true
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

}