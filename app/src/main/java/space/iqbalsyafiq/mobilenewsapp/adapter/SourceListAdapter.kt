package space.iqbalsyafiq.mobilenewsapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import space.iqbalsyafiq.mobilenewsapp.databinding.ItemSourceBinding
import space.iqbalsyafiq.mobilenewsapp.model.response.sources.Source

class SourceListAdapter : PagingDataAdapter<Source, SourceListAdapter.MyViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((Source) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemSourceBinding.inflate(
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

    inner class MyViewHolder(private val binding: ItemSourceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Source) {
            Log.d("TAG", "bind data: $data")
            binding.tvSourceName.text = data.name

            binding.cardRoot.setOnClickListener {
                onItemClick?.invoke(data)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Source>() {
            override fun areItemsTheSame(
                oldItem: Source,
                newItem: Source
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Source,
                newItem: Source
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}