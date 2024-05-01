package com.hampson.dabokadmin.data.repository

import android.app.Application
import com.hampson.dabokadmin.R
import com.hampson.dabokadmin.data.api.CategoryApi
import com.hampson.dabokadmin.data.mapper.toCategories
import com.hampson.dabokadmin.domain.model.Category
import com.hampson.dabokadmin.domain.repository.CategoryRepository
import com.hampson.dabokadmin.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryApi: CategoryApi,
    private val application: Application
) : CategoryRepository {

    override suspend fun getCategoriesResult(): Flow<Result<List<Category>>> {
        return flow {
            emit(Result.Loading(true))

            val remoteCategoriesResultDto = try {
                categoryApi.getCategories()
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Result.Error(application.getString(R.string.can_t_get_result)))
                emit(Result.Loading(false))
                return@flow
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Result.Error(application.getString(R.string.can_t_get_result)))
                emit(Result.Loading(false))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(application.getString(R.string.can_t_get_result)))
                emit(Result.Loading(false))
                return@flow
            }

            remoteCategoriesResultDto.let { categoriesResultDto ->
                categoriesResultDto.let { categoriesDto ->
                    emit(Result.Success(categoriesDto.payload.data?.toCategories()))
                    emit(Result.Loading(false))
                    return@flow
                }
            }

            emit(Result.Error(application.getString(R.string.can_t_get_result)))
            emit(Result.Loading(false))
        }
    }
}


























