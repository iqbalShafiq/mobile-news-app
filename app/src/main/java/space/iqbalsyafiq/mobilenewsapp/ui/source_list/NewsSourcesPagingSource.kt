package space.iqbalsyafiq.mobilenewsapp.ui.source_list

import androidx.paging.PagingSource
import androidx.paging.PagingState
import space.iqbalsyafiq.mobilenewsapp.model.ApiService
import space.iqbalsyafiq.mobilenewsapp.model.response.sources.Source

class NewsSourcesPagingSource(
    private val apiService: ApiService,
    private val query: String,
    private val category: String
) : PagingSource<Int, Source>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Source> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getNewsSources(
                query,
                category,
                position,
                params.loadSize
            ).sources.filter {
                it.name.lowercase().contains(query.lowercase())
            }

            LoadResult.Page(
                data = responseData,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = null
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Source>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}