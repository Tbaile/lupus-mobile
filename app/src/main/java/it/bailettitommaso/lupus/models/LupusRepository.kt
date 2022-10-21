package it.bailettitommaso.lupus.models

import it.bailettitommaso.lupus.api.LupusService
import it.bailettitommaso.lupus.models.requests.PostLogin
import okhttp3.ResponseBody
import javax.inject.Inject

class LupusRepository @Inject constructor(private val service: LupusService) : BaseRepository() {
    suspend fun getSelfUser(): Resource<User> = apiCall { service.getUserSelf() }
    suspend fun login(postLogin: PostLogin): Resource<ResponseBody> =
        apiCall { service.login(postLogin) }
}