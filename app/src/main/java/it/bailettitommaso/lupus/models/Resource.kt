package it.bailettitommaso.lupus.models

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val statusCode: Int? = null
) {
    class Success<T>(data: T?) : Resource<T>(data = data)
    class Loading<T> : Resource<T>()
    class Error<T>(errorMessage: String, errorCode: Int? = null) :
        Resource<T>(message = errorMessage, statusCode = errorCode)
}