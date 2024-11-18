package com.tian.common.app.feature.simplemvvm

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.tian.common.R
import com.tian.common.app.ext.init
import com.tian.common.app.feature.adapter.ArticleAdapter
import com.tian.common.app.weight.SpaceItemDecoration
import com.tian.common.databinding.ActivityHomeMvvmBinding
import com.tian.lib_common.lib_base.mvvm.activity.BaseVmDbActivity
import com.tian.lib_common.lib_ext.dp2px
import com.tian.lib_common.lib_ext.parseState
import com.tian.lib_common.lib_widget.titlebar.CommonTitleBar

class HomeMvvmActivity : BaseVmDbActivity<HomeViewModel, ActivityHomeMvvmBinding>() {

    //适配器
    private val articleAdapter: ArticleAdapter by lazy { ArticleAdapter(arrayListOf()) }

    override fun onBindLayout(): Int = R.layout.activity_home_mvvm

    override fun attachTitleBarView(): CommonTitleBar = mBinding.appbar.titleBar

    override fun attachStatePageView(): View = mBinding.refreshLayout

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        setBarTitle(getString(R.string.mvvm))
        // 初始化列表
        mBinding.rvContent.init(LinearLayoutManager(this), articleAdapter).apply {
            addItemDecoration(SpaceItemDecoration(0, dp2px(8), true))
        }
    }

    override fun initListener() {
        articleAdapter.run {
            setCollectClick { item, v ->

            }
            setOnItemClickListener { adapter, view, position ->

            }
        }
    }

    override fun initData() {
        mViewModel.getHomeData(true)
        mViewModel.getBannerData()
    }

    override fun createObserver() {
        mViewModel.run {
            homeDataState.observe(this@HomeMvvmActivity) {
                articleAdapter.submitList(it.listData)
            }
            bannerData.observe(this@HomeMvvmActivity) { resultState ->
                parseState(resultState, { data ->

                })
            }
        }
    }
}