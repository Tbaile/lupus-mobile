package it.bailettitommaso.lupus.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import it.bailettitommaso.lupus.api.LupusService

@Module
@InstallIn(ViewModelComponent::class)
class ServiceModule {

    @Provides
    fun provideLupusService(dataStore: DataStore<Preferences>): LupusService {
        return LupusService.create(dataStore)
    }
}