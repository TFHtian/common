package com.tian.lib_common.lib_widget.pagestate

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.tian.lib_common.R
import com.tian.lib_common.lib_ext.visibleOrGone
import com.zy.multistatepage.MultiState
import com.zy.multistatepage.MultiStateContainer

class BaseEmptyState : MultiState() {

    private lateinit var mContext: Context
    private lateinit var tvEmptyTip: AppCompatTextView
    private lateinit var imEmptyIcon: AppCompatImageView

    override fun onCreateView(
        context: Context,
        inflater: LayoutInflater,
        container: MultiStateContainer
    ): View {
        mContext = context
        return inflater.inflate(R.layout.layout_base_empty, container, false)
    }

    override fun onViewCreated(view: View) {
        tvEmptyTip = view.findViewById(R.id.tv_empty_tip)
        imEmptyIcon = view.findViewById(R.id.im_empty_icon)
        setEmptyTip(mContext.getString(R.string.base_no_data_text))
        setEmptyIcon(R.drawable.ic_base_empty)
    }

    fun setEmptyTip(emptyTip: String) {
        tvEmptyTip.text = emptyTip
        tvEmptyTip.visibleOrGone(emptyTip.isNotEmpty())
    }

    fun setEmptyIcon(@DrawableRes emptyIcon: Int) {
        imEmptyIcon.setImageResource(emptyIcon)
    }
}