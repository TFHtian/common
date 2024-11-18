package com.tian.common.app.feature.simplemvp

import com.tian.common.app.data.model.bean.ArticleResponse
import com.tian.common.app.network.response.ApiPagerResponse
import com.tian.common.app.network.response.ApiResponse
import com.tian.lib_common.lib_base.view.BaseView
import io.reactivex.rxjava3.core.Observable

interface HomeMvpContract {

    interface View : BaseView {

        fun notifyChangeArticleList(articleList: ArrayList<ArticleResponse>)
    }

    interface Presenter {

        fun getHomeArticleList(pageNo: Int)
    }

    interface Model  {

        fun getHomeArticleList(pageNo: Int): Observable<ApiResponse<ApiPagerResponse<ArrayList<ArticleResponse>>>>
    }
}