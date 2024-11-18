package com.tian.common.app.feature.simplemvvm

import androidx.lifecycle.MutableLiveData
import com.tian.common.app.data.model.bean.ArticleResponse
import com.tian.common.app.data.model.bean.BannerResponse
import com.tian.common.app.data.repository.HttpRequestCoroutine
import com.tian.common.app.network.apiService
import com.tian.common.app.network.state.ListDataUiState
import com.tian.lib_common.lib_base.mvvm.viewmodel.BaseViewModel
import com.tian.lib_common.lib_ext.request
import com.tian.lib_common.lib_network.state.LoadingType
import com.tian.lib_common.lib_network.state.ResultState

class HomeViewModel: BaseViewModel() {

    //页码 首页数据页码从0开始
    var pageNo = 0

    //首页文章列表数据
    var homeDataState: MutableLiveData<ListDataUiState<ArticleResponse>> = MutableLiveData()

    //首页轮播图数据
    var bannerData: MutableLiveData<ResultState<ArrayList<BannerResponse>>> = MutableLiveData()

    /**
     * 获取首页文章列表数据
     * @param isRefresh 是否是刷新，即第一页
     */
    fun getHomeData(isRefresh: Boolean) {
        if (isRefresh) {
            pageNo = 0
        }
        request({ HttpRequestCoroutine.getHomeData(pageNo) }, {
            //请求成功
            pageNo++
            val listDataUiState =
                ListDataUiState(
                    isSuccess = true,
                    isRefresh = isRefresh,
                    isEmpty = it.isEmpty(),
                    hasMore = it.hasMore(),
                    isFirstEmpty = isRefresh && it.isEmpty(),
                    listData = it.datas
                )
            homeDataState.value = listDataUiState
        }, {
            //请求失败
            val listDataUiState =
                ListDataUiState(
                    isSuccess = false,
                    errMessage = it.errorMsg,
                    isRefresh = isRefresh,
                    listData = arrayListOf<ArticleResponse>()
                )
            homeDataState.value = listDataUiState
        }, isShowDialog = true, loadingType = LoadingType.INIT_LOADING)
    }

    /**
     * 获取轮播图数据
     */
    fun getBannerData() {
        request({ apiService.getBanner() }, bannerData)
    }
}