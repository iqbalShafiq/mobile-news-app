package space.iqbalsyafiq.mobilenewsapp.model

import retrofit2.http.GET
import retrofit2.http.Query
import space.iqbalsyafiq.mobilenewsapp.model.response.news.NewsResponse
import space.iqbalsyafiq.mobilenewsapp.model.response.sources.SourceResponse

interface ApiService {
    @GET("top-headlines/sources")
    suspend fun getNewsSources(
        @Query("q") query: String,
        @Query("category") category: String,
        @Query("page") page: Int,
        @Query("pageSize") size: Int
    ): SourceResponse

    @GET("top-headlines")
    suspend fun getNewsBySource(
        @Query("q") query: String,
        @Query("sources") sources: String,
        @Query("page") page: Int,
        @Query("pageSize") size: Int
    ): NewsResponse
}