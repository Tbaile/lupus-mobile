package it.bailettitommaso.lupus.di

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
    fun provideLupusService(): LupusService {
        return LupusService.create()
    }
}