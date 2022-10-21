package it.bailettitommaso.lupus.viewmodels

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import it.bailettitommaso.lupus.models.LupusRepository
import it.bailettitommaso.lupus.models.Resource
import it.bailettitommaso.lupus.models.requests.PostLogin
import okhttp3.ResponseBody
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LupusRepository,
    private val datastore: DataStore<Preferences>
) : ViewModel() {
    fun login(email: String, password: String): LiveData<Resource<ResponseBody>> {
        return liveData {
            emit(Resource.Loading())
            val data = repository.login(PostLogin(email, password))
            emit(data)
        }
    }
}