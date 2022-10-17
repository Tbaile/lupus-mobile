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
                    val errorMessage = parseError(response.errorBody()!!)
                    Resource.Error(
                        errorMessage = errorMessage?.message ?: "Something went wrong.",
                        errorCode = response.code()
                    )
                }
            } catch (e: Exception) {
                Resource.Error(errorMessage = e.message ?: "Something went wrong.")
            }
        }
    }

    private fun parseError(errorBody: ResponseBody?): ErrorMessage? {
        return try {
            errorBody?.string()?.let {
                Gson().fromJson(it, ErrorMessage::class.java)
            }
        } catch (e: Exception) {
            null
        }
    }
}