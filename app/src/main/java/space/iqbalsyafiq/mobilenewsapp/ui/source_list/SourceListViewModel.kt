package space.iqbalsyafiq.mobilenewsapp.ui.source_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.Dispatchers
import space.iqbalsyafiq.mobilenewsapp.model.response.sources.Source

class SourceListViewModel(
    private val sourceListRepository: SourceListRepository
) : ViewModel() {
    fun loadSources(
        query: String = "",
        category: String
    ): LiveData<PagingData<Source>> = sourceListRepository.loadSourceList(
        query, category
    ).asLiveData(Dispatchers.IO).cachedIn(viewModelScope)
}