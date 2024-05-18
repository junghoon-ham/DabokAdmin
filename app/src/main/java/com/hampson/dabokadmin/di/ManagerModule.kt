package com.hampson.dabokadmin.di

import com.hampson.dabokadmin.data.manager.LocalUserManagerImpl
import com.hampson.dabokadmin.domain.manager.LocalUserManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ManagerModule {

    @Binds
    @Singleton
    abstract fun bindLocalUserManager(
        localUserManager: LocalUserManagerImpl
    ): LocalUserManager
}