package com.vioside.viofoundation.sample.di

import com.vioside.foundation.network.helpers.AuthorizationInterceptor
import com.vioside.foundation.network.respositories.AuthenticationRepository
import com.vioside.viofoundation.sample.models.AuthenticationResponse
import com.vioside.viofoundation.sample.repositories.AuthenticationDataSourceImpl
import com.vioside.viofoundation.sample.repositories.RemoteRepository
import com.vioside.viofoundation.sample.services.AuthenticationAuthorizedApi
import com.vioside.viofoundation.sample.services.AuthenticationOpenApi
import com.vioside.viofoundation.sample.services.AuthorizedApi
import com.vioside.viofoundation.sample.services.OpenApi
import com.vioside.viofoundation.sample.viewModels.SampleViewModel
import okhttp3.Interceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule: Module = module {

    viewModel { SampleViewModel(get(), get(), get()) }

    single { AuthenticationRepository(AuthenticationDataSourceImpl(get(), get())) as AuthenticationRepository<AuthenticationResponse, AuthenticationResponse> }
    single { RemoteRepository(get()) }

    single(qualifier = named("apiUrl")) { "https://test.com/api/" }

    factory { get<Retrofit>(qualifier = named("OpenRetrofit")).create(OpenApi::class.java) }
    factory { get<Retrofit>(qualifier = named("AuthorizedRetrofit")).create(AuthorizedApi::class.java) }
    factory { get<Retrofit>(qualifier = named("AuthenticationOpenRetrofit")).create(
        AuthenticationOpenApi::class.java) }
    factory { get<Retrofit>(qualifier = named("AuthenticationAuthorizedRetrofit")).create(AuthenticationAuthorizedApi::class.java) }

    // OPEN Interceptor
    factory<Interceptor> (qualifier = named("OpenInterceptor")) {
        AuthorizationInterceptor<AuthenticationResponse, AuthenticationResponse>(authorized = false)
    }

    // Authorized Interceptor
    factory<Interceptor> (qualifier = named("AuthorizedInterceptor")) {
        AuthorizationInterceptor<AuthenticationResponse, AuthenticationResponse>(authorized = true)
    }


}
