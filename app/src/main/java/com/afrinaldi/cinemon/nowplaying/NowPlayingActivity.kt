package com.afrinaldi.cinemon.nowplaying

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.afrinaldi.cinemon.core.remote.response.ResultsItemNowPlaying
import com.afrinaldi.cinemon.core.ui.NowPlayingAdapter
import com.afrinaldi.cinemon.core.ui.NowPlayingListAdapter
import com.afrinaldi.cinemon.core.utils.IMAGE
import com.afrinaldi.cinemon.core.utils.OVERVIEW
import com.afrinaldi.cinemon.core.utils.RATING
import com.afrinaldi.cinemon.core.utils.TITLE
import com.afrinaldi.cinemon.databinding.ActivityNowPlayingBinding
import com.afrinaldi.cinemon.detail.DetailActivity
import com.afrinaldi.cinemon.home.MainViewModel

class NowPlayingActivity : AppCompatActivity() {

    private var _binding : ActivityNowPlayingBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel : MainViewModel by viewModels()
    private val listNowPlaying = ArrayList<ResultsItemNowPlaying>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityNowPlayingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showNowPlaying()
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
                binding.rvNowPlaying.adapter = NowPlayingListAdapter(listNowPlaying) { data ->
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