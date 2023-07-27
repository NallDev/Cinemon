package com.afrinaldi.cinemon.upcoming

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.afrinaldi.cinemon.core.ui.UpcomingListAdapter
import com.afrinaldi.cinemon.core.utils.IMAGE
import com.afrinaldi.cinemon.core.utils.OVERVIEW
import com.afrinaldi.cinemon.core.utils.RATING
import com.afrinaldi.cinemon.core.utils.TITLE
import com.afrinaldi.cinemon.databinding.ActivityUpcomingBinding
import com.afrinaldi.cinemon.detail.DetailActivity
import com.afrinaldi.cinemon.home.MainViewModel

class UpcomingActivity : AppCompatActivity() {
    private var _binding : ActivityUpcomingBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel : MainViewModel by viewModels()

    private lateinit var upcomingListAdapter: UpcomingListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityUpcomingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showShimmerBar(false)

        upcomingListAdapter = UpcomingListAdapter { data ->
            Intent(this, DetailActivity::class.java).also { intent ->
                intent.putExtra(TITLE, data.title)
                intent.putExtra(RATING, data.voteAverage.toString())
                intent.putExtra(IMAGE, data.posterPath)
                intent.putExtra(OVERVIEW, data.overview)
                startActivity(intent)
            }
        }

        binding.rvUpcoming.adapter = upcomingListAdapter

        showUpcoming()
    }

    private fun showUpcoming() {
        mainViewModel.upcoming1.observe(this){
            upcomingListAdapter.submitData(lifecycle, it)
        }
    }

    private fun showShimmerBar(isLoading: Boolean) {
        binding.loading.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }
}