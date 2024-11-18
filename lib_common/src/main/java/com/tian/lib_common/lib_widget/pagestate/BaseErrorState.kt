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

class BaseErrorState : MultiState() {

    private lateinit var mContext: Context
    private lateinit var tvErrorTip: AppCompatTextView
    private lateinit var tvErrorRetry: AppCompatTextView
    private lateinit var imErrorIcon: AppCompatImageView
    private var retry: OnRetryClickListener? = null

    override fun onCreateView(
        context: Context,
        inflater: LayoutInflater,
        container: MultiStateContainer
    ): View {
        mContext = context
        return inflater.inflate(R.layout.layout_base_error, container, false)
    }

    override fun onViewCreated(view: View) {
        tvErrorTip = view.findViewById(R.id.tv_error_tip)
        tvErrorRetry = view.findViewById(R.id.tv_error_retry)
        imErrorIcon = view.findViewById(R.id.im_error_icon)
        setErrorTip(mContext.getString(R.string.base_request_error_text))
        setErrorRetry(mContext.getString(R.string.base_request_retry_text))
        setErrorIcon(R.drawable.ic_base_error)
        tvErrorRetry.setOnClickListener { retry?.retry() }
    }

    fun setErrorTip(errorTip: String) {
        tvErrorTip.text = errorTip
        tvErrorTip.visibleOrGone(errorTip.isNotEmpty())
    }

    fun setErrorRetry(errorRetry: String) {
        tvErrorRetry.text = errorRetry
        tvErrorRetry.visibleOrGone(errorRetry.isNotEmpty())
    }

    fun setErrorIcon(@DrawableRes errorIcon: Int) {
        imErrorIcon.setImageResource(errorIcon)
    }

    fun retry(retry: OnRetryClickListener) {
        this.retry = retry
    }

    fun interface OnRetryClickListener {

        fun retry()
    }
}