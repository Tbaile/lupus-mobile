package it.bailettitommaso.lupus.api

import it.bailettitommaso.lupus.models.User
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface LupusService {

    @GET("/api/user/self")
    suspend fun getUserSelf(): Response<User>

    companion object {

        private const val BASE_URL = "https://lupus.bailettitommaso.it"

        fun create(): LupusService {
            val logger =
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val jsonInterceptor = Interceptor {
                val jsonRequest = it.request().newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .build()
                it.proceed(jsonRequest)
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .addInterceptor(jsonInterceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(LupusService::class.java)
        }
    }
}