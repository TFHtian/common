package com.tian.lib_common.lib_widget.titlebar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.tian.lib_common.R
import com.tian.lib_common.databinding.LayoutCommonTitleBarBinding
import com.tian.lib_common.lib_ext.clickNoRepeat

class CommonTitleBar: LinearLayout {

    private lateinit var mContext: Context
    private var mBind: LayoutCommonTitleBarBinding? = null
    private var titleBarListener: CommonTitleBarListener? = null

    constructor(
        context: Context
    ) : super(context) {
        init(context)
    }

    constructor(
        context: Context, attrs: AttributeSet?
    ) : super(context, attrs) {
        init(context)
    }

    private fun init(context: Context) {
        mContext = context
        mBind = LayoutCommonTitleBarBinding.inflate(LayoutInflater.from(context), this, true)
        initData()
    }

    /**
     * 初始化数据
     */
    private fun initData() {
        setBarBgColor(mContext.getColor(R.color.base_theme))
        setBackIcon(R.drawable.ic_base_back)
        mBind?.run {
            barLayoutBack.clickNoRepeat{
                titleBarListener?.startBackClick()
            }
            barTvEnd.clickNoRepeat{
                titleBarListener?.endTvClick()
            }
            barIvEnd.clickNoRepeat{
                titleBarListener?.endIvClick()
            }
        }
    }

    /**
     * 设置背景
     */
    fun setBarBgColor(color: Int) {
        mBind?.run {
            barLayout.setBackgroundColor(color)
        }
    }

    /**
     * 设置返回图标
     */
    fun setBackIcon(iconResId: Int) {
        mBind?.run {
            barIvBack.setImageResource(iconResId)
        }
    }

    /**
     * 设置title文案
     */
    fun setBarTitle(title: String) {
        mBind?.run {
            barTvTitle.text = title
        }
    }

    /**
     * 设置title字体颜色
     */
    fun setBarTitleColor(color: Int) {
        mBind?.run {
            barTvTitle.setTextColor(color)
        }
    }

    /**
     * 设置title字体大小
     */
    fun setBarTitleSize(size: Float) {
        mBind?.run {
            barTvTitle.textSize = size
        }
    }

    /**
     * 设置end文本
     */
    /**
     * 设置title文案
     */
    fun setBarTvEnd(endText: String) {
        mBind?.run {
            barTvEnd.visibility = VISIBLE
            barTvEnd.text = endText
        }
    }

    /**
     * 设置title字体颜色
     */
    fun setBarTvEndColor(color: Int) {
        mBind?.run {
            barTvEnd.setTextColor(color)
        }
    }

    /**
     * 设置title字体大小
     */
    fun setBarTvEndSize(size: Float) {
        mBind?.run {
            barTvEnd.textSize = size
        }
    }

    /**
     * 设置end图标
     */
    fun setBarEndIcon(iconResId: Int) {
        mBind?.run {
            barIvEnd.visibility = VISIBLE
            barIvEnd.setImageResource(iconResId)
        }
    }

    /**
     * 设置事件回调监听
     */
    fun setTitleBarListener(titleBarListener: CommonTitleBarListener) {
        this.titleBarListener = titleBarListener
    }

}