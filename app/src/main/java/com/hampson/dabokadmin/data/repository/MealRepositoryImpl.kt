package com.hampson.dabokadmin.data.repository

import android.app.Application
import com.hampson.dabokadmin.R
import com.hampson.dabokadmin.data.api.MealApi
import com.hampson.dabokadmin.data.dto.MealRegistrationRequest
import com.hampson.dabokadmin.data.mapper.toMeal
import com.hampson.dabokadmin.data.mapper.toMeals
import com.hampson.dabokadmin.domain.model.Meal
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

    override suspend fun getMealResult(
        date: String
    ): Flow<Result<Meal>> {
        return flow {
            emit(Result.Loading(true))

            val remoteMealResultDto = try {
                mealApi.getMeal(date = date)
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

            remoteMealResultDto.let { mealResultDto ->
                mealResultDto.let { mealDto ->
                    emit(
                        Result.Success(
                            Meal(
                                date = mealDto.payload.date ?: "",
                                menuList = mealDto.payload.menuList ?: listOf()
                            )
                        )
                    )
                    emit(Result.Loading(false))
                    return@flow
                }
            }

            emit(Result.Error(application.getString(R.string.can_t_get_result)))
            emit(Result.Loading(false))
        }
    }

    override suspend fun getMealsResult(
        date: String,
        size: Int
    ): Flow<Result<List<Meal>>> {
        return flow {
            emit(Result.Loading(true))

            val remoteMealsResultDto = try {
                mealApi.getMeals(
                    date = date,
                    size = size
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

            remoteMealsResultDto.let { mealsResultDto ->
                mealsResultDto.let { mealsDto ->
                    emit(Result.Success(mealsDto.payload.data?.toMeals()))
                    emit(Result.Loading(false))
                    return@flow
                }
            }

            emit(Result.Error(application.getString(R.string.can_t_get_result)))
            emit(Result.Loading(false))
        }
    }

    override suspend fun deleteMeal(date: String): Flow<Result<Unit>> {
        return flow {
            emit(Result.Loading(true))

            val remoteMealsRegistrationDto = try {
                mealApi.deleteMeal(
                    date = date
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

            remoteMealsRegistrationDto.let { mealDto ->
                mealDto.let {
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


























