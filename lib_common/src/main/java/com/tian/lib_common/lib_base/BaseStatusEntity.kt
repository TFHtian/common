package com.tian.lib_common.lib_base

import com.tian.lib_common.R

data class BaseStatusEntity(
    var isShowLoading: Boolean = false,
    var loadTip: String = "请求中…",
    var emptyTip: String = "暂无数据",
    var emptyIconId: Int = R.drawable.ic_base_empty,
    var errorTip: String = "加载失败",
    var errorRetry: String = "重试",
    var errorIconId: Int = R.drawable.ic_base_error
)
