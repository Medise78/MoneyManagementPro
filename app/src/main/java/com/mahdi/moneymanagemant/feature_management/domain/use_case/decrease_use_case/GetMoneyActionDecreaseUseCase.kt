package com.mahdi.moneymanagemant.feature_management.domain.use_case.decrease_use_case

import com.mahdi.moneymanagemant.feature_management.domain.model.money_decrease_model.MoneyManagementDecrease
import com.mahdi.moneymanagemant.feature_management.domain.repository.decrease_repository.MoneyManagementDecreaseRepository
import com.mahdi.moneymanagemant.feature_management.domain.util.MoneyManagementOrder
import com.mahdi.moneymanagemant.feature_management.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetMoneyActionDecreaseUseCase(
     private val repository: MoneyManagementDecreaseRepository
) {
     suspend operator fun invoke(id:Int):MoneyManagementDecrease?{
          return repository.getMoneyActionById(id)
     }
}