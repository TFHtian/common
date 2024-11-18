package com.tian.lib_common.lib_widget.loading

import android.content.Context
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatTextView
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.Circle
import com.lxj.xpopup.core.CenterPopupView
import com.tian.lib_common.R
import com.tian.lib_common.lib_ext.visibleOrGone

class LoadingDialog: CenterPopupView {

    private var mContext: Context
    private var loadTip: String = ""
    private lateinit var progress: ProgressBar
    private lateinit var tvLoadTip: AppCompatTextView

    constructor(
        context: Context
    ) : super(context) {
        mContext = context;
    }

    constructor(
        context: Context, loadTip: String = "请求中…"
    ) : super(context) {
        mContext = context
        this.loadTip = loadTip;
    }

    override fun getImplLayoutId(): Int {
        return R.layout.dialog_loading
    }

    override fun onCreate() {
        super.onCreate()
        progress = findViewById(R.id.progress_load)
        tvLoadTip = findViewById(R.id.tv_loading_tip)
        tvLoadTip.text = loadTip
        tvLoadTip.visibleOrGone(loadTip.isNotEmpty())
        val circleLoad: Sprite = Circle()
        circleLoad.color = mContext.getColor(R.color.base_white)
        progress.indeterminateDrawable = circleLoad
    }
}