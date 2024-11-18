package com.tian.common.app.feature.simplemvp

import android.content.Context
import com.tian.lib_common.lib_base.mvp.presenter.BasePresenter
import com.tian.common.app.ext.executeResult
import com.tian.lib_common.lib_network.state.LoadingType

class HomeMvpPresenter(context: Context): BasePresenter<HomeMvpModel, HomeMvpContract.View>(context),HomeMvpContract.Presenter  {

    override fun initModel(): HomeMvpModel {
        return HomeMvpModel(mContext)
    }

    override fun getHomeArticleList(pageNo: Int) {
        mModel?.getHomeArticleList(pageNo)?.executeResult({
            it?.let { pageResult->
                mView?.notifyChangeArticleList(pageResult.datas)
            }
        }, {
            addDisposable(it)
        }, view = mView, isShowDialog = true, loadingType = LoadingType.INIT_LOADING)
    }
}