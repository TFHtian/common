package com.tian.lib_common.lib_widget.pagestate

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatTextView
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.ThreeBounce
import com.tian.lib_common.R
import com.tian.lib_common.lib_ext.visibleOrGone
import com.zy.multistatepage.MultiState
import com.zy.multistatepage.MultiStateContainer

class BaseLoadingState : MultiState(){

    private lateinit var mContext: Context
    private lateinit var progressLoad: ProgressBar
    private lateinit var tvLoadingTip: AppCompatTextView

    override fun onCreateView(
        context: Context,
        inflater: LayoutInflater,
        container: MultiStateContainer
    ): View {
        mContext = context
        return inflater.inflate(R.layout.layout_base_loading, container, false)
    }

    override fun onViewCreated(view: View) {
        progressLoad = view.findViewById(R.id.progress_load)
        tvLoadingTip = view.findViewById(R.id.tv_loading_tip)
        val threeBounce: Sprite = ThreeBounce()
        threeBounce.color = mContext.getColor(R.color.base_theme)
        progressLoad.indeterminateDrawable = threeBounce
        setLoadingTip(mContext.getString(R.string.base_load_init_text))
    }

    fun setLoadingTip(loadingMsg: String) {
        tvLoadingTip.text = loadingMsg
        tvLoadingTip.visibleOrGone(loadingMsg.isNotEmpty())
    }
}