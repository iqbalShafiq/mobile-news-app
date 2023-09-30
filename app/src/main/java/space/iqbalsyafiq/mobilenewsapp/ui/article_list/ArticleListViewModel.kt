package space.iqbalsyafiq.mobilenewsapp.ui.article_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.Dispatchers
import space.iqbalsyafiq.mobilenewsapp.model.response.news.Article

class ArticleListViewModel(
    private val articleListRepository: ArticleListRepository
) : ViewModel() {
    fun loadArticles(
        query: String = "",
        source: String
    ): LiveData<PagingData<Article>> = articleListRepository.loadArticleList(
        query, source
    ).asLiveData(Dispatchers.IO).cachedIn(viewModelScope)
}