package space.iqbalsyafiq.mobilenewsapp.di

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import space.iqbalsyafiq.mobilenewsapp.BuildConfig
import space.iqbalsyafiq.mobilenewsapp.model.ApiService
import space.iqbalsyafiq.mobilenewsapp.ui.article_list.ArticleListRepository
import space.iqbalsyafiq.mobilenewsapp.ui.article_list.ArticleListViewModel
import space.iqbalsyafiq.mobilenewsapp.ui.source_list.SourceListRepository
import space.iqbalsyafiq.mobilenewsapp.ui.source_list.SourceListViewModel
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        val loggingLevel = if (
            BuildConfig.DEBUG
        ) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(loggingLevel))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()

        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    factory { SourceListRepository(get()) }
    factory { ArticleListRepository(get()) }
}

val viewModelModule = module {
    viewModel { SourceListViewModel(get()) }
    viewModel { ArticleListViewModel(get()) }
}