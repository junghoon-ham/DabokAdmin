package com.hampson.dabokadmin.domain.use_case

import com.hampson.dabokadmin.domain.model.Menu
import com.hampson.dabokadmin.domain.repository.MenuRepository
import com.hampson.dabokadmin.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetMenuUseCase @Inject constructor(
    private val repository: MenuRepository
) {
    suspend operator fun invoke(menuId: Long): Flow<Result<Menu>> {
        return repository.getMenuResult(menuId).flowOn(Dispatchers.Default)
    }
}