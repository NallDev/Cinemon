package com.afrinaldi.cinemon.core.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.afrinaldi.cinemon.core.remote.network.ApiService
import com.afrinaldi.cinemon.core.remote.response.ResultsItemPopular
import java.lang.Exception

class PopularPagingSource (private val apiService: ApiService) : PagingSource<Int, ResultsItemPopular>() {

    override fun getRefreshKey(state: PagingState<Int, ResultsItemPopular>): Int? {
        return state.anchorPosition?.let { position ->
            val anchorPage = state.closestPageToPosition(position)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultsItemPopular> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getPopular(page = page)

            LoadResult.Page(
                data = responseData.results,
                prevKey = if (page == 1) null else page -1,
                nextKey = if (responseData.results.isEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}
