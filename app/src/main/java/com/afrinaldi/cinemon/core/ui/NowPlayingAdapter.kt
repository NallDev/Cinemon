package com.afrinaldi.cinemon.core.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afrinaldi.cinemon.core.remote.response.ResultsItemNowPlaying
import com.afrinaldi.cinemon.databinding.ItemMoviesBinding
import com.bumptech.glide.Glide

class NowPlayingAdapter(private val data: List<ResultsItemNowPlaying> ,
                        private val listener: (ResultsItemNowPlaying) -> Unit) : RecyclerView.Adapter<NowPlayingAdapter.ViewHolder>() {
    private lateinit var contextAdapter : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        contextAdapter = parent.context
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], listener, contextAdapter)
    }

    override fun getItemCount(): Int{
        return if (data.size > 4){
            4
        } else
            data.size
    }
    inner class ViewHolder(private val binding: ItemMoviesBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item : ResultsItemNowPlaying, listener: (ResultsItemNowPlaying) -> Unit, context : Context){
            with(binding) {
                tvTitle.text = item.title
                tvRating.text = item.voteAverage.toString()

                Glide.with(context)
                    .load("https://image.tmdb.org/t/p/w600_and_h900_bestv2${item.posterPath}")
                    .into(ivMain)
            }
            itemView.setOnClickListener{
                listener(item)
            }
        }
    }
}