package com.tian.common.app.feature.simplemvp

import android.content.Context
import com.tian.common.app.data.model.bean.ArticleResponse
import com.tian.common.app.network.apiService
import com.tian.common.app.network.response.ApiPagerResponse
import com.tian.common.app.network.response.ApiResponse
import com.tian.lib_common.lib_base.mvp.model.BaseModel
import io.reactivex.rxjava3.core.Observable

class HomeMvpModel(context: Context): BaseModel(context), HomeMvpContract.Model {

    override fun getHomeArticleList(pageNo: Int): Observable<ApiResponse<ApiPagerResponse<ArrayList<ArticleResponse>>>> {
        return apiService.getHomeArticleList(pageNo)
    }
}