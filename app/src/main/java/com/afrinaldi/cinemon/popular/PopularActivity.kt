package com.afrinaldi.cinemon.popular

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.afrinaldi.cinemon.core.remote.response.ResultsItemPopular
import com.afrinaldi.cinemon.core.ui.PopularAdapter
import com.afrinaldi.cinemon.core.ui.PopularListAdapter
import com.afrinaldi.cinemon.core.utils.IMAGE
import com.afrinaldi.cinemon.core.utils.OVERVIEW
import com.afrinaldi.cinemon.core.utils.RATING
import com.afrinaldi.cinemon.core.utils.TITLE
import com.afrinaldi.cinemon.databinding.ActivityNowPlayingBinding
import com.afrinaldi.cinemon.databinding.ActivityPopularBinding
import com.afrinaldi.cinemon.detail.DetailActivity
import com.afrinaldi.cinemon.home.MainViewModel
import com.bumptech.glide.Glide

class PopularActivity : AppCompatActivity() {
    private var _binding : ActivityPopularBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel : MainViewModel by viewModels()
    private val listPopular = ArrayList<ResultsItemPopular>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPopularBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showPopular()
    }

    private fun showPopular() {
        mainViewModel.getPopular()
        mainViewModel.popular.observe(this){

            listPopular.clear()
            for (i in it.indices){
                listPopular.add(
                    ResultsItemPopular(
                        it[i].id,
                        it[i].title,
                        it[i].overview,
                        it[i].posterPath,
                        it[i].releaseDate,
                        it[i].voteAverage
                    )
                )
            }

            if (listPopular.isNotEmpty()){
                binding.rvPopular.adapter = PopularListAdapter(listPopular) { data ->
                    Intent(this, DetailActivity::class.java).also { intent ->
                        intent.putExtra(TITLE, data.title)
                        intent.putExtra(RATING, data.voteAverage.toString())
                        intent.putExtra(IMAGE, data.posterPath)
                        intent.putExtra(OVERVIEW, data.overview)
                        startActivity(intent)
                    }
                }
            }
        }
    }
}