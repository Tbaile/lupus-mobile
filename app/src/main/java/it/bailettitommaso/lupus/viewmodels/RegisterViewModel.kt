package it.bailettitommaso.lupus.viewmodels

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import it.bailettitommaso.lupus.models.LupusRepository
import it.bailettitommaso.lupus.models.Resource
import it.bailettitommaso.lupus.models.requests.PostRegister
import it.bailettitommaso.lupus.models.responses.DataWrapResponse
import it.bailettitommaso.lupus.models.responses.TokenResponse
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: LupusRepository, private val datastore: DataStore<Preferences>
) : ViewModel() {
    fun register(
        name: String, email: String, password: String
    ): LiveData<Resource<DataWrapResponse<TokenResponse>>> {
        return liveData {
            emit(Resource.Loading())
            val resource = repository.register(PostRegister(name, email, password))
            emit(resource)
            if (resource is Resource.Success) {
                datastore.edit { preferences ->
                    preferences[stringPreferencesKey("user_data")] = resource.data!!.data.token
                }
            }
        }
    }
}