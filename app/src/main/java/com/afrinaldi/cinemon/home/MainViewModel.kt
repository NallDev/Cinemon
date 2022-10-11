package com.afrinaldi.cinemon.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afrinaldi.cinemon.core.remote.network.ApiConfig
import com.afrinaldi.cinemon.core.remote.response.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel(){
    private val _nowPlaying = MutableLiveData<List<ResultsItemNowPlaying>>()
    val nowPlaying: LiveData<List<ResultsItemNowPlaying>> = _nowPlaying

    private val _popular = MutableLiveData<List<ResultsItemPopular>>()
    val popular: LiveData<List<ResultsItemPopular>> = _popular

    private val _topRated = MutableLiveData<List<ResultsItemTopRated>>()
    val topRated: LiveData<List<ResultsItemTopRated>> = _topRated

    private val _upcoming = MutableLiveData<List<ResultsItemUpcoming>>()
    val upcoming: LiveData<List<ResultsItemUpcoming>> = _upcoming

    fun getNowPlaying(){
        val client = ApiConfig.getApiService().getNowPlaying()
        client.enqueue(object : Callback<NowPlayingResponse>{
            override fun onResponse(
                call: Call<NowPlayingResponse>,
                response: Response<NowPlayingResponse>
            ) {
                if (response.isSuccessful) {
                    _nowPlaying.postValue(response.body()?.results)
                    Log.e(TAG, "Berhasil")
                } else {
                    Log.e(TAG, "Gagal")
                }
            }

            override fun onFailure(call: Call<NowPlayingResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    fun getPopular(){
        val client = ApiConfig.getApiService().getPopular()
        client.enqueue(object : Callback<PopularResponse>{
            override fun onResponse(
                call: Call<PopularResponse>,
                response: Response<PopularResponse>
            ) {
                if (response.isSuccessful) {
                    _popular.postValue(response.body()?.results)
                    Log.e(TAG, "Berhasil")
                } else {
                    Log.e(TAG, "Gagal")
                }
            }

            override fun onFailure(call: Call<PopularResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    fun getTopRated(){
        val client = ApiConfig.getApiService().getTopRated()
        client.enqueue(object : Callback<TopRatedResponse>{
            override fun onResponse(
                call: Call<TopRatedResponse>,
                response: Response<TopRatedResponse>
            ) {
                if (response.isSuccessful) {
                    _topRated.postValue(response.body()?.results)
                    Log.e(TAG, "Berhasil")
                } else {
                    Log.e(TAG, "Gagal")
                }
            }

            override fun onFailure(call: Call<TopRatedResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    fun getUpcoming(){
        val client = ApiConfig.getApiService().getUpcoming()
        client.enqueue(object : Callback<UpcomingResponse>{
            override fun onResponse(
                call: Call<UpcomingResponse>,
                response: Response<UpcomingResponse>
            ) {
                if (response.isSuccessful) {
                    _upcoming.postValue(response.body()?.results)
                    Log.e(TAG, "Berhasil")
                } else {
                    Log.e(TAG, "Gagal")
                }
            }

            override fun onFailure(call: Call<UpcomingResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    companion object{
        const val TAG = "MainViewModel"
    }
}