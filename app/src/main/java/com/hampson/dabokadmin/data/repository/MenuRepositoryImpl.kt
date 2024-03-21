package com.hampson.dabokadmin.data.repository

import android.app.Application
import com.hampson.dabokadmin.R
import com.hampson.dabokadmin.data.api.MenuApi
import com.hampson.dabokadmin.data.mapper.toMenu
import com.hampson.dabokadmin.domain.model.Menu
import com.hampson.dabokadmin.domain.repository.MenuRepository
import com.hampson.dabokadmin.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MenuRepositoryImpl @Inject constructor(
    private val menuApi: MenuApi,
    private val application: Application
) : MenuRepository {

    override suspend fun getMenuResult(
        menuId: Long
    ): Flow<Result<Menu>> {
        return flow {
            emit(Result.Loading(true))

            val remoteMenuResultDto = try {
                menuApi.getMenuId(menuId)
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

            remoteMenuResultDto.let { menuResultDto ->
                menuResultDto.let { menuDto ->
                    emit(Result.Success(menuDto.toMenu()))
                    emit(Result.Loading(false))
                    return@flow
                }
            }

            emit(Result.Error(application.getString(R.string.can_t_get_result)))
            emit(Result.Loading(false))
        }
    }
}


























