package space.iqbalsyafiq.mobilenewsapp.model.response.sources


import com.google.gson.annotations.SerializedName

data class SourceResponse(
    @SerializedName("sources")
    val sources: List<Source> = listOf(),
    @SerializedName("status")
    val status: String = ""
)