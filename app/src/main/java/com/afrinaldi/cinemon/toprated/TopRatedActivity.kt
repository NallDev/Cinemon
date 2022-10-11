package com.afrinaldi.cinemon.toprated

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.afrinaldi.cinemon.core.remote.response.ResultsItemTopRated
import com.afrinaldi.cinemon.core.ui.TopRatedAdapter
import com.afrinaldi.cinemon.core.ui.TopRatedListAdapter
import com.afrinaldi.cinemon.core.utils.IMAGE
import com.afrinaldi.cinemon.core.utils.OVERVIEW
import com.afrinaldi.cinemon.core.utils.RATING
import com.afrinaldi.cinemon.core.utils.TITLE
import com.afrinaldi.cinemon.databinding.ActivityNowPlayingBinding
import com.afrinaldi.cinemon.databinding.ActivityTopRatedBinding
import com.afrinaldi.cinemon.detail.DetailActivity
import com.afrinaldi.cinemon.home.MainViewModel

class TopRatedActivity : AppCompatActivity() {
    private var _binding : ActivityTopRatedBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel : MainViewModel by viewModels()
    private val listTopRated = ArrayList<ResultsItemTopRated>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTopRatedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showTopRated()
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
                binding.rvTopRated.adapter = TopRatedListAdapter(listTopRated) { data ->
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