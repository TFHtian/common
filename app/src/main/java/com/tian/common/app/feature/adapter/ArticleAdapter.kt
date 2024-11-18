package com.tian.common.app.feature.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chad.library.adapter4.BaseMultiItemAdapter
import com.tian.common.app.data.model.bean.ArticleResponse
import com.tian.common.databinding.ItemArticleBinding
import com.tian.common.databinding.ItemProjectBinding
import com.tian.lib_common.lib_ext.clickNoRepeat
import com.tian.lib_common.lib_ext.gone
import com.tian.lib_common.lib_ext.toHtml
import com.tian.lib_common.lib_ext.visible
import com.tian.lib_common.lib_ext.visibleOrGone

class ArticleAdapter(data: MutableList<ArticleResponse>) : BaseMultiItemAdapter<ArticleResponse>(data) {

    class ArticleVH(val viewBinding: ItemArticleBinding) : RecyclerView.ViewHolder(viewBinding.root)

    class ProjectVH(val viewBinding: ItemProjectBinding) : RecyclerView.ViewHolder(viewBinding.root)

    private var collectAction: (item: ArticleResponse, v: AppCompatImageView) -> Unit =
        { _: ArticleResponse, _: AppCompatImageView -> }

    init {
        addItemType(ARTICLE_TYPE, object : OnMultiItemAdapterListener<ArticleResponse, ArticleVH> {
            override fun onCreate(context: Context, parent: ViewGroup, viewType: Int): ArticleVH {
                val viewBinding =
                    ItemArticleBinding.inflate(LayoutInflater.from(context), parent, false)
                return ArticleVH(viewBinding)
            }

            @SuppressLint("SetTextI18n")
            override fun onBind(holder: ArticleVH, position: Int, item: ArticleResponse?) {
                if (item == null) return
                holder.viewBinding.run {
                    item.run {
                        tvHomeAuthor.text = author.ifEmpty { shareUser }
                        tvHomeContent.text = title.toHtml()
                        tvHomeType2.text = "$superChapterName·$chapterName".toHtml()
                        tvHomeDate.text = niceDate
                        ivHomeCollect.isSelected = collect
                        //展示标签
                        tvHomeNew.visibleOrGone(fresh)
                        tvHomeTop.visibleOrGone(type == 1)
                        if (tags.isNotEmpty()) {
                            tvHomeType1.visible()
                            tvHomeType1.text = tags[0].name
                        } else {
                            tvHomeType1.gone()
                        }
                        ivHomeCollect.clickNoRepeat { v ->
                            collectAction.invoke(item, v as AppCompatImageView)
                        }
                    }
                }
            }
        }).addItemType(PROJECT_TYPE, object : OnMultiItemAdapterListener<ArticleResponse, ProjectVH> {
            override fun onCreate(context: Context, parent: ViewGroup, viewType: Int): ProjectVH {
                val viewBinding =
                    ItemProjectBinding.inflate(LayoutInflater.from(context), parent, false)
                return ProjectVH(viewBinding)
            }

            @SuppressLint("SetTextI18n")
            override fun onBind(holder: ProjectVH, position: Int, item: ArticleResponse?) {
                if (item == null) return
                holder.viewBinding.run {
                    item.run {
                        tvProjectAuthor.text = author.ifEmpty { shareUser }
                        tvProjectContent.text = title.toHtml()
                        tvProjectType.text = "$superChapterName·$chapterName".toHtml()
                        tvProjectDate.text = niceDate
                        ivProjectCollect.isSelected = collect
                        //展示标签
                        tvProjectNew.visibleOrGone(fresh)
                        tvProjectTop.visibleOrGone(type == 1)
                        if (tags.isNotEmpty()) {
                            tvProjectType1.visible()
                            tvProjectType1.text = tags[0].name
                        } else {
                            tvProjectType1.gone()
                        }
                        Glide.with(context).load(envelopePic)
                            .transition(DrawableTransitionOptions.withCrossFade(500))
                            .into(ivProjectImageview)
                    }
                }
            }
        }).onItemViewType { position, list ->
            if (list[position].envelopePic.isEmpty()) {
                ARTICLE_TYPE
            } else {
                PROJECT_TYPE
            }
        }
    }

    fun setCollectClick(inputCollectAction: (item: ArticleResponse, v: AppCompatImageView) -> Unit) {
        this.collectAction = inputCollectAction
    }

    companion object {
        private const val ARTICLE_TYPE = 1 // 文章类型
        private const val PROJECT_TYPE = 2 // 项目类型
    }
}


