package com.hampson.dabokadmin.di

import com.hampson.dabokadmin.data.api.MenuApi
import com.hampson.dabokadmin.domain.repository.MenuRepository
import com.hampson.dabokadmin.domain.use_case.GetMenuUseCase
import com.hampson.dabokadmin.domain.use_case.MenuUseCases
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
    fun provideUseCases(repository: MenuRepository): MenuUseCases {
        return MenuUseCases(
            getMenuUseCase = GetMenuUseCase(repository)
        )
    }
}