package it.bailettitommaso.lupus.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import it.bailettitommaso.lupus.api.LupusService
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ServiceModule {
    @Singleton
    @Provides
    fun provideLupusService(dataStore: DataStore<Preferences>): LupusService {
        return LupusService.create(dataStore)
    }
}