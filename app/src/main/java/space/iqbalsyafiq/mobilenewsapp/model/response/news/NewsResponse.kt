package space.iqbalsyafiq.mobilenewsapp.model.response.news


import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("articles")
    val articles: List<Article> = listOf(),

    @SerializedName("status")
    val status: String = "",

    @SerializedName("totalResults")
    val totalResults: Int = 0
)