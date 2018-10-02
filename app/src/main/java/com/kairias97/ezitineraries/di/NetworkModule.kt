package com.kairias97.ezitineraries.di

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kairias97.ezitineraries.data.remote.ConnectionVerifier
import com.kairias97.ezitineraries.data.remote.ConnectivityChecker
import com.kairias97.ezitineraries.data.remote.ItinerariesService
import com.kairias97.ezitineraries.data.remote.SuggestionSerializer
import com.kairias97.ezitineraries.model.TouristAttractionSuggestion
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [BuildConfigModule::class, ApplicationModule::class])
class NetworkModule {


    @Provides
    fun providesConnectivityManager(app: Application) : ConnectivityManager {
        return app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
    @Provides
    fun providesConnectivityChecker(connectivityManager: ConnectivityManager) : ConnectivityChecker {
        return ConnectionVerifier(connectivityManager)
    }
    @Singleton
    @Provides
    fun provideLoggingInterceptor(@Named("isDebug") isDebug: Boolean) : HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        if (isDebug) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return interceptor
    }
    @Singleton
    @Provides
    fun provideCache(app: Application) : Cache {
        val cacheSize = (10 * 1024 * 1024).toLong()// 10 MB
        return Cache(File(app.cacheDir, "http-cache"), cacheSize)
    }

    @Singleton
    @Provides
    @Named("CacheInterceptor")
    fun provideNetworkCacheInterceptor() : Interceptor{
        val networkCacheInterceptor = Interceptor { chain ->
            val response = chain.proceed(chain.request())
            var cacheControl = CacheControl.Builder()
                    .maxAge(1, TimeUnit.MINUTES)
                    .build()
            response.newBuilder()
                    .header("Cache-Control", cacheControl.toString())
                    .build()
        }
        return networkCacheInterceptor
    }


    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor,
                            cache : Cache,
                            @Named("CacheInterceptor") networkCacheInterceptor: Interceptor)
            : OkHttpClient {
        var client =  OkHttpClient.Builder()
                .cache(cache)
                .addNetworkInterceptor(networkCacheInterceptor)
                .addInterceptor(interceptor)
                .build()
        client.cache()
        return client
    }

    @Singleton
    @Provides
    fun provideCustomGson() : Gson {

        return GsonBuilder()
                .registerTypeAdapter(TouristAttractionSuggestion::class.java, SuggestionSerializer())
                .create()
    }
    @Singleton
    @Provides
    fun provideGsonConverterFactory(gson : Gson) : Converter.Factory {
        return GsonConverterFactory
                .create(gson)
    }

    @Singleton
    @Provides
    @Named("itinerariesRetrofit")
    fun provideDemoRetrofitInstance(client: OkHttpClient,
                                    @Named("itinerariesAPIHost") hostUrl: String,
                                    converter: Converter.Factory) : Retrofit {
        return Retrofit.Builder()
                .baseUrl(hostUrl)
                .client(client)
                .addConverterFactory(converter)
                .build()
    }
    @Singleton
    @Provides
    fun provideItinerariesService(@Named("itinerariesRetrofit") retrofit: Retrofit) : ItinerariesService {
        return retrofit.create(ItinerariesService::class.java)
    }
}