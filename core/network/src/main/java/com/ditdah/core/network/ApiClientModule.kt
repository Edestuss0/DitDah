package com.ditdah.core.network

import com.ditdah.core.config.API_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.plugins.plugin
import io.ktor.client.request.post
import io.ktor.http.encodedPath
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiClientModule {

    @Provides
    @Singleton
    fun providesApiClient(
        sessionManager: SessionManager,
        cookiesStorage: PersistentCookiesStorage
    ): HttpClient {
        val client = HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    explicitNulls = false
                })
            }
            install(HttpCookies) {
                storage = cookiesStorage
            }
            expectSuccess = false
        }

        client.plugin(HttpSend).intercept { request ->
            val originalResponse = execute(request)

            val is401 = originalResponse.response.status.value == 401
            val isRefreshRequest = request.url.encodedPath.endsWith("/refresh")

            if (isRefreshRequest) {
                return@intercept originalResponse
            }

            if (is401 && !isRefreshRequest) {
                val refreshResponse = client.post("$API_URL/refresh")

                if (refreshResponse.status.isSuccess()) {
                    return@intercept execute(request)
                } else {
                    sessionManager.forceLogout()
                }
            }

            originalResponse
        }

        return client
    }
}
