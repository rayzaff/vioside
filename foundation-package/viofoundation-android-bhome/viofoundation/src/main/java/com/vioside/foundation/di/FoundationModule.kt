package com.vioside.foundation.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.vioside.foundation.network.helpers.AuthorizationInterceptor
import com.vioside.foundation.network.services.ApiEndpointService
import com.vioside.foundation.network.services.AuthenticationTokenService
import com.vioside.foundation.network.services.FileService
import com.vioside.foundation.network.services.StreamingApi
import com.vioside.foundation.services.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

var foundationModule: Module = module {

    val connectionTimeout: Long = 30

    single { ApiEndpointService() }
    single { AuthenticationTokenService() }
    single { FileService() }
    single { UserSharedPreferences(androidContext(), "User_Preferences") as UserStorage }
    single { ClipboardServiceImpl(androidContext()) as ClipboardService }
    single { ShareServiceImpl(androidContext()) as ShareService }
    single { AndroidOrientationService(androidContext()) as OrientationService }
    single { AndroidSoundService(androidContext()) as SoundService }

    factory(qualifier = named("OpenRetrofit")) {
        Retrofit.Builder()
            .client(get(qualifier = named("OpenClient")))
            .baseUrl(get(qualifier = named("apiUrl")) as String)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    factory(qualifier = named("AuthorizedRetrofit")) {
        get<Retrofit>(qualifier = named("OpenRetrofit")).newBuilder()
            .client(get(qualifier = named("AuthorizedClient")))
            .build()
    }

    factory(qualifier = named("StreamingRetrofit")) {
        get<Retrofit>(qualifier = named("OpenRetrofit")).newBuilder()
            .client(get(qualifier = named("OpenClient")))
            .build()
    }


    factory(qualifier = named("AuthenticationOpenRetrofit")) {
        get<Retrofit>(qualifier = named("OpenRetrofit")).newBuilder()
            .client(get(qualifier = named("OpenClient")))
            .build()
    }

    factory(qualifier = named("AuthenticationAuthorizedRetrofit")) {
        get<Retrofit>(qualifier = named("OpenRetrofit")).newBuilder()
            .client(get(qualifier = named("AuthorizedClient")))
            .build()
    }

    // OPEN httpClient
    factory (qualifier = named("OpenClient")) {
        OkHttpClient.Builder()
            .connectTimeout(connectionTimeout, TimeUnit.SECONDS)
            .readTimeout(connectionTimeout, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(get(qualifier = named("OpenInterceptor")) as Interceptor)
            .build()
    }

    // Authorized httpClient
    factory (qualifier = named("AuthorizedClient")) {
        OkHttpClient.Builder()
            .connectTimeout(connectionTimeout, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .readTimeout(connectionTimeout, TimeUnit.SECONDS)
            .addInterceptor(get(qualifier = named("AuthorizedInterceptor")) as Interceptor)
            .build()
    }

    // Streaming API
    factory { get<Retrofit>(qualifier = named("StreamingRetrofit")).create(StreamingApi::class.java) }

}