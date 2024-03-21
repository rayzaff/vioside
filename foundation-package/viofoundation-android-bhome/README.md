# Vioside Network Layer for Android

## Overview

This package provides a number of services, extensions, u base system for navigation graphs with view models, and a base network layer which can be used to provide network support for your app
using Koin and Retrofit.

## Usage

- In your gradle file:

```
android {
    ...
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/vioside/viofoundation-android")

            Properties properties = new Properties()
            properties.load(project.rootProject.file('local.properties').newDataInputStream())
            credentials {
                username = properties.getProperty("gpr.usr") ?: System.getenv("GPR_USER")
                password = properties.getProperty("gpr.key") ?: System.getenv("GPR_API_KEY")
            }
        }
    }
}
```

- Add the username and password to the local.properties

- Add the dependency
```
    implementation "com.vioside:viofoundation:v0.0.4"
```

## Networking

- Extend the `ApiEndpointService` to include your paths

```
enum class ApiPaths: ApiPath {

    // User
    LOGIN { override  fun path(): String { return "/jwt-auth/v1/token" } },
    VALIDATE { override  fun path(): String { return "/jwt-auth/v1/token/validate" } },

}
```

- Create a Retrofit API Interface in your module. One for OpenApi and one for AuthorizedApi

Example:
```
interface OpenApi {

    @POST
    fun loginAsync(
        @Url url: String,
        @Body request: LoginCredentials
    ): Deferred<Response<AuthenticationResponse>>


}
```

### For Authentication Mechanism

- Declare models for the LoginCredentials and AuthenticationReponse using Gson annotations

```
data class AuthenticationResponse(

    @SerializedName("token")
    override val token: String,

    @SerializedName("refreshToken")
    override val refreshToken: String?

): AuthenticationResponseInterface
```

```
data class LoginCredentials(

    @SerializedName("username")
    val username: String,

    @SerializedName("password")
    val password: String

)
```

- Create a Retrofit API Interface in your module. One for AuthenticationApi

Example:
```
interface AuthenticationApi {

    @POST
    fun loginAsync(
        @Url url: String,
        @Body request: LoginCredentials
    ): Deferred<Response<AuthenticationResponse>>

}
```

- Extend AuthenticationDataSource

```
class AuthenticationDataSourceImpl(): KoinComponent, AuthenticationDataSource<AuthenticationResponse> {

    private val authenticationApi: AuthenticationApi by inject()
    private val endPointService: ApiEndpointService by inject()

    override fun authenticateAsync(
        username: String,
        password: String
    ) = authenticationApi.loginAsync(
        endPointService.endpointUrl(ApiPaths.LOGIN),
        LoginCredentials(username, password)
    )

}
```

- Extend AuthenticationRepository to declare your generics for the reponses

```
class AuthenticationRepositoryImpl(
    dataSource: AuthenticationDataSource<AuthenticationResponse>
): AuthenticationRepository<AuthenticationResponse>(dataSource)
```

- Create a Retrofit instance in your Koin Module

```
    single(qualifier = named("apiUrl")) { "https://xxx.kinsta.cloud/wp-json/" }
    single { AuthenticationRepositoryImpl(AuthenticationDataSourceImpl()) as AuthenticationRepository<AuthenticationResponse> }

    factory { get<Retrofit>(qualifier = named("OpenRetrofit")).create(OpenApi::class.java) }
    factory { get<Retrofit>(qualifier = named("AuthorizedRetrofit")).create(AuthorizedApi::class.java) }
    factory { get<Retrofit>(qualifier = named("AuthenticationRetrofit")).create(AuthenticationApi::class.java) }
```

### Usage

- For the authentication, in your module, you can simply inject the repository using `get`

```
    viewModel { SplashViewModel( Dispatchers.Main, get() ) }
```

- Then you can use the suspend functions as you normally would

```
    viewModelScope.launch(dispatchers) {
        try {
            authenticationRepository.authenticate("user", "p4$$")?.let {
                //result
            }
        } catch (e: Exception) {
            /failed
        }
    }
```

- Obviously don't forget the internet permission in your manifest

## Contribution

- To use the sample project, go to `AppModule.kt` and add your baseUrl. Then find `ApiPaths.kt` and enter your paths


- To release a build:
    - Don't forget to add the credentials to your local.properties or env variable to be able to publish the package to GitHub

        ```
        gpr.usr=xxx
        gpr.key=xxx
        ```
  
    - add a tag with your commit
    - in the `viofoundation` module's `build.gradle.kts` add the build number
    - generate the aap by running the gradle command `assemble` on the module
    - publish the build by running the gradle command `publish` on the module