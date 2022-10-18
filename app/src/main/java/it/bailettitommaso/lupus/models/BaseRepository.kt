package it.bailettitommaso.lupus.models

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response

abstract class BaseRepository {
    suspend fun <T> apiCall(apiToCall: suspend () -> Response<T>): Resource<T> {
        return withContext(Dispatchers.Default) {
            try {
                val response = apiToCall()
                if (response.isSuccessful) {
                    Resource.Success(data = response.body()!!)
                } else {
                    Resource.Error(
                        error = parseError(response.errorBody()!!),
                        status = response.code()
                    )
                }
            } catch (e: Exception) {
                Resource.Error(
                    error = ErrorBag(message = e.message ?: "Something went wrong."),
                    status = 400
                )
            }
        }
    }

    private fun parseError(errorBody: ResponseBody?): ErrorBag {
        return errorBody?.string().let {
            Gson().fromJson(it, ErrorBag::class.java)
        }
    }
}