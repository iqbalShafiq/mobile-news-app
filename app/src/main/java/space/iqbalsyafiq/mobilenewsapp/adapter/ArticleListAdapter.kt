package space.iqbalsyafiq.mobilenewsapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import space.iqbalsyafiq.mobilenewsapp.databinding.ItemArticleBinding
import space.iqbalsyafiq.mobilenewsapp.model.response.news.Article


class ArticleListAdapter : PagingDataAdapter<Article, ArticleListAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {

    var onItemClick: ((Article) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemArticleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    inner class MyViewHolder(
        private val binding: ItemArticleBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Article) {
            Log.d(TAG, "bind data: $data")

            binding.cardRoot.setOnClickListener {
                onItemClick?.invoke(data)
            }

            binding.tvTitle.text = data.title

            if (data.description.isNullOrEmpty()) {
                binding.lineSeparator.visibility = View.GONE
                binding.tvLabelContent.visibility = View.GONE
                binding.tvContent.visibility = View.GONE
            }
            binding.tvContent.text = data.description

            data.urlToImage?.let {
                val requestOptions = RequestOptions().transform(
                    CenterCrop(),
                    RoundedCorners(8)
                )

                Glide.with(binding.root)
                    .asBitmap()
                    .load(it)
                    .apply(requestOptions)
                    .into(binding.ivHeadline)
            } ?: run {
                binding.ivHeadline.visibility = View.GONE
            }
        }
    }

    companion object {
        private val TAG = ArticleListAdapter::class.java.simpleName

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(
                oldItem: Article,
                newItem: Article
            ): Boolean {
                Log.d(TAG, "areItemsTheSame: ${oldItem == newItem}")
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(
                oldItem: Article,
                newItem: Article
            ): Boolean {
                Log.d(TAG, "areContentsTheSame: ${oldItem.title == newItem.title}")
                return oldItem.title == newItem.title
            }
        }
    }
}