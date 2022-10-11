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
    fun getPopular(
        @Query("api_key") api_key: String = API_KEY,
        @Query("language") language: String = "en-US"
    ): Call<PopularResponse>

    @GET("now_playing")
    fun getNowPlaying(
        @Query("api_key") api_key: String = API_KEY,
        @Query("language") language: String = "en-US"
    ): Call<NowPlayingResponse>

    @GET("top_rated")
    fun getTopRated(
        @Query("api_key") api_key: String = API_KEY,
        @Query("language") language: String = "en-US"
    ): Call<TopRatedResponse>

    @GET("upcoming")
    fun getUpcoming(
        @Query("api_key") api_key: String = API_KEY,
        @Query("language") language: String = "en-US"
    ): Call<UpcomingResponse>
}