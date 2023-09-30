package space.iqbalsyafiq.mobilenewsapp.ui.article_list

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.filter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import space.iqbalsyafiq.mobilenewsapp.model.ApiService
import space.iqbalsyafiq.mobilenewsapp.model.response.news.Article

class ArticleListRepository(
    private val apiService: ApiService
) {
    fun loadArticleList(
        query: String,
        source: String
    ): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 5),
            pagingSourceFactory = {
                ArticlesPagingSource(
                    apiService, query, source
                )
            }
        ).flow.map {
            val titleList = mutableListOf<String>()
            it.filter { article ->
                if (titleList.contains(article.title)) {
                    false
                } else {
                    titleList.add(article.title)
                }
            }
        }
    }
}