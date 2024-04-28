package com.hampson.dabokadmin.domain.use_case.menu

import com.hampson.dabokadmin.domain.model.Menu
import com.hampson.dabokadmin.domain.repository.MenuRepository
import com.hampson.dabokadmin.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetMenusUseCase @Inject constructor(
    private val repository: MenuRepository
) {
    suspend operator fun invoke(typeId: Int): Flow<Result<List<Menu>>> {
        return repository.getMenusResult(typeId).flowOn(Dispatchers.Default)
    }
}