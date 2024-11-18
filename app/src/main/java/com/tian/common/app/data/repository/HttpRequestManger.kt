package com.tian.common.app.data.repository

import com.tian.common.app.network.response.ApiPagerResponse
import com.tian.common.app.network.response.ApiResponse
import com.tian.common.app.data.model.bean.ArticleResponse
import com.tian.common.app.data.model.bean.HomePageData
import com.tian.common.app.data.model.bean.UserInfo
import com.tian.common.app.network.apiService
import com.tian.common.app.util.CacheUtil
import com.tian.lib_common.lib_network.AppException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

/**
 *  处理协程的请求类
 */
val HttpRequestCoroutine: HttpRequestManger by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
    HttpRequestManger()
}

class HttpRequestManger {
    /**
     * 获取首页文章数据
     */
    suspend fun getHomeData(pageNo: Int): ApiResponse<ApiPagerResponse<ArrayList<ArticleResponse>>> {
        //同时异步请求2个接口，请求完成后合并数据
        return withContext(Dispatchers.IO) {
            // 发起请求
            val listDataDeferred = async { apiService.getArticleList(pageNo) }
            val topDataDeferred = if (CacheUtil.isNeedTop() && pageNo == 0) {
                async { apiService.getTopArticleList() }
            } else {
                null
            }

            // 等待结果
            val listData = listDataDeferred.await()
            val topData = topDataDeferred?.await()

            // 如果有置顶数据，将置顶文章加到列表开头
            if (topData != null) {
                listData.data.datas.addAll(0, topData.data)
            }

            return@withContext listData
        }
    }

    suspend fun getMergeHomeData(pageNo: Int): ApiResponse<HomePageData> {
        // 重组新结构
        // 切换到 IO 线程池，执行异步请求
        return withContext(Dispatchers.IO) {
            // 发起请求
            val listDataDeferred = async { apiService.getArticleList(pageNo) }
            val topDataDeferred = if (CacheUtil.isNeedTop() && pageNo == 0) {
                async { apiService.getTopArticleList() }
            } else {
                null
            }

            // 等待结果
            val listData = listDataDeferred.await()
            val topData = topDataDeferred?.await()

            // 创建合并后的数据结构
            val topArticles = topData?.data ?: arrayListOf()  // 如果没有置顶数据，返回空数组
            val articles = listData.data.datas  // 文章列表

            // 返回合并后的结构
            return@withContext ApiResponse(0,"",HomePageData(topArticles, articles))
        }
    }

    /**
     * 注册并登陆
     */
    suspend fun register(username: String, password: String): ApiResponse<UserInfo> {
        val registerData = apiService.register(username, password, password)
        //判断注册结果 注册成功，调用登录接口
        if (registerData.isSuccess()) {
            return apiService.login(username, password)
        } else {
            //抛出错误异常
            throw AppException(registerData.errorCode, registerData.errorMsg)
        }
    }

    /**
     * 获取项目标题数据
     */
    suspend fun getProjectData(
        pageNo: Int,
        cid: Int = 0,
        isNew: Boolean = false
    ): ApiResponse<ApiPagerResponse<ArrayList<ArticleResponse>>> {
        return if (isNew) {
            apiService.getProjectNewData(pageNo)
        } else {
            apiService.getProjectDataByType(pageNo, cid)
        }
    }
}