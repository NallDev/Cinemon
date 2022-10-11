package com.afrinaldi.cinemon.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.afrinaldi.cinemon.R
import com.afrinaldi.cinemon.core.utils.IMAGE
import com.afrinaldi.cinemon.core.utils.OVERVIEW
import com.afrinaldi.cinemon.core.utils.RATING
import com.afrinaldi.cinemon.core.utils.TITLE
import com.afrinaldi.cinemon.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class DetailActivity : AppCompatActivity() {
    private var _binding : ActivityDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra(TITLE)
        val image = intent.getStringExtra(IMAGE)
        val rating = intent.getStringExtra(RATING)
        val desc = intent.getStringExtra(OVERVIEW)

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w600_and_h900_bestv2$image")
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_image)
            )
            .into(binding.ivImage)

        binding.tvTitle.text = title
        binding.tvRating.text = rating
        binding.tvDescription.text = desc

    }
}