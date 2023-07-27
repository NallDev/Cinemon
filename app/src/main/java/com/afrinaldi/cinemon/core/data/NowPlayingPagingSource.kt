package com.afrinaldi.cinemon.core.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.afrinaldi.cinemon.core.remote.network.ApiService
import com.afrinaldi.cinemon.core.remote.response.ResultsItemNowPlaying
import java.lang.Exception

class NowPlayingPagingSource(private val apiService: ApiService) : PagingSource<Int, ResultsItemNowPlaying>() {

    override fun getRefreshKey(state: PagingState<Int, ResultsItemNowPlaying>): Int? {
        return state.anchorPosition?.let { position ->
            val anchorPage = state.closestPageToPosition(position)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultsItemNowPlaying> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getNowPlaying(page = page)

            LoadResult.Page(
                data = responseData.results,
                prevKey = if (page == 1) null else page -1,
                nextKey = if (responseData.results.isNullOrEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}
