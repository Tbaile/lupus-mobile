package it.bailettitommaso.lupus.models

import it.bailettitommaso.lupus.api.LupusService
import it.bailettitommaso.lupus.models.requests.PostLogin
import it.bailettitommaso.lupus.models.requests.PostRegister
import it.bailettitommaso.lupus.models.responses.DataWrapResponse
import it.bailettitommaso.lupus.models.responses.TokenResponse
import javax.inject.Inject

class LupusRepository @Inject constructor(private val service: LupusService) : BaseRepository() {
    suspend fun getSelfUser(): Resource<User> = apiCall { service.getUserSelf() }
    suspend fun login(postLogin: PostLogin): Resource<DataWrapResponse<TokenResponse>> =
        apiCall { service.login(postLogin) }

    suspend fun register(postRegister: PostRegister): Resource<DataWrapResponse<TokenResponse>> =
        apiCall { service.register(postRegister) }
}