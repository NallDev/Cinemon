package com.afrinaldi.cinemon.popular

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.afrinaldi.cinemon.core.ui.PopularListAdapter
import com.afrinaldi.cinemon.core.utils.IMAGE
import com.afrinaldi.cinemon.core.utils.OVERVIEW
import com.afrinaldi.cinemon.core.utils.RATING
import com.afrinaldi.cinemon.core.utils.TITLE
import com.afrinaldi.cinemon.databinding.ActivityPopularBinding
import com.afrinaldi.cinemon.detail.DetailActivity
import com.afrinaldi.cinemon.home.MainViewModel

class PopularActivity : AppCompatActivity() {
    private var _binding : ActivityPopularBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel : MainViewModel by viewModels()
    private lateinit var popularListAdapter: PopularListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPopularBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showShimmerBar(false)

        popularListAdapter = PopularListAdapter { data ->
            Intent(this, DetailActivity::class.java).also { intent ->
                intent.putExtra(TITLE, data.title)
                intent.putExtra(RATING, data.voteAverage.toString())
                intent.putExtra(IMAGE, data.posterPath)
                intent.putExtra(OVERVIEW, data.overview)
                startActivity(intent)
            } }
        binding.rvPopular.adapter = popularListAdapter
        showPopular()
    }

    private fun showPopular() {
        mainViewModel.popular1.observe(this){
            popularListAdapter.submitData(lifecycle, it)
        }
    }

    private fun showShimmerBar(isLoading: Boolean) {
        binding.loading.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }
}