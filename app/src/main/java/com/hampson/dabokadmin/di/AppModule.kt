package com.hampson.dabokadmin.di

import android.content.Context
import com.hampson.dabokadmin.data.api.CategoryApi
import com.hampson.dabokadmin.data.api.MealApi
import com.hampson.dabokadmin.data.api.MenuApi
import com.hampson.dabokadmin.domain.manager.LocalUserManager
import com.hampson.dabokadmin.domain.repository.CategoryRepository
import com.hampson.dabokadmin.domain.repository.MealRepository
import com.hampson.dabokadmin.domain.repository.MenuRepository
import com.hampson.dabokadmin.domain.repository.UserRepository
import com.hampson.dabokadmin.domain.use_case.category.CategoryUseCases
import com.hampson.dabokadmin.domain.use_case.category.GetCategoriesUseCase
import com.hampson.dabokadmin.domain.use_case.meal.GetMealsUseCase
import com.hampson.dabokadmin.domain.use_case.meal.MealUseCases
import com.hampson.dabokadmin.domain.use_case.meal.RegisterMealUseCase
import com.hampson.dabokadmin.domain.use_case.menu.GetMenusUseCase
import com.hampson.dabokadmin.domain.use_case.menu.MenuUseCases
import com.hampson.dabokadmin.domain.use_case.manager.GetThemeUseCase
import com.hampson.dabokadmin.domain.use_case.manager.ManagerUseCases
import com.hampson.dabokadmin.domain.use_case.manager.UpdateThemeUseCase
import com.hampson.dabokadmin.domain.use_case.meal.DeleteMealUseCase
import com.hampson.dabokadmin.domain.use_case.meal.GetMealUseCase
import com.hampson.dabokadmin.domain.use_case.meal.UpdateMealUseCase
import com.hampson.dabokadmin.domain.use_case.user.GetUserInfoUseCase
import com.hampson.dabokadmin.domain.use_case.user.SaveUserInfoUseCase
import com.hampson.dabokadmin.domain.use_case.user.UserInfoUseCases
import com.hampson.dabokadmin.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val interceptor: HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    @Provides
    @Singleton
    fun providesMenuApi() : MenuApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun providesCategoryApi() : CategoryApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun providesMealApi() : MealApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideMenuUseCases(repository: MenuRepository): MenuUseCases {
        return MenuUseCases(
            getMenusUseCase = GetMenusUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideCategoryUseCases(repository: CategoryRepository): CategoryUseCases {
        return CategoryUseCases(
            getCategoriesUseCase = GetCategoriesUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideMealUseCases(repository: MealRepository): MealUseCases {
        return MealUseCases(
            registerMealUseCase = RegisterMealUseCase(repository),
            updateMealUseCase = UpdateMealUseCase(repository),
            getMealUseCase = GetMealUseCase(repository),
            getMealsUseCase = GetMealsUseCase(repository),
            deleteMealUseCase = DeleteMealUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideManagerUseCases(manager: LocalUserManager): ManagerUseCases {
        return ManagerUseCases(
            getThemeUseCase = GetThemeUseCase(manager),
            updateThemeUseCase = UpdateThemeUseCase(manager)
        )
    }

    @Provides
    @Singleton
    fun provideUserInfoUseCases(repository: UserRepository): UserInfoUseCases {
        return UserInfoUseCases(
            getUserInfoUseCase = GetUserInfoUseCase(repository),
            saveUserInfoUseCase = SaveUserInfoUseCase(repository)
        )
    }
}