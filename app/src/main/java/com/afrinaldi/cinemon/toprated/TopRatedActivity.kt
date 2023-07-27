package com.afrinaldi.cinemon.toprated

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.afrinaldi.cinemon.core.ui.TopRatedListAdapter
import com.afrinaldi.cinemon.core.utils.IMAGE
import com.afrinaldi.cinemon.core.utils.OVERVIEW
import com.afrinaldi.cinemon.core.utils.RATING
import com.afrinaldi.cinemon.core.utils.TITLE
import com.afrinaldi.cinemon.databinding.ActivityTopRatedBinding
import com.afrinaldi.cinemon.detail.DetailActivity
import com.afrinaldi.cinemon.home.MainViewModel

class TopRatedActivity : AppCompatActivity() {
    private var _binding : ActivityTopRatedBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel : MainViewModel by viewModels()
    private lateinit var topRatedListAdapter: TopRatedListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTopRatedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showShimmerBar(false)

        topRatedListAdapter = TopRatedListAdapter {data ->
            Intent(this, DetailActivity::class.java).also { intent ->
                intent.putExtra(TITLE, data.title)
                intent.putExtra(RATING, data.voteAverage.toString())
                intent.putExtra(IMAGE, data.posterPath)
                intent.putExtra(OVERVIEW, data.overview)
                startActivity(intent)
            }
        }

        binding.rvTopRated.adapter = topRatedListAdapter

        showTopRated()
    }

    private fun showTopRated() {
        mainViewModel.topRated1.observe(this) {
            topRatedListAdapter.submitData(lifecycle, it)
        }
    }

    private fun showShimmerBar(isLoading: Boolean) {
        binding.loading.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }
}