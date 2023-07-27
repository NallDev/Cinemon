package com.afrinaldi.cinemon.core.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.afrinaldi.cinemon.databinding.ShimmerItemListMoviesBinding

class LoadingStateAdapter (val retry: () -> Unit) : LoadStateAdapter<LoadingStateAdapter.ViewHolder>() {
    class ViewHolder(private val binding: ShimmerItemListMoviesBinding, retry: () -> Unit) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.btnRetry.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                btnRetry.isVisible = loadState is LoadState.Error
                cardView2.isVisible = loadState is LoadState.Loading
                tvTitle.isVisible = loadState is LoadState.Loading
                view.isVisible = loadState is LoadState.Loading
                tvDate.isVisible = loadState is LoadState.Loading
                tvRating.isVisible = loadState is LoadState.Loading
            }
        }
    }

    override fun onBindViewHolder(holder: LoadingStateAdapter.ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): ViewHolder {
        val binding = ShimmerItemListMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, retry)
    }
}