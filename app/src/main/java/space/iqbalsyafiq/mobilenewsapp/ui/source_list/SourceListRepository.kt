package space.iqbalsyafiq.mobilenewsapp.ui.source_list

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import space.iqbalsyafiq.mobilenewsapp.model.ApiService
import space.iqbalsyafiq.mobilenewsapp.model.response.sources.Source

class SourceListRepository(
    private val apiService: ApiService
) {
    fun loadSourceList(
        query: String,
        category: String
    ): Flow<PagingData<Source>> {
        return Pager(
            config = PagingConfig(pageSize = 5),
            pagingSourceFactory = {
                NewsSourcesPagingSource(
                    apiService, query, category
                )
            }
        ).flow
    }
}