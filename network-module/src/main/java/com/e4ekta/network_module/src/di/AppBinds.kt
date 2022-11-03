package com.e4ekta.network_module.src.di

import com.e4ekta.network_module.src.api.RemoteDataSource
import com.e4ekta.network_module.src.api.ApiHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppBinds {
    @Binds
    @Singleton
   abstract fun bindApiHelper(apiHelper: ApiHelperImpl): RemoteDataSource

}