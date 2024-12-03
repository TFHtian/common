package com.tian.common.app.ext

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import com.bumptech.glide.load.Transformation
import com.tian.common.app.feature.imageloader.core.ImageOptions
import com.tian.common.app.feature.imageloader.core.ImageOptions.DrawableOptions.Companion.DEFAULT
import com.tian.common.app.feature.imageloader.core.OnImageListener
import com.tian.common.app.feature.imageloader.glide.ImageLoader
import com.tian.common.app.feature.imageloader.glide.progress.OnProgressListener

fun ImageView.loadImage(
    context: Context,
    @RawRes @DrawableRes drawableId: Int,
    @RawRes @DrawableRes errorId: Int = drawableId,
) {
    this.loadImage(with = context, load = drawableId, placeHolderResId = drawableId, errorResId = errorId)
}

fun ImageView.loadImage(
    context: Context,
    url: String?,
    @RawRes @DrawableRes placeHolder: Int = DEFAULT.placeHolderResId,
    @RawRes @DrawableRes errorId: Int = placeHolder,
    requestListener: (OnImageListener.() -> Unit)? = null,
) {
    this.loadImage(with = context, load = url, placeHolderResId = placeHolder, errorResId = errorId,
        requestListener = requestListener)
}

fun ImageView.loadProgressImage(
    context: Context,
    url: String?,
    @RawRes @DrawableRes placeHolder: Int = DEFAULT.placeHolderResId,
    progressListener: OnProgressListener? = null,
) {
    this.loadImage(with = context, load = url, placeHolderResId = placeHolder, errorResId = placeHolder,
        onProgressListener = progressListener)
}

fun ImageView.loadResizeImage(
    context: Context,
    url: String?,
    width: Int,
    height: Int,
    @RawRes @DrawableRes placeHolder: Int = DEFAULT.placeHolderResId,
) {
    this.loadImage(with = context, load = url, placeHolderResId = placeHolder, errorResId = placeHolder,
        size = ImageOptions.OverrideSize(width, height))
}

fun ImageView.loadGrayImage(
    context: Context,
    url: String?,
    @DrawableRes placeHolder: Int = DEFAULT.placeHolderResId,
) {
    this.loadImage(with = context, load = url,
        placeHolderResId = placeHolder,
        errorResId = placeHolder,
        isGray = true)
}

fun ImageView.loadBlurImage(
    context: Context,
    url: String?,
    radius: Int = 25,
    sampling: Int = 4,
    @DrawableRes placeHolder: Int = DEFAULT.placeHolderResId,
) {
    this.loadImage(with = context, load = url, placeHolderResId = placeHolder, errorResId = placeHolder,
        isBlur = true, blurRadius = radius, blurSampling = sampling)
}

fun ImageView.loadRoundCornerImage(
    context: Context,
    url: String?,
    radius: Int = 0,
    type: ImageOptions.CornerType = ImageOptions.CornerType.ALL,
    @DrawableRes placeHolder: Int = DEFAULT.placeHolderResId,
) {
    this.loadImage(with = context, load = url, placeHolderResId = placeHolder, errorResId = placeHolder,
        isRoundedCorners = radius > 0, roundRadius = radius, cornerType = type)
}

fun ImageView.loadCircleImage(
    context: Context,
    url: String?,
    borderWidth: Int = 0,
    @ColorInt borderColor: Int = 0,
    @DrawableRes placeHolder: Int = DEFAULT.placeHolderResId,
) {
    this.loadImage(with = context, load = url, placeHolderResId = placeHolder, errorResId = placeHolder,
        isCircle = true, borderWidth = borderWidth, borderColor = borderColor)
}

fun ImageView.loadBorderImage(
    context: Context,
    url: String?,
    borderWidth: Int = 0,
    @ColorInt borderColor: Int = 0,
    @DrawableRes placeHolder: Int = DEFAULT.placeHolderResId,
) {
    this.loadImage(with = context, load = url, placeHolderResId = placeHolder, errorResId = placeHolder,
        borderWidth = borderWidth, borderColor = borderColor)
}

/**模仿 coil**/
fun ImageView.load(load: Any?, options: (ImageOptions.() -> Unit)? = null) {
    val imageOptions = ImageOptions().also {
        it.res = load
        it.imageView = this
    }
    ImageLoader.loadImage(options?.let {
        imageOptions.also(options)
    } ?: imageOptions)
}

@JvmOverloads
fun ImageView.loadImage(
    with: Context,
    load: Any?,
    //占位图 错误图
    @DrawableRes placeHolderResId: Int = DEFAULT.placeHolderResId,
    placeHolderDrawable: Drawable? = DEFAULT.placeHolderDrawable,
    @DrawableRes errorResId: Int = DEFAULT.errorResId,
    errorDrawable: Drawable? = DEFAULT.errorDrawable,
    @DrawableRes fallbackResId: Int = DEFAULT.fallbackResId,
    fallbackDrawable: Drawable? = DEFAULT.fallbackDrawable,
    //缓存策略等
    skipMemoryCache: Boolean = false,
    diskCacheStrategy: ImageOptions.DiskCache = ImageOptions.DiskCache.AUTOMATIC,
    //优先级
    priority: ImageOptions.LoadPriority = ImageOptions.LoadPriority.NORMAL,
    size: ImageOptions.OverrideSize? = null,
    //gif或者动画
    isAnim: Boolean = true,
    isCrossFade: Boolean = false,
    isCircle: Boolean = false,
    isGray: Boolean = false,
    isFitCenter: Boolean = false,
    centerCrop: Boolean = false,
    //输出图像像素格式
    format: Bitmap.Config? = null,
    //边框 一组一起
    borderWidth: Int = 0,
    borderColor: Int = 0,
    //模糊处理 一组一起使用
    isBlur: Boolean = false,
    blurRadius: Int = 25,
    blurSampling: Int = 4,
    //圆角 一组一起使用
    isRoundedCorners: Boolean = false,
    roundRadius: Int = 0,
    cornerType: ImageOptions.CornerType = ImageOptions.CornerType.ALL,
    //自定义转换器
    vararg transformation: Transformation<Bitmap>,
    //进度监听,请求回调监听
    onProgressListener: OnProgressListener? = null,
    requestListener: (OnImageListener.() -> Unit)? = null,
) {
    val options = ImageOptions().also {
        it.res = load
        it.imageView = this
        it.context = with
        it.placeHolderResId = placeHolderResId
        it.placeHolderDrawable = placeHolderDrawable
        it.errorResId = errorResId
        it.errorDrawable = errorDrawable
        it.fallbackResId = fallbackResId
        it.fallbackDrawable = fallbackDrawable
        it.isCrossFade = isCrossFade
        it.skipMemoryCache = skipMemoryCache
        it.isAnim = isAnim
        it.diskCacheStrategy = diskCacheStrategy
        it.priority = priority
        it.size = size
        it.isCircle = isCircle
        it.isGray = isGray
        it.centerCrop = centerCrop
        it.isFitCenter = isFitCenter
        it.format = format
        it.borderWidth = borderWidth
        it.borderColor = borderColor
        it.isBlur = isBlur
        it.blurRadius = blurRadius
        it.blurSampling = blurSampling
        it.isRoundedCorners = isRoundedCorners
        it.roundRadius = roundRadius
        it.cornerType = cornerType
        it.transformation = transformation
        if (onProgressListener != null) {
            it.progressListener(onProgressListener)
        }
        requestListener?.let { l -> it.requestListener(l) }
    }
    ImageLoader.loadImage(options)
}