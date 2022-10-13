package it.bailettitommaso.lupus.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import it.bailettitommaso.lupus.models.LupusRepository
import it.bailettitommaso.lupus.models.Resource
import it.bailettitommaso.lupus.models.User
import javax.inject.Inject

@HiltViewModel
class SelfUserViewModel @Inject constructor(repository: LupusRepository) : ViewModel() {
    private val selfUser: LiveData<Resource<User>> = liveData {
        emit(Resource.Loading())
        val data = repository.getSelfUser()
        emit(data)
    }

    fun getSelfUser(): LiveData<Resource<User>> {
        return selfUser
    }
}