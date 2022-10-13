package it.bailettitommaso.lupus.models

import it.bailettitommaso.lupus.api.LupusService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class LupusRepository @Inject constructor(private val service: LupusService): BaseRepository() {
    suspend fun getSelfUser(): Resource<User> = apiCall { service.getUserSelf() }
}