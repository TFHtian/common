package com.tian.common.app.feature.imageloader.glide

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.bumptech.glide.util.Preconditions
import com.tian.common.app.feature.imageloader.core.ImageOptions
import com.tian.common.app.feature.imageloader.glide.progress.GlideImageViewTarget
import com.tian.common.app.feature.imageloader.glide.progress.ProgressManager
import com.tian.common.app.feature.imageloader.glide.transformation.BlurTransformation
import com.tian.common.app.feature.imageloader.glide.transformation.BorderTransformation
import com.tian.common.app.feature.imageloader.glide.transformation.CircleWithBorderTransformation
import com.tian.common.app.feature.imageloader.glide.transformation.GrayscaleTransformation
import com.tian.common.app.feature.imageloader.glide.transformation.RoundedCornersTransformation
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

object ImageLoader {

    @SuppressLint("CheckResult")
    @JvmStatic
    fun loadImage(options: ImageOptions) {
        Preconditions.checkNotNull(options, "ImageConfigImpl is required")
        val context = options.context
        Preconditions.checkNotNull(context, "Context is required")
        Preconditions.checkNotNull(options.imageView, "ImageView is required")
        val glideRequest = context?.let { GlideApp.with(it).load(options.res) }
        glideRequest?.apply {
            //缓存配置
            val diskCacheStrategy = when (options.diskCacheStrategy) {
                ImageOptions.DiskCache.ALL -> DiskCacheStrategy.ALL
                ImageOptions.DiskCache.NONE -> DiskCacheStrategy.NONE
                ImageOptions.DiskCache.RESOURCE -> DiskCacheStrategy.RESOURCE
                ImageOptions.DiskCache.DATA -> DiskCacheStrategy.DATA
                ImageOptions.DiskCache.AUTOMATIC -> DiskCacheStrategy.AUTOMATIC
            }
            diskCacheStrategy(diskCacheStrategy)

            // 是否跳过内存缓存
            skipMemoryCache(options.skipMemoryCache)

            //优先级
            val priority = when (options.priority) {
                ImageOptions.LoadPriority.LOW -> Priority.LOW
                ImageOptions.LoadPriority.NORMAL -> Priority.NORMAL
                ImageOptions.LoadPriority.HIGH -> Priority.HIGH
                ImageOptions.LoadPriority.IMMEDIATE -> Priority.IMMEDIATE
            }
            priority(priority)

            //图片资源管理
            val drawableOptions = options.drawableOptions
            //设置占位符
            if (drawableOptions.placeHolderDrawable != null) {
                placeholder(drawableOptions.placeHolderDrawable)
            } else if (drawableOptions.placeHolderResId != 0) {
                placeholder(drawableOptions.placeHolderResId)
            }
            //设置错误的图片
            if (drawableOptions.errorDrawable != null) {
                error(drawableOptions.errorDrawable)
            } else if (drawableOptions.errorResId != 0) {
                error(drawableOptions.errorResId)
            }
            //设置请求 url 为空图片
            if (drawableOptions.fallbackDrawable != null) {
                fallback(drawableOptions.fallbackDrawable)
            } else if (drawableOptions.fallbackResId != 0) {
                fallback(drawableOptions.fallbackResId)
            }

            //目标尺寸
            val size = options.size
            size?.let {
                override(size.width, size.height)
            }

            //解码格式
            val format = when (options.format) {
                Bitmap.Config.ARGB_8888 -> DecodeFormat.PREFER_ARGB_8888
                Bitmap.Config.RGB_565 -> DecodeFormat.PREFER_RGB_565
                else -> DecodeFormat.DEFAULT
            }
            format(format)

            //region ========== 特效 ==========
            //动画
            if (!options.isAnim) {
                dontAnimate()
            }
            //是否渐隐加载
            if (options.isCrossFade) {
                val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
                transition(DrawableTransitionOptions.withCrossFade(factory))
            }
            if (options.centerCrop) {
                centerCrop()
            }
            if (options.isFitCenter) {
                fitCenter()
            }

            //圆形及边框
            if (options.isCircle || options.borderWidth > 0) {
                if (options.isCircle) {
                    transform(
                        CircleWithBorderTransformation(options.borderWidth,
                        options.borderColor)
                    )
                } else {
                    transform(BorderTransformation(options.borderWidth, options.borderColor))
                }
            }

            // 圆角
            if (options.isRoundedCorners) {
                var transformation: BitmapTransformation? = null
                // 圆角特效受到ImageView的scaleType属性影响
                val scaleType = options.imageView?.scaleType
                if (scaleType == ImageView.ScaleType.FIT_CENTER ||
                    scaleType == ImageView.ScaleType.CENTER_INSIDE ||
                    scaleType == ImageView.ScaleType.CENTER ||
                    scaleType == ImageView.ScaleType.CENTER_CROP
                ) {
                    transformation = CenterCrop()
                }
                if (transformation == null) {
                    transform(
                        RoundedCornersTransformation(options.roundRadius,
                        0,
                        options.cornerType)
                    )
                } else {
                    transform(transformation,
                        RoundedCornersTransformation(options.roundRadius, 0, options.cornerType))
                }
            }

            //模糊
            if (options.isBlur) {
                transform(
                    BlurTransformation(options.imageView!!.context,
                    options.blurRadius,
                    options.blurSampling)
                )
            }

            //黑白
            if (options.isGray) {
                transform(GrayscaleTransformation())
            }

            //转换
            options.transformation?.let {
                transform(*options.transformation!!)
            }

            //endregion
            options.requestListener?.let {
                addListener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>,
                        isFirstResource: Boolean,
                    ): Boolean {
                        options.requestListener?.onFailAction?.invoke(e.toString())
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>?,
                        dataSource: DataSource,
                        isFirstResource: Boolean,
                    ): Boolean {
                        options.requestListener?.onSuccessAction?.invoke(resource)
                        return false
                    }
                })
            }

            if (options.res is String) {
                into(GlideImageViewTarget(options.imageView, options.res as String))
            }
        }

        options.onProgressListener?.let {
            ProgressManager.addListener(options.res.toString(), options.onProgressListener)
        }
    }

    /**
     * 清除本地缓存
     */
    @SuppressLint("CheckResult")
    fun clearDiskCache(context: Context) {
        Observable.just(0)
            .observeOn(Schedulers.io())
            .subscribe { Glide.get(context).clearDiskCache() }
    }

    /**
     * 清除内存缓存
     */
    @SuppressLint("CheckResult")
    @JvmStatic
    fun clearMemory(context: Context) {
        Observable.just(0)
            .observeOn(Schedulers.io())
            .subscribe { Glide.get(context).clearDiskCache() }
    }

    /**
     * 取消图片加载
     */
    @JvmStatic
    fun clearImage(context: Context, imageView: ImageView) {
        GlideApp.get(context).requestManagerRetriever[context].clear(imageView)
    }

    /**
     * 预加载
     */
    @JvmStatic
    fun preloadImage(context: Context, url: String?) {
        Glide.with(context).load(url).preload()
    }
}