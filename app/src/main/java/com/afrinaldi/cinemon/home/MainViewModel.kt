package com.afrinaldi.cinemon.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.afrinaldi.cinemon.core.remote.response.*
import com.afrinaldi.cinemon.core.repository.MoviesRepository
import com.afrinaldi.cinemon.core.utils.RequestState
import retrofit2.HttpException

class MainViewModel : ViewModel(){
    private val moviesResponse = MoviesRepository()

//    private val _nowPlaying = MutableLiveData<List<ResultsItemNowPlaying>>()
//    val nowPlaying: LiveData<List<ResultsItemNowPlaying>> = _nowPlaying
//
//    private val _popular = MutableLiveData<List<ResultsItemPopular>>()
//    val popular: LiveData<List<ResultsItemPopular>> = _popular
//
//    private val _topRated = MutableLiveData<List<ResultsItemTopRated>>()
//    val topRated: LiveData<List<ResultsItemTopRated>> = _topRated
//
//    private val _upcoming = MutableLiveData<List<ResultsItemUpcoming>>()
//    val upcoming: LiveData<List<ResultsItemUpcoming>> = _upcoming

    fun getPopular(): LiveData<RequestState<PopularResponse>> = liveData {
        emit(RequestState.Loading)
        try {
            val response = moviesResponse.getPopular()
            emit(RequestState.Success(response))
        } catch (e : HttpException) {
            emit(RequestState.Error(e.message()))
        }
    }

    fun getNowPlaying(): LiveData<RequestState<NowPlayingResponse>> = liveData {
        emit(RequestState.Loading)
        try {
            val response = moviesResponse.getNowPlaying()
            emit(RequestState.Success(response))
        } catch (e : HttpException) {
            emit(RequestState.Error(e.message()))
        }
    }

    fun getTopRated(): LiveData<RequestState<TopRatedResponse>> = liveData {
        emit(RequestState.Loading)
        try {
            val response = moviesResponse.getTopRated()
            emit(RequestState.Success(response))
        } catch (e : HttpException) {
            emit(RequestState.Error(e.message()))
        }
    }

    fun getUpcoming(): LiveData<RequestState<UpcomingResponse>> = liveData {
        emit(RequestState.Loading)
        try {
            val response = moviesResponse.getUpcoming()
            emit(RequestState.Success(response))
        } catch (e : HttpException) {
            emit(RequestState.Error(e.message()))
        }
    }

    companion object{
        const val TAG = "MainViewModel"
    }
}