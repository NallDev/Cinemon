package com.afrinaldi.cinemon.core.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import androidx.paging.PagingData
import com.afrinaldi.cinemon.core.data.NowPlayingPagingSource
import com.afrinaldi.cinemon.core.data.PopularPagingSource
import com.afrinaldi.cinemon.core.data.TopRatedPagingSource
import com.afrinaldi.cinemon.core.data.UpcomingPagingSource
import com.afrinaldi.cinemon.core.remote.network.ApiConfig
import com.afrinaldi.cinemon.core.remote.response.ResultsItemNowPlaying
import com.afrinaldi.cinemon.core.remote.response.ResultsItemPopular
import com.afrinaldi.cinemon.core.remote.response.ResultsItemTopRated
import com.afrinaldi.cinemon.core.remote.response.ResultsItemUpcoming

class MoviesRepository {

    private val client = ApiConfig.getApiService()

    fun getNowPlayingPager() : LiveData<PagingData<ResultsItemNowPlaying>> {
        return Pager(
            config = PagingConfig(pageSize = 5),
            pagingSourceFactory = {
                NowPlayingPagingSource(client)
            }
        ).liveData
    }

    fun getPopularPager() : LiveData<PagingData<ResultsItemPopular>> {
        return Pager(
            config = PagingConfig(pageSize = 5),
            pagingSourceFactory = {
                PopularPagingSource(client)
            }
        ).liveData
    }

    fun getTopRatedPager() : LiveData<PagingData<ResultsItemTopRated>> {
        return Pager(
            config = PagingConfig(pageSize = 5),
            pagingSourceFactory = {
                TopRatedPagingSource(client)
            }
        ).liveData
    }

    fun getUpcomingPager() : LiveData<PagingData<ResultsItemUpcoming>> {
        return Pager(
            config = PagingConfig(pageSize = 5),
            pagingSourceFactory = {
                UpcomingPagingSource(client)
            }
        ).liveData
    }

    suspend fun getPopular() = client.getPopular()

    suspend fun getNowPlaying() = client.getNowPlaying()

    suspend fun getTopRated() = client.getTopRated()

    suspend fun getUpcoming() = client.getUpcoming()
}