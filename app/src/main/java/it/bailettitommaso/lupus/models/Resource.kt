package it.bailettitommaso.lupus.models

/**
 * Class that handles the Resource request to the API
 */
sealed class Resource<T>(
    val data: T? = null, val error: ErrorBag? = null, val status: Int? = null
) {
    class Success<T>(data: T, status: Int? = 200) : Resource<T>(data = data, status = status)
    class Loading<T> : Resource<T>()
    class Error<T>(error: ErrorBag?, status: Int) : Resource<T>(error = error, status = status)
}