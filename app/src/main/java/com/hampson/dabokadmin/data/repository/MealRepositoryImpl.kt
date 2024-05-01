package com.hampson.dabokadmin.data.repository

import android.app.Application
import com.hampson.dabokadmin.R
import com.hampson.dabokadmin.data.api.MealApi
import com.hampson.dabokadmin.data.dto.MealRegistrationRequest
import com.hampson.dabokadmin.domain.repository.MealRepository
import com.hampson.dabokadmin.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MealRepositoryImpl @Inject constructor(
    private val mealApi: MealApi,
    private val application: Application
) : MealRepository {

    override suspend fun registerMeal(
        date: String,
        menuIds: List<Long>
    ): Flow<Result<Unit>> {
        return flow {
            emit(Result.Loading(true))

            val remoteMealsRegistrationDto = try {
                mealApi.registerMeal(
                    MealRegistrationRequest(
                        date = date,
                        menuIdList = menuIds
                    )
                )
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

            remoteMealsRegistrationDto.let { mealRegistrationDto ->
                mealRegistrationDto.let {
                    emit(Result.Success(Unit))
                    emit(Result.Loading(false))
                    return@flow
                }
            }

            emit(Result.Error(application.getString(R.string.can_t_get_result)))
            emit(Result.Loading(false))
        }
    }
}


























