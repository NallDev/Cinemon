package com.afrinaldi.cinemon.nowplaying

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.afrinaldi.cinemon.core.remote.response.ResultsItemNowPlaying
import com.afrinaldi.cinemon.core.ui.NowPlayingAdapter
import com.afrinaldi.cinemon.core.ui.NowPlayingListAdapter
import com.afrinaldi.cinemon.core.utils.IMAGE
import com.afrinaldi.cinemon.core.utils.OVERVIEW
import com.afrinaldi.cinemon.core.utils.RATING
import com.afrinaldi.cinemon.core.utils.RequestState
import com.afrinaldi.cinemon.core.utils.TITLE
import com.afrinaldi.cinemon.databinding.ActivityNowPlayingBinding
import com.afrinaldi.cinemon.detail.DetailActivity
import com.afrinaldi.cinemon.home.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NowPlayingActivity : AppCompatActivity() {

    private var _binding : ActivityNowPlayingBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel : MainViewModel by viewModels()
    private lateinit var movieAdapter: NowPlayingListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityNowPlayingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showShimmerBar(false)

        movieAdapter = NowPlayingListAdapter { data ->
            Intent(this, DetailActivity::class.java).also { intent ->
                intent.putExtra(TITLE, data.title)
                intent.putExtra(RATING, data.voteAverage.toString())
                intent.putExtra(IMAGE, data.posterPath)
                intent.putExtra(OVERVIEW, data.overview)
                startActivity(intent)
            }
        }

        binding.rvNowPlaying.adapter = movieAdapter

        showNowPlaying()
    }

    private fun showNowPlaying() {
        mainViewModel.nowPlaying1.observe(this@NowPlayingActivity){
            movieAdapter.submitData(lifecycle, it)
        }
    }

    private fun showShimmerBar(isLoading: Boolean) {
        binding.loading.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }
}