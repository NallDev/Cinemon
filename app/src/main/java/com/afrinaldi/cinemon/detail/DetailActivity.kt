package com.afrinaldi.cinemon.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.afrinaldi.cinemon.core.remote.response.ResultsItemNowPlaying
import com.afrinaldi.cinemon.core.remote.response.ResultsItemUpcoming
import com.afrinaldi.cinemon.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {
    private var _binding : ActivityDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<ResultsItemNowPlaying>("data")

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w600_and_h900_bestv2${data!!.posterPath}")
            .into(binding.ivImage)

        binding.tvTitle.text = data.title
        binding.tvRating.text = data.voteAverage.toString()
        binding.tvDescription.text = data.overview

    }
}