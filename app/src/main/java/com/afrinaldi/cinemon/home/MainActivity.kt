package com.afrinaldi.cinemon.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.afrinaldi.cinemon.R
import com.afrinaldi.cinemon.core.remote.response.ResultsItemNowPlaying
import com.afrinaldi.cinemon.core.remote.response.ResultsItemPopular
import com.afrinaldi.cinemon.core.remote.response.ResultsItemTopRated
import com.afrinaldi.cinemon.core.remote.response.ResultsItemUpcoming
import com.afrinaldi.cinemon.core.ui.NowPlayingAdapter
import com.afrinaldi.cinemon.core.ui.PopularAdapter
import com.afrinaldi.cinemon.core.ui.TopRatedAdapter
import com.afrinaldi.cinemon.core.ui.UpcomingAdapter
import com.afrinaldi.cinemon.core.utils.IMAGE
import com.afrinaldi.cinemon.core.utils.OVERVIEW
import com.afrinaldi.cinemon.core.utils.RATING
import com.afrinaldi.cinemon.core.utils.TITLE
import com.afrinaldi.cinemon.databinding.ActivityMainBinding
import com.afrinaldi.cinemon.detail.DetailActivity
import com.afrinaldi.cinemon.nowplaying.NowPlayingActivity
import com.afrinaldi.cinemon.popular.PopularActivity
import com.afrinaldi.cinemon.toprated.TopRatedActivity
import com.afrinaldi.cinemon.upcoming.UpcomingActivity
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val listNowPlaying = ArrayList<ResultsItemNowPlaying>()
    private val listPopular = ArrayList<ResultsItemPopular>()
    private val listTopRated = ArrayList<ResultsItemTopRated>()
    private val listUpcoming = ArrayList<ResultsItemUpcoming>()

    private val mainViewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvToPopular.setOnClickListener(this)
        binding.tvToNowPlaying.setOnClickListener(this)
        binding.tvToTopRated.setOnClickListener(this)
        binding.tvToUpcoming.setOnClickListener(this)

        showNowPlaying()
        showPopular()
        showTopRated()
        showUpcoming()
    }

    private fun showUpcoming() {
        mainViewModel.getUpcoming()
        mainViewModel.upcoming.observe(this){
            listUpcoming.clear()
            for (i in it.indices){
                listUpcoming.add(
                    ResultsItemUpcoming(
                        it[i].id,
                        it[i].title,
                        it[i].overview,
                        it[i].posterPath,
                        it[i].releaseDate,
                        it[i].voteAverage
                    )
                )
            }

            if (listUpcoming.isNotEmpty()){
                binding.rvUpcoming.adapter = UpcomingAdapter(listUpcoming) { data ->
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

    private fun showTopRated() {
        mainViewModel.getTopRated()
        mainViewModel.topRated.observe(this){
            listTopRated.clear()
            for (i in it.indices){
                listTopRated.add(
                    ResultsItemTopRated(
                        it[i].id,
                        it[i].title,
                        it[i].overview,
                        it[i].posterPath,
                        it[i].releaseDate,
                        it[i].voteAverage
                    )
                )
            }

            if (listTopRated.isNotEmpty()){
                binding.rvTopRated.adapter = TopRatedAdapter(listTopRated) { data ->
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

    private fun showPopular() {
        mainViewModel.getPopular()
        mainViewModel.popular.observe(this){
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w600_and_h900_bestv2${it[0].posterPath}")
                .into(binding.ivRecommendation)
            binding.tvTitleRecommendation.text = it[0].title
            binding.ivRecommendation.setOnClickListener { _ ->
                Intent(this, DetailActivity::class.java).also { intent ->
                    intent.putExtra(TITLE, it[0].title)
                    intent.putExtra(RATING, it[0].voteAverage.toString())
                    intent.putExtra(IMAGE, it[0].posterPath)
                    intent.putExtra(OVERVIEW, it[0].overview)
                    startActivity(intent)
                }
            }

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
                binding.rvPopular.adapter = PopularAdapter(listPopular) { data ->
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

    private fun showNowPlaying() {
        mainViewModel.getNowPlaying()
        mainViewModel.nowPlaying.observe(this){
            listNowPlaying.clear()
            for (i in it.indices){
                listNowPlaying.add(
                    ResultsItemNowPlaying(
                        it[i].id,
                        it[i].title,
                        it[i].overview,
                        it[i].posterPath,
                        it[i].releaseDate,
                        it[i].voteAverage
                    )
                )
            }

            if (listNowPlaying.isNotEmpty()){
                binding.rvNowPlaying.adapter = NowPlayingAdapter(listNowPlaying) { data ->
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

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv_to_upcoming -> {
                Intent(this, UpcomingActivity::class.java).also {
                    startActivity(it)
                }
            }
            R.id.tv_to_now_playing -> {
                Intent(this, NowPlayingActivity::class.java).also {
                    startActivity(it)
                }
            }
            R.id.tv_to_top_rated -> {
                Intent(this, TopRatedActivity::class.java).also {
                    startActivity(it)
                }
            }
            R.id.tv_to_popular -> {
                Intent(this, PopularActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
    }
}