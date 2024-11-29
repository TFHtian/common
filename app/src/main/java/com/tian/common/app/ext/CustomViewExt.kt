package com.tian.common.app.ext

import androidx.recyclerview.widget.RecyclerView
import com.hjq.toast.Toaster

/**
 * 吐司
 */
fun toast(text: CharSequence ) {
    Toaster.show(text)
}

/**
 * 绑定普通的Recyclerview
 */
fun RecyclerView.init(
    layoutManger: RecyclerView.LayoutManager,
    bindAdapter: RecyclerView.Adapter<*>,
    isScroll: Boolean = true,
): RecyclerView {
    layoutManager = layoutManger
    setHasFixedSize(true)
    adapter = bindAdapter
    isNestedScrollingEnabled = isScroll
    return this
}