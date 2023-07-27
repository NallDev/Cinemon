package com.afrinaldi.cinemon.core.repository

import com.afrinaldi.cinemon.core.remote.network.ApiConfig

class MoviesRepository {

    private val client = ApiConfig.getApiService()

    suspend fun getPopular() = client.getPopular()

    suspend fun getNowPlaying() = client.getNowPlaying()

    suspend fun getTopRated() = client.getTopRated()

    suspend fun getUpcoming() = client.getUpcoming()
}