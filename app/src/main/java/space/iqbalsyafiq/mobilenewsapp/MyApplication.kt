package space.iqbalsyafiq.mobilenewsapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import space.iqbalsyafiq.mobilenewsapp.di.networkModule
import space.iqbalsyafiq.mobilenewsapp.di.repositoryModule
import space.iqbalsyafiq.mobilenewsapp.di.viewModelModule

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    networkModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }
    }
}