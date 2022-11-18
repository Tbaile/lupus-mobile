package it.bailettitommaso.lupus.api

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import it.bailettitommaso.lupus.models.User
import it.bailettitommaso.lupus.models.requests.PostLogin
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface LupusService {

    @GET("/api/user/self")
    suspend fun getUserSelf(): Response<User>

    @POST("/api/user/login")
    suspend fun login(@Body postLogin: PostLogin): Response<ResponseBody>

    companion object {

        private const val BASE_URL = "https://lupus.bailettitommaso.it"

        fun create(dataStore: DataStore<Preferences>): LupusService {
            val logger =
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val userToken = runBlocking {
                dataStore.data.map {
                    it[stringPreferencesKey("user_token")] ?: ""
                }.first()
            }

            val jsonInterceptor = Interceptor {
                val jsonRequest = it.request().newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer $userToken")
                    .build()
                it.proceed(jsonRequest)
            }

            val client =
                OkHttpClient.Builder()
                    .addInterceptor(logger)
                    .addInterceptor(jsonInterceptor)
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .callTimeout(5, TimeUnit.SECONDS)
                    .writeTimeout(5, TimeUnit.SECONDS)
                    .build()

            return Retrofit.Builder().baseUrl(BASE_URL).client(client)
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(LupusService::class.java)
        }
    }
}