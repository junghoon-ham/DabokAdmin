package com.hampson.dabokadmin.di

import com.hampson.dabokadmin.data.repository.CategoryRepositoryImpl
import com.hampson.dabokadmin.data.repository.MealRepositoryImpl
import com.hampson.dabokadmin.data.repository.MenuRepositoryImpl
import com.hampson.dabokadmin.domain.repository.CategoryRepository
import com.hampson.dabokadmin.domain.repository.MealRepository
import com.hampson.dabokadmin.domain.repository.MenuRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMenuRepository(
        menuRepositoryImpl: MenuRepositoryImpl
    ): MenuRepository

    @Binds
    @Singleton
    abstract fun bindCategoryRepository(
        categoryRepositoryImpl: CategoryRepositoryImpl
    ): CategoryRepository

    @Binds
    @Singleton
    abstract fun bindMealRepository(
        mealRepository: MealRepositoryImpl
    ): MealRepository
}