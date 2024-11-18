package com.tian.common.app.feature.simplemvp

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.tian.common.R
import com.tian.common.app.data.model.bean.ArticleResponse
import com.tian.common.app.ext.init
import com.tian.common.app.feature.adapter.ArticleAdapter
import com.tian.common.app.weight.SpaceItemDecoration
import com.tian.common.databinding.ActivityHomeMvpBinding
import com.tian.lib_common.lib_base.mvp.activity.BaseVpDbActivity
import com.tian.lib_common.lib_ext.dp2px
import com.tian.lib_common.lib_widget.titlebar.CommonTitleBar

class HomeMvpActivity: BaseVpDbActivity<HomeMvpModel, HomeMvpContract.View, HomeMvpPresenter,ActivityHomeMvpBinding>()
        , HomeMvpContract.View {

    private var pageNo = 0
    //适配器
    private val articleAdapter: ArticleAdapter by lazy { ArticleAdapter(arrayListOf()) }

    override fun onBindLayout(): Int = R.layout.activity_home_mvp

    override fun attachTitleBarView(): CommonTitleBar = mBinding.appbar.titleBar

    override fun attachStatePageView(): View = mBinding.refreshLayout

    override fun initPresenter(): HomeMvpPresenter = HomeMvpPresenter(this)

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        setBarTitle(getString(R.string.mvp))
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
        mPresenter?.getHomeArticleList(pageNo)
    }

    override fun notifyChangeArticleList(articleList: ArrayList<ArticleResponse>) {
        articleAdapter.submitList(articleList)
    }
}