package com.afrinaldi.cinemon.core.remote.network

import com.afrinaldi.cinemon.BuildConfig.API_KEY
import com.afrinaldi.cinemon.core.remote.response.NowPlayingResponse
import com.afrinaldi.cinemon.core.remote.response.PopularResponse
import com.afrinaldi.cinemon.core.remote.response.TopRatedResponse
import com.afrinaldi.cinemon.core.remote.response.UpcomingResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("popular")
    suspend fun getPopular(
        @Query("api_key") api_key: String = API_KEY,
        @Query("language") language: String = "en-US"
    ): PopularResponse

    @GET("now_playing")
    suspend fun getNowPlaying(
        @Query("api_key") api_key: String = API_KEY,
        @Query("language") language: String = "en-US"
    ): NowPlayingResponse

    @GET("top_rated")
    suspend fun getTopRated(
        @Query("api_key") api_key: String = API_KEY,
        @Query("language") language: String = "en-US"
    ): TopRatedResponse

    @GET("upcoming")
    suspend fun getUpcoming(
        @Query("api_key") api_key: String = API_KEY,
        @Query("language") language: String = "en-US"
    ): UpcomingResponse
}