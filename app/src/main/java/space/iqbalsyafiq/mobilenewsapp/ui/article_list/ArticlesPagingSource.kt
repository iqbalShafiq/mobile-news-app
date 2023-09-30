package space.iqbalsyafiq.mobilenewsapp.ui.article_list

import androidx.paging.PagingSource
import androidx.paging.PagingState
import space.iqbalsyafiq.mobilenewsapp.model.ApiService
import space.iqbalsyafiq.mobilenewsapp.model.response.news.Article

class ArticlesPagingSource(
    private val apiService: ApiService,
    private val query: String,
    private val source: String
) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getNewsBySource(
                query,
                source,
                position,
                params.loadSize
            ).articles

            LoadResult.Page(
                data = responseData,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}