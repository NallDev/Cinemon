package com.afrinaldi.cinemon.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
import com.afrinaldi.cinemon.core.utils.*
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
    private val dialog = LoadingDialog(this)

    private val mainViewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvToPopular.setOnClickListener(this)
        binding.tvToNowPlaying.setOnClickListener(this)
        binding.tvToTopRated.setOnClickListener(this)
        binding.tvToUpcoming.setOnClickListener(this)
        dialog.startLoading()

        showNowPlaying()
        showPopular()
        showTopRated()
        showUpcoming()
    }

    private fun showUpcoming() {
        mainViewModel.getUpcoming().observe(this){
            if (it != null){
                when(it) {
                    is RequestState.Loading -> {}
                    is RequestState.Success -> {
                        dialog.isDismiss()
                        listUpcoming.clear()
                        it.data.results.forEach { data ->
                            listUpcoming.add(ResultsItemUpcoming(
                                data.id,
                                data.title,
                                data.overview,
                                data.posterPath,
                                data.releaseDate,
                                data.voteAverage
                            ))
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
                    is RequestState.Error -> {
                        dialog.isDismiss()
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun showTopRated() {
        mainViewModel.getTopRated().observe(this){
            if (it != null){
                when(it) {
                    is RequestState.Loading -> {}
                    is RequestState.Success -> {
                        dialog.isDismiss()
                        listTopRated.clear()
                        it.data.results.forEach { data ->
                            listTopRated.add(
                                ResultsItemTopRated(
                                data.id,
                                data.title,
                                data.overview,
                                data.posterPath,
                                data.releaseDate,
                                data.voteAverage
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
                    is RequestState.Error -> {
                        dialog.isDismiss()
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun showPopular() {
        mainViewModel.getPopular().observe(this) {
            if (it != null){
                when(it) {
                    is RequestState.Loading -> {}
                    is RequestState.Success -> {
                        dialog.isDismiss()
                        listPopular.clear()

                        it.data.results.also { data ->
                            Glide.with(this)
                                .load("https://image.tmdb.org/t/p/w600_and_h900_bestv2${data[0].posterPath}")
                                .into(binding.ivRecommendation)
                            binding.tvTitleRecommendation.text = data[0].title
                            binding.ivRecommendation.setOnClickListener { _ ->
                                Intent(this, DetailActivity::class.java).also { intent ->
                                    intent.putExtra(TITLE, data[0].title)
                                    intent.putExtra(RATING, data[0].voteAverage.toString())
                                    intent.putExtra(IMAGE, data[0].posterPath)
                                    intent.putExtra(OVERVIEW, data[0].overview)
                                    startActivity(intent)
                                }
                            }
                        }

                        it.data.results.forEach { data ->
                            listPopular.add(ResultsItemPopular(
                                data.id,
                                data.title,
                                data.overview,
                                data.posterPath,
                                data.releaseDate,
                                data.voteAverage
                            ))
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
                    is RequestState.Error -> {
                        dialog.isDismiss()
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun showNowPlaying() {
        mainViewModel.getNowPlaying().observe(this) {
            if (it != null){
                when(it) {
                    is RequestState.Loading -> {}
                    is RequestState.Success -> {
                        dialog.isDismiss()
                        listNowPlaying.clear()

                        it.data.results.forEach { data ->
                            listNowPlaying.add(
                                ResultsItemNowPlaying(
                                data.id,
                                data.title,
                                data.overview,
                                data.posterPath,
                                data.releaseDate,
                                data.voteAverage
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
                    is RequestState.Error -> {
                        dialog.isDismiss()
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
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