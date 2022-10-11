package com.afrinaldi.cinemon.core.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afrinaldi.cinemon.R
import com.afrinaldi.cinemon.core.remote.response.ResultsItemNowPlaying
import com.afrinaldi.cinemon.core.remote.response.ResultsItemTopRated
import com.afrinaldi.cinemon.databinding.ItemListMoviesBinding
import com.afrinaldi.cinemon.databinding.ItemMoviesBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class TopRatedListAdapter(private val data: List<ResultsItemTopRated>, private val listener: (ResultsItemTopRated) -> Unit) : RecyclerView.Adapter<TopRatedListAdapter.ViewHolder>() {
    private lateinit var contextAdapter : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        contextAdapter = parent.context
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], listener, contextAdapter)
    }

    override fun getItemCount(): Int = data.size

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
}