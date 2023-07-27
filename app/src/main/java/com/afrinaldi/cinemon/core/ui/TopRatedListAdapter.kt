package com.afrinaldi.cinemon.core.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.afrinaldi.cinemon.R
import com.afrinaldi.cinemon.core.remote.response.ResultsItemTopRated
import com.afrinaldi.cinemon.databinding.ItemListMoviesBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class TopRatedListAdapter(private val listener: (ResultsItemTopRated) -> Unit) : PagingDataAdapter<ResultsItemTopRated, TopRatedListAdapter.ViewHolder>(
    DIFF_CALLBACK) {
    private lateinit var contextAdapter : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        contextAdapter = parent.context
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data, listener, contextAdapter)
        }
    }

    inner class ViewHolder(private val binding: ItemListMoviesBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item : ResultsItemTopRated, listener: (ResultsItemTopRated) -> Unit, context : Context){
            with(binding) {
                tvTitle.text = item.title
                tvRating.text = item.voteAverage.toString()
                tvDate.text = item.releaseDate

                Glide.with(context)
                    .load("https://image.tmdb.org/t/p/w600_and_h900_bestv2${item.posterPath}")
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.ic_image)
                    )
                    .into(ivMain)
            }
            itemView.setOnClickListener{
                listener(item)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ResultsItemTopRated>() {
            override fun areItemsTheSame(oldItem: ResultsItemTopRated, newItem: ResultsItemTopRated): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ResultsItemTopRated, newItem: ResultsItemTopRated): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}